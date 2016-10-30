package interpreter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

public abstract class SubInterpreter {
	
	private final String DEFAULT_RESOURCE_PACKAGE = "resources/properties/";
	private final String PROPERTIES_TITLE = "Interpreter";
	protected ResourceBundle rb = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+PROPERTIES_TITLE);
	
	protected int currSearchIndex;
	
	abstract boolean canHandle(String keyword);
	abstract double handle(String[] input, String keyword, double[] param, int searchStartIndex) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException;
	abstract SlogoUpdate getModel();
	
	
	protected Class[] createDoubleArgs(int num){
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
	
	public int getCurrSearchIndex(){
		return currSearchIndex;
	}
	
}
