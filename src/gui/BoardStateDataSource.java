package gui;
import java.util.List;
import java.util.Map;

public interface BoardStateDataSource {
	public double getXCoordinate();
	public double getYCoordinate();
	public double getAngle();
	public boolean getTurtleIsShowing();
	public boolean getTurtleIsDrawing();
	public List getLineCoordinates();
	public Map<String, String> getUserCreatedVariables();
}
