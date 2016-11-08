// This entire file is part of my masterpiece.
// Ray Song(ys101)
// Lines 88-112
// This method loops through a collection of SubInterpreters, all of which have the canHandle() and handle() methods.
// because the two methods are abstract methods declared in the SubInterpreter abstract class.
// By looping through each SubInterpreter, the Main Interpreter can tell which SubInterpreter can handle the 
// given keyword, and only then will the SubInterpreter be called to actually handle the keyword.
// I feel that this not only displays my understanding of inheritance and polymorphism, but also does so in a clever
// way - looping through specific instances that all have the same method.

package interpreter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Set;

import gui.SlogoCommandInterpreter;
import regularExpression.ProgramParser;

/**
 * Main Interpreter that ensures parsing of String input commands and calls relevant sub-interpreters whenever necessary.
 * @author Ray Song
 */
public class MainInterpreter implements SlogoCommandInterpreter {
	
	private final String DEFAULT_RESOURCE_LANGUAGE = "resources/languages/";
	private final String DEFAULT_RESOURCE_PACKAGE = "resources/properties/";
	private final String PROPERTIES_TITLE = "Interpreter";
	private final String NONINPUT_TITLE = "NonInputCommand";
	private final String UNARY_TITLE = "UnaryCommand";
	private final String BINARY_TITLE = "BinaryCommand";
	private final String MULTIPLE_INPUT_TITLE = "MultipleInputCommand";
	private final double erroneousReturnValue = Double.NEGATIVE_INFINITY;
	private String[] languages = {"English", "Syntax"};  //default language is English
	
	private ProgramParser lang;
	private SlogoUpdate model;
	private Collection<SubInterpreter> listOfSubInterpreters;
	private BoardStateUpdater boardStateUpdater;
	private TurtleStateDataSource stateDataSource;
	private TurtleStateUpdater turtleStateUpdater;
	private UserVariablesDataSource varDataSource;
	private ErrorPresenter errorPresenter;
	private ResourceBundle rb;
	private Queue<String[]> listQueue;
	
	private int repCount;
	private double singleRetVal;
	
	public MainInterpreter(){
		rb = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+PROPERTIES_TITLE);
	}
	
	public void parseInput(String input) throws ClassNotFoundException, NoSuchMethodException, 
			SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException{
		List<Integer> listOfActiveTurtles = stateDataSource.getActiveTurtleIDs();
		boolean commandContainsAsk = containsAsk(input);
		for(int turtleID: listOfActiveTurtles){
			parseInputForActiveTurtles(input, turtleID);
			if(commandContainsAsk) break;
		}
		if(tellWhileNoActiveTurtles(input)) parseInputForActiveTurtles(input, 0);
	}

	
	private double parseInputForActiveTurtles(String input, int turtleID) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		model = new SlogoUpdate(stateDataSource, turtleID);
		String[] split = input.split("\\s+");
		if(split[0].equals("")){
			split = Arrays.copyOfRange(split, 1, split.length);
		}
		lang = addLanguagePatterns();	
		listOfSubInterpreters = createListOfInterpreters();
		return interpretCommand(split, 0);   //first search(non-recursive) begins at index 0;
	}
	
	// This method is part of my masterpiece (lines 88-112)
	// Ray Song(ys101)
	private double interpretCommand(String[] input, int searchStartIndex) throws ClassNotFoundException, NoSuchMethodException, 
	SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		String[] parsed = createParsedArray(input, lang);
		String keyword = parsed[searchStartIndex].toLowerCase();

		listQueue = searchForList(input, parsed); //scan for list first before executing commands
		
		double returnValue = erroneousReturnValue; //return value is initialized as erroneous number
		double[] param = createParams(input, keyword, searchStartIndex);
		
		for(SubInterpreter elem: listOfSubInterpreters){
			if(elem.canHandle(keyword)){	
				if(elem.needList()){
					if(listQueue.isEmpty()) returnValue = presentEmptyListMessage();
					elem.setList(listQueue);
				}
				returnValue = elem.handle(input, keyword, param, searchStartIndex);
				if(elem.getModel() != null) model = elem.getModel(); 
				break;
			}
		}		
		returnValue = handleEdgeCases(input, searchStartIndex, parsed, keyword, returnValue);		
		return getValueOfCommand(input, searchStartIndex, parsed, returnValue);
	}

	private double getValueOfCommand(String[] input, int searchStartIndex, String[] parsed, double returnValue) throws ClassNotFoundException, NoSuchMethodException,
			InstantiationException, IllegalAccessException, InvocationTargetException {
		if(returnValue != erroneousReturnValue){
			int remainingActionableIndex = hasRemainingActionable(parsed, searchStartIndex);
			if(remainingActionableIndex < 0){
				turtleStateUpdater.applyChanges(model);
				System.out.println("Return Value: "+returnValue);
				singleRetVal = returnValue;
				return returnValue;
			}
			else{
				turtleStateUpdater.applyChanges(model);
				System.out.println("Return Value: "+returnValue);
				singleRetVal = returnValue;
				return interpretCommand(input, remainingActionableIndex);
			}
		}
		else{
			printErrorMessage(input, searchStartIndex, returnValue);
			return returnValue;
		}
	}

	private void printErrorMessage(String[] input, int searchStartIndex, double returnValue) {
		String errorMessage = "Invalid argument detected: '"+input[searchStartIndex]+"' is not a valid command!";
		System.out.println(errorMessage);
		errorPresenter.presentError(errorMessage);
		System.out.println("Return Value: "+returnValue);
	}
	

	private double handleEdgeCases(String[] input, int searchStartIndex, String[] parsed, String keyword,
			double returnValue) throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		if(isControl(keyword)) returnValue = interpretControl(input, parsed, keyword, searchStartIndex);
		if(isAskCommand(keyword)) returnValue = handleAsk(keyword);
		
		//if not a valid command, check whether it is pre-defined variable or method
		Set<String> userDefinedVariables = varDataSource.getUserDefinedVariables().keySet();
		String newKeyword = input[searchStartIndex];
		if(keyword.equalsIgnoreCase(rb.getString("VariableLabel"))) returnValue = handleVariable(input, searchStartIndex);
		else if(userDefinedVariables.contains(newKeyword)){
			String newCommand = varDataSource.getUserDefinedVariable(newKeyword);		
			parseInput(newCommand);
			returnValue = singleRetVal;
		}
		return returnValue;
	}

	private double handleAsk(String keyword) throws ClassNotFoundException, NoSuchMethodException,
			InstantiationException, IllegalAccessException, InvocationTargetException {
		double res=erroneousReturnValue; // initialized as erroneous value
		Queue<String[]> newListQueue = new LinkedList<String[]>();
		while(!listQueue.isEmpty()){
			newListQueue.add(listQueue.poll());
		}
		List<Integer> turtlesToAsk = new ArrayList<Integer>();
		if(keyword.equalsIgnoreCase(rb.getString("ask"))) turtlesToAsk = createTurtlesForAsk(turtlesToAsk, newListQueue);
		else turtlesToAsk = createTurtlesForAskWith(turtlesToAsk, newListQueue);
		String[] commands = newListQueue.poll();
		String newCommandAsString = createStringCommandFromArray(commands);	
		for(int turtleID: turtlesToAsk){
			res = parseInputForActiveTurtles(newCommandAsString, turtleID);
		}
		return res;
	}

	private List<Integer> createTurtlesForAskWith(List<Integer> turtlesToAsk, Queue<String[]> newListQueue) throws ClassNotFoundException,
			NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		String[] command = newListQueue.poll();
		double valueOfCommand = interpretCommand(command, 0);
		
		//currently adds value of command as index of turtle
		if(valueOfCommand!=0){
			turtlesToAsk.add((int)valueOfCommand);
		}
		return turtlesToAsk;
	}

	private List<Integer> createTurtlesForAsk(List<Integer> turtlesToAsk, Queue<String[]> newListQueue) {
		String[] turtles = newListQueue.poll();
		for(String turtle: turtles){
			turtlesToAsk.add(Integer.parseInt(turtle));
		}
		return turtlesToAsk;
	}
	
	private double handleVariable(String[] input, int searchStartIndex) {
		double returnValue;
		String newKeyword = input[searchStartIndex].toLowerCase();
		if(newKeyword.equalsIgnoreCase(rb.getString("RepCountLabel"))){
			returnValue = repCount;
		}
		if(varDataSource.getUserDefinedVariable(newKeyword) != null){
			returnValue = Double.parseDouble(varDataSource.getUserDefinedVariable(newKeyword));
		}
		else returnValue = 0;  //By definition, unassigned variables have a value of 0.
		return returnValue;
	}

	private Queue<String[]> searchForList(String[] input, String[] parsed) {
		Queue<String[]> res = new LinkedList<String[]>();
		for(int i=0;i<parsed.length;i++){
			String keyword = parsed[i];
			if(keyword.equalsIgnoreCase(rb.getString("ListStartLabel"))){
				int temp = i+1;
				int listStartIndex = temp;
				while(!parsed[temp].equalsIgnoreCase(rb.getString("ListEndLabel"))){	
					temp++;
				}
				int listEndIndex = temp;  //temp is currently at index of ']'.
				res.add(Arrays.copyOfRange(input, listStartIndex, listEndIndex));
			}
		}	
		return res;
	}
	
	private double interpretControl(String[] input, String[] parsed, String keyword, int searchStartIndex) throws ClassNotFoundException, 
	NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException{
		
		if(keyword.equalsIgnoreCase(rb.getString("if"))) return handleIf(input, searchStartIndex);	
		else if(keyword.equalsIgnoreCase(rb.getString("dotimes")) || keyword.equalsIgnoreCase(rb.getString("for"))){
			errorPresenter.presentError(rb.getString("UnimplementedCommandMessage"));
			return 0;
		}
		
		Class<?>[] args = createArgsForControl();
		Method method = this.getClass().getDeclaredMethod(keyword, args);
		return (double) method.invoke(this, input, parsed, searchStartIndex);
	}

	private Class<?>[] createArgsForControl() {
		Class<?>[] args = new Class[3];
		args[0] = String[].class;
		args[1] = String[].class;
		args[2] = Integer.TYPE;
		return args;
	}

	/**
	 * The following instructions (marked as "never used locally)" are in fact called using Java reflection.
	 */
	private double makeuserinstruction(String[] input, String[] parsed, int index) {
		if(listQueue.size()!=2) return 0;
		String[] varNameInArray = listQueue.poll();
		String varName = "";
		for(String elem: varNameInArray) varName += elem;
		String[] newCommand = listQueue.poll();
		String newCommandAsString = createStringCommandFromArray(newCommand);
		varDataSource.addUserDefinedVariable(varName, newCommandAsString);
		return 1;
	}
	
	private double ifelse(String[] input, String[] parsed, int searchStartIndex) throws ClassNotFoundException,
	NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		double[] param = parseParam(input, searchStartIndex+1, 1);
		double res = 0;
		String[] temp = listQueue.poll();
		if(param[0] != 0){
			res = interpretCommand(temp, 0);
		}
		else{
			temp = listQueue.poll();
			res = interpretCommand(temp, 0);
		}
		return res;
	}
	
	private double handleIf(String[] input, int searchStartIndex) throws ClassNotFoundException,
	NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		double[] param = parseParam(input, searchStartIndex+1, 1);
		double res = 0;
		String[] temp = listQueue.poll();
		if(param[0] != 0){
			res = interpretCommand(temp, 0);
		}
		return res;
	}

	private double repeat(String[] input, String[] parsed, int searchStartIndex) throws ClassNotFoundException,
			NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		double[] param = parseParam(input, searchStartIndex+1, 1);
		repCount = 0;
		double res = 0;
		System.out.println("qwerqwer: " + param[0]);
		String[] temp = listQueue.poll();
		for(int i=0;i<param[0];i++){
			res = interpretCommand(temp, 0);
			repCount++;
		}
		return res;
	}

	private double makevariable(String[] input, String[] parsed, int searchStartIndex) throws ClassNotFoundException,
			NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		double[] param = parseParam(input, searchStartIndex+2, 1);
		if(parsed[searchStartIndex+1].equalsIgnoreCase(rb.getString("VariableLabel"))){
			varDataSource.addUserDefinedVariable(input[searchStartIndex+1], Double.toString(param[0]));
			return param[0];
		}
		else{
			String errorMessage = "Illegal Variable detected: '" + input[searchStartIndex+1] + "' is not a variable!";
			System.out.println(errorMessage);
			errorPresenter.presentError(errorMessage);
			throw new IllegalArgumentException();
		}
	}
	
	private String createStringCommandFromArray(String[] commands) {
		StringBuilder newCommand = new StringBuilder();
		for(String elem: commands){
			newCommand.append(elem);
			newCommand.append(" ");
		}
		String newCommandAsString = newCommand.toString();
		return newCommandAsString;
	}
	
	private double[] createParams(String[] input, String keyword, int searchStartIndex) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		double[] param;
		if(determineNumberOfInputs(keyword, NONINPUT_TITLE)){
			param = null;
		}
		else if(determineNumberOfInputs(keyword, UNARY_TITLE)){
			param = parseParam(input, searchStartIndex+1, 1);			
		}
		else if(determineNumberOfInputs(keyword, BINARY_TITLE)){
			param = parseParam(input, searchStartIndex+1, 2);
		}
		else if(determineNumberOfInputs(keyword, MULTIPLE_INPUT_TITLE)){
			param = parseParam(input, searchStartIndex+1, 3);
		}
		else param = null;
		return param;
	}
	
	/**
	 * This is a key method that accounts for the recursive parsing of the String command.
	 */
	private double[] parseParam(String[] input, int startSearchIndex, int numOfParams) throws ClassNotFoundException, 
	NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException{
		double[] res = new double[numOfParams];
		int index=0;
		for(int i=startSearchIndex;i<startSearchIndex+numOfParams;i++){
			if(i >= input.length){
				System.out.println(rb.getString("AOBMessage"));
				errorPresenter.presentError(rb.getString("AOBMessage"));
				break;
			}
			if(isDouble(input[i])){
				double temp = Double.parseDouble(input[i]);
				res[index++] = temp;
			}
			else res[index++] = interpretCommand(input, i); //recursive parsing of input statement
		}
		return res;
	}
	
	private Collection<SubInterpreter> createListOfInterpreters(){
		Collection<SubInterpreter> list = new ArrayList<SubInterpreter>();
		list.add(new TurtleCommandInterpreter(model, boardStateUpdater));
		list.add(new MathInterpreter());
		list.add(new TurtleQueryInterpreter(model));
		list.add(new BooleanInterpreter());		
		list.add(new DisplayInterpreter(model, boardStateUpdater));
		list.add(new MultipleTurtleInterpreter(model, stateDataSource, turtleStateUpdater, listQueue));
		return list;
	}
	
	private boolean determineNumberOfInputs(String keyword, String properties){
		ResourceBundle reader = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+properties);
		for(String elem: reader.keySet()){
			if(keyword.equalsIgnoreCase(reader.getString(elem))) return true;
		}	
		return false;
	}
	
	private String[] createParsedArray(String[] in, ProgramParser lang){
		String[] out = new String[in.length];
		for(int i=0;i<in.length;i++){
			out[i] = lang.getSymbol(in[i]);
		}
		return out;
	}
	
	private ProgramParser addLanguagePatterns(){
		ProgramParser lang = new ProgramParser();
		for(String language:languages){
			lang.addPatterns(DEFAULT_RESOURCE_LANGUAGE+language);
		}
        return lang;
	}

	private boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	private boolean containsAsk(String input){
		String[] split = input.split("\\s+");
		for(String elem: split){
			if(elem.equalsIgnoreCase(rb.getString("ask"))) return true;
		}
		return false;
	}
	
	//return true only when no active turtles and command has tell
	private boolean tellWhileNoActiveTurtles(String input){
		String[] split = input.split("\\s+");
		if(!stateDataSource.getActiveTurtleIDs().isEmpty()) return false;
		for(String elem: split){
			if(elem.equalsIgnoreCase(rb.getString("tell")))	return true;
		}
		return false;
	}
	
	private boolean isControl(String input){
		return input.equalsIgnoreCase(rb.getString("makevar")) || input.equalsIgnoreCase(rb.getString("repeat")) ||
			input.equalsIgnoreCase(rb.getString("dotimes")) || input.equalsIgnoreCase(rb.getString("for")) ||
			input.equalsIgnoreCase(rb.getString("if")) || input.equalsIgnoreCase(rb.getString("ifelse"))|| 
			input.equalsIgnoreCase(rb.getString("to")) ;
	}
	
	private boolean isAskCommand(String input){
		return input.equalsIgnoreCase(rb.getString("ask")) || input.equalsIgnoreCase(rb.getString("askwith"));
	}
	
	private double presentEmptyListMessage() {
		errorPresenter.presentError(rb.getString("EmptyListMessage"));
		return 0;
	}
	
	/**
	 * Returns -1 if no remaining Turtle Commands, otherwise returns index of next Turtle Command
	 */
	private int hasRemainingActionable(String[] parsed, int index){
		int resIndex = -1;
		
		//Instantiates copy of turtleCommandInterpreter to check keyword
		TurtleCommandInterpreter interpreter = new TurtleCommandInterpreter(model, boardStateUpdater);
		if(index == parsed.length) return resIndex;
		for(int i=index+1;i<parsed.length;i++){
			//If at any point the command includes '[', method returns "There are no further actionables"
			if(parsed[i].equalsIgnoreCase(rb.getString("ListStartLabel"))){
				return resIndex;
			}
			if(interpreter.isBinaryTurtleCommand(parsed[i]) || interpreter.isUnaryTurtleCommand(parsed[i]) ||
				interpreter.isNonInputTurtleCommand(parsed[i])){
				resIndex = i;
				break;
			}
		}
		return resIndex;
	}
	
	public void setLanguage(String language){
		ProgramParser checkLang = new ProgramParser();
		try{
			checkLang.addPatterns(DEFAULT_RESOURCE_LANGUAGE+language);
			String[] temp = {language, "Syntax"};
			languages = temp;
			lang = checkLang;
		} catch(MissingResourceException e){
			String langErrorMessage = language+" is not a valid language!\n Set to English by default.";
			errorPresenter.presentError(langErrorMessage);
			System.out.println(langErrorMessage);
		}

	}
	
	public void setBoardStateUpdater(BoardStateUpdater boardStateUpdater){
		this.boardStateUpdater = boardStateUpdater;
	}
	
	public void setStateDataSource(TurtleStateDataSource stateDataSource){
		this.stateDataSource = stateDataSource;
	}
	
	public void setTurtleStateUpdater(TurtleStateUpdater turtleStateUpdater){
		this.turtleStateUpdater = turtleStateUpdater;
	}
	
	public void setVarDataSource(UserVariablesDataSource varDataSource){
		this.varDataSource = varDataSource;
	}
	
	public void setErrorPresenter(ErrorPresenter p){
		this.errorPresenter = p;
	}
}
