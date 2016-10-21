package interpreter;

public class TurtleCommandInterpreter extends SubInterpreter{

	private slogoUpdate model;
	
	TurtleCommandInterpreter(slogoUpdate model){
		this.model = model;
	}
	
	double forward(double pixels){
		return pixels;
	}
	
	
	double clearScreen(){
		//erase trail of the turtle
		//return total distance travelled
		return 0;
	}
	
	slogoUpdate getModel(){
		return model;
	}

}
