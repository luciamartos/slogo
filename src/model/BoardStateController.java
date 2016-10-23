package model;
import java.util.List;
import java.util.Map;

import gui.BoardStateDataSource;
import interpreter.SlogoUpdate;
import interpreter.TurtleStateDataSource;
import interpreter.TurtleStateUpdater;
import interpreter.UserVariablesDataSource;

/**
 * @author Andrew Bihl
 */

public class BoardStateController implements TurtleStateDataSource, BoardStateDataSource, TurtleStateUpdater, UserVariablesDataSource {
	
	
	public void applyChanges(SlogoUpdate changes){
		BoardState modelToUpdate = BoardState.getCurrentState();
		modelToUpdate.setAngle(changes.getAngle());
		modelToUpdate.setDrawing(changes.getTurtleShouldDraw());
		modelToUpdate.setShowing(changes.getTurtleShouldShow());
		if (modelToUpdate.isDrawing()){
			double currentX = modelToUpdate.getXCoordinate();
			double currentY = modelToUpdate.getYCoordinate();
			double newX = changes.getXCoordinate();
			double newY = changes.getYCoordinate();
			PathLine line = new PathLine(currentX, currentY, newX, newY);
			modelToUpdate.addLineCoordinates(line);
		}
		modelToUpdate.setXCoordinate(changes.getXCoordinate());
		modelToUpdate.setYCoordinate(changes.getYCoordinate());
		modelToUpdate.notifyObservers();
	}
	
/*
 * interpreter.TurtleQueryDataSource interface methods
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

	
/*
 * gui.BoardStateDataSource 
 */
	@Override
	public List<PathLine> getLineCoordinates() {
		return BoardState.getCurrentState().getLineCoordinates();
	}

	@Override
	public Map<String, String> getUserDefinedVariables() {
		return BoardState.getCurrentState().getUserDefinedVariables();
	}
	
/*
 * interpreter.UserVariablesDataSource
 */
	@Override
	public void addUserDefinedVariable(String varName, String userInput) {
		BoardState.getCurrentState().addUserDefinedVariable(varName, userInput);
	}

	@Override
	public String getUserDefinedVariable(String key) {
		return BoardState.getCurrentState().getUserDefinedVariables().get(key);
	}
	
}
