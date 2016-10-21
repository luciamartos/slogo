package model;

public class TurtleStateController implements TurtleQueryDataSource{

	
	
	
/*
 * TurtleQueryDataSource interface methods
 */
	@Override
	public double getXCoordinate() {
		return TurtleState.getCurrentState().getXCoordinate();
	}

	@Override
	public double getYCoordinate() {
		return TurtleState.getCurrentState().getYCoordinate();
	}

	@Override
	public double getAngle() {
		return TurtleState.getCurrentState().getAngle();
	}

	@Override
	public boolean getTurtleIsShowing() {
		return TurtleState.getCurrentState().isShowing();
	}

	@Override
	public boolean getTurtleIsDrawing() {
		return TurtleState.getCurrentState().isDrawing();
	}
	
}
