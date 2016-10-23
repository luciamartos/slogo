package gui;

import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import general.Properties;
import javafx.animation.SequentialTransition;
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
import model.PathLine;

public class CanvasActions{
	private static final String COLOR_CANVAS = "white";
	private static final String IMAGE_PATH = "resources/images/";
	private GraphicsContext gc;
	private Canvas canvas;
	private Pane pane;
	private Color myColor;
	private ImageView turtleImgView;
	private boolean penDown;
	private boolean showTurtle;
	private double heading;
	private double xLoc;
	private double yLoc;


	public CanvasActions(double canvasX, double canvasY, double canvasWidth, double canvasHeight, double canvasLayoutX,
			double canvasLayoutY, double errorLabelX, double errorLabelY){
		initializePane(canvasWidth, canvasHeight, canvasLayoutX, canvasLayoutY);
		initializeCanvas(canvasX, canvasY, canvasWidth, canvasHeight, canvasLayoutX, canvasLayoutY);
		pane.getChildren().addAll(canvas);
		gc = canvas.getGraphicsContext2D(); // TODO: make a more descriptive
											// name
		initializeTurtle();
	}

	public void setBackgroundColorCanvas(String myColor) {
		pane.setStyle("-fx-background-color: " + myColor + ";");
	}

	private void initializeCanvas(double canvasX, double canvasY, double canvasWidth, double canvasHeight,
			double canvasLayoutX, double canvasLayoutY) {
		//TODO: delete all x and y's
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
//		showTurtle = true;
//		heading = 90;
//		xLoc = canvas.getWidth() / 2;
//		yLoc = canvas.getHeight() / 2;
		
		turtleImgView = new ImageView(new Image(IMAGE_PATH + "turtle.png", 50, 50, true, true));
	//	myTurtle = new TurtleView(canvas.getWidth() / 2, canvas.getHeight() / 2, turtleImg, true, Color.BLACK);
		addTurtleAtXY();
	}

	public void changeImage(Image image, double xLoc, double yLoc) {
		setTurtleImage(image, xLoc, yLoc);
	}
	
	public void setHeading(double degrees){
		heading = degrees;
	}
	
	public void setXandYLoc(double xLocation, double yLocation){
		xLoc = xLocation;
		yLoc = yLocation;
	}
	
	public void drawPath(List<PathLine> myCords){
		if(!penDown){
	        gc.setStroke(myColor);
	        for(int i =0; i<myCords.size();i++){
		        gc.strokeLine(myCords.get(i).getX1(), myCords.get(i).getY1(), myCords.get(i).getX2(), myCords.get(i).getY2());
	        }
		}	
	}

	public void addTurtleAtXY() {
		//note that when initialised myController cannot be null
	//	 turtleImgView = new ImageView( new Image(Main.class.getResourceAsStream("a.jpg")));
		turtleImgView.setRotate(heading);
		turtleImgView.setTranslateX(xLoc);
		turtleImgView.setTranslateY(yLoc);
		if(showTurtle){
			pane.getChildren().add(turtleImgView);
		}
	}

	public void removeTurtle() {
		pane.getChildren().remove(turtleImgView);
	}

	//METHOD NEVER BEING CALLED RN
	public void moveTurtle() {
		removeTurtle();
		addTurtleAtXY();
	}

	public void setShowTurtle(boolean isShowing){
		showTurtle = isShowing;
	}

	public void setPenDown(boolean penPos) {
		penDown = penPos;
	}

	//where is the method that takes in the string?
	public void setTurtleImage(Image image, double xLoc, double yLoc) {
		removeTurtle();
		turtleImgView = new ImageView(new Image(IMAGE_PATH + image, 50, 50, true, true));
		addTurtleAtXY();
	}

	public void setPenColor(Color color) {
		myColor = color;
	}

}
