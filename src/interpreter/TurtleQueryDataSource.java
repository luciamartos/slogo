package interpreter;

public interface TurtleQueryDataSource {
	public double getXCoordinate();
	public double getYCoordinate();
	public double getAngle();
	public boolean getTurtleIsShown();
	public boolean getPenIsDown();
}
