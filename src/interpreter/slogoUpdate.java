package interpreter;

public class slogoUpdate {
	private double angle;
	private double pixel;
	private boolean penUp;
	private boolean turtleIsShowing;
	
	public void setHeading(double heading){
		this.angle = heading;
	}
	
	public void setPixel(double pixel){
		this.pixel = pixel;
	}
	
	public void setPen(){
		this.penUp = !this.penUp;
	}
	
	public void setShowing(){
		this.turtleIsShowing = !this.turtleIsShowing;
	}
	
	public double getHeading(){
		return angle;
	}
	
	public double getPixel(){
		return pixel;
	}
	
	public boolean getPenUp(){
		return penUp;
	}
	
	public boolean getShowing(){
		return turtleIsShowing;
	}
}
