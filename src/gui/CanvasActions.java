package gui;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import XMLparser.XMLReader;
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
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.PathLine;
import model.RGBColor;

public class CanvasActions {
	private static final RGBColor COLOR_CANVAS = new RGBColor(255, 255, 255);
	private static final String IMAGE_PATH = "resources/images/";
	private GraphicsContext gc;
	private Canvas canvas;
	private Pane pane;
	private double imageWidth;
	private double imageHeight;
	// private Color myColor;
	// private String penType;
	// private double myThickness;
	// private ImageView turtleImgView;
	// private boolean penDown;
	// private boolean showTurtle;
	// private double heading;
	// private double xLoc;
	// private double yLoc;
	// private double prevTurtleImgViewX;
	// private double prevTurtleImgViewY;

	private List<PathLine> myPathLines;
	private Map<Integer, ImageView> map;

	public CanvasActions(double canvasWidth, double canvasHeight, double imWidth, double imHeight) {
		initializePane(canvasWidth, canvasHeight);
		initializeCanvas(canvasWidth, canvasHeight);
		imageWidth = imWidth;
		imageHeight = imHeight;
		pane.getChildren().addAll(canvas);
		gc = canvas.getGraphicsContext2D(); // TODO: make a more descriptive
											// name
		map = new HashMap<Integer, ImageView>();
	}

	public void setBackgroundColorCanvas(RGBColor rgbColor) {
		Color myColor = Color.rgb(rgbColor.getRed(), rgbColor.getGreen(), rgbColor.getBlue());
		pane.setBackground(new Background(new BackgroundFill(myColor, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	private void initializeCanvas(double canvasWidth, double canvasHeight) {
		// TODO: delete all x and y's
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

	// public void setHeading(double degrees) {
	// heading = degrees;
	// }
	//
	// public void setXandYLoc(double xLocation, double yLocation) {
	// xLoc = xLocation;
	// yLoc = yLocation;
	// }

	public void drawPath(Iterator<PathLine> pathLine) {
		while (pathLine.hasNext()) {
			PathLine currPathLine = pathLine.next();
			gc.setStroke(Color.rgb(currPathLine.getPenColor().getRed(), currPathLine.getPenColor().getGreen(),
					currPathLine.getPenColor().getBlue()));
			gc.setLineWidth(currPathLine.getPenThickness());
			gc.strokeLine(currPathLine.getX1(), currPathLine.getY1(), currPathLine.getX2(), currPathLine.getY2());

			handleDifferentPenTypes(currPathLine.getPenType());
		}

	}

	private void handleDifferentPenTypes(String penType) {
		if (penType.equals("dashed")) { // THESE ARENT WORKING EXACTLY HOW THEY
										// SHOULD
			gc.setLineDashes(6.0f);
			gc.setLineDashOffset(0.0f);
		}
		if (penType.equals("dotted")) {
			gc.setLineDashes(3.0f);
		} else {
			gc.setLineDashes(null);
		}
	}

	public void animatedMovementToXY(int id, double nextXLoc, double nextYLoc, double heading, boolean isShowing) {
		ImageView turtleImgView = map.get(id);
		turtleImgView.setVisible(isShowing);
		// turtleImgView.setTranslateX(xLoc);
		// turtleImgView.setTranslateY(yLoc);
		// pane.getChildren().add(turtleImgView);

//		if (heading != turtleImgView.getRotate())
//			makeAnimationRotateTurtle(turtleImgView, heading);
//
		makeAnimationMovementTurtle(id, turtleImgView, nextXLoc, nextYLoc);
		
		


		// else if (myPathLines.size() != 0) {
		// // else if(myPathLines.size()!=0 && (xLoc!=prevTurtleImgViewX ||
		// // yLoc!=prevTurtleImgViewY)){
		// int i = myPathLines.size() - 1;
		// makeAnimationMovementTurtle(turtleImgView,
		// myPathLines.get(i).getX1(), myPathLines.get(i).getY1(),
		// myPathLines.get(i).getX2(), myPathLines.get(i).getY2());
		// }
	}

	private void makeAnimationRotateTurtle(ImageView turtleImgView, double heading) {
		RotateTransition rt = new RotateTransition(Duration.seconds(3), turtleImgView);
		rt.setByAngle(heading - turtleImgView.getRotate());
		rt.play();
		return;
	}

	private void makeAnimationMovementTurtle(int id ,ImageView turtleImgView, double x2, double y2) {
		System.out.println(turtleImgView.getTranslateX()+" "+ turtleImgView.getTranslateY());
		Path path = new Path();
		path.getElements().addAll(new MoveTo(turtleImgView.getTranslateX(), turtleImgView.getTranslateY()), new LineTo(x2, y2));
		PathTransition pt = new PathTransition(Duration.millis(10), path, turtleImgView);
		pt.delayProperty();
		pt.play();
		
//		turtleImgView.setTranslateX(x2);
//		turtleImgView.setTranslateY(y2);
		map.put(id,	turtleImgView);
		
		return;
	}

	// public void addTurtleAtXY() {
	// turtleImgView.setRotate(heading);
	// turtleImgView.setTranslateX(xLoc);
	// turtleImgView.setTranslateY(yLoc);
	// if (showTurtle) {
	// pane.getChildren().add(turtleImgView);
	// }
	// }

	// public void removeTurtle(int id) {
	// ImageView turtleImgView = map.get(id);
	// pane.getChildren().remove(turtleImgView);
	// }

	// METHOD NEVER BEING CALLED RN
	// public void moveTurtle() {
	// removeTurtle();
	// addTurtleAtXY();
	// }

	// public void setShowTurtle(boolean isShowing) {
	// showTurtle = isShowing;
	// }
	//
	// public void setPenDown(boolean penPos) {
	// penDown = penPos;
	// }

	// where is the method that takes in the string?
	public void setTurtleImage(int id, Image image) {
		ImageView turtleImgView = map.get(id);
		turtleImgView.setImage(image);
		//
		// pane.getChildren().remove(turtleImgView);
		// turtleImgView = new ImageView(image);
		// addTurtleAtXY();
	}

	public void initializeTurtle(int id, double xLoc, double yLoc, double heading, boolean isShowing) {
		ImageView turtleImgView = new ImageView(
				FileChooserPath.selectImage(IMAGE_PATH + "turtle.png", imageWidth, imageHeight));
		turtleImgView.setTranslateX(xLoc);
		turtleImgView.setTranslateY(yLoc);
		turtleImgView.setRotate(heading);
		turtleImgView.setVisible(isShowing);
		map.put(id, turtleImgView);
		pane.getChildren().add(turtleImgView);
		
	}

	// public void setPenColor(Color color) {
	// myColor = color;
	// }
	//
	// public void setPenThickness(double thickness) {
	// myThickness = thickness;
	// }
	//
	// public void setPenType(String type) {
	// penType = type;
	// }

	// public void setPathLine(List<PathLine> pathLine) {
	// myPathLines = pathLine;
	// }

}
