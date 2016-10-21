package interpreter;

public class TurtleCommandInterpreter extends SubInterpreter{

	private SlogoUpdate model;
	
	TurtleCommandInterpreter(SlogoUpdate model){
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
	
	SlogoUpdate getModel(){
		return model;
	}

}
