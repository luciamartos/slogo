package gui;

import java.io.File;
import java.util.ResourceBundle;

import general.Properties;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CanvasActions {
	private static final String COLOR_CANVAS = "white";
	private static final String IMAGE_PATH = "resources/images/";
	private GraphicsContext gc;
	private Canvas canvas;
	private Pane pane;
	private Turtle myTurtle;

	public CanvasActions(double canvasX, double canvasY, double canvasWidth, double canvasHeight, double canvasLayoutX,
			double canvasLayoutY) {
		initializePane(canvasWidth, canvasHeight, canvasLayoutX, canvasLayoutY);
		initializeCanvas(canvasX, canvasY, canvasWidth, canvasHeight, canvasLayoutX, canvasLayoutY);
		pane.getChildren().addAll(canvas);
		gc = canvas.getGraphicsContext2D(); // TODO: make a more descriptive
											// name
		initializeTurtle();
	}

	public void setBackgroundColorCanvas(String myColor) {
		pane.setStyle("-fx-background-color: " + myColor);
	}

	private void initializeCanvas(double canvasX, double canvasY, double canvasWidth, double canvasHeight,
			double canvasLayoutX, double canvasLayoutY) {
		canvas = new Canvas(canvasX, canvasY);
		canvas.setWidth(canvasWidth);
		canvas.setHeight(canvasHeight);
		canvas.setLayoutX(canvasLayoutX);
		canvas.setLayoutY(canvasLayoutY);
	}

	private void initializePane(double canvasWidth, double canvasHeight, double canvasLayoutX, double canvasLayoutY) {
		pane = new Pane();
		setBackgroundColorCanvas(COLOR_CANVAS);
		pane.setPrefSize(canvasWidth, canvasHeight);
		pane.setLayoutX(canvasLayoutX);
		pane.setLayoutY(canvasLayoutY);
	}

	public Pane getPane() {
		return pane;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	private void initializeTurtle() {
		Image turtleImg = new Image(IMAGE_PATH+"turtle.png", 50, 50, true, true);
		myTurtle = new Turtle(canvas.getWidth() / 2, canvas.getHeight() / 2, turtleImg, true, Color.BLACK);
		// better way to get the lengths and sizes
		addTurtleAtXY();
	}

	public void changeImage(File file) {

		try {
			Image img = new Image(IMAGE_PATH+file.getName(), 50, 50, true, true);
			setTurtleImage(img);
		} catch (Exception e) {
			displayErrorMessage("The file you selected is not a valid image file.");
		}

	}

	private void addTurtleAtXY() {
		ImageView turtleImage = myTurtle.getMyImageView();
		turtleImage.setTranslateX(myTurtle.getXPos());
		turtleImage.setTranslateY(myTurtle.getYPos());
		pane.getChildren().add(turtleImage);
	}

	public void removeTurtle() {
		pane.getChildren().remove(myTurtle.getMyImageView());
	}

	public void moveTurtle(double x, double y) {
		removeTurtle();
		addTurtleAtXY();
	}

	public void hideTurtle() {
		myTurtle.setShowTurtle(false);
		removeTurtle();
	}

	public void showTurtle() {
		myTurtle.setShowTurtle(true);
		addTurtleAtXY();
	}

	public void putPenDown() {
		myTurtle.setPenDown(true);
	}

	public void putPenUp() {
		myTurtle.setPenDown(false);
	}

	// TODO: error which clears the message
	public void displayErrorMessage(String myError) {
		final Label label = new Label();
		label.setLayoutX(0);
		label.setLayoutY(canvas.getHeight() / 2);
		label.setFont(Font.font("Verdana", 30));
		label.setText(myError);
		pane.getChildren().add(label);
	}

	public void setTurtleImage(Image image) {
		removeTurtle();
		myTurtle.setImage(image);
		addTurtleAtXY();
	}

	public void setPenColor(Turtle myTurtle, Color myColor) {
		myTurtle.setPenColor(myColor);
	}

}
