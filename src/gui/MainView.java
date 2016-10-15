package gui;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class MainView {
	private static final double PADDING = 15;
	private static final double TITLE_BOX_HEIGHT = 40;
	private static final double APP_WIDTH = 600;
	private static final double APP_HEIGHT = 600;
	private static final double INPUT_PANEL_HEIGHT = 20;
	private Group sceneRoot;
	private Scene scene;
	private double appWidth, appHeight;
	private TitleBox titleBox;

	public MainView() {
		sceneRoot = new Group();
		appWidth = APP_WIDTH; 
		appHeight = APP_HEIGHT; 
		scene = new Scene(sceneRoot, appWidth, appHeight);
		sceneRoot.setId("root");

		createTitleBox();
		createDrawingGrid();
		createCommandInputter();

	}

	private void createTitleBox() {
		double x = PADDING; 
		double y = PADDING;
		double width = appWidth - (2 * PADDING);
		double height = TITLE_BOX_HEIGHT; 
		String title = "SLOGO";
		titleBox = new TitleBox(x, y, width, height, title);
		sceneRoot.getChildren().add(titleBox);
	}

	private void createDrawingGrid() {
		Canvas canvas = new Canvas(400, 200);
		canvas.setWidth(appWidth - PADDING);
		canvas.setHeight(appHeight - PADDING - TITLE_BOX_HEIGHT);
		canvas.setLayoutX(PADDING);
		canvas.setLayoutY(PADDING + TITLE_BOX_HEIGHT);

		// Get the graphics context of the canvas
		// TODO: make a more descriptive name
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLUE);
		gc.fillOval(100, 70, 50, 30);
		sceneRoot.getChildren().add(canvas);
	}

	private void createCommandInputter() {
		//set the text box
		double width = appWidth - (PADDING * 2);
		double height = INPUT_PANEL_HEIGHT;
		final TextField commandInput = new TextField();
		commandInput.setPromptText("Enter your command here...");
		commandInput.setPrefWidth(width);
		commandInput.setPrefHeight(height);
		commandInput.setLayoutY(appHeight - height - PADDING);
		commandInput.setLayoutX(PADDING);
		commandInput.getText();
		
		//set the run button
		Button runButton = new Button("Run");

		sceneRoot.getChildren().addAll(commandInput, runButton);
		
		
	}

	public Scene getScene() {
		return scene;
	}
}
