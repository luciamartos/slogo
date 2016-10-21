package gui;

import java.io.File;
import java.io.IOException;


import general.Properties;
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
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author LuciaMartos
 */
public class ViewController {
	private Properties viewProperties;

	private static final String VIEW_PROPERTIES_PACKAGE = "resources.properties/";
	private static final Paint BACKGROUND_COLOR_SCENE = Color.ALICEBLUE;
	private Group sceneRoot;
	private Scene scene;
	private TitleBox titleBox;
	private InputPanel inputPanel;
	private ListView<String> myListPastCommands;
	private ObservableList<String> pastCommands;
	private CanvasActions canvasActions;
	private Settings settings;
	private Stage stage;

	public ViewController(Stage stage) {
		this.stage = stage;
		viewProperties = new Properties(VIEW_PROPERTIES_PACKAGE + "View");
		
		
		sceneRoot = new Group();
		double appWidth = viewProperties.getDoubleProperty("app_width");
		double appHeight = viewProperties.getDoubleProperty("app_height");
		scene = new Scene(sceneRoot, appWidth, appHeight, BACKGROUND_COLOR_SCENE);

		createTitleBox();
		createCanvas();
		createCommandInputter();
		createListPastCommands();
		createSettings();
		
		
		setupStage();
	}
	
	private void setupStage(){
		stage.setTitle(viewProperties.getStringProperty("title"));
        stage.setScene(scene);
        stage.show();
	}

	private void createTitleBox() {
		double padding = viewProperties.getDoubleProperty("padding");
		double x = padding;
		double y = padding;
		double width = viewProperties.getDoubleProperty("title_box_width");
		double height = viewProperties.getDoubleProperty("title_box_height");
		String title = "SLOGO";
		titleBox = new TitleBox(x, y, width, height, title);
		sceneRoot.getChildren().add(titleBox);
	}

	public void createCanvas() {
		canvasActions = new CanvasActions(viewProperties);
		sceneRoot.getChildren().addAll(canvasActions.getPane());
	}

	private void createSettings() {
		settings = new Settings(viewProperties);
		sceneRoot.getChildren().addAll(settings.getVBox());
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				FileChooser fileChooser = new FileChooser();
				ViewController.this.createCanvas();
				File file = fileChooser.showOpenDialog(stage);
				if (file != null && file.isFile()) {
					canvasActions.changeImage(file);
				}
			}
		};
		settings.turtleImageSetAction(event);
	}



	private void createListPastCommands() {
		myListPastCommands = new ListView<String>();
		pastCommands = FXCollections.observableArrayList();
		myListPastCommands.setItems(pastCommands);

		// produce sample label to signal command being pressed (this will be
		// removed)
		final Label label = new Label();
		label.setLayoutX(viewProperties.getDoubleProperty("label_x"));
		label.setLayoutY(viewProperties.getDoubleProperty("label_y"));
		label.setFont(Font.font("Verdana", 20));

		myListPastCommands.setPrefWidth(viewProperties.getDoubleProperty("past_command_list_width"));
		myListPastCommands.setPrefHeight(viewProperties.getDoubleProperty("past_command_list_height"));
		myListPastCommands.setLayoutX(viewProperties.getDoubleProperty("past_command_list_x"));
		myListPastCommands.setLayoutY(viewProperties.getDoubleProperty("past_command_list_y"));

		// handle user clicking on an value of the list
		myListPastCommands.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, String old_val, String curCommand) {
				label.setText("Run: " + curCommand);
				// TODO: RUN COMMAND OF STRING
			}
		});

		sceneRoot.getChildren().addAll(myListPastCommands, label);
	}

	private void createCommandInputter() {
		EventHandler<ActionEvent> runCommandHandler = event -> {
			String currentCommandLine = inputPanel.getCurrentCommandLine();
			if (!(currentCommandLine == null) && !(currentCommandLine.length() == 0)) {
				pastCommands.add(currentCommandLine);
			}
		};

		inputPanel = new InputPanel(viewProperties, runCommandHandler);
		sceneRoot.getChildren().addAll(inputPanel);
	}
}
