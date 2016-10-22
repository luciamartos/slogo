package interpreter;

public class TurtleCommandInterpreter extends SubInterpreter{

	/**
	 * fd=Forward
		bk=Backward
		lt=Left
		rt=Right
		seth=SetHeading
		towards=SetTowards
		setxy=SetPosition
		pd=PenDown
		pu=PenUp
		st=ShowTurtle
		ht=HideTurtle
		home=Home
		cs=ClearScreen
	 */
	
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
		model.setAngle(degrees);
		//TODO: difference between curr angle and new angle; how do I get curr angle?
		return Math.abs(0-degrees);
	}
	
	double settowards(double x, double y){
		model.turnToward(x, y);
		//TODO: return number of degrees that turtle turned
		return 0;
	}
	
	double setxy(double x, double y){
		model.moveTo(x, y);
		//TODO: replace 0 with (x,y) coordinate of when the turtle first started.
		return Math.abs(x-0) + Math.abs(y-0);
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
		model.moveTo(0, 0);
		//TODO: replace 1 with where turtle first started off
		return Math.abs(1-0) + Math.abs(1-0);
	}
	
	
	double clearScreen(){
		//TODO: how do I erase the turtle trail?
		
		model.moveTo(0, 0);
		//TODO: replace 1 with where turtle first started off
		return Math.abs(1-0) + Math.abs(1-0);
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
