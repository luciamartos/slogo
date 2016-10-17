package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * 
 * @author LuciaMartos
 */
public class Turtle {
	private double xPos;
	private double yPos;
	private boolean showTurtle;
	private ImageView myImageView;
	private Image myImage;

	public Turtle(double x, double y, String imageName, boolean turtleStatus) {
		xPos = x;
		yPos = y;
		showTurtle = turtleStatus;
		myImage = new Image(getClass().getClassLoader().getResourceAsStream(imageName), 50, 50, true, true);
		myImageView = new ImageView(myImage);
	}

	protected double getXPos() {
		return xPos;
	}

	protected void setXPos(double x) {
		xPos = x;
	}

	protected double getYPos() {
		return yPos;
	}

	protected void setYPos(double y) {
		yPos = y;
	}

	protected boolean getShowTurtle() {
		return showTurtle;
	}

	protected void setShowTurtle(boolean state) {
		showTurtle = state;
	}

	protected ImageView getMyImageView() {
		return myImageView;
	}

	protected Image getMyImage() {
		return myImage;
	}
	
	protected void setMyImage(String nameImage) {
		myImage = new Image(getClass().getClassLoader().getResourceAsStream("turtle.png"), 50, 50, true, true);
		myImageView = new ImageView(myImage);
	}

}
