package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

/**
 * @author Andrew Bihl, Eric Song
 */

public class BoardState{
	private ArrayList<PathLine> lineCoordinates;
	private HashMap<String, String> userDefinedVariables;
	private HashMap<Integer, RGBColor> colorMap;
	private int backgroundColorIndex;
	
	
	public BoardState(){
		this.lineCoordinates= new ArrayList<PathLine>();
		this.userDefinedVariables = new HashMap<String, String>();
		this.colorMap = new HashMap<Integer, RGBColor>();
		this.backgroundColorIndex = 0;
	}

	public List<PathLine> getLineCoordinates(){
		return lineCoordinates;
	}
	
	public void addLineCoordinates(PathLine line){
		lineCoordinates.add(line);
	}
	
	public void addUserDefinedVariable(String varName, String userInput){
		this.userDefinedVariables.put(varName, userInput);
	}
	
	public HashMap<String, String> getUserDefinedVariables(){
		return userDefinedVariables;
	}
	
	public RGBColor getBackgroundColor(){
		return colorMap.get(this.backgroundColorIndex);
	}

	public RGBColor getColorForIndex(int i){
		return colorMap.get(i);
	}
}
