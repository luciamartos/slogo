package gui;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

public class MainView {
	private static final double PADDING = 15;
	private static final double TITLE_BOX_HEIGHT = 40;
	private static final double APP_WIDTH = 600;
	private static final double APP_HEIGHT = 600;
	private static final double INPUT_PANEL_HEIGHT = 20;
	private static final double PAST_COMMAND_LIST_WIDTH = 120;
	private Group sceneRoot;
	private Scene scene;
	private double appWidth, appHeight;
	private TitleBox titleBox;
	private InputPanel inputPanel;
	private ListView<String> myListPastCommands;
	private ObservableList<String> pastCommands;

	public MainView() {
		sceneRoot = new Group();
		appWidth = APP_WIDTH;
		appHeight = APP_HEIGHT;
		scene = new Scene(sceneRoot, appWidth, appHeight);
		sceneRoot.setId("root");

		createTitleBox();
		createDrawingGrid();
		createCommandInputter();
		createListPastCommands();

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
		canvas.setWidth(appWidth - PADDING - PAST_COMMAND_LIST_WIDTH);
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
	
	private void createListPastCommands(){
		myListPastCommands = new ListView<String>();
		pastCommands = FXCollections.observableArrayList("Peras", "Manzanas");
		myListPastCommands.setItems(pastCommands);
		
		myListPastCommands.setPrefWidth(PAST_COMMAND_LIST_WIDTH);
		myListPastCommands.setPrefHeight(appHeight - INPUT_PANEL_HEIGHT - TITLE_BOX_HEIGHT - PADDING*4);
		myListPastCommands.setLayoutX(appWidth - PADDING -PAST_COMMAND_LIST_WIDTH);
		myListPastCommands.setLayoutY(PADDING*2 + TITLE_BOX_HEIGHT);

		sceneRoot.getChildren().add(myListPastCommands);
	}

	private void createCommandInputter() {
		//TODO: Fix delay, it runs one command delayed how can i make the commadline accessible before?
		EventHandler<ActionEvent> runCommandHandler = event -> {
			String currentCommandLine = inputPanel.getCurrentCommandLine();
			if (currentCommandLine == null || currentCommandLine.length() == 0) {
				System.out.println("Return Error: EMPTY STRING");
			} else {
				System.out.println("RUN COMMAND: " + currentCommandLine);
				pastCommands.add(currentCommandLine);
			}
		};

		inputPanel = new InputPanel(INPUT_PANEL_HEIGHT, appHeight, appWidth, runCommandHandler);
		sceneRoot.getChildren().addAll(inputPanel);
	}

	public Scene getScene() {
		return scene;
	}
}
