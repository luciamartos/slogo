package model;

/**
 * @author Andrew Bihl
 */

public class PathLine {
	private double x1, y1, x2, y2;
	private double length;
	private RGBColor penColor;
	private double penThickness;
	private String penType;
	private int turtleID;
	
	public PathLine(double x1, double y1, double x2, double y2, int turtleID){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.length = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2-y1), 2));
		this.turtleID = turtleID;
	}
	
	//TODO: implement these methods
	public RGBColor getPenColor(){
		return this.penColor;
	}
	
	public double getPenThickness(){
		return this.penThickness;
	}
	
	public String getPenType(){
		return this.penType;
	}
	
	public double getLength(){
		return this.length;
	}
	
	public double getX1(){
		return this.x1;
	}
	
	public double getY1(){
		return this.y1;
	}
	
	public double getX2(){
		return this.x2;
	}
	
	public double getY2(){
		return this.y2;
	}
	
	public int getTurtleID(){
		return this.turtleID;
	}
	
}
