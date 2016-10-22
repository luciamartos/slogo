package interpreter;

import model.TurtleStateDataSource;

public class TurtleQueryInterpreter extends SubInterpreter{
	
	private SlogoUpdate model;
	
	TurtleQueryInterpreter(SlogoUpdate model){
		this.model = model;
	}
	
	double xcoordinate(){
		return model.getXCoordinate();
	}
	
	double ycoordinate(){
		return model.getYCoordinate();
	}
	
	double heading(){
		return model.getAngle();
	}
	
	double ispendown(){
		return (model.getTurtleShouldDraw()) ? 1 : 0;
	}
	
	double isshowing(){
		return (model.getTurtleShouldShow()) ? 1 : 0;
	}
}
