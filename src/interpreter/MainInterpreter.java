package interpreter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.ResourceBundle;

import regularExpression.ProgramParser;
import slogo_update.slogoUpdate;

public class MainInterpreter {
	
	final String WHITESPACE = "\\p{Space}";
	private final String DEFAULT_RESOURCE_PACKAGE = "resources/properties/";
	private final String PROPERTIES_TITLE = "Interpreter";
	
	private slogoUpdate model;
	private ResourceBundle rb;
	private SubInterpreter interpreter;
	
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
		if(isMathExpression(keyword)){
			MathInterpreter mi = new MathInterpreter();
			
			double[] param = parseParam(Arrays.copyOfRange(input, 1, 3)); //last index is exclusive
			
			Class cls = Class.forName("interpreter.MathInterpreter");
			Object obj = cls.newInstance();
			Class[] args = new Class[2];
			for(int i=0;i<args.length;i++){
				args[i] = Double.TYPE;
			}
			
			Method method = cls.getDeclaredMethod(keyword, args);
			System.out.println(method.invoke(obj, param[0],param[1]));
			
		}
		
//		//parse command, get number of Parameters. 
//		int numberOfParameters = input.length-1;
//		for (int i = 1; i <= numberOfParameters; i++){  //Handle case of not ENOUGH parameters
//			String s = input[i];
//			if(isDouble(s)){
//				double param = Double.parseDouble(s);
//			}
//			else{
//				String[] substring = new String[0];
//				interpretCommand(substring);
//			}	
//					
//		}
		
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

	private boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	boolean isMathExpression(String input){
		return input.equalsIgnoreCase(rb.getString("sum")) || input.equalsIgnoreCase(rb.getString("diff")) ||
				input.equalsIgnoreCase(rb.getString("prod")) || input.equalsIgnoreCase(rb.getString("quo")) ||
				input.equalsIgnoreCase(rb.getString("minus")) || input.equalsIgnoreCase(rb.getString("rand")) ||
				input.equalsIgnoreCase(rb.getString("sin")) || input.equalsIgnoreCase(rb.getString("cos")) ||
				input.equalsIgnoreCase(rb.getString("tan")) || input.equalsIgnoreCase(rb.getString("atan")) ||
				input.equalsIgnoreCase(rb.getString("log")) || input.equalsIgnoreCase(rb.getString("pwr")) ||
				input.equalsIgnoreCase(rb.getString("pi")) || input.equalsIgnoreCase(rb.getString("remain"));
	}
	
	public slogoUpdate getModel(){
		return model;
	}
}
