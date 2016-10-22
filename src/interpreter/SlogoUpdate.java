package interpreter;

import model.TurtleStateDataSource;

public class SlogoUpdate {
	private Boolean turtleShouldDraw;
	private Boolean turtleShouldShow;
	private Double xCoordinate;
	private Double yCoordinate;
	private Double currAngle;
	
	public SlogoUpdate(TurtleStateDataSource source){
		currAngle = source.getAngle();
		turtleShouldDraw = source.getTurtleIsDrawing();
		turtleShouldShow = source.getTurtleIsShowing();
		xCoordinate = source.getXCoordinate();
		yCoordinate = source.getYCoordinate();
	}
	
	public Boolean getTurtleShouldDraw(){
		return turtleShouldDraw;
	}
	
	public Boolean getTurtleShouldShow(){
		return turtleShouldShow;
	}
	
	public Double getXCoordinate(){
		return xCoordinate;
	}
	
	public Double getYCoordinate(){
		return yCoordinate;
	}
	
	public Double getAngle(){
		return currAngle;
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
		xCoordinate = x;
		yCoordinate = y;
	}
	
	public void turnToward(double x, double y){
		//TODO: This is probably not the right function?
		currAngle = 0.0;
	}
	
	public void setAngle(double angle){
		currAngle = angle;
	}
	
	public void putPenDown(){
		turtleShouldDraw = true;
	}
	
	public void putPenUp(){
		turtleShouldDraw = false;
	}
	
}

