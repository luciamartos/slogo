package model;
import java.util.List;
import java.util.Map;

import gui.BoardStateDataSource;
import interpreter.SlogoUpdate;
import interpreter.TurtleStateDataSource;

public class BoardStateController implements TurtleStateDataSource, BoardStateDataSource{

	public void applyChanges(SlogoUpdate changes){
		
		BoardState modelToUpdate = BoardState.getCurrentState();
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
		return BoardState.getCurrentState().getXCoordinate();
	}

	@Override
	public double getYCoordinate() {
		return BoardState.getCurrentState().getYCoordinate();
	}

	@Override
	public double getAngle() {
		return BoardState.getCurrentState().getAngle();
	}

	@Override
	public boolean getTurtleIsShowing() {
		return BoardState.getCurrentState().isShowing();
	}

	@Override
	public boolean getTurtleIsDrawing() {
		return BoardState.getCurrentState().isDrawing();
	}

	@Override
	public List getLineCoordinates() {
		return BoardState.getCurrentState().getLineCoordinates();
	}

	@Override
	public Map<String, String> getUserDefinedVariables() {
		return BoardState.getCurrentState().getUserDefinedVariables();
	}
	
}
