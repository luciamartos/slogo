package gui;

import java.util.ArrayList;
import java.util.List;

import model.PathLine;

/**
	 * This is a class to convert back end data to front end data
	 * 
	 */

public class TurtleDataTranslator {
	private double width;
	private double height;
	
	public TurtleDataTranslator(double canvasWidth, double canvasHeight) {
		// TODO Auto-generated constructor stub
		canvasWidth = width;
		canvasHeight = height;
	}
	
	public double convertXCordinate(double xCor){
		return xCor + width/2;
	}
	
	public double convertYCordinate(double yCor){
		return yCor + height/2;
	}
	
	public List<PathLine> convertLineCordinates(List<PathLine> myPath){
		List<PathLine> myRes = new ArrayList<PathLine>();
		for(int i = 0; i < myPath.size(); i++){
			myRes.add(new PathLine(convertXCordinate(myPath.get(i).getX1()), convertYCordinate(myPath.get(i).getY1()), convertXCordinate(myPath.get(i).getX2()), convertYCordinate(myPath.get(i).getY2())));
		}
		return myRes;
	}
	
	public double convertAngle(double degree){
		return degree - 90;
	}

}
