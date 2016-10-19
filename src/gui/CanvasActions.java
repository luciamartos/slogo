package gui;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class CanvasActions {
	private static final double PADDING = 15;
	private static final double TITLE_BOX_HEIGHT = 40;
	private static final double APP_WIDTH = 600;
	private static final double APP_HEIGHT = 600;
	private static final String COLOR_CANVAS = "white";
	private GraphicsContext gc;
	private Canvas canvas;
	private Pane pane;
	private Turtle myTurtle;
	
	public CanvasActions() {
		initializePane();
		initializeCanvas();
		pane.getChildren().addAll(canvas);
		gc = canvas.getGraphicsContext2D(); // TODO: make a more descriptive name
		initializeTurtle();
	}
	
	public void setBackgroundColorCanvas(String myColor){
		pane.setStyle("-fx-background-color: " + myColor);
	}

	private void initializeCanvas() {
		canvas = new Canvas(APP_WIDTH - 300, APP_HEIGHT-400);
		canvas.setWidth(APP_WIDTH - 160);
		canvas.setHeight(APP_HEIGHT-120);
		canvas.setLayoutX(PADDING);
		canvas.setLayoutY(PADDING*2 + TITLE_BOX_HEIGHT);
	}

	private void initializePane() {
		pane = new Pane();
		setBackgroundColorCanvas(COLOR_CANVAS);
	    pane.setPrefSize(APP_WIDTH - 160, APP_HEIGHT-120); //QUESTION! DO I HAVE TO SET UP FOR BOTH?
	    pane.setLayoutX(PADDING);
	    pane.setLayoutY(PADDING*2 + TITLE_BOX_HEIGHT);
	}
	
	public Pane getPane(){
		return pane;
	}
	public Canvas getCanvas(){
		return canvas;
	}
	
	private void initializeTurtle() {
		 myTurtle = new Turtle(canvas.getWidth() / 2, canvas.getHeight() / 2, "turtle.png", true, Color.BLACK);
		// better way to get the lengths and sizes
		addTurtleAtXY();
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


	public void moveTurtle(int x, int y) {
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
	
	public void putPenDown(){
		myTurtle.setPenDown(true);
	}
	
	public void putPenUp(){
		myTurtle.setPenDown(false);
	}
	
	//TODO: error which clears the message
	public void displayErrorMessage(String myError){
		final Label label = new Label();
		label.setLayoutX(canvas.getWidth()/2);
		label.setLayoutY(canvas.getHeight()/2);
		label.setFont(Font.font("Verdana", 50));
		label.setText(myError);
		pane.getChildren().add(label);
	}
	
	public void changeTurtleImage(String myFile, Turtle myTurtle){
		myTurtle.setMyImage(myFile);
	}
	
	public void setPenColor(Turtle myTurtle, Color myColor){
		myTurtle.setPenColor(myColor);
	}


}
