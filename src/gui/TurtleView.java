package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * 
 * @author LuciaMartos
 */
public class TurtleView {
	private double xPos;
	private double yPos;
	private boolean showTurtle;
	private ImageView myImageView;
	private Image myImage;
	private Color penColor;
	private boolean penDown;
	private double heading;

	public TurtleView(double x, double y, Image image, boolean turtleStatus, Color myColor) {
		xPos = x;
		yPos = y;
		showTurtle = turtleStatus;
		penColor = myColor;
		setImage(image);
	}

	public boolean getPenDown() {
		return penDown;
	}

	public void setPenDown(boolean penPos) {
		penDown = penPos;
	}

	public double getHeading() {
		return heading;
	}

	public void setHeading(double direction) {
		heading = direction;
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

	protected void setPenColor(Color myColor) {
		penColor = myColor;
	}

	protected Color getPenColor() {
		return penColor;
	}

	protected void setImage(Image image) {
		myImage = image;
		myImageView = new ImageView(myImage);
	}

}
