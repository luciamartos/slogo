package gui;

import javafx.scene.paint.Color;

public interface TurtleStateDataSource {
	public double getXCoordinate(int turtleID);
	public double getYCoordinate(int turtleID);
	public double getAngle(int turtleID);
	public boolean getTurtleIsShowing(int turtleID);
	public boolean getTurtleIsDrawing(int turtleID);
	public Color getPenColor(int turtleID);
	public double getPenThickness(int turtleID);
	public int getShape(int turtleID);
}
