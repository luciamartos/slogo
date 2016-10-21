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
	private GraphicsContext gc;
	private Canvas canvas;
	private Pane pane;
	private Turtle myTurtle;
	private Properties viewProperties;
	
	public CanvasActions(Properties viewProperties) {
		this.viewProperties = viewProperties;
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
		canvas = new Canvas(viewProperties.getDoubleProperty("canvas_x"), viewProperties.getDoubleProperty("canvas_y"));
		canvas.setWidth(viewProperties.getDoubleProperty("canvas_width"));
		canvas.setHeight(viewProperties.getDoubleProperty("canvas_height"));
		canvas.setLayoutX(viewProperties.getDoubleProperty("canvas_layout_x"));
		canvas.setLayoutY(viewProperties.getDoubleProperty("canvas_layout_y"));
	}

	private void initializePane() {
		pane = new Pane();
		setBackgroundColorCanvas(COLOR_CANVAS);
	    pane.setPrefSize(viewProperties.getDoubleProperty("canvas_width"), viewProperties.getDoubleProperty("canvas_height"));
	    pane.setLayoutX(viewProperties.getDoubleProperty("canvas_layout_x"));
	    pane.setLayoutY(viewProperties.getDoubleProperty("canvas_layout_y"));
	}
	
	public Pane getPane(){
		return pane;
	}
	public Canvas getCanvas(){
		return canvas;
	}
	
	private void initializeTurtle() {
		Image turtleImg = new Image("resources/images/turtle.png", 50, 50, true, true);		
		 myTurtle = new Turtle(canvas.getWidth() / 2, canvas.getHeight() / 2, turtleImg, true, Color.BLACK);
		// better way to get the lengths and sizes
		addTurtleAtXY();
	}
	
	public void changeImage(File file) {
		
		try {
			Image img = new Image(file.getName(), 50, 50, true, true);			
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
	
	public void putPenDown(){
		myTurtle.setPenDown(true);
	}
	
	public void putPenUp(){
		myTurtle.setPenDown(false);
	}
	
	//TODO: error which clears the message
	public void displayErrorMessage(String myError){
		final Label label = new Label();
		label.setLayoutX(0);
		label.setLayoutY(canvas.getHeight()/2);
		label.setFont(Font.font("Verdana", 30));
		label.setText(myError);
		pane.getChildren().add(label);
	}
	
	public void setTurtleImage(Image image){
		myTurtle.setImage(image);
		moveTurtle(myTurtle.getXPos(),myTurtle.getYPos());
	}
	
	public void setPenColor(Turtle myTurtle, Color myColor){
		myTurtle.setPenColor(myColor);
	}


}
