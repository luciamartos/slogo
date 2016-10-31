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
	private HashMap<Integer, RGBColor> colorMap;
	private int backgroundColorIndex;
	
	
	BoardState(){
		this.lineCoordinates= new ArrayList<PathLine>();
		this.userDefinedVariables = new HashMap<String, String>();
		this.colorMap = new HashMap<Integer, RGBColor>();
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
		return colorMap.get(this.backgroundColorIndex);
	}

	void setBackgroundColorIndex(int index){
		this.backgroundColorIndex = index;
	}
	
	RGBColor getColorForIndex(int i){
		return colorMap.get(i);
	}
}
