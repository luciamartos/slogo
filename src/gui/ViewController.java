package gui;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

import general.Properties;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author LuciaMartos
 */
public class ViewController implements Observer {
	private Properties viewProperties;

	private static final String VIEW_PROPERTIES_PACKAGE = "resources.properties/";
	private static final Paint BACKGROUND_COLOR_SCENE = Color.ALICEBLUE;
	private Group sceneRoot;
	private TitleBox titleBox;
	private InputPanel inputPanel;
	private ListView<String> myListPastCommands;
	private ObservableList<String> pastCommands;
	private CanvasActions canvasActions;
	private SettingsController settingsController;
	private Stage stage;
	private ErrorConsole errorConsole;

	public ViewController(Stage stage) {
		viewProperties = new Properties(VIEW_PROPERTIES_PACKAGE + "View");
		sceneRoot = new Group();

		HBox box1 = new HBox(15);
		box1.setPadding(new Insets(15,15,15,15));
		VBox box2 = new VBox(15);
		HBox box3 = new HBox(15);

		VBox box4 = new VBox(15);


		sceneRoot.getChildren().add(box1);

		box1.getChildren().add(box2);

		box2.getChildren().add(createTitleBox());
		box2.getChildren().add(box3);
		box2.getChildren().add(createErrorConsole());

		box3.getChildren().add(box4);
		box3.getChildren().add(createListPastCommands());

		box4.getChildren().add(createCanvas());
		box4.getChildren().add(createCommandInputter());

		initializeSettingsController();
		setupStage(stage);
	}

	private void createCanvasAndLists() {
		HBox canvasAndLists = new HBox(15);
		// canvasAndLists.setLayoutX(value);
		VBox canvasAndCommandInputter = new VBox(15);
	}

	private void setupStage(Stage stage) {
		double appWidth = viewProperties.getDoubleProperty("app_width");
		double appHeight = viewProperties.getDoubleProperty("app_height");
		Scene scene = new Scene(sceneRoot, appWidth, appHeight, BACKGROUND_COLOR_SCENE);
		stage.setTitle(viewProperties.getStringProperty("title"));
		stage.setScene(scene);
		stage.show();
	}

	private Node createTitleBox() {
		double padding = viewProperties.getDoubleProperty("padding");
		double x = padding;
		double y = padding;
		double width = viewProperties.getDoubleProperty("title_box_width");
		double height = viewProperties.getDoubleProperty("title_box_height");
		String title = "SLOGO";
		titleBox = new TitleBox(x, y, width, height, title);
		return titleBox;
	}

	public Node createCanvas() {
		double canvasX = viewProperties.getDoubleProperty("canvas_x");
		double canvasY = viewProperties.getDoubleProperty("canvas_y");
		double canvasWidth = viewProperties.getDoubleProperty("canvas_width");
		double canvasHeight = viewProperties.getDoubleProperty("canvas_height");
		double canvasLayoutX = viewProperties.getDoubleProperty("canvas_layout_x");
		double canvasLayoutY = viewProperties.getDoubleProperty("canvas_layout_y");
		double errorLabelX = viewProperties.getDoubleProperty("error_label_x");
		double errorLabelY = viewProperties.getDoubleProperty("error_label_y");
		canvasActions = new CanvasActions(canvasX, canvasY, canvasWidth, canvasHeight, canvasLayoutX, canvasLayoutY,
				errorLabelX, errorLabelY);
		return canvasActions.getPane();
	}

	private void initializeSettingsController() {
		settingsController = new SettingsController(stage, viewProperties);
		settingsController.addObserver(this);
		sceneRoot.getChildren().addAll(settingsController.getVBox());

	}

	private Node createListPastCommands() {
		myListPastCommands = new ListView<String>();
		pastCommands = FXCollections.observableArrayList();
		myListPastCommands.setItems(pastCommands);

		// produce sample label to signal command being pressed (this will be
		// removed)
		// final Label label = new Label();
		// label.setLayoutX(viewProperties.getDoubleProperty("console_label_x"));
		// label.setLayoutY(viewProperties.getDoubleProperty("console_label_y"));
		// label.setFont(Font.font("Verdana", 20));

		myListPastCommands.setPrefWidth(viewProperties.getDoubleProperty("past_command_list_width"));
		myListPastCommands.setPrefHeight(viewProperties.getDoubleProperty("past_command_list_height"));
		myListPastCommands.setLayoutX(viewProperties.getDoubleProperty("past_command_list_x"));
		myListPastCommands.setLayoutY(viewProperties.getDoubleProperty("past_command_list_y"));

		// handle user clicking on an value of the list
		myListPastCommands.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, String old_val, String curCommand) {
				// not actually an error
				errorConsole.displayErrorMessage("Run: " + curCommand);
				// TODO: RUN COMMAND OF STRING
			}
		});

		return myListPastCommands;
	}

	private Node createCommandInputter() {
		EventHandler<ActionEvent> runCommandHandler = event -> {
			String currentCommandLine = inputPanel.getCurrentCommandLine();
			if (!(currentCommandLine == null) && !(currentCommandLine.length() == 0)) {
				pastCommands.add(currentCommandLine);
			}
		};

		inputPanel = new InputPanel(viewProperties, runCommandHandler);
		return inputPanel;
	}

	private Node createErrorConsole() {
		errorConsole = new ErrorConsole(viewProperties.getDoubleProperty("error_label_x"),
				viewProperties.getDoubleProperty("error_label_y"), viewProperties.getDoubleProperty("error_font_size"));
		return errorConsole.getErrorMessage();

	}

	// currently only observable this controller observes is settingsController
	public void update(Observable obs, Object o) {
		try {
			Method update = getClass().getMethod("update", obs.getClass(), Object.class);
			update.invoke(this, obs, o);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update(SettingsController obs, Object o) {

		if (o != null) {
			errorConsole.displayErrorMessage(o.toString());
			return;
		}
		if (settingsController.getNewImage() != null)
			canvasActions.changeImage(settingsController.getNewImage());
		if (settingsController.getNewBackgroundColor() != null)
			canvasActions.setBackgroundColorCanvas(settingsController.getNewBackgroundColor());
		if (settingsController.getNewPenColor() != null)
			canvasActions.setPenColor(settingsController.getNewPenColor());

	}
}
