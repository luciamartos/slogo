package interpreter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import model.TurtleStamp;

/**
 * This is a newly added sub-interpreter for the SLogo Extension of CS308 Final Project.
 * This class inherits from an abstract class just like all the other interpreters,
 * so it is flawlessly integrated into the loop that MainInterpreter runs to check for
 * which sub-interpreter can handle a given command.
 * 
 * @author Ray Song
 *
 */
public class StampInterpreter extends SubInterpreter{

	private TurtleStamp stamp;
	private TurtleStampUpdater turtleStampUpdater;
	
	public StampInterpreter(TurtleStamp stamp, TurtleStampUpdater turtleStateUpdater ){
		this.stamp = stamp;
		this.turtleStampUpdater = turtleStampUpdater;
	}
	
	@Override
	boolean canHandle(String keyword) {
		return isStampCommand(keyword);
	}

	@Override
	double handle(String[] input, String keyword, double[] param, int searchStartIndex)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, InstantiationException {
		if(isStampCommand(keyword)){
			Class<?>[] args = createDoubleArgs(0);
			Method method = this.getClass().getDeclaredMethod(keyword, args);
			return (double) method.invoke(this);
		}
		else throw new IllegalArgumentException();
	}

	@Override
	SlogoUpdate getModel() {
		return null;
	}

	@Override
	boolean needList() {
		return false;
	}
	
	boolean isStampCommand(String input){
		return input.equalsIgnoreCase(rb.getString("stamp")) || input.equalsIgnoreCase(rb.getString("clearstamps"));
	}
	
	double stamp(){
		turtleStampUpdater.addTurtleStamp(stamp);
		return stamp.getTurtleID();
	}
	
	double clearstamps(){
		double hasStamps = 0;
		if(turtleStampUpdater.getNumberOfStamps() > 0) hasStamps = 1;
		turtleStampUpdater.clearTurtleStamps();
		return hasStamps;
	}

}
