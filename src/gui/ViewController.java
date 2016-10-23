package gui;

import java.awt.Canvas;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import general.MainController;
import general.Properties;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.BoardStateController;

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
	private ObservableList<String> pastCommands;
	private CanvasActions canvasActions;
	private SettingsController settingsController;
	private Stage stage;
	private ErrorConsole errorConsole;
	private BoardStateDataSource modelController;
	private SlogoCommandInterpreter interpreter;
	private TurtleDataTranslator turtleTranslator;

	// private TableColumn userDefinedCommandNames;
	// private TableColumn userDefinedCommandValues;
	// private TableColumn variableNames;
	// private TableColumn variableValues;

	TableView<Variable> tableView;

	public ViewController(Stage stage) {
		viewProperties = new Properties(VIEW_PROPERTIES_PACKAGE + "View");
		sceneRoot = new Group();
		turtleTranslator = new TurtleDataTranslator(viewProperties.getDoubleProperty("canvas_width"),
				viewProperties.getDoubleProperty("canvas_height"));
		sceneRoot.getChildren().add(setupBoxes());
		setupStage(stage);
	}

	private Node setupBoxes() {
		VBox box1 = new VBox(15);
		HBox box2 = new HBox(15);
		VBox box3 = new VBox(15);

		box1.setPadding(new Insets(15, 15, 15, 15));
		box1.getChildren().add(createTitleBox());
		box1.getChildren().add(box2);
		box1.getChildren().add(initializeSettingsController());
		box1.getChildren().add(createErrorConsole());

		box2.getChildren().add(box3);
		box2.getChildren().add(createPastCommandsListView());
		box2.getChildren().add(createTableView());

		box3.getChildren().add(createCanvas());
		box3.getChildren().add(createCommandInputter());

		return box1;
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

	private Node initializeSettingsController() {
		settingsController = new SettingsController(stage, viewProperties);
		settingsController.addObserver(this);
		return settingsController.getHBox();

	}

	private Node createPastCommandsListView() {
		ListView<String> pastCommandsListView = new ListView<String>();
		pastCommands = FXCollections.observableArrayList();
		pastCommandsListView.setItems(pastCommands);

		pastCommandsListView.setPrefWidth(viewProperties.getDoubleProperty("past_command_list_width"));
		pastCommandsListView.setPrefHeight(viewProperties.getDoubleProperty("past_command_list_height"));
		// handle user clicking on an value of the list
		pastCommandsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, String old_val, String curCommand) {
				// not actually an error
				errorConsole.displayErrorMessage("Run: " + curCommand);
				// TODO: RUN COMMAND OF STRING
			}
		});

		return pastCommandsListView;
	}

	private Node createTableView() {
		tableView = new TableView<Variable>();

		return tableView;
	}

	private String[][] getUserDefinedVariableNamesAndVars(HashMap<String, String> myMap) {
		String[] userDefinedVars = new String[myMap.size()];
		String[] userDefinedNames = new String[myMap.size()];
		int i = 0;
		for (String elem : myMap.keySet()) {
			userDefinedVars[i] = elem;
			userDefinedNames[i] = myMap.get(elem);
			i++;
		}

		return new String[][] { userDefinedVars, userDefinedNames };
	}

	private Node createCommandInputter() {
		EventHandler<ActionEvent> runCommandHandler = event -> {
			String currentCommandLine = inputPanel.getCurrentCommandLine();
			if (!(currentCommandLine == null) && !(currentCommandLine.length() == 0)) {
				pastCommands.add(currentCommandLine);
				try {
					interpreter.parseInput(currentCommandLine);
				} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
						| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	// DOES THIS ACCOUNT FOR MY UPDATE THING TOO?
	public void update(Observable obs, Object o) {
		try {
			Method update = getClass().getMethod("update", obs.getClass(), Object.class);
			update.invoke(this, obs, o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(MainController obs, Object o) {
		canvasActions.removeTurtle();
		canvasActions.setShowTurtle(modelController.getTurtleIsShowing());
		canvasActions.setHeading(turtleTranslator.convertAngle(modelController.getAngle()));
		canvasActions.setPenDown(modelController.getTurtleIsDrawing());
		canvasActions.setXandYLoc(turtleTranslator.convertXCordinate(modelController.getXCoordinate()),
				turtleTranslator.convertYCordinate(modelController.getYCoordinate()));
		canvasActions.addTurtleAtXY();
		canvasActions.drawPath(turtleTranslator.convertLineCordinates(modelController.getLineCoordinates()));
	}

	public void update(SettingsController obs, Object o) {
		if (o != null) {
			errorConsole.displayErrorMessage(o.toString());
			return;
		}
		if (settingsController.getNewImage() != null)
			canvasActions.changeImage(settingsController.getNewImage(), modelController.getXCoordinate(),
					modelController.getYCoordinate());
		if (settingsController.getNewBackgroundColor() != null)
			canvasActions.setBackgroundColorCanvas(settingsController.getNewBackgroundColor());
		if (settingsController.getNewPenColor() != null)
			canvasActions.setPenColor(settingsController.getNewPenColor());
	}

	public void setModelController(BoardStateController modelController) {
		this.modelController = modelController;
		setupEnvironmentVariables();
	}

	private void setupEnvironmentVariables() {
		
		TableColumn variables = new TableColumn("Environment\nVariables");
		TableColumn userDefinedCommands = new TableColumn("User-Defined\nCommands");

		TableColumn variableNames = new TableColumn("Name");
		TableColumn variableValues = new TableColumn("Value");
		variables.getColumns().addAll(variableNames, variableValues);

		// tableView.setItems(data);
		TableColumn userDefinedCommandNames = new TableColumn("Name");
		TableColumn userDefinedCommandValues = new TableColumn("Value");
		userDefinedCommands.getColumns().addAll(userDefinedCommandNames, userDefinedCommandValues);

		ObservableList<Variable> environmentVariableList = createEnvironmentVariablesList();
		variableNames.setCellValueFactory(new PropertyValueFactory<Variable, String>("name"));
		variableValues.setCellValueFactory(new PropertyValueFactory<Variable, String>("value"));

		
		tableView.setItems(environmentVariableList);
		tableView.getColumns().addAll(variables, userDefinedCommands);

	}

	private ObservableList<Variable> createEnvironmentVariablesList() {
		
		ObservableList<Variable> data = FXCollections.observableArrayList();
		data.add(new Variable("X Coordinate", Double.toString(modelController.getXCoordinate())));
		data.add(new Variable("Y Coordinate", Double.toString(modelController.getYCoordinate())));
		data.add(new Variable("Angle", Double.toString(modelController.getAngle())));
		data.add(new Variable("Turtle is Showing", Boolean.toString(modelController.getTurtleIsShowing())));
		data.add(new Variable("Pen is Down", Boolean.toString(modelController.getTurtleIsDrawing())));
		return data;
	}

	public void setInterpreter(SlogoCommandInterpreter interpreter) {
		this.interpreter = interpreter;
	}
}
