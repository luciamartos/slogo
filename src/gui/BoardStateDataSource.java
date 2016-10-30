package gui;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import model.PathLine;
/**
 * @author Andrew Bihl
 */
public interface BoardStateDataSource {
	public double getXCoordinate();
	public double getYCoordinate();
	public double getAngle();
	public boolean getTurtleIsShowing();
	public boolean getTurtleIsDrawing();
	public List<PathLine> getLineCoordinates();
	public Map<String, String> getUserDefinedVariables();
	public Color getPenColor();
	public Color getBackgroundColor();
	public double getPenThickness();
	public String getImage();
}
