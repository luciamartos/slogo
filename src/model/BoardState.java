package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class BoardState extends Observable {
	private double xCoordinate;
	private double yCoordinate;
	private double angle;
	private double distanceMoved;
	private boolean drawing;
	private boolean showing;
	private ArrayList<PathLine> lineCoordinates;
	private HashMap<String, String> userDefinedVariables;
	
	private static BoardState currentState;
	
	protected BoardState(){
	}
	
	private void setToDefaultValues(){
		setXCoordinate(0.0);
		setYCoordinate(0.0);
		setAngle(0.0);
		setDrawing(false);
		setShowing(true);
	}
	
	
	public static BoardState getCurrentState(){
		if (currentState == null){
			currentState = new BoardState();
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
	
	public ArrayList<PathLine> getLineCoordinates(){
		return lineCoordinates;
	}
	
	public void addLineCoordinates(PathLine line){
		lineCoordinates.add(line);
	}
	
	public HashMap<String, String> getUserDefinedVariables(){
		return userDefinedVariables;
	}
	
}
