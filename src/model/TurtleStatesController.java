package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.function.Consumer;

import gui.TurtleActionsHandler;
import interpreter.SlogoUpdate;
import interpreter.TurtleStateDataSource;
import interpreter.TurtleStateUpdater;

public class TurtleStatesController extends Observable implements interpreter.TurtleStateDataSource, gui.TurtleStateDataSource, TurtleStateUpdater, TurtleActionsHandler {
	private BoardStateController board;
	private HashMap<Integer, TurtleState> turtles;
	
	private class Coordinates{
		double x;
		double y;
		public Coordinates(double x, double y){
			this.x = x;
			this.y = y;
		}
	}
	
	public TurtleStatesController(BoardStateController board){
		this.turtles = new HashMap<Integer, TurtleState>();
		addNewTurtle(0);
		this.board = board;
	}
	
	private TurtleState addNewTurtle(int id){
		TurtleState turtle = new TurtleState();
		turtles.put(id, turtle);
		return turtle;
	}

	@Override
	public void applyChanges(SlogoUpdate update) {
		TurtleState turtle = this.turtles.get(update.getTurtleID());
		if (turtle == null){
			turtle = this.addNewTurtle(update.getTurtleID());
		}
		this.applyChangesToTurtle(turtle, update);
		this.setChanged();
		this.notifyObservers();
	}
	
	private void applyChangesToTurtle(TurtleState turtle, SlogoUpdate changes){
		turtle.setAngle(changes.getAngle());
		turtle.setDrawing(changes.getTurtleShouldDraw());
		turtle.setShowing(changes.getTurtleShouldShow());
		turtle.setPenSize(changes.getPenSize());
		turtle.setPenColorIndex(changes.getPenColor());
		turtle.setShapeIndex(changes.getShape());
		Coordinates oldCoordinates = new Coordinates(turtle.getXCoordinate(), turtle.getYCoordinate());
		Coordinates newCoordinates = new Coordinates(changes.getXCoordinate(), changes.getYCoordinate());
		newCoordinates = calculateValidUpdatedCoordinates(oldCoordinates, newCoordinates, Math.toRadians(changes.getAngle()));
		turtle.setXCoordinate(newCoordinates.x);
		turtle.setYCoordinate(newCoordinates.y);
		PathLine line = new PathLine(oldCoordinates.x, oldCoordinates.y, turtle.getXCoordinate(), turtle.getYCoordinate(), changes.getTurtleID());
		if (turtle.isDrawing()){
			RGBColor penColor = board.getColorForIndex(turtle.getPenColorIndex());
			line.setPenColor(penColor);
			line.setPenThickness(turtle.getPenSize());
			line.setPenType(turtle.getPenType());
			board.addLine(line);
		}
		turtle.setDistanceMoved(turtle.getDistanceMoved() + line.getLength());
	}

	//Restrict movement to the bounds of the board.
	private Coordinates calculateValidUpdatedCoordinates(Coordinates current, Coordinates updated, double thetaInRadians){
		double maxXCoordinate = board.getMaxXCoordinate();
		double maxYCoordinate = board.getMaxYCoordinate();
		double minXCoordinate = board.getMinXCoordinate();
		double minYCoordinate = board.getMinYCoordinate();
		double rawHorizontalDelta = updated.x - current.x;
		double rawVerticalDelta = updated.y - current.y;
		double horizontalLeg = updated.x > maxXCoordinate ? maxXCoordinate - current.x : updated.x < minXCoordinate ? minXCoordinate-current.x : rawHorizontalDelta;
		double verticalLeg = updated.y > maxYCoordinate ? maxYCoordinate - current.y : updated.y < minYCoordinate ? minYCoordinate-current.y : rawVerticalDelta;
		
		Double hypotenuseWithHorizontalLeg = calculateHypotenuseWithHorizontalLeg(horizontalLeg, thetaInRadians);
		Double hypotenuseWithVerticalLeg = calculateHypotenuseWithVerticalLeg(verticalLeg, thetaInRadians);
		if (Math.abs(hypotenuseWithHorizontalLeg) <= Math.abs(hypotenuseWithVerticalLeg) || hypotenuseWithVerticalLeg.isNaN()){
			verticalLeg = calculateVerticalLegWithHypotenuse(hypotenuseWithHorizontalLeg, thetaInRadians);
		}
		else{
			horizontalLeg = calculateHorizontalLegWithHypotenuse(hypotenuseWithVerticalLeg, thetaInRadians);
		}
		double newX = current.x + horizontalLeg;
		double newY = current.y + verticalLeg;
		//Truncate to nearest pixel
		newX = (int)newX;
		newY = (int)newY;
		return new Coordinates(newX, newY);
	}
	
	private Double calculateHypotenuseWithHorizontalLeg(double legLength, double theta){
		return legLength / Math.cos(theta);
	}
	
	private Double calculateHypotenuseWithVerticalLeg(double legLength, double theta){
		return legLength / Math.sin(theta);
	}
	
	private double calculateHorizontalLegWithHypotenuse(double hypotenuseLength, double theta){
		return Math.cos(theta) * hypotenuseLength;
	}
	
	private double calculateVerticalLegWithHypotenuse(double hypotenuseLength, double theta){
		return Math.sin(theta) * hypotenuseLength;
	}

	@Override
	public int getShape(int turtleID) {
		TurtleState turtle = this.turtles.get(turtleID);
		return turtle.getShapeIndex();
	}

	@Override
	public double getXCoordinate(int turtleID) {
		TurtleState turtle = this.turtles.get(turtleID);
		return turtle.getXCoordinate();
	}

	@Override
	public double getYCoordinate(int turtleID) {
		TurtleState turtle = this.turtles.get(turtleID);
		return turtle.getYCoordinate();
	}

	@Override
	public double getAngle(int turtleID) {
		TurtleState turtle = this.turtles.get(turtleID);
		return turtle.getAngle();
	}

	@Override
	public boolean getTurtleIsShowing(int turtleID) {
		TurtleState turtle = this.turtles.get(turtleID);
		return turtle.isShowing();
	}

	@Override
	public boolean getTurtleIsDrawing(int turtleID) {
		TurtleState turtle = this.turtles.get(turtleID);
		return turtle.isDrawing();
	}

	@Override
	public Iterator<Integer> getTurtleIDs(){
		return this.turtles.keySet().iterator();
	}
	
	@Override
	public List<Integer> getActiveTurtleIDs() {
		ArrayList<Integer> turtleIDs = new ArrayList<Integer>();
		for (Integer turtleID : this.turtles.keySet()){
			TurtleState turtle = this.turtles.get(turtleID);
			if (turtle.isActive()){
				turtleIDs.add(turtleID);
			}
		}
		return turtleIDs;
	}

	@Override
	public void setPenColor(int color) {
		Consumer<TurtleState> changePenColor = (TurtleState turtle) -> {
			turtle.setPenColorIndex(color);
		};
		this.applyChangeToActiveTurtles(changePenColor);
	}

	@Override
	public void setPenThickness(int thickness) {
		Consumer<TurtleState> changePenThickness = (TurtleState turtle) -> {
			turtle.setPenSize(thickness);
		};
		this.applyChangeToActiveTurtles(changePenThickness);
	}

	@Override
	public void setPenType(int type) {
		Consumer<TurtleState> changePenType = (TurtleState turtle) -> {
			turtle.setPenType(type);
		};
		this.applyChangeToActiveTurtles(changePenType);
	}

	@Override
	public void setShape(int shape) {
		Consumer<TurtleState> changeShape = (TurtleState turtle) -> {
			turtle.setShapeIndex(shape);
		};
		this.applyChangeToActiveTurtles(changeShape);
	}

	@Override
	public void toggleTurtle(int id) {
		TurtleState turtle = this.turtles.get(id);
		turtle.setActive(!turtle.isActive());
		this.setChanged();
		this.notifyObservers();
	}
	
	private void applyChangeToActiveTurtles(Consumer<TurtleState> lambda){
		for (TurtleState turtle : this.turtles.values()){
			if (turtle.isActive()){
				lambda.accept(turtle);
			}
		}
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public int getPenColor(int turtleID) {
		TurtleState turtle = this.turtles.get(turtleID);
		return turtle.getPenColorIndex();
	}

	@Override
	public int getPenSize(int turtleID) {
		TurtleState turtle = this.turtles.get(turtleID);
		return turtle.getPenSize();
	}

	@Override
	public void setActiveTurtles(List<Integer> activeTurtles) {
		Consumer<TurtleState> setInactive = (TurtleState turtle) -> {
			turtle.setActive(false);
		};
		this.applyChangeToActiveTurtles(setInactive);
		for (Integer i : activeTurtles){
			TurtleState turtle = this.turtles.get(i);
			if (turtle == null){
				turtle = new TurtleState();
				this.turtles.put(i, turtle);
			}
			turtle.setActive(true);
		}
	}
}
