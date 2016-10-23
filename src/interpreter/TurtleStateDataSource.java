package interpreter;
/**
 * @author Andrew Bihl
 */
public interface TurtleStateDataSource {
	public double getXCoordinate();
	public double getYCoordinate();
	public double getAngle();
	public boolean getTurtleIsShowing();
	public boolean getTurtleIsDrawing();
}
