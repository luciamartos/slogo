package interpreter;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Andrew Bihl
 */
public class SlogoUpdate {
	private boolean turtleShouldDraw;
	private boolean turtleShouldShow;
	private double xCoordinate;
	private double yCoordinate;
	private double angle;
	private HashSet<Integer> turtles;
	
	public SlogoUpdate(TurtleStateDataSource source, int turtleID){
		turtles = new HashSet<Integer>();
		angle = source.getAngle(turtleID);
		turtleShouldDraw = source.getTurtleIsDrawing(turtleID);
		turtleShouldShow = source.getTurtleIsShowing(turtleID);
		xCoordinate = source.getXCoordinate(turtleID);
		yCoordinate = source.getYCoordinate(turtleID);
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
		angle = convertAngle(angle - degrees);
	}
	
	public void rotateCounterClockwise(double degrees){
		angle = convertAngle(angle + degrees);
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
	
	public double turnToward(double x, double y){
		double yDiff = y - yCoordinate;
		double xDiff = x - xCoordinate;
		double arcTan = Math.atan(yDiff/xDiff);
		arcTan = Math.toDegrees(arcTan);
		if (arcTan < 0){
			arcTan = 180 - arcTan;
		}
		double angleDelta = Math.abs(angle - arcTan);
		angle = arcTan;
		return angleDelta;
	}
	
	public double setAngle(double newAngle){
		double angleDelta = angle - newAngle;
		angle = convertAngle(newAngle);
		return convertAngle(angleDelta);
	}
	
	public void putPenDown(){
		turtleShouldDraw = true;
	}
	
	public void putPenUp(){
		turtleShouldDraw = false;
	}
	
	public Collection<Integer> getTurtles(){
		return this.turtles;
	}
	
	/**
	 * @param unconvertedAngle
	 * @return The angle converted to a positive value between 0.0 and 360.0
	 */
	private static double convertAngle(double unconvertedAngle){
		double numerator = unconvertedAngle;
		double denominator = 360;
		int multiplier = (int)(numerator / denominator);
		//multiplier is negative if numerator < (-1 * denominator)
		//Get numerator in range [-denominator, denominator]
		numerator -= (multiplier * denominator);
		if (numerator < 0){
			numerator = denominator + numerator;
		}		
		return numerator;
	}
	
}

