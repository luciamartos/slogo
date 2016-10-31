package model;

import java.util.HashMap;

import interpreter.SlogoUpdate;
import interpreter.TurtleStateDataSource;
import interpreter.TurtleStateUpdater;
import model.BoardStateController.Coordinates;

public class TurtleStatesController implements TurtleStateDataSource, TurtleStateUpdater {
	private HashMap<Integer, TurtleState> turtles;
	
	public TurtleStatesController(){
		this.turtles = new HashMap<Integer, TurtleState>();
	}
	
	void addNewTurtle(){
		TurtleState turtle = new TurtleState();
		turtles.put(turtles.size(), turtle);
	}
	
	void addNewTurtle(int id){
		TurtleState turtle = new TurtleState();
		turtles.put(id, turtle);
	}

	@Override
	public void applyChanges(SlogoUpdate update) {
		for (TurtleState turtle : this.turtles.values()){
			this.applyChangesToTurtle(turtle, update);
		}
	}
	
	private void applyChangesToTurtle(TurtleState turtle, SlogoUpdate changes){
		turtle.setAngle(changes.getAngle());
		turtle.setDrawing(changes.getTurtleShouldDraw());
		turtle.setShowing(changes.getTurtleShouldShow());
		Coordinates oldCoordinates = new Coordinates(boardState.getXCoordinate(), boardState.getYCoordinate());
		Coordinates newCoordinates = new Coordinates(changes.getXCoordinate(), changes.getYCoordinate());
		newCoordinates = calculateValidUpdatedCoordinates(oldCoordinates, newCoordinates, Math.toRadians(changes.getAngle()));
		turtle.setXCoordinate(newCoordinates.x);
		turtle.setYCoordinate(newCoordinates.y);
		PathLine line = new PathLine(oldCoordinates.x, oldCoordinates.y, turtle.getXCoordinate(), turtle.getYCoordinate());
		if (turtle.isDrawing()){
			turtle.addLineCoordinates(line);
		}
		turtle.setDistanceMoved(boardState.getDistanceMoved() + line.getLength());
	}

	@Override
	public void resetBoard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getXCoordinate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getYCoordinate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAngle() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getTurtleIsShowing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getTurtleIsDrawing() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
