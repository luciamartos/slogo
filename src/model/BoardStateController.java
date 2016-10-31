package model;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import general.Properties;
import gui.BoardStateDataSource;
import interpreter.BoardStateUpdater;
import interpreter.SlogoUpdate;
import interpreter.UserVariablesDataSource;
import javafx.scene.paint.Color;

/**
 * @author Andrew Bihl, Eric Song
 */

public class BoardStateController extends Observable implements BoardStateDataSource, BoardStateUpdater, UserVariablesDataSource {
	private final String VIEW_PROPERTIES_FILE_PATH = "resources.properties.View";
	private final String BOARD_WIDTH_KEY = "canvas_width";
	private final String BOARD_HEIGHT_KEY = "canvas_height";
	private double maxXCoordinate;
	private double minXCoordinate;
	private double maxYCoordinate;
	private double minYCoordinate;
	private BoardState boardState;
	private TurtleStatesController turtleController;

	public BoardStateController(){
		boardState = new BoardState();
		Properties visualProperties = new Properties(VIEW_PROPERTIES_FILE_PATH);
		double boardWidth = visualProperties.getDoubleProperty(BOARD_WIDTH_KEY);
		double boardHeight = visualProperties.getDoubleProperty(BOARD_HEIGHT_KEY);
		maxXCoordinate = boardWidth/2;
		minXCoordinate = -maxXCoordinate;
		maxYCoordinate = boardHeight/2;
		minYCoordinate = -maxYCoordinate;
	}

	public void applyChanges(SlogoUpdate changes){
		setChanged();
		notifyObservers();
	}

	public void addBoardStateListener(Observer o){
		this.addObserver(o);
		setChanged();
		this.notifyObservers();
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
		return 0;
	}

	@Override
	public double getYCoordinate() {
		return 0;
	}

	@Override
	public double getAngle() {
		return 0;
	}

	@Override
	public boolean getTurtleIsShowing() {
		return true;
	}

	@Override
	public boolean getTurtleIsDrawing() {
		return true;
	}

	
/*
 * gui.BoardStateDataSource 
 */
	@Override
	public List<PathLine> getLineCoordinates() {
		return boardState.getLineCoordinates();
	}

	@Override
	public Map<String, String> getUserDefinedVariables() {
		return boardState.getUserDefinedVariables();
	}
	
/*
 * interpreter.UserVariablesDataSource
 */
	@Override
	public void addUserDefinedVariable(String varName, String userInput) {
		boardState.addUserDefinedVariable(varName, userInput);
	}

	@Override
	public String getUserDefinedVariable(String key) {
		return boardState.getUserDefinedVariables().get(key);
	}

	@Override
	public void resetBoard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getPenColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getBackgroundColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getPenThickness() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getImage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	double getMaxXCoordinate(){
		return this.maxXCoordinate;
	}
	
	double getMinXCoordinate(){
		return this.minXCoordinate;
	}
	
	double getMaxYCoordinate(){
		return this.maxYCoordinate;
	}
	
	double getMinYCoordinate(){
		return this.minYCoordinate;
	}
	
	void addLine(PathLine line){
		boardState.addLineCoordinates(line);
	}

	@Override
	public Color getBackgroundColor() {
		RGBColor rgb = boardState.getBackgroundColor();
		return Color.color(rgb.getRed(), rgb.getGreen(), rgb.getBlue());
	}

	@Override
	public void setBackgroundColorIndex(int i) {
		boardState.setBackgroundColorIndex(i);
	}
	
	RGBColor getColorForIndex(int i){
		return this.boardState.getColorForIndex(i);
	}
}
