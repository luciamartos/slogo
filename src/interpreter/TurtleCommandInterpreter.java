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
	
	
	double clearScreen(){
		//erase trail of the turtle
		//return total distance travelled
		return 0;
	}
	
	SlogoUpdate getModel(){
		return model;
	}

}
