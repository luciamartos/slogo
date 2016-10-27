package gui;

import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import general.Properties;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
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
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.util.Duration;
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
	private List<PathLine> myPathLines;

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
		System.out.println("LUCIA");

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
	
	
	
	
	public void drawPath(){
		if(penDown){
	        gc.setStroke(myColor);
	    	gc.setLineWidth(myThickness);
	    	if(penType !=null){			//MAKE SURE ITS INITIALISED TO NOT NULL SO I CAN REMOVE THIS
	    		handleDifferentPenTypes();
	    	}
	    	
	        for(int i =0; i<myPathLines.size();i++){
		        gc.strokeLine(myPathLines.get(i).getX1(), myPathLines.get(i).getY1(), myPathLines.get(i).getX2(), myPathLines.get(i).getY2());
	        }
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
	
	public void animatedMovementToXY(){
		makeAnimationRotateTurtle();
        for(int i =0; i<myPathLines.size();i++){
	        makeAnimationMovementTurtle(myPathLines.get(i).getX1(), myPathLines.get(i).getY1(), myPathLines.get(i).getX2(), myPathLines.get(i).getY2());
        }
        return;
	}

	private Animation makeAnimationRotateTurtle() {
		System.out.println("LUCIA");
		RotateTransition rt = new RotateTransition(Duration.seconds(3));
        rt.setByAngle(heading);
        return new SequentialTransition(turtleImgView, rt);
	}
	
	 private Animation makeAnimationMovementTurtle( double x1, double y1, double x2, double y2) {
	        // create something to follow
	        Path path = new Path();
	        path.getElements().addAll(new MoveTo(x2, y2), new HLineTo(350));
	        // create an animation where the shape follows a path
	        PathTransition pt = new PathTransition(Duration.millis(4000), path, turtleImgView);
	        return new SequentialTransition(turtleImgView, pt);
	    }

	public void addTurtleAtXY() {		
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

	public void setPathLine(List<PathLine> pathLine) {
		myPathLines = pathLine;
	}

}
