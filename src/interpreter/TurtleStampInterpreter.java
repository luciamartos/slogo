package interpreter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TurtleStampInterpreter extends SubInterpreter {
	private TurtleStateUpdater turtleStateUpdater;
	
	public TurtleStampInterpreter(TurtleStateUpdater updater){
		this.turtleStateUpdater = updater;
	}
	@Override
	boolean canHandle(String keyword) {
		return keyword.equalsIgnoreCase(rb.getString("stamp")) || keyword.equalsIgnoreCase(rb.getString("clearstamps"));
	}

	@Override
	double handle(String[] input, String keyword, double[] param, int searchStartIndex)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, InstantiationException {
		Method method = this.getClass().getDeclaredMethod(keyword);
		return (double) method.invoke(this);
	}

	
	double stamp(){
		return (double)this.turtleStateUpdater.createTurtleStamps();
	}
	
	double clearstamps(){
		boolean didClear = this.turtleStateUpdater.clearTurtleStamps();
		if (didClear){
			return 1.0;
		}
		return 0.0;
	}
	
	
	@Override
	SlogoUpdate getModel() {
		return null;
	}

	@Override
	boolean needList() {
		return false;
	}

}
