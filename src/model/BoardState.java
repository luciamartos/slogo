package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Methods and variables have been added to this class for the stamp and clearStamp functionalities.
 * The frontend will use the turtleStamps List in this class to display stamps on the screen.
 * 
 * @author Andrew Bihl, Eric Song, Ray Song
 */

public class BoardState{
	private ArrayList<PathLine> lineCoordinates;
	private HashMap<String, String> userDefinedVariables;
	private HashMap<Integer, RGBColor> colors;
	private int backgroundColorIndex;
	private ArrayList<TurtleStamp> turtleStamps;
	
	
	BoardState(){
		setToDefaultValues();
	}
	
	void setToDefaultValues(){
		this.lineCoordinates= new ArrayList<PathLine>();
		this.userDefinedVariables = new HashMap<String, String>();
		this.colors = new HashMap<Integer, RGBColor>();
		this.turtleStamps = new ArrayList<TurtleStamp>();
		RGBColor white = new RGBColor(255,255,255);
		RGBColor black = new RGBColor(0,0,0);
		RGBColor red = new RGBColor(255, 0, 0);
		this.colors.put(0, white);
		this.colors.put(1, black);
		this.colors.put(2,red);
		this.backgroundColorIndex = 0;
	}

	List<PathLine> getLineCoordinates(){
		return lineCoordinates;
	}
	
	void addLineCoordinates(PathLine line){
		lineCoordinates.add(line);
	}
	
	public List<TurtleStamp> getStamps(){
		return turtleStamps;
	}
	
	public void addTurtleStamp(TurtleStamp stamp){
		turtleStamps.add(stamp);
	}
	
	public void clearTurtleStamp(){
		turtleStamps.clear();
	}
	
	void addUserDefinedVariable(String varName, String userInput){
		this.userDefinedVariables.put(varName, userInput);
	}
	
	HashMap<String, String> getUserDefinedVariables(){
		return userDefinedVariables;
	}
	
	int getBackgroundColorIndex(){
		return this.backgroundColorIndex;
	}

	void setBackgroundColorIndex(int index){
		this.backgroundColorIndex = index;
	}
	
	void addColorToMap(RGBColor color, int index){
		this.colors.put(index, color);
	}
	
	RGBColor getColorForIndex(int i){
		return colors.get(i);
	}
	
	HashMap<Integer, RGBColor> getColorMap(){
		return this.colors;
	}
}
