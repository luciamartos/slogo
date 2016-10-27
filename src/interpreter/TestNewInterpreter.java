//package interpreter;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.Arrays;
//import java.util.LinkedList;
//import java.util.Queue;
//import java.util.ResourceBundle;
//
//import regularExpression.ProgramParser;
//
//public class TestNewInterpreter {
//	
//	private final String DEFAULT_RESOURCE_LANGUAGE = "resources/languages/";
//	private final String DEFAULT_RESOURCE_PACKAGE = "resources/properties/";
//	private final String PROPERTIES_TITLE = "Interpreter";
//	private final double erroneousReturnValue = Double.NaN;
//	private String[] languages = {"English", "Syntax"};  //default language is English
//	
//	private ProgramParser lang;
//	private SlogoUpdate model;
//	private TurtleStateDataSource stateDatasource;
//	private TurtleStateUpdater stateUpdater;
//	private UserVariablesDataSource varDataSource;
//	private ErrorPresenter errorPresenter;
//	private ResourceBundle rb;
//	private Queue<String[]> listQueue;
//	private int currSearchIndex;
//	
//	private int repCount;
//	
//	public TestNewInterpreter(){
//		rb = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+PROPERTIES_TITLE);
//		listQueue = new LinkedList();
//	}
//	
//	public void parseInput(String input) throws ClassNotFoundException, NoSuchMethodException, 
//	SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, 
//	InvocationTargetException{
//		model = new SlogoUpdate(stateDatasource);
//		String[] split = input.split("\\s+");
//		lang = new ProgramParser();
//		lang = addLanguagePatterns(lang);	
//
//		interpretCommand(split, 0);   //first search(non-recursive) begins at index 0;
//	}
//	
//	private double interpretCommand(String[] input, int searchStartIndex) throws ClassNotFoundException, NoSuchMethodException, 
//	SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		
//		NewNonControlInterpreter decideCommand = new NewNonControlInterpreter();	
//		String[] parsed = decideCommand.createParsedArray(input, lang);
//		String keyword = parsed[searchStartIndex].toLowerCase();
//
//		//scan for list first before anything else
//		searchForList(input, parsed);
//		
//		double returnValue = erroneousReturnValue; //initialized as an erroneous number
//		
//		if(decideCommand.isControl(keyword)){
//			returnValue = interpretControl(input, parsed, keyword, searchStartIndex);
//		}
//		
//		//if current keyword is a variable
//		else if(keyword.equalsIgnoreCase(rb.getString("VariableLabel"))){
//			String newKeyword = input[searchStartIndex].toLowerCase();
//			if(varDataSource.getUserDefinedVariable(newKeyword) != null){
//				returnValue = Double.parseDouble(varDataSource.getUserDefinedVariable(newKeyword));
//			}
//			else returnValue = 0;  //By definition, unassigned variables have a value of 0.
//		}	
//			
//		return getValueOfCommand(input, searchStartIndex, parsed, decideCommand, returnValue);
//	}
//	
//	private double interpretControl(String[] input, String[] parsed, String keyword, int searchStartIndex) throws ClassNotFoundException, 
//	NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, 
//	IllegalArgumentException, InvocationTargetException{
//		double[] param;
//		
//		//Make, Set
//		if(keyword.equalsIgnoreCase(rb.getString("makevar"))){
//			param = parseParam(input, searchStartIndex+2, 1);
//			currSearchIndex = searchStartIndex+2;
//			if(parsed[searchStartIndex+1].equalsIgnoreCase(rb.getString("VariableLabel"))){
//				varDataSource.addUserDefinedVariable(input[searchStartIndex+1], Double.toString(param[0]));
//				System.out.println(param[0]);
//				return param[0];
//			}
//			else{
//				String errorMessage = "Illegal Variable detected: '" + input[searchStartIndex+1] + "' is not a variable!";
//				System.out.println(errorMessage);
//				errorPresenter.presentError(errorMessage);
//				throw new IllegalArgumentException();
//			}
//		}
//		
//		//Repeat
//		else if(keyword.equalsIgnoreCase(rb.getString("repeat"))){
//			repCount = 0;
//			param = parseParam(input, searchStartIndex+1, 1);
//			double res = 0;
//			String[] temp = listQueue.peek();
//			for(int i=0;i<param[0];i++){
//				res = interpretCommand(temp, 0);
//				repCount++;
//			}
//			listQueue.remove();
//			System.out.println(res);
//			currSearchIndex = searchStartIndex+2;
//			return res;
//		}
//		
//		//TODO: Implement other controls other than set and repeat
//		else return 0;
//	}
//
//	private double getValueOfCommand(String[] input, int searchStartIndex, String[] parsed,
//			NewNonControlInterpreter decideCommand, double returnValue) throws ClassNotFoundException, NoSuchMethodException,
//			InstantiationException, IllegalAccessException, InvocationTargetException {
//		
//		if(returnValue != erroneousReturnValue){
//			if(hasRemainingActionable(parsed, decideCommand, currSearchIndex) < 0){  //has no remaining Actionables
//				stateUpdater.applyChanges(model);
//				return returnValue;
//			}
//			else{
//				stateUpdater.applyChanges(model);
//				return interpretCommand(input, currSearchIndex+1);
//			}
//		}
//		else{
//			String errorMessage = "Invalid argument detected: '"+input[searchStartIndex]+"' is not a valid command!";
//			System.out.println(errorMessage);
//			errorPresenter.presentError(errorMessage);
//			throw new IllegalArgumentException();
//		}
//	}
//	
////	private double[] parseParam(String[] input, int startSearchIndex, int numOfParams) throws ClassNotFoundException, 
////	NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, 
////	IllegalArgumentException, InvocationTargetException{
////		double[] res = new double[numOfParams];
////		int index=0;
////		for(int i=startSearchIndex;i<startSearchIndex+numOfParams;i++){
////			if(isDouble(input[i])){
////				double temp = Double.parseDouble(input[i]);
////				res[index++] = temp;
////			}
////			else{
////				//recursive parsing of input statement
////				res[index++] = interpretCommand(input, i);
////			}
////		}
////		return res;
////	}
//	
//	private void searchForList(String[] input, String[] parsed) {
//		for(int i=0;i<parsed.length;i++){
//			String keyword = parsed[i];
//			if(keyword.equalsIgnoreCase(rb.getString("ListStartLabel"))){
//				int temp = i+1;
//				int listStartIndex = temp;
//				while(!parsed[temp].equalsIgnoreCase(rb.getString("ListEndLabel"))){	
//					temp++;
//				}
//				int listEndIndex = temp;  //temp is currently at index of ']'.
//				listQueue.add(Arrays.copyOfRange(input, listStartIndex, listEndIndex));
//			}
//		}	
//	}
//	
//	private int hasRemainingActionable(String[] parsed, NewNonControlInterpreter interpreter, int index){
//		int resIndex = -1;
//		if(index == parsed.length || parsed[index].equalsIgnoreCase(rb.getString("ListStartLabel"))) return resIndex;
//		for(int i=index+1;i<parsed.length;i++){
//
//			if(interpreter.isBinaryTurtleCommand(parsed[i]) || interpreter.isUnaryTurtleCommand(parsed[i]) ||
//				interpreter.isNonInputTurtleCommand(parsed[i])){
//				resIndex = i;
//				break;
//			}
//		}
//		return resIndex;
//	}
//	
//	private boolean isDouble(String str) {
//		try {
//			Double.parseDouble(str);
//			return true;
//		} catch (NumberFormatException e) {
//			return false;
//		}
//	}
//	
//	private ProgramParser addLanguagePatterns(ProgramParser lang){
//		for(String language:languages){
//			lang.addPatterns(DEFAULT_RESOURCE_LANGUAGE+language);
//		}
//        return lang;
//	}
//
//	
//}
