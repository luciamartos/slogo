package model;

public class TurtleState {
	public double xCoordinate;
	public double yCoordinate;
	public double angle;
	public boolean penIsDown;
	public boolean turtleIsShowing;
	
	private static TurtleState currentState;
	
	protected TurtleState(){
	}
	
	private void setToDefaultValues(){
		xCoordinate = 0.0;
		yCoordinate = 0.0;
		angle = 0.0;
		penIsDown = false;
		turtleIsShowing = true;
	}
	
	
	public static TurtleState getCurrentState(){
		if (currentState == null){
			currentState = new TurtleState();
			currentState.setToDefaultValues();
		}
		return currentState;
	}
}
