package gui;
import java.util.List;
import java.util.Map;
import model.PathLine;

public interface BoardStateDataSource {
	public double getXCoordinate();
	public double getYCoordinate();
	public double getAngle();
	public boolean getTurtleIsShowing();
	public boolean getTurtleIsDrawing();
	public List<PathLine> getLineCoordinates();
	public Map<String, String> getUserDefinedVariables();
}
