package gui;

import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import general.Properties;
import javafx.animation.SequentialTransition;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.PathLine;

public class CanvasActions{
	private static final Color COLOR_CANVAS = Color.WHITE;
	private static final String IMAGE_PATH = "resources/images/";
	private GraphicsContext gc;
	private Canvas canvas;
	private Pane pane;
	private Color myColor;
	private String penType;
	private double myThickness;
	private ImageView turtleImgView;
	private boolean penDown;
	private boolean showTurtle;
	private double heading;
	private double xLoc;
	private double yLoc;
	private double imageWidth;
	private double imageHeight;

	public CanvasActions(double canvasWidth, double canvasHeight, double imWidth, double imHeight){
		initializePane(canvasWidth, canvasHeight);
		initializeCanvas(canvasWidth, canvasHeight);
		imageWidth = imWidth;
		imageHeight = imHeight;
		pane.getChildren().addAll(canvas);
		gc = canvas.getGraphicsContext2D(); // TODO: make a more descriptive
											// name
		initializeTurtle();
	}

	public void setBackgroundColorCanvas(Color color) {
		pane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	private void initializeCanvas(double canvasWidth, double canvasHeight) {
		//TODO: delete all x and y's
		canvas = new Canvas();
		canvas.setWidth(canvasWidth);
		canvas.setHeight(canvasHeight);
	}

	private void initializePane(double canvasWidth, double canvasHeight) {
		pane = new Pane();
		setBackgroundColorCanvas(COLOR_CANVAS);
		pane.setPrefSize(canvasWidth, canvasHeight);
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

		turtleImgView = new ImageView(ViewImageChooser.selectImage(IMAGE_PATH + "turtle.png", imageWidth, imageHeight));

	//	myTurtle = new TurtleView(canvas.getWidth() / 2, canvas.getHeight() / 2, turtleImg, true, Color.BLACK);
		//addTurtleAtXY();
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
		//if(penDown){
      //  System.out.println(myCords.size());
	        gc.setStroke(myColor);
	    	gc.setLineWidth(myThickness);
	    	if(penType !=null){			//MAKE SURE ITS INITIALISED TO NOT NULL SO I CAN REMOVE THIS
	    		handleDifferentPenTypes();
	    	}
	    	
	        for(int i =0; i<myCords.size();i++){
		        gc.strokeLine(myCords.get(i).getX1(), myCords.get(i).getY1(), myCords.get(i).getX2(), myCords.get(i).getY2());
	     //   }
		}	
	}

	private void handleDifferentPenTypes() {
		if(penType.equals("dashed")){	//THESE ARENT WORKING EXACTLY HOW THEY SHOULD
			gc.setLineDashes(6.0f);
			gc.setLineDashOffset(0.0f);
		}
		if(penType.equals("dotted")){
			gc.setLineDashes(3.0f);
		}
		else{
			gc.setLineDashes(null);
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
		turtleImgView = new ImageView(image);
		addTurtleAtXY();
	}

	public void setPenColor(Color color) {
		myColor = color;
	}
	
	public void setPenThickness(double thickness){
		myThickness = thickness;
	}
	
	public void setPenType(String type){
		penType = type;
	}

}
