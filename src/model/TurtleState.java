package model;

import java.util.Observable;

public class TurtleState extends Observable {
	private double xCoordinate;
	private double yCoordinate;
	private double angle;
	private double distanceMoved;
	private boolean drawing;
	private boolean showing;
	
	private static TurtleState currentState;
	
	protected TurtleState(){
	}
	
	private void setToDefaultValues(){
		setXCoordinate(0.0);
		setYCoordinate(0.0);
		setAngle(0.0);
		setDrawing(false);
		setShowing(true);
	}
	
	
	public static TurtleState getCurrentState(){
		if (currentState == null){
			currentState = new TurtleState();
			currentState.setToDefaultValues();
		}
		return currentState;
	}

// Getters and Setters
	
	public double getXCoordinate() {
		return xCoordinate;
	}

	public void setXCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public double getYCoordinate() {
		return yCoordinate;
	}

	public void setYCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public boolean isDrawing() {
		return drawing;
	}

	public void setDrawing(boolean turtleShouldDraw) {
		this.drawing = turtleShouldDraw;
	}

	public double getDistanceMoved() {
		return distanceMoved;
	}

	public void setDistanceMoved(double distanceMoved) {
		this.distanceMoved = distanceMoved;
	}

	public boolean isShowing() {
		return showing;
	}

	public void setShowing(boolean turtleShouldShow) {
		this.showing = turtleShouldShow;
	}
}
