package model;
import interpreter.SlogoUpdate;

public class TurtleStateController implements TurtleStateDataSource{

	public void applyChanges(SlogoUpdate changes){
		
		TurtleState modelToUpdate = TurtleState.getCurrentState();
		double currentAngle = modelToUpdate.getAngle();
		double currentX = modelToUpdate.getXCoordinate();
		double currentY = modelToUpdate.getYCoordinate();
		
		
		Boolean shouldDraw = changes.getTurtleShouldDraw();
		//null state means no changes (no command issued)
		if (shouldDraw != null){
			modelToUpdate.setDrawing(shouldDraw);
		}
		Boolean shouldShow = changes.getTurtleShouldShow();
		if (shouldShow != null){
			modelToUpdate.setShowing(shouldShow);
		}
	}
	
	private double calculateNewAngle(double currentAngle, double angleDelta){
		currentAngle += angleDelta;
		currentAngle = convertAngle(currentAngle);
		return 0.0;
	}
	
	private double calculateNewXCoordinate(double currentX, double currentAngle, double movementDelta){
		
		return 0.0;
	}
	
	private double calculateNewYCoordinate(double currentY, double currentAngle, double movementDelta){
		
		return 0.0;
	}
	
	/**
	 * @param unconvertedAngle
	 * @return The angle converted to a positive value between 0.0 and 360.0
	 */
	private static double convertAngle(double unconvertedAngle){
		double numerator = unconvertedAngle;
		double denominator = 360;
		int multiplier = (int)(numerator / denominator);
		//multiplier is negative if numerator < (-1 * denominator)
		//Get numerator in range [-denominator, denominator]
		numerator -= (multiplier * denominator);
		if (numerator < 0){
			numerator = denominator + numerator;
		}		
		return numerator;
	}
	
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
