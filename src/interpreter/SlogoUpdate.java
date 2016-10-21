package interpreter;

public class SlogoUpdate {
	private double rotationDelta;
	private double movementDelta;
	private Boolean turtleShouldDraw;
	private Boolean turtleShouldShow;
	
	public SlogoUpdate(){
		rotationDelta = 0;
		movementDelta = 0;
		turtleShouldDraw = null;
		turtleShouldShow = null;
	}
	
	public double getRotationDelta(){
		return rotationDelta;
	}
	
	public double getMovementDelta(){
		return movementDelta;
	}
	
	public boolean getTurtleShouldDraw(){
		return turtleShouldDraw;
	}
	
	public boolean getTurtleShouldShow(){
		return turtleShouldShow;
	}
	
//Interpreter should call these methods to update the data object as it handles commands
	
	public void rotateClockwise(double degrees){
		rotationDelta += degrees;
	}
	
	public void rotateCounterClockwise(double degrees){
		rotationDelta -= degrees;
	}
	
	public void moveForward(double pixels){
		movementDelta += pixels;
	}
	
	public void moveBackward(double pixels){
		movementDelta -= pixels;
	}
	
	public void showTurtle(){
		turtleShouldShow = true;
	}
	
	public void hideTurtle(){
		turtleShouldShow = false;
	}
	
	public void putPenDown(){
		turtleShouldDraw = true;
	}
	
	public void putPenUp(){
		turtleShouldDraw = false;
	}
	
}

