package gui;

import javafx.scene.paint.Color;

public interface TurtleActionsHandler {

	public void setPenColor(int color);
	public void setPenThickness(int thickness);
	public void setPenType(int type);
	public void setShape(int shape);
	public void toggleTurtle(int id);
}
