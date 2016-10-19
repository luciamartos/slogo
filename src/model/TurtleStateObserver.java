package model;

public interface TurtleStateObserver {
	public void xCoordinateDidChange(double newValue);
	public void yCoordinateDidChange(double newValue);
	public void angleDidChange(double newValue);
	public void turtleIsShowingDidChange(double newValue);
	public void penIsDownDidChange(boolean newValue);
}
