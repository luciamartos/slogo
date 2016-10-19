package gui;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * 
 * @author LuciaMartos
 */
public class MainView {
	private static final String VIEW_PROPERTIES_PACKAGE = "resources.properties/";
	protected ResourceBundle viewProperties;

	private static final Paint BACKGROUND_COLOR_SCENE = Color.ALICEBLUE;
	private Group sceneRoot;
	private Scene scene;
	private double appWidth, appHeight;
	private TitleBox titleBox;
	private InputPanel inputPanel;
	private ListView<String> myListPastCommands;
	private ObservableList<String> pastCommands;
	private CanvasActions canvasActions;

	public MainView() {

		viewProperties = ResourceBundle.getBundle(VIEW_PROPERTIES_PACKAGE + "View");

		sceneRoot = new Group();
		appWidth = getProperty("app_width");
		appHeight = getProperty("app_height");
		scene = new Scene(sceneRoot, appWidth, appHeight, BACKGROUND_COLOR_SCENE);

		createTitleBox();
		createCanvas();
		createCommandInputter();
		createListPastCommands();

	}

	private void createTitleBox() {
		double padding = getProperty("padding");
		double x = padding;
		double y = padding;
		double width = appWidth - (2 * padding);
		double height = getProperty("title_box_height");
		String title = "SLOGO";
		titleBox = new TitleBox(x, y, width, height, title);
		sceneRoot.getChildren().add(titleBox);
	}

	private void createCanvas() {
		canvasActions = new CanvasActions();
		sceneRoot.getChildren().addAll(canvasActions.getPane());
	}

	private void createListPastCommands() {
		myListPastCommands = new ListView<String>();
		pastCommands = FXCollections.observableArrayList("move 10");
		myListPastCommands.setItems(pastCommands);

		// produce sample label to signal command being pressed (this will be
		// removed)
		final Label label = new Label();
		label.setLayoutX(10);
		label.setLayoutY(appHeight - 50);
		label.setFont(Font.font("Verdana", 20));

		myListPastCommands.setPrefWidth(getProperty("past_command_list_width"));
		myListPastCommands.setPrefHeight(getProperty("past_command_list_height"));
		myListPastCommands.setLayoutX(getProperty("past_command_list_x"));
		myListPastCommands.setLayoutY(getProperty("past_command_list_y"));

		// handle user clicking on an value of the list
		myListPastCommands.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, String old_val, String curCommand) {
				label.setText("Run: " + curCommand);
				// TODO: RUN COMMAND OF STRING
			}
		});

		sceneRoot.getChildren().addAll(myListPastCommands, label);
	}

	// TODO: check for missing param and assign default value / errors
	private double getProperty(String propertyName) {
		return Double.parseDouble(viewProperties.getString(propertyName));
	}

	private void createCommandInputter() {
		// TODO: Fix delay, it runs one command delayed how can i make the
		// command line accessible before?
		EventHandler<ActionEvent> runCommandHandler = event -> {
			String currentCommandLine = inputPanel.getCurrentCommandLine();
			if (!(currentCommandLine == null) && !(currentCommandLine.length() == 0)) {
				pastCommands.add(currentCommandLine);
			}
		};

		inputPanel = new InputPanel(getProperty("input_panel_height"), appHeight, appWidth, runCommandHandler);
		sceneRoot.getChildren().addAll(inputPanel);
	}

	Scene getScene() {
		return scene;
	}
}
