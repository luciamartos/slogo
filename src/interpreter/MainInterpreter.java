package interpreter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.ResourceBundle;

import regularExpression.ProgramParser;

public class MainInterpreter {
	
	final String WHITESPACE = "\\p{Space}";
	private final String DEFAULT_RESOURCE_PACKAGE = "resources/properties/";
	private final String PROPERTIES_TITLE = "Interpreter";
	
	private slogoUpdate model;
	private ResourceBundle rb;
	private Class interpreter;
	
	public MainInterpreter(){
		model = new slogoUpdate();
		rb = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+PROPERTIES_TITLE);
	}
	
	public void parseInput(String input) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String[] split = input.split("\\s+");
		ProgramParser lang = new ProgramParser();
		lang = addPatterns(lang);
		
		String[] parsed = createParsedArray(split, lang);
		interpretCommand(split, parsed);
	}
	
	public void interpretCommand(String[] input, String[] parsed) throws ClassNotFoundException, NoSuchMethodException, 
	SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		String keyword = parsed[0].toLowerCase();
		if(isNonInputMathExpression(keyword) || isUnaryMathExpression(keyword) || isBinaryMathExpression(keyword)){
			double[] param;
			interpreter = Class.forName(rb.getString("MathInterpreterLabel"));
			Object obj = interpreter.newInstance();
			Class[] args;
			
			if(isNonInputMathExpression(keyword)){
				args = createDoubleArgs(0);
				Method method = interpreter.getDeclaredMethod(keyword, args);
				System.out.println(method.invoke(obj));
			}
			
			else if(isUnaryMathExpression(keyword)){
				param = parseParam(Arrays.copyOfRange(input, 1, 2));
				args = createDoubleArgs(1);
				Method method = interpreter.getDeclaredMethod(keyword, args);
				System.out.println(method.invoke(obj, param[0]));
			}
			
			else if(isBinaryMathExpression(keyword)){
				param = parseParam(Arrays.copyOfRange(input, 1, 3)); //last index is exclusive
				args = createDoubleArgs(2);
				Method method = interpreter.getDeclaredMethod(keyword, args);
				System.out.println(method.invoke(obj, param[0], param[1]));
			}
		}
		
	}
	
	public double[] parseParam(String[] params){
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
		lang.addPatterns("resources/languages/English");
        lang.addPatterns("resources/languages/Syntax");
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
	
	boolean isNonInputMathExpression(String input){
		return input.equalsIgnoreCase(rb.getString("pi"));
	}
	
	boolean isUnaryMathExpression(String input){
		return input.equalsIgnoreCase(rb.getString("sin")) || input.equalsIgnoreCase(rb.getString("cos")) ||
				input.equalsIgnoreCase(rb.getString("tan")) || input.equalsIgnoreCase(rb.getString("atan")) ||
				input.equalsIgnoreCase(rb.getString("log")) || input.equalsIgnoreCase(rb.getString("rand"));
	}
	
	boolean isBinaryMathExpression(String input){
		return input.equalsIgnoreCase(rb.getString("sum")) || input.equalsIgnoreCase(rb.getString("diff")) ||
				input.equalsIgnoreCase(rb.getString("prod")) || input.equalsIgnoreCase(rb.getString("quo")) ||
				input.equalsIgnoreCase(rb.getString("minus")) || input.equalsIgnoreCase(rb.getString("remain")) || 
				input.equalsIgnoreCase(rb.getString("pwr")) ;
	}
	
	boolean isUnaryBooleanExpression(String input){
		return input.equalsIgnoreCase(rb.getString("sum"));
	}
	
	public slogoUpdate getModel(){
		return model;
	}
}
