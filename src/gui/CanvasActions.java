package gui;

import java.awt.BasicStroke;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import fileIO.XMLReader;
import general.Properties;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.PathLine;
import model.RGBColor;

/**
 * @author Lucia Martos
 */
public class CanvasActions {
	private static final RGBColor COLOR_CANVAS = new RGBColor(255, 255, 255);
	private static final String IMAGE_PATH = "resources/images/";
	private GraphicsContext gc;
	private Canvas canvas;
	private Pane pane;
	private double imageWidth;
	private double imageHeight;
	private double canvasHeight;
	private double canvasWidth;
	private int animationSpeed;

	private Map<Integer, ImageView> map;

	public CanvasActions(double canvasWidth, double canvasHeight, double imWidth, double imHeight) {
		initializePane(canvasWidth, canvasHeight);
		initializeCanvas(canvasWidth, canvasHeight);
		this.canvasHeight = canvasHeight;
		this.canvasWidth = canvasWidth;
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

	public void drawPath(Color color, int penThickness, double x1, double y1, double x2, double y2, String penType) {
		handleDifferentPenTypes(penType);
		gc.setStroke(color);
		gc.setLineWidth(penThickness);
		gc.strokeLine(x1, y1, x2, y2);
		gc.setFill(color);

	}

	private void handleDifferentPenTypes(String penType) {
		if (penType.equals("dashed")) { // THESE ARENT WORKING EXACTLY HOW THEY


			gc.setLineCap(StrokeLineCap.BUTT);
			gc.setLineJoin(StrokeLineJoin.MITER);
			gc.setMiterLimit(10.0f);
			gc.setLineDashes(10.0f);
			gc.setLineDashOffset(0.0f);
		} else if (penType.equals("dotted")) {
			gc.setLineDashes(3.0f);
		} else {
			gc.setLineDashes(null);
		}
	}

	public void animatedMovementToXY(int id, double nextXLoc, double nextYLoc, double heading, boolean isShowing) {
		ImageView turtleImgView = map.get(id);
		turtleImgView.setVisible(isShowing);
		if (heading != turtleImgView.getRotate())
			makeAnimationRotateTurtle(turtleImgView, heading);

		makeAnimationMovementTurtle(id, turtleImgView, nextXLoc, nextYLoc);

	}

	private void makeAnimationRotateTurtle(ImageView turtleImgView, double heading) {
		RotateTransition rt = new RotateTransition(Duration.millis(3000 / animationSpeed), turtleImgView);
		rt.setByAngle(heading - turtleImgView.getRotate());
		rt.play();
		return;
	}

	private void makeAnimationMovementTurtle(int id, ImageView turtleImgView, double x2, double y2) {

		TranslateTransition pt = new TranslateTransition(Duration.millis(1500 / animationSpeed), turtleImgView);
		pt.setByX(x2 - turtleImgView.getTranslateX());
		pt.setByY(y2 - turtleImgView.getTranslateY());
		pt.delayProperty();
		pt.play();
		map.put(id, turtleImgView);
		return;
	}

	// where is the method that takes in the string?
	public void setTurtleImage(int id, String image) {
		ImageView turtleImgView = map.get(id);
		turtleImgView.setImage(FileChooserPath.selectImage(IMAGE_PATH + image + ".png", 50, 50));
	}

	public void initializeTurtle(int id, double xLoc, double yLoc, double heading, boolean isShowing,
			EventHandler<MouseEvent> e) {
		ImageView turtleImgView = new ImageView(
				FileChooserPath.selectImage(IMAGE_PATH + "turtle.png", imageWidth, imageHeight));
		turtleImgView.setTranslateX(xLoc);
		turtleImgView.setTranslateY(yLoc);
		turtleImgView.setRotate(heading);
		turtleImgView.setVisible(isShowing);
		turtleImgView.addEventHandler(MouseEvent.MOUSE_CLICKED, e);
		map.put(id, turtleImgView);
		pane.getChildren().add(turtleImgView);

	}

	public boolean turtleExists(int currId) {
		return map.get(currId) != null;
	}

	public void clearCanvas() {
		gc.clearRect(0, 0, canvasWidth, canvasHeight);

	}

	public void setAnimationSpeed(int speed) {
		animationSpeed = speed;
	}

	public void setTurtleActive(int currId, boolean active) {
		ImageView turtleImgView = map.get(currId);
		if (active)
			turtleImgView.setOpacity(1);
		else
			turtleImgView.setOpacity(0.5);
	}
}
