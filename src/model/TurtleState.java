package model;

import java.util.Observable;

public class TurtleState extends Observable {
	private final int DEFAULT_PEN_SIZE = 5;
	
	private double xCoordinate;
	private double yCoordinate;
	private double angle;
	private double distanceMoved;
	private boolean drawing;
	private boolean showing;
	private int penColorIndex;
	private int shapeIndex;
	private int penSize;
	private boolean active;

	TurtleState(){
		setToDefaultValues();
	}
	
	private void setToDefaultValues(){
		setXCoordinate(0.0);
		setYCoordinate(0.0);
		setAngle(90.0);
		setDistanceMoved(0.0);
		setDrawing(true);
		setShowing(true);
		setPenColorIndex(0);
		setShapeIndex(0);
		setPenSize(DEFAULT_PEN_SIZE);
		
	}
	
	private void setPenSize(int pixels) {
		this.penSize = pixels;
	}

	private void setShapeIndex(int index) {
		this.shapeIndex = index;
	}

	private void setPenColorIndex(int index) {
		this.penColorIndex = index;	
	}

	double getXCoordinate() {
		return xCoordinate;
	}

	void setXCoordinate(double xCoordinate) {
		if (this.xCoordinate != xCoordinate){
			this.xCoordinate = xCoordinate;
			setChanged();
		}

	}

	double getYCoordinate() {
		return yCoordinate;
	}

	void setYCoordinate(double yCoordinate) {
		if (this.yCoordinate != yCoordinate){
			this.yCoordinate = yCoordinate;
			setChanged();
		}
	}

	double getAngle() {
		return angle;
	}

	void setAngle(double angle) {
		if (this.angle != angle){
			this.angle = angle;
			setChanged();
		}
	}

	boolean isDrawing() {
		return drawing;
	}

	void setDrawing(boolean turtleShouldDraw) {
		this.drawing = turtleShouldDraw;
	}

	double getDistanceMoved() {
		return distanceMoved;
	}

	void setDistanceMoved(double distanceMoved) {
		this.distanceMoved = distanceMoved;
	}

	boolean isShowing() {
		return showing;
	}

	void setShowing(boolean turtleShouldShow) {
		if (this.showing != turtleShouldShow){
			this.showing = turtleShouldShow;
			setChanged();
		}
	}

	boolean isActive(){
		return this.active;
	}

	int getShapeIndex(){
		return this.shapeIndex;
	}
	
	int getPenSize(){
		return this.penSize;
	}
	
	int getPenColorIndex(){
		return this.penColorIndex;
	}
}
