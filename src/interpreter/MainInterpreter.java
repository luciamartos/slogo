package interpreter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

import gui.SlogoCommandInterpreter;
//import model.TurtleStateDataSource;
import regularExpression.ProgramParser;

public class MainInterpreter implements SlogoCommandInterpreter {
	
	private final String DEFAULT_RESOURCE_LANGUAGE = "resources/languages/";
	private final String DEFAULT_RESOURCE_PACKAGE = "resources/properties/";
	private final String PROPERTIES_TITLE = "Interpreter";
	private String[] languages = {"English", "Syntax"};  //default language is English
	
	private SlogoUpdate model;
//	private TurtleStateDataSource stateDataSource;
	private TurtleStateUpdater stateUpdater;
	private ResourceBundle rb;
	private String[] parsed;
	
	public MainInterpreter(){
		rb = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+PROPERTIES_TITLE);
	}
	
	public void parseInput(String input, TurtleStateDataSource source) throws ClassNotFoundException, NoSuchMethodException, 
	SecurityException, InstantiationException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException{
		model = new SlogoUpdate(source);
//		stateDataSource = source;
		
		String[] split = input.split("\\s+");
		ProgramParser lang = new ProgramParser();
		lang = addPatterns(lang);
		parsed = createParsedArray(split, lang);
		
//		for(String elem: parsed){
//			System.out.println(elem);
//		}
		
		//split is the original input, parsed is the translated version (translated with ProgramParser)
		interpretCommand(split, parsed, 0);   //first search(non-recursive) begins at index 0;
		
		stateUpdater.applyChanges(model);
	}
	
	private double interpretCommand(String[] input, String[] parsed, int searchStartIndex) throws ClassNotFoundException, NoSuchMethodException, 
	SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		GeneralInterpreter decideCommand = new GeneralInterpreter();
		String keyword = parsed[searchStartIndex].toLowerCase();
		
		if(decideCommand.isNonInputTurtleCommand(keyword) || decideCommand.isUnaryTurtleCommand(keyword) || 
				decideCommand.isBinaryTurtleCommand(keyword)){
			return interpretTurtleCommand(input, keyword, searchStartIndex);
		}
		
		else if(decideCommand.isTurtleQuery(keyword)){
			return interpretTurtleQuery(input, keyword, searchStartIndex);
		}
		
		else if(decideCommand.isNonInputMathExpression(keyword) || decideCommand.isUnaryMathExpression(keyword) || 
				decideCommand.isBinaryMathExpression(keyword)){
			return interpretMathCommand(input, keyword, searchStartIndex);
		}
		
		else if(decideCommand.isUnaryBooleanExpression(keyword) || decideCommand.isBinaryBooleanExpression(keyword)){
			return interpretBooleanCommand(input, keyword, searchStartIndex);
		}
		
		else{
			System.out.println("Invalid argument detected: '" + input[searchStartIndex]
					+"' is not a valid command!");
			throw new IllegalArgumentException();
		}
		
	}
	
	private double interpretTurtleCommand(String[] input, String keyword, int searchStartIndex) throws ClassNotFoundException, 
	InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, 
	IllegalArgumentException, InvocationTargetException{
		double[] param;
		Class interpreterClass = Class.forName(rb.getString("TurtleCommandInterpreterLabel"));
		Object obj = interpreterClass.getDeclaredConstructor(SlogoUpdate.class).newInstance(model);
		Class[] args;
		TurtleCommandInterpreter interpreter = new TurtleCommandInterpreter(model);
		
		if(interpreter.isNonInputTurtleCommand(keyword)){
			return handleNonInputKeywordWithModel(keyword, interpreterClass, obj, interpreter);
		}
		else if(interpreter.isUnaryTurtleCommand(keyword)){
			param = parseParam(input, searchStartIndex+1, 1);
			args = createDoubleArgs(1);
			Method method = interpreterClass.getDeclaredMethod(keyword, args);
			System.out.println(method.invoke(obj, param[0]));
			double res = (double) method.invoke(obj, param[0]);
			model = interpreter.getModel();
			return res;
		}
		else if(interpreter.isBinaryTurtleCommand(keyword)){
			param = parseParam(input, searchStartIndex+1, 2);
			args = createDoubleArgs(2);
			Method method = interpreterClass.getDeclaredMethod(keyword, args);
			System.out.println(method.invoke(obj, param[0], param[1]));
			double res = (double) method.invoke(obj, param[0], param[1]);
			model = interpreter.getModel();
			return res;
		}
		else throw new IllegalArgumentException();
	}

	
	private double interpretTurtleQuery(String[] input, String keyword, int searchStartIndex) throws ClassNotFoundException, 
	InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		double[] param;
		Class interpreterClass = Class.forName(rb.getString("TurtleQueryInterpreterLabel"));
		Object obj = interpreterClass.getDeclaredConstructor(SlogoUpdate.class).newInstance(model);
		Class[] args;
		TurtleCommandInterpreter interpreter = new TurtleCommandInterpreter(model);
		if(interpreter.isTurtleQuery(keyword)){
			return handleNonInputKeywordWithModel(keyword, interpreterClass, obj, interpreter);
		}
		else throw new IllegalArgumentException();
	}
	
	private double interpretMathCommand(String[] input, String keyword, int searchStartIndex) throws ClassNotFoundException, InstantiationException, 
	IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		double[] param;
		Class interpreterClass = Class.forName(rb.getString("MathInterpreterLabel"));
		Object obj = interpreterClass.newInstance();
		Class[] args;
		MathInterpreter interpreter = new MathInterpreter();
		
		if(interpreter.isNonInputMathExpression(keyword)){
			return handleNonInputKeyword(keyword, interpreterClass, obj);
		}
		else if(interpreter.isUnaryMathExpression(keyword)){
			return handleUnaryKeyword(input, keyword, searchStartIndex, interpreterClass, obj);
		}
		else if(interpreter.isBinaryMathExpression(keyword)){
			return handleBinaryKeyword(input, keyword, searchStartIndex, interpreterClass, obj);
		}
		else throw new IllegalArgumentException();
	}
	
	private double interpretBooleanCommand(String[] input, String keyword, int searchStartIndex) throws ClassNotFoundException, InstantiationException, 
	IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		double[] param;
		Class interpreterClass = Class.forName(rb.getString("BooleanInterpreterLabel"));
		Object obj = interpreterClass.newInstance();
		Class[] args;
		BooleanInterpreter interpreter = new BooleanInterpreter();

		if(interpreter.isUnaryBooleanExpression(keyword)){
			return handleUnaryKeyword(input, keyword, searchStartIndex, interpreterClass, obj);
		}
		
		else if(interpreter.isBinaryBooleanExpression(keyword)){
			return handleBinaryKeyword(input, keyword, searchStartIndex, interpreterClass, obj);
		}
		else throw new IllegalArgumentException();
	}
	
	private double handleNonInputKeywordWithModel(String keyword, Class interpreterClass, Object obj,
			TurtleCommandInterpreter interpreter)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Class[] args;
		args = createDoubleArgs(0);
		Method method = interpreterClass.getDeclaredMethod(keyword, args);
		System.out.println(method.invoke(obj));
		double res =  (double) method.invoke(obj);
		model = interpreter.getModel();
		return res;
	}

	private double handleBinaryKeyword(String[] input, String keyword, int searchStartIndex, Class interpreterClass,
			Object obj) throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		double[] param;
		Class[] args;
		param = parseParam(input, searchStartIndex+1, 2);
		args = createDoubleArgs(2);
		Method method = interpreterClass.getDeclaredMethod(keyword, args);
		System.out.println(method.invoke(obj, param[0], param[1]));
		return (double) method.invoke(obj, param[0], param[1]);
	}

	private double handleUnaryKeyword(String[] input, String keyword, int searchStartIndex, Class interpreterClass,
			Object obj) throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		double[] param;
		Class[] args;
		param = parseParam(input, searchStartIndex+1, 1);
		args = createDoubleArgs(1);
		Method method = interpreterClass.getDeclaredMethod(keyword, args);
		System.out.println(method.invoke(obj, param[0]));
		return (double) method.invoke(obj, param[0]);
	}

	private double handleNonInputKeyword(String keyword, Class interpreterClass, Object obj)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Class[] args;
		args = createDoubleArgs(0);
		Method method = interpreterClass.getDeclaredMethod(keyword, args);
		System.out.println(method.invoke(obj));
		return (double) method.invoke(obj);
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
			//recursive parsing of input statement
			else{
				res[index++] = interpretCommand(input, parsed, i);
			}
		}
		return res;
	}
	
	private String[] createParsedArray(String[] in, ProgramParser lang){
		String[] out = new String[in.length];
		for(int i=0;i<in.length;i++){
			out[i] = lang.getSymbol(in[i]);
		}
		return out;
	}
	
	private ProgramParser addPatterns(ProgramParser lang){
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
	
	public void setLanguage(String language){
		String[] temp = {language, "Syntax"};
		languages = temp;
	}
	
	public SlogoUpdate getModel(){
		return model;
	}
}
