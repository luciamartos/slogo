package gui;

import javafx.scene.paint.Color;

public interface TurtleActionsHandler {

	public void setPenColor(Color color);
	public void setPenThickness(double thickness);
	public void setPenType(String type);
	public void setShape(String shape);
	public void toggleTurtle(int id);
	
}
