package gui;

import javafx.scene.paint.Color;

/**
 * @author Eric Song
 *
 */
public interface TurtleActionsHandler {
	public void setPenColor(int color);
	public void setPenThickness(int thickness);
	public void setPenType(int type);
	public void setShape(int shape);
	public void toggleTurtle(int id);
	public void setShape(int shape, int id);

}
