package interpreter;

public class TurtleCommandInterpreter extends SubInterpreter{
	
	private SlogoUpdate model;
	private TurtleStateUpdater stateUpdater;
	
	TurtleCommandInterpreter(SlogoUpdate model, TurtleStateUpdater stateUpdater){
		this.model = model;
		this.stateUpdater = stateUpdater;
	}
	
	double forward(double pixels){
		model.moveForward(pixels);
		return pixels;
	}
	
	double backward(double pixels){
		model.moveBackward(pixels);
		return pixels;
	}
	
	double left(double degrees){
		model.rotateCounterClockwise(degrees);
		return degrees;
	}
	
	double right(double degrees){
		model.rotateClockwise(degrees);
		return degrees;
	}
	
	double setheading(double degrees){
		return model.setAngle(degrees);
	}
	
	double settowards(double x, double y){
//		double tempX = model.getXCoordinate();
//		double tempY = model.getYCoordinate();
		return model.turnToward(x, y);
	}
	
	double setposition(double x, double y){
		double tempX = model.getXCoordinate();
		double tempY = model.getYCoordinate();
		model.moveTo(x, y);
		return Math.abs(x-tempX) + Math.abs(y-tempY);
	}
	
	double penup(){
		model.putPenUp();
		return 1;
	}
	
	double pendown(){
		model.putPenDown();
		return 0;
	}
	
	double showturtle(){
		model.show();
		return 1;
	}
	
	double hideturtle(){
		model.hide();
		return 0;
	}
	
	double home(){
		double tempX = model.getXCoordinate();
		double tempY = model.getYCoordinate();
		model.moveTo(0, 0);
		return Math.abs(tempX) + Math.abs(tempY);
	}
	
	
	double clearScreen(){
		double tempX = model.getXCoordinate();
		double tempY = model.getYCoordinate();
		model.moveTo(0, 0);
		stateUpdater.resetBoard();  //this will clear the trail of paths
		return Math.abs(tempX) + Math.abs(tempY);
	}
	
	SlogoUpdate getModel(){
		return model;
	}

}
