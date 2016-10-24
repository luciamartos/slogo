package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

/**
 * @author Andrew Bihl
 */

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
		this.lineCoordinates= new ArrayList<PathLine>();
		this.userDefinedVariables = new HashMap<String, String>();
	}
	
	private void setToDefaultValues(){
		setXCoordinate(0.0);
		setYCoordinate(0.0);
		setAngle(90.0);
		setDrawing(false);
		setShowing(true);
		setChanged();
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
		if (this.xCoordinate != xCoordinate){
			this.xCoordinate = xCoordinate;
			setChanged();
		}
	
	}

	public double getYCoordinate() {
		return yCoordinate;
	}

	public void setYCoordinate(double yCoordinate) {
		if (this.yCoordinate != yCoordinate){
			this.yCoordinate = yCoordinate;
			setChanged();
		}
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		if (this.angle != angle){
			this.angle = angle;
			setChanged();
		}
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
		if (this.showing != turtleShouldShow){
			this.showing = turtleShouldShow;
			setChanged();
		}
	}
	
	public List<PathLine> getLineCoordinates(){
		return lineCoordinates;
	}
	
	public void addLineCoordinates(PathLine line){
		lineCoordinates.add(line);
		setChanged();
	}
	
	public void addUserDefinedVariable(String varName, String userInput){
		this.userDefinedVariables.put(varName, userInput);
		setChanged();
	}
	
	public HashMap<String, String> getUserDefinedVariables(){
		return userDefinedVariables;
	}
	
}
