package interpreter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.ResourceBundle;

import regularExpression.ProgramParser;

public class MainInterpreter {
	
	final String WHITESPACE = "\\p{Space}";
	private final String DEFAULT_RESOURCE_LANGUAGE = "resources/languages/";
	private final String DEFAULT_RESOURCE_PACKAGE = "resources/properties/";
	private final String PROPERTIES_TITLE = "Interpreter";
	private final String[] languages = {"Chinese","English","French","German","Italian",
			"Portuguese","Russian","Spanish","Syntax"};
	
	
	private slogoUpdate model;
	private ResourceBundle rb;
	private Class interpreterClass;
	
	public MainInterpreter(){
		model = new slogoUpdate();
		rb = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+PROPERTIES_TITLE);
	}
	
	public void parseInput(String input) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String[] split = input.split("\\s+");
		ProgramParser lang = new ProgramParser();
		lang = addPatterns(lang);
		
		String[] parsed = createParsedArray(split, lang);
//		for(String elem: parsed){
//			System.out.println(elem);
//		}
		interpretCommand(split, parsed);
	}
	
	private void interpretCommand(String[] input, String[] parsed) throws ClassNotFoundException, NoSuchMethodException, 
	SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		GeneralInterpreter decideCommand = new GeneralInterpreter();
		String keyword = parsed[0].toLowerCase();
		
		if(decideCommand.isNonInputTurtleCommand(keyword) || decideCommand.isUnaryTurtleCommand(keyword) || 
				decideCommand.isBinaryTurtleCommand(keyword)){
			interpretTurtleCommand(input, keyword);
		}
		
		else if(decideCommand.isNonInputMathExpression(keyword) || decideCommand.isUnaryMathExpression(keyword) || 
				decideCommand.isBinaryMathExpression(keyword)){
			interpretMathCommand(input, keyword);
		}
		
		else if(decideCommand.isUnaryBooleanExpression(keyword) || decideCommand.isBinaryBooleanExpression(keyword)){
			interpretBooleanCommand(input, keyword);
		}
		
		else{
			System.out.println("Invalid argument!");
//			throw new IllegalArgumentException();
		}
		
	}
	
	private void interpretTurtleCommand(String[] input, String keyword){
		
	}
	
	private void interpretMathCommand(String[] input, String keyword) throws ClassNotFoundException, InstantiationException, 
	IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		double[] param;
		interpreterClass = Class.forName(rb.getString("MathInterpreterLabel"));
		Object obj = interpreterClass.newInstance();
		Class[] args;
		MathInterpreter interpreter = new MathInterpreter();
		
		if(interpreter.isNonInputMathExpression(keyword)){
			args = createDoubleArgs(0);
			Method method = interpreterClass.getDeclaredMethod(keyword, args);
			System.out.println(method.invoke(obj));
		}
		
		else if(interpreter.isUnaryMathExpression(keyword)){
			//TODO: parseParam assumes that all inputs are already doubles. What if they're not? ex: less? sum 10 20 50
			param = parseParam(Arrays.copyOfRange(input, 1, 2));
			args = createDoubleArgs(1);
			Method method = interpreterClass.getDeclaredMethod(keyword, args);
			System.out.println(method.invoke(obj, param[0]));
		}
		
		else if(interpreter.isBinaryMathExpression(keyword)){
			param = parseParam(Arrays.copyOfRange(input, 1, 3)); //last index is exclusive
			args = createDoubleArgs(2);
			Method method = interpreterClass.getDeclaredMethod(keyword, args);
			System.out.println(method.invoke(obj, param[0], param[1]));
		}
	}
	
	private void interpretBooleanCommand(String[] input, String keyword) throws ClassNotFoundException, InstantiationException, 
	IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		double[] param;
		interpreterClass = Class.forName(rb.getString("BooleanInterpreterLabel"));
		Object obj = interpreterClass.newInstance();
		Class[] args;
		BooleanInterpreter interpreter = new BooleanInterpreter();
		
		if(interpreter.isUnaryBooleanExpression(keyword)){
			param = parseParam(Arrays.copyOfRange(input, 1, 2));
			args = createDoubleArgs(1);
			Method method = interpreterClass.getDeclaredMethod(keyword, args);
			System.out.println(method.invoke(obj, param[0]));
		}
		
		else if(interpreter.isBinaryBooleanExpression(keyword)){
			param = parseParam(Arrays.copyOfRange(input, 1, 3)); //last index is exclusive
			args = createDoubleArgs(2);
			Method method = interpreterClass.getDeclaredMethod(keyword, args);
			System.out.println(method.invoke(obj, param[0], param[1]));
		}
	}
	
	private double[] parseParam(String[] params){
		double[] res = new double[params.length];
		int index = 0;
		for(String elem: params){
			if(isDouble(elem)){
				double temp = Double.parseDouble(elem);
				res[index++] = temp;
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
	
	
	public slogoUpdate getModel(){
		return model;
	}
}
