package interpreter;

import model.TurtleStateDataSource;

public class SlogoUpdate {
	private Boolean turtleShouldDraw;
	private Boolean turtleShouldShow;
	private Double newXCoordinate;
	private Double newYCoordinate;
	private Double newAngle;
	
	public SlogoUpdate(TurtleStateDataSource source){
		newAngle = source.getAngle();
		turtleShouldDraw = source.getTurtleIsDrawing();
		turtleShouldShow = source.getTurtleIsShowing();
		newXCoordinate = source.getXCoordinate();
		newYCoordinate = source.getYCoordinate();
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
	
	//TODO: Implement methods.
	public void rotateClockwise(double degrees){
		
	}
	
	public void rotateCounterClockwise(double degrees){

	}
	
	public void moveForward(double pixels){

	}
	
	public void moveBackward(double pixels){

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

