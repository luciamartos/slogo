package interpreter;
/**
 * @author Andrew Bihl
 */
public class SlogoUpdate {
	private boolean turtleShouldDraw;
	private boolean turtleShouldShow;
	private double xCoordinate;
	private double yCoordinate;
	private double angle;
	
	public SlogoUpdate(TurtleStateDataSource source){
		angle = source.getAngle();
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
		return angle;
	}
	
//Interpreter should call these methods to update the data object as it handles commands
	
	//TODO: Implement methods.
	public void rotateClockwise(double degrees){
		angle -= degrees;
	}
	
	public void rotateCounterClockwise(double degrees){
		angle += degrees;
	}
	
	public void moveForward(double pixels){
		double theta = getAngle();
		theta = Math.toRadians(theta);
		this.yCoordinate += pixels * Math.sin(theta);
		this.xCoordinate += pixels * Math.cos(theta);
	}
	
	public void moveBackward(double pixels){
		double theta = getAngle();
		theta = Math.toRadians(theta);
		this.xCoordinate -= pixels * Math.cos(theta);
		this.yCoordinate -= pixels * Math.sin(theta);
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
		double yDiff = y - yCoordinate;
		double xDiff = x - xCoordinate;
		double arcTan = Math.atan(yDiff/xDiff);
		arcTan = Math.toDegrees(arcTan);
		if (arcTan < 0){
			arcTan = 180 - arcTan;
		}
		angle = arcTan;
	}
	
	public void setAngle(double newAngle){
		angle = newAngle;
	}
	
	public void putPenDown(){
		turtleShouldDraw = true;
	}
	
	public void putPenUp(){
		turtleShouldDraw = false;
	}
	
}

