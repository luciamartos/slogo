package model;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import general.Properties;
import gui.BoardStateDataSource;
import interpreter.SlogoUpdate;
import interpreter.TurtleStateDataSource;
import interpreter.TurtleStateUpdater;
import interpreter.UserVariablesDataSource;

/**
 * @author Andrew Bihl
 */

public class BoardStateController implements TurtleStateDataSource, BoardStateDataSource, TurtleStateUpdater, UserVariablesDataSource {
	private final String VIEW_PROPERTIES_FILE_PATH = "resources.properties.View";
	private final String BOARD_WIDTH_KEY = "canvas_width";
	private final String BOARD_HEIGHT_KEY = "canvas_height";
	private double maxXCoordinate;
	private double minXCoordinate;
	private double maxYCoordinate;
	private double minYCoordinate;
	
	private class Coordinates{
		public double x;
		public double y;
		public Coordinates(double x, double y){
			this.x = x;
			this.y = y;
		}
	}
	
	public BoardStateController(){
		Properties visualProperties = new Properties(VIEW_PROPERTIES_FILE_PATH);
		double boardWidth = visualProperties.getDoubleProperty(BOARD_WIDTH_KEY);
		double boardHeight = visualProperties.getDoubleProperty(BOARD_HEIGHT_KEY);
		maxXCoordinate = boardWidth/2;
		minXCoordinate = -maxXCoordinate;
		maxYCoordinate = boardHeight/2;
		minYCoordinate = -maxYCoordinate;
	}
	
	public void applyChanges(SlogoUpdate changes){
		BoardState modelToUpdate = BoardState.getCurrentState();
		modelToUpdate.setAngle(changes.getAngle());
		modelToUpdate.setDrawing(changes.getTurtleShouldDraw());
		modelToUpdate.setShowing(changes.getTurtleShouldShow());
		Coordinates oldCoordinates = new Coordinates(modelToUpdate.getXCoordinate(), modelToUpdate.getYCoordinate());
		Coordinates newCoordinates = new Coordinates(changes.getXCoordinate(), changes.getYCoordinate());
		newCoordinates = calculateValidUpdatedCoordinates(oldCoordinates, newCoordinates, Math.toRadians(changes.getAngle()));
		modelToUpdate.setXCoordinate(newCoordinates.x);
		modelToUpdate.setYCoordinate(newCoordinates.y);
		PathLine line = new PathLine(oldCoordinates.x, oldCoordinates.y, modelToUpdate.getXCoordinate(), modelToUpdate.getYCoordinate());
		if (modelToUpdate.isDrawing()){
			modelToUpdate.addLineCoordinates(line);
		}
		modelToUpdate.setDistanceMoved(modelToUpdate.getDistanceMoved() + line.getLength());
		modelToUpdate.notifyObservers(this);
	}
	
	public void addBoardStateListener(Observer o){
		BoardState.getCurrentState().addObserver(o);
		BoardState.getCurrentState().notifyObservers(this);
	}

	//Restrict movement to the bounds of the board.
	private Coordinates calculateValidUpdatedCoordinates(Coordinates current, Coordinates updated, double thetaInRadians){
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

	@Override
	public void resetBoard() {
		// TODO Auto-generated method stub
		
	}
	
}
