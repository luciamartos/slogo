package model;

/**
 * This is a newly created class that represents the concept of "stamps".
 * @author Ray Song
 *
 */
public class TurtleStamp {
	
	private double xCoordinate;
	private double yCoordinate;
	private int turtleID;
	
	public TurtleStamp(int turtleID){
		this.turtleID = turtleID;
	}
	
	public Double getXCoordinate(){
		return xCoordinate;
	}
	
	public Double getYCoordinate(){
		return yCoordinate;
	}
	
	public int getTurtleID(){
		return this.turtleID;
	}
}
