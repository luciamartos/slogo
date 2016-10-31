package interpreter;
import java.util.List;

/**
 * @author Andrew Bihl
 */
public interface TurtleStateDataSource {
	public double getXCoordinate(int turtleID);
	public double getYCoordinate(int turtleID);
	public double getAngle(int turtleID);
	public boolean getTurtleIsShowing(int turtleID);
	public boolean getTurtleIsDrawing(int turtleID);
	public List<Integer> getActiveTurtleIDs();
	public int getShape(int turtleID);
	public int getPenColor(int turtleID);
	public int getPenSize(int turtleID);
}
