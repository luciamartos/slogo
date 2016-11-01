package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Andrew Bihl, Eric Song
 */

public class BoardState{
	private ArrayList<PathLine> lineCoordinates;
	private HashMap<String, String> userDefinedVariables;
	private HashMap<Integer, RGBColor> colors;
	private int backgroundColorIndex;
	
	
	BoardState(){
		this.lineCoordinates= new ArrayList<PathLine>();
		this.userDefinedVariables = new HashMap<String, String>();
		this.colors = new HashMap<Integer, RGBColor>();
		this.backgroundColorIndex = 0;
	}

	List<PathLine> getLineCoordinates(){
		return lineCoordinates;
	}
	
	void addLineCoordinates(PathLine line){
		lineCoordinates.add(line);
	}
	
	void addUserDefinedVariable(String varName, String userInput){
		this.userDefinedVariables.put(varName, userInput);
	}
	
	HashMap<String, String> getUserDefinedVariables(){
		return userDefinedVariables;
	}
	
	RGBColor getBackgroundColor(){
		return colors.get(this.backgroundColorIndex);
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
