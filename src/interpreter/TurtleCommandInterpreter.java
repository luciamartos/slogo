package interpreter;

public class TurtleCommandInterpreter extends SubInterpreter{
	
	private SlogoUpdate model;
	
	TurtleCommandInterpreter(SlogoUpdate model){
		this.model = model;
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
		double temp = model.getAngle();
		model.setAngle(degrees);
		return Math.abs(temp-degrees);
	}
	
	double settowards(double x, double y){
		model.turnToward(x, y);
		//TODO: return number of degrees that turtle turned
		return 0;
	}
	
	double setxy(double x, double y){
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
		//TODO: how do I erase the turtle trail?
		double tempX = model.getXCoordinate();
		double tempY = model.getYCoordinate();
		model.moveTo(0, 0);
		return Math.abs(tempX) + Math.abs(tempY);
	}
	
	SlogoUpdate getModel(){
		return model;
	}
	
	boolean isNonInputTurtleCommand(String input){
		return input.equalsIgnoreCase(rb.getString("pd")) || input.equalsIgnoreCase(rb.getString("pu")) ||
				input.equalsIgnoreCase(rb.getString("st")) || input.equalsIgnoreCase(rb.getString("ht")) ||
				input.equalsIgnoreCase(rb.getString("home")) || input.equalsIgnoreCase(rb.getString("cs"));
	}
	
	boolean isUnaryTurtleCommand(String input){
		return input.equalsIgnoreCase(rb.getString("fd")) || input.equalsIgnoreCase(rb.getString("bk")) ||
				input.equalsIgnoreCase(rb.getString("lt")) || input.equalsIgnoreCase(rb.getString("rt")) ||
				input.equalsIgnoreCase(rb.getString("seth"));
	}
	
	boolean isBinaryTurtleCommand(String input){
		return input.equalsIgnoreCase(rb.getString("towards")) || input.equalsIgnoreCase(rb.getString("setxy"));
	}

}
