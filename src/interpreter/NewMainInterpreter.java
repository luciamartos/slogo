package interpreter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Queue;
import java.util.ResourceBundle;


import gui.SlogoCommandInterpreter;
import regularExpression.ProgramParser;

public class NewMainInterpreter implements SlogoCommandInterpreter {
	
	private final String DEFAULT_RESOURCE_LANGUAGE = "resources/languages/";
	private final String DEFAULT_RESOURCE_PACKAGE = "resources/properties/";
	private final String PROPERTIES_TITLE = "Interpreter";
	private final String NONINPUT_TITLE = "NonInputCommand";
	private final String UNARY_TITLE = "UnaryCommand";
	private final String BINARY_TITLE = "BinaryCommand";
	private final double erroneousReturnValue = Double.NaN;
	private String[] languages = {"English", "Syntax"};  //default language is English
	
	private ProgramParser lang;
	private SlogoUpdate model;
	private List<SubInterpreter> listOfSubInterpreters;
	private TurtleStateDataSource stateDatasource;
	private TurtleStateUpdater stateUpdater;
	private UserVariablesDataSource varDataSource;
	private ErrorPresenter errorPresenter;
	private ResourceBundle rb;
	private Queue<String[]> listQueue;
	private int currSearchIndex;
	
	private int repCount;
	
	public NewMainInterpreter(){
		rb = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+PROPERTIES_TITLE);
		listQueue = new LinkedList<String[]>();
		listOfSubInterpreters = createListOfInterpreters();
	}
	
	/**
	 * Overload of parseInput() method that enables a global interpreter for multiple turtles
	 */
 	public void parseInput(String input, TurtleStateDataSource stateDataSource, TurtleStateUpdater stateUpdater, UserVariablesDataSource varDataSource, ErrorPresenter errorPresenter) throws ClassNotFoundException, NoSuchMethodException, 
 	SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, 
 	InvocationTargetException{
 		this.stateDatasource = stateDataSource;
 		this.stateUpdater = stateUpdater;
 		this.varDataSource = varDataSource;
 		this.errorPresenter = errorPresenter;
 		this.parseInput(input);
 	}
	
	public void parseInput(String input) throws ClassNotFoundException, NoSuchMethodException, 
			SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException{
		model = new SlogoUpdate(stateDatasource);
		String[] split = input.split("\\s+");
		lang = new ProgramParser();
		lang = addLanguagePatterns(lang);	
		interpretCommand(split, 0);   //first search(non-recursive) begins at index 0;
	}
	
	private double interpretCommand(String[] input, int searchStartIndex) throws ClassNotFoundException, NoSuchMethodException, 
	SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		String[] parsed = createParsedArray(input, lang);
		String keyword = parsed[searchStartIndex].toLowerCase();

		//scan for list first before anything else
		searchForList(input, parsed);
		
		double returnValue = erroneousReturnValue; //initialized as an erroneous number
		double[] param = createParams(input, keyword, searchStartIndex);
		
		for(SubInterpreter elem: listOfSubInterpreters){
			if(elem.canHandle(keyword)){	
				returnValue = elem.handle(input, keyword, param, searchStartIndex);
			}
		}
		
//		else if(decideCommand.isControl(keyword)){
//			returnValue = interpretControl(input, parsed, keyword, searchStartIndex);
//		}
		
		//if current keyword is a variable
		if(keyword.equalsIgnoreCase(rb.getString("VariableLabel"))){
			String newKeyword = input[searchStartIndex].toLowerCase();
			if(varDataSource.getUserDefinedVariable(newKeyword) != null){
				returnValue = Double.parseDouble(varDataSource.getUserDefinedVariable(newKeyword));
			}
			else returnValue = 0;  //By definition, unassigned variables have a value of 0.
		}	
			
		return getValueOfCommand(input, searchStartIndex, parsed, returnValue);
	}

	private double getValueOfCommand(String[] input, int searchStartIndex, String[] parsed, double returnValue) throws ClassNotFoundException, NoSuchMethodException,
			InstantiationException, IllegalAccessException, InvocationTargetException {
		
		if(returnValue != erroneousReturnValue){
			if(hasRemainingActionable(parsed, currSearchIndex) < 0){
				stateUpdater.applyChanges(model);
				return returnValue;
			}
			else{
				stateUpdater.applyChanges(model);
				return interpretCommand(input, currSearchIndex+1);
			}
		}
		else{
			String errorMessage = "Invalid argument detected: '"+input[searchStartIndex]+"' is not a valid command!";
			System.out.println(errorMessage);
			errorPresenter.presentError(errorMessage);
			throw new IllegalArgumentException();
		}
	}

	private void searchForList(String[] input, String[] parsed) {
		for(int i=0;i<parsed.length;i++){
			String keyword = parsed[i];
			if(keyword.equalsIgnoreCase(rb.getString("ListStartLabel"))){
				int temp = i+1;
				int listStartIndex = temp;
				while(!parsed[temp].equalsIgnoreCase(rb.getString("ListEndLabel"))){	
					temp++;
				}
				int listEndIndex = temp;  //temp is currently at index of ']'.
				listQueue.add(Arrays.copyOfRange(input, listStartIndex, listEndIndex));
			}
		}	
	}
	
	private double interpretControl(String[] input, String[] parsed, String keyword, int searchStartIndex) throws ClassNotFoundException, 
	NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException{
		double[] param;
		if(keyword.equalsIgnoreCase(rb.getString("makevar"))){
			param = parseParam(input, searchStartIndex+2, 1);
			currSearchIndex = searchStartIndex+2;
			if(parsed[searchStartIndex+1].equalsIgnoreCase(rb.getString("VariableLabel"))){
				varDataSource.addUserDefinedVariable(input[searchStartIndex+1], Double.toString(param[0]));
				System.out.println(param[0]);
				return param[0];
			}
			else{
				String errorMessage = "Illegal Variable detected: '" + input[searchStartIndex+1] + "' is not a variable!";
				System.out.println(errorMessage);
				errorPresenter.presentError(errorMessage);
				throw new IllegalArgumentException();
			}
		}
		else if(keyword.equalsIgnoreCase(rb.getString("repeat"))){
			repCount = 0;
			param = parseParam(input, searchStartIndex+1, 1);
			double res = 0;
			String[] temp = listQueue.peek();
			for(int i=0;i<param[0];i++){
				res = interpretCommand(temp, 0);
				repCount++;
			}
			listQueue.remove();
			System.out.println(res);
			currSearchIndex = searchStartIndex+2;
			return res;
		}
		
		//TODO: Implement other controls other than set and repeat
		else return 0;
	}
	
	private double[] createParams(String[] input, String keyword, int searchStartIndex) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		double[] param;
		if(determineNumberOfInputs(keyword, NONINPUT_TITLE)){
			param = null;
		}
		else if(determineNumberOfInputs(keyword, UNARY_TITLE)){
			param = parseParam(input, searchStartIndex, 1);
		}
		else if(determineNumberOfInputs(keyword, BINARY_TITLE)){
			param = parseParam(input, searchStartIndex, 2);
		}
		else throw new IllegalArgumentException();
		return param;
	}
	

	private double[] parseParam(String[] input, int startSearchIndex, int numOfParams) throws ClassNotFoundException, 
	NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException{
		double[] res = new double[numOfParams];
		int index=0;
		for(int i=startSearchIndex;i<startSearchIndex+numOfParams;i++){
			if(isDouble(input[i])){
				double temp = Double.parseDouble(input[i]);
				res[index++] = temp;
			}
			else{
				//recursive parsing of input statement
				res[index++] = interpretCommand(input, i);
			}
		}
		return res;
	}
	
	private List<SubInterpreter> createListOfInterpreters(){
		List<SubInterpreter> list = new ArrayList<SubInterpreter>();
		list.add(new TurtleCommandInterpreter(model, stateUpdater));
		list.add(new MathInterpreter());
		list.add(new TurtleQueryInterpreter(model));
		list.add(new BooleanInterpreter());	
//		ResourceBundle reader = 
//				ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+LISTOFSUBINTERPRETER_TITLE);
//		for(String elem: reader.keySet()){
//			Class<?> clazz = Class.forName(reader.getString(elem));
//			Constructor<?> constructor = clazz.getConstructor(SlogoUpdate.class, TurtleStateUpdater.class);
//			SubInterpreter instance = (SubInterpreter)constructor.newInstance(model, stateUpdater);
//			list.add(instance);
//		}		
		return list;
	}
	
	private boolean determineNumberOfInputs(String keyword, String properties){
		ResourceBundle reader = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+properties);
		for(String elem: reader.keySet()){
			if(keyword.equals(reader.getString(elem))){
				return true;
			}
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
	
	private ProgramParser addLanguagePatterns(ProgramParser lang){
		for(String language:languages){
			lang.addPatterns(DEFAULT_RESOURCE_LANGUAGE+language);
		}
        return lang;
	}
	
	private Class[] createDoubleArgs(int num){
		Class[] args = new Class[num];
		for(int i=0;i<args.length;i++){
			args[i] = Double.TYPE;
		}
		return args;
	}

	private boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Returns -1 if no remaining Turtle Commands, otherwise returns index of next Turtle Command
	 */
	private int hasRemainingActionable(String[] parsed, int index){
		int resIndex = -1;
		
		//TODO: Is this the best way to tell TurtleCommand or not?
		TurtleCommandInterpreter interpreter = new TurtleCommandInterpreter(model, stateUpdater);
		if(index == parsed.length || parsed[index].equalsIgnoreCase(rb.getString("ListStartLabel"))) return resIndex;
		for(int i=index+1;i<parsed.length;i++){
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
	
	public void setStateDataSource(TurtleStateDataSource stateDataSource){
		this.stateDatasource = stateDataSource;
	}
	
	public void setStateUpdater(TurtleStateUpdater stateUpdater){
		this.stateUpdater = stateUpdater;
	}
	
	public void setVarDataSource(UserVariablesDataSource varDataSource){
		this.varDataSource = varDataSource;
	}
	
	public void setErrorPresenter(ErrorPresenter p){
		this.errorPresenter = p;
	}
	
	public int getRepCount(){
		return repCount;
	}
}
