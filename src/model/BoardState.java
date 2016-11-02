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
		setToDefaultValues();
	}
	
	void setToDefaultValues(){
		this.lineCoordinates= new ArrayList<PathLine>();
		this.userDefinedVariables = new HashMap<String, String>();
		this.colors = new HashMap<Integer, RGBColor>();
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
