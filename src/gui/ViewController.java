package gui;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

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
public class ViewController implements Observer{
	private Properties viewProperties;

	private static final String VIEW_PROPERTIES_PACKAGE = "resources.properties/";
	private static final Paint BACKGROUND_COLOR_SCENE = Color.ALICEBLUE;
	private Group sceneRoot;
	private TitleBox titleBox;
	private InputPanel inputPanel;
	private ListView<String> myListPastCommands;
	private ObservableList<String> pastCommands;
	private CanvasActions canvasActions;
	private SettingsController settings;
	private Stage stage;

	public ViewController(Stage stage) {
		viewProperties = new Properties(VIEW_PROPERTIES_PACKAGE + "View");
		sceneRoot = new Group();
		
		initializeSettingsController();

		createTitleBox();
		createCanvas();
		createCommandInputter();
		createListPastCommands();
		
		setupStage(stage);
	}
	
	private void setupStage(Stage stage){
		double appWidth = viewProperties.getDoubleProperty("app_width");
		double appHeight = viewProperties.getDoubleProperty("app_height");
		Scene scene = new Scene(sceneRoot, appWidth, appHeight, BACKGROUND_COLOR_SCENE);
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
		double canvasX =viewProperties.getDoubleProperty("canvas_x");
				double canvasY=viewProperties.getDoubleProperty("canvas_y");
		double canvasWidth=viewProperties.getDoubleProperty("canvas_width");
		double canvasHeight=viewProperties.getDoubleProperty("canvas_height");
		double canvasLayoutX=viewProperties.getDoubleProperty("canvas_layout_x");
		double canvasLayoutY=viewProperties.getDoubleProperty("canvas_layout_y");
		canvasActions = new CanvasActions(canvasX, canvasY, canvasWidth, canvasHeight, canvasLayoutX, canvasLayoutY);
		sceneRoot.getChildren().addAll(canvasActions.getPane());
	}

	private void initializeSettingsController() {
		settings = new SettingsController(stage,viewProperties);
		settings.addObserver(this);
		sceneRoot.getChildren().addAll(settings.getVBox());
		
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
	
	//currently only observable this controller observes is settingsController
	public void update(Observable obs, Object o){
		if(obs instanceof SettingsController){
			if(o instanceof File)
				canvasActions.changeImage((File)o);
			else if(o instanceof String){
				
			}
		}

	}
}
