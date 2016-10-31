package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import interpreter.SlogoUpdate;
import interpreter.TurtleStateDataSource;
import interpreter.TurtleStateUpdater;
import javafx.scene.paint.Color;

public class TurtleStatesController implements interpreter.TurtleStateDataSource, gui.TurtleStateDataSource, TurtleStateUpdater {
	private BoardStateController board;
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
		if (update.getTurtles().size() > 0){
			for (Integer turtleID : update.getTurtles()){
				TurtleState turtle = turtles.get(turtleID);
				this.applyChangesToTurtle(turtle, update);
			}
		}
		else{
			for (TurtleState turtle : this.turtles.values()){
				if (turtle.isActive()){
					this.applyChangesToTurtle(turtle, update);
				}
			}
		}
	}
	
	private void applyChangesToTurtle(TurtleState turtle, SlogoUpdate changes){
		turtle.setAngle(changes.getAngle());
		turtle.setDrawing(changes.getTurtleShouldDraw());
		turtle.setShowing(changes.getTurtleShouldShow());
		Coordinates oldCoordinates = new Coordinates(turtle.getXCoordinate(), turtle.getYCoordinate());
		Coordinates newCoordinates = new Coordinates(changes.getXCoordinate(), changes.getYCoordinate());
		newCoordinates = calculateValidUpdatedCoordinates(oldCoordinates, newCoordinates, Math.toRadians(changes.getAngle()));
		turtle.setXCoordinate(newCoordinates.x);
		turtle.setYCoordinate(newCoordinates.y);
		PathLine line = new PathLine(oldCoordinates.x, oldCoordinates.y, turtle.getXCoordinate(), turtle.getYCoordinate());
		if (turtle.isDrawing()){
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
	public Color getPenColor(int turtleID) {
		TurtleState turtle = this.turtles.get(turtleID);
		int index = turtle.getPenColorIndex();
		RGBColor rgb = this.board.getColorForIndex(index);
		return Color.color(rgb.getRed(), rgb.getGreen(), rgb.getBlue());
	}

	@Override
	public double getPenThickness(int turtleID) {
		TurtleState turtle = this.turtles.get(turtleID);
		return turtle.getPenSize();
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
}
