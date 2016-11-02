package gui;

import java.util.ArrayList;
import java.util.List;

import model.PathLine;

/**
 * @author Lucia Martos
 */

public class TurtleDataTranslator {
	private double width;
	private double height;
	private double imageWidth;
	private double imageHeight;
	
	public TurtleDataTranslator(double canvasWidth, double canvasHeight, double imWidth, double imHeight) {
		// TODO Auto-generated constructor stub
		width = canvasWidth;
		height = canvasHeight;
		imageWidth = imWidth;
		imageHeight = imHeight;
	}
	
	public double convertXImageCordinate(double xCor){
			return width/2 + xCor-imageWidth/2;
	}
	
	public double convertYImageCordinate(double yCor){
			return height/2 - yCor-imageHeight/2;
	}
	
	
	public double convertXCordinate(double xCor){
			return width/2 + xCor;
	}
	
	public double convertYCordinate(double yCor){
			return height/2 - yCor;
	}
	
	public List<PathLine> convertLineCordinates(List<PathLine> myPath){
		List<PathLine> myRes = new ArrayList<PathLine>();
		for(int i = 0; i < myPath.size(); i++){
			PathLine path = myPath.get(i);
			myRes.add(new PathLine(convertXCordinate(path.getX1()), convertYCordinate(path.getY1()), convertXCordinate(path.getX2()), convertYCordinate(path.getY2()), path.getTurtleID()));
		}
		return myRes;
	}
	
	public double convertAngle(double degree){
		return (-degree + 90);
	}

}
