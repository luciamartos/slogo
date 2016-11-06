package gui;

import java.util.Iterator;
import java.util.List;

/**
 * @author Andrew Bihl
 *
 */
public interface TurtleStateDataSource {
	/**
	 * @param turtleID
	 * @return x coordinate for turtle with turtle ID
	 */
	public double getXCoordinate(int turtleID);
	
	/**
	 * @param turtleID
	 * @return y coordinate for turtle with turtle ID
	 */
	public double getYCoordinate(int turtleID);
	
	/**
	 * @param turtleID
	 * @return angle for turtle with turtle ID
	 */
	public double getAngle(int turtleID);
	
	/**
	 * @param turtleID
	 * @return if turtle with turtle ID is showing
	 */
	public boolean getTurtleIsShowing(int turtleID);
	
	/**
	 * @param turtleID
	 * @return if turtle with turtle ID is drawing
	 */
	public boolean getTurtleIsDrawing(int turtleID);
	
	/**
	 * @param turtleID
	 * @return shape for turtle with turtle ID
	 */
	public int getShape(int turtleID);
	
	/**
	 * @return number of turtles
	 */
	public int getNumberOfTurtles();
	
	/**
	 * @return a list of all the turtle IDs
	 */
	public Iterator<Integer> getTurtleIDs();
	
	/**
	 * @return a list of all the active turtle IDs
	 */
	public List<Integer> getActiveTurtleIDs();
}
