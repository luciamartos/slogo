package interpreter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MultipleTurtleInterpreter extends SubInterpreter{
	
	SlogoUpdate model;
	TurtleStateDataSource stateDataSource;
	TurtleStateUpdater turtleStateUpdater;
	
	MultipleTurtleInterpreter(SlogoUpdate model, TurtleStateDataSource stateDataSource, TurtleStateUpdater turtleStateUpdater){
		this.model = model;
		this.stateDataSource = stateDataSource;
		this.turtleStateUpdater = turtleStateUpdater;
	}

	@Override
	boolean canHandle(String keyword) {
		return isMultipleTurtleCommand(keyword);
	}

	@Override
	double handle(String[] input, String keyword, double[] param, int searchStartIndex)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, InstantiationException {
		if(isMultipleTurtleCommand(keyword)){
			Class[] args = createDoubleArgs(0);
			Method method = this.getClass().getDeclaredMethod(keyword, args);
			return (double) method.invoke(this);
		}
		else throw new IllegalArgumentException();
	}

	@Override
	SlogoUpdate getModel() {
		// TODO This should most likely be updated to accommodate for multiple turtles
		return null;
	}
	
	double id(){
		return model.getTurtleID();
	}
	
	double turtles(){
		return stateDataSource.getActiveTurtleIDs().size();
	}
	
	double tell(){
		return 0;
	}
	
	boolean isMultipleTurtleCommand(String input){
		return input.equalsIgnoreCase(rb.getString("id")) || input.equalsIgnoreCase(rb.getString("turtles")) ||
				input.equalsIgnoreCase(rb.getString("tell")) || input.equalsIgnoreCase(rb.getString("ask")) || 
				input.equalsIgnoreCase(rb.getString("askwith")); 
	}


}
