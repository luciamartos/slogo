package gui;

import java.util.Iterator;
import java.util.List;

import javafx.scene.paint.Color;

public interface TurtleStateDataSource {
	public double getXCoordinate(int turtleID);
	public double getYCoordinate(int turtleID);
	public double getAngle(int turtleID);
	public boolean getTurtleIsShowing(int turtleID);
	public boolean getTurtleIsDrawing(int turtleID);
	public int getShape(int turtleID);
	
	public Iterator<Integer> getTurtleIDs();
	public List<Integer> getActiveTurtleIDs();
}
