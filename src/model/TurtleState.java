package model;

import java.util.Observable;

public class TurtleState extends Observable {
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
