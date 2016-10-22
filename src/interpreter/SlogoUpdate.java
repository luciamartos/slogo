package interpreter;

public class SlogoUpdate {
	private double rotationDelta;
	private double movementDelta;
	private Boolean turtleShouldDraw;
	private Boolean turtleShouldShow;
	private Double newXCoordinate;
	private Double newYCoordinate;
	private Double newAngle;
	
	public SlogoUpdate(){
		rotationDelta = 0;
		movementDelta = 0;
		turtleShouldDraw = null;
		turtleShouldShow = null;
		newXCoordinate = null;
		newYCoordinate = null;
		newAngle = null;
	}
	
	public double getRotationDelta(){
		return rotationDelta;
	}
	
	public double getMovementDelta(){
		return movementDelta;
	}
	
	public Boolean getTurtleShouldDraw(){
		return turtleShouldDraw;
	}
	
	public Boolean getTurtleShouldShow(){
		return turtleShouldShow;
	}
	
	public Double getNewXCoordinate(){
		return newXCoordinate;
	}
	
	public Double getNewYCoordinate(){
		return newYCoordinate;
	}
	
	public Double getNewAngle(){
		return newAngle;
	}
	
//Interpreter should call these methods to update the data object as it handles commands
	
	public void rotateClockwise(double degrees){
		rotationDelta += degrees;
	}
	
	public void rotateCounterClockwise(double degrees){
		rotationDelta -= degrees;
	}
	
	public void moveForward(double pixels){
		movementDelta += pixels;
	}
	
	public void moveBackward(double pixels){
		movementDelta -= pixels;
	}
	
	public void show(){
		turtleShouldShow = true;
	}
	
	public void hide(){
		turtleShouldShow = false;
	}
	
	public void moveTo(double x, double y){
		newXCoordinate = x;
		newYCoordinate = y;
	}
	
	public void turnToward(double x, double y){
		//TODO: This is probably not the right function?
		newAngle = 0.0;
	}
	
	public void setAngle(double angle){
		newAngle = angle;
	}
	
	public void putPenDown(){
		turtleShouldDraw = true;
	}
	
	public void putPenUp(){
		turtleShouldDraw = false;
	}
	
}

