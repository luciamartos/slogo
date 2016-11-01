package interpreter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MultipleTurtleInterpreter extends SubInterpreter{
	
	SlogoUpdate model;
	TurtleStateDataSource stateDataSource;
	TurtleStateUpdater turtleStateUpdater;
	
	MultipleTurtleInterpreter(SlogoUpdate model, TurtleStateDataSource stateDataSource, 
			TurtleStateUpdater turtleStateUpdater, Queue<String[]> listQueue){
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
	
	@Override
	boolean needList() {
		return true;
	}

	
	double id(){
		return model.getTurtleID();
	}
	
	double turtles(){
		return stateDataSource.getActiveTurtleIDs().size();
	}
	
	double tell(){
		int turtleIndex=0;
		String[] turtles = listQueue.poll();
		List<Integer> listOfTurtles = new ArrayList<Integer>();
		for(String turtle: turtles){
			turtleIndex = Integer.parseInt(turtle);
			listOfTurtles.add(turtleIndex);
		}
		turtleStateUpdater.setActiveTurtles(listOfTurtles);
		return (double)turtleIndex;
	}
	
	boolean isMultipleTurtleCommand(String input){
		return input.equalsIgnoreCase(rb.getString("id")) || input.equalsIgnoreCase(rb.getString("turtles")) ||
				input.equalsIgnoreCase(rb.getString("tell")); 
	}


}
