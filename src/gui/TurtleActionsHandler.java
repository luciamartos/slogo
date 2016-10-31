package gui;

import javafx.scene.paint.Color;

public interface TurtleActionsHandler {

	public void setPenColor(int color);
	public void setPenThickness(int thickness);
	public void setPenType(String type);
	public void setShape(String shape);
	public void toggleTurtle(int id);
}
