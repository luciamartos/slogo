package model;
import interpreter.SlogoUpdate;
import interpreter.TurtleStateDataSource;

public class BoardStateController implements TurtleStateDataSource{

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
	
	//deleted
//	private double calculateNewAngle(double currentAngle, double angleDelta){
//		currentAngle += angleDelta;
//		currentAngle = convertAngle(currentAngle);
//		return 0.0;
//	}
	
	private double calculateNewXCoordinate(double currentX, double currentAngle, double movementDelta){
		
		return 0.0;
	}
	
	private double calculateNewYCoordinate(double currentY, double currentAngle, double movementDelta){
		
		return 0.0;
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
	
}
