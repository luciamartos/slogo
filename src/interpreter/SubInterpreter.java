package interpreter;

import java.lang.reflect.InvocationTargetException;
import java.util.Queue;
import java.util.ResourceBundle;

public abstract class SubInterpreter {
	
	private final String DEFAULT_RESOURCE_PACKAGE = "resources/properties/";
	private final String PROPERTIES_TITLE = "Interpreter";
	protected ResourceBundle rb = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+PROPERTIES_TITLE);
	
	protected Queue<String[]> listQueue;
	
	abstract boolean canHandle(String keyword);
	abstract double handle(String[] input, String keyword, double[] param, int searchStartIndex) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException;
	abstract SlogoUpdate getModel();
	abstract boolean needList();
	
	
	void setList(Queue<String[]> queue){
		this.listQueue = queue;
	}
	
	protected Class<?>[] createDoubleArgs(int num){
		Class<?>[] args = new Class[num];
		for(int i=0;i<args.length;i++){
			args[i] = Double.TYPE;
		}
		return args;
	}
	
}
