package slogo_update;

public class slogoUpdate {
	private double heading;
	private double pixel;
	private boolean penUp;
	
	private void setHeading(double heading){
		this.heading = heading;
	}
	
	private void setPixel(double pixel){
		this.pixel = pixel;
	}
	
	private void setPen(){
		this.penUp = !this.penUp;
	}
}
