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
		
}
