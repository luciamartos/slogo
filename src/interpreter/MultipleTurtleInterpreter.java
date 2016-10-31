package interpreter;

import java.lang.reflect.InvocationTargetException;

public class MultipleTurtleInterpreter extends SubInterpreter{
	
	SlogoUpdate model;
	TurtleStateDataSource stateDataSource;
	
	MultipleTurtleInterpreter(SlogoUpdate model, TurtleStateDataSource stateDataSource){
		this.model = model;
		this.stateDataSource = stateDataSource;
	}

	@Override
	boolean canHandle(String keyword) {
		return isMultipleTurtleCommand(keyword);
	}

	@Override
	double handle(String[] input, String keyword, double[] param, int searchStartIndex)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, InstantiationException {
		// TODO Auto-generated method stub
		return 0;
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
	
	boolean isMultipleTurtleCommand(String input){
		return input.equalsIgnoreCase(rb.getString("id")) || input.equalsIgnoreCase(rb.getString("turtles")) ||
				input.equalsIgnoreCase(rb.getString("tell")) || input.equalsIgnoreCase(rb.getString("ask")) || 
				input.equalsIgnoreCase(rb.getString("askwith")); 
	}


}
