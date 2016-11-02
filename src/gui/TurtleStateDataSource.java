package gui;

import java.util.Iterator;
import java.util.List;

public interface TurtleStateDataSource {
	public double getXCoordinate(int turtleID);
	public double getYCoordinate(int turtleID);
	public double getAngle(int turtleID);
	public boolean getTurtleIsShowing(int turtleID);
	public boolean getTurtleIsDrawing(int turtleID);
	public int getShape(int turtleID);
	
	public int getNumberOfTurtles();
	public Iterator<Integer> getTurtleIDs();
	public List<Integer> getActiveTurtleIDs();
}
