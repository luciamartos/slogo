package model;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import general.Properties;
import gui.BoardActionsHandler;
import gui.BoardStateDataSource;
import interpreter.BoardStateUpdater;
import interpreter.UserVariablesDataSource;
import javafx.scene.paint.Color;

/**
 * @author Andrew Bihl
 */

public class BoardStateController extends Observable implements BoardStateDataSource, BoardStateUpdater, UserVariablesDataSource, BoardActionsHandler{
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
		this.turtleController = new TurtleStatesController();
	}
	
	public void addBoardStateListener(Observer o){
		this.addObserver(o);
		setChanged();
		this.notifyObservers();
	}
	
/*
 * gui.BoardStateDataSource 
 */
	@Override
	public Iterator<PathLine> getPaths() {
		return boardState.getLineCoordinates().iterator();
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
	
	public TurtleStatesController getTurtleStatesController(){
		return this.turtleController;
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBackgroundColor(int colorIndex) {
		this.boardState.setBackgroundColorIndex(colorIndex);
	}

	@Override
	public void addColorToPalette(int index, int red, int green, int blue) {
		RGBColor color = new RGBColor(red, green, blue);
		this.boardState.addColorToMap(color, index);
	}

	@Override
	public Map<Integer, RGBColor> getColorMap() {
		return boardState.getColorMap();
	}
}
