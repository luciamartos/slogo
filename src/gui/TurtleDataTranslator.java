package gui;

	/**
	 * This is a class to convert back end data to front end data
	 * 
	 */

public class TurtleDataTranslator {
	
	
	public TurtleDataTranslator() {
		// TODO Auto-generated constructor stub
		
	}
	
	public double convertXCordinate(double xCor, double curX, double curY){
		return xCor + curX;
	}

}
