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
	abstract double handle(String[] input, String keyword, double[] param, int searchStartIndex);
	
	protected double handleNonInputKeyword(String keyword, int searchStartIndex, Class interpreterClass, Object obj)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		double res = helpHandleNonInputKeyword(keyword, searchStartIndex, interpreterClass, obj);
		return res;
	}

	protected double handleBinaryKeyword(String[] input, String keyword, int searchStartIndex, Class interpreterClass,
			Object obj) throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		double res = helpHandleBinaryKeyword(input, keyword, searchStartIndex, interpreterClass, obj);
		return res;
	}

	protected double handleUnaryKeyword(String[] input, String keyword, int searchStartIndex, Class interpreterClass,
			Object obj) throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		double res = helpHandleUnaryKeyword(input, keyword, searchStartIndex, interpreterClass, obj);
		return res;
	}

	protected double helpHandleNonInputKeyword(String keyword, int searchStartIndex, Class interpreterClass, Object obj)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		currSearchIndex = searchStartIndex;
		Class[] args;
		args = createDoubleArgs(0);
		Method method = interpreterClass.getDeclaredMethod(keyword, args);
		double res = (double) method.invoke(obj);
		System.out.println(res);
		return res;
	}

	protected double helpHandleBinaryKeyword(String[] input, String keyword, int searchStartIndex, Class interpreterClass,
			Object obj) throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		currSearchIndex = searchStartIndex+2;
		Class[] args;
		args = createDoubleArgs(2);
		Method method = interpreterClass.getDeclaredMethod(keyword, args);
		double res = (double) method.invoke(obj, param[0], param[1]);
		System.out.println(res);
		return res;
	}
	
	protected double helpHandleUnaryKeyword(String[] input, String keyword, int searchStartIndex, Class interpreterClass,
			Object obj) throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		currSearchIndex = searchStartIndex+1;
		Class[] args;
		args = createDoubleArgs(1);
		Method method = interpreterClass.getDeclaredMethod(keyword, args);
		double res = (double) method.invoke(obj, param[0]);
		System.out.println(res);
		return res;
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
	
	public int getCurrSearchIndex(){
		return currSearchIndex;
	}
	
}
