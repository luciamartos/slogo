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
import interpreter.ErrorPresenter;
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
import javafx.scene.input.KeyCode;
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
public class ViewController implements Observer, ErrorPresenter {
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

	TableView<Variable> variableTableView;
	TableView<UserDefinedCommand> userDefinedTableView;

	public ViewController(Stage stage) {
		viewProperties = new Properties(VIEW_PROPERTIES_PACKAGE + "View");
		sceneRoot = new Group();
		turtleTranslator = new TurtleDataTranslator(viewProperties.getDoubleProperty("canvas_width"),
				viewProperties.getDoubleProperty("canvas_height"));
		sceneRoot.getChildren().add(setupBoxes());
		setupStage(stage);
		sceneRoot.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
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
		box2.getChildren().add(createVariableTableView());
		box2.getChildren().add(createUserDefinedTableView());

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
		double canvasWidth = viewProperties.getDoubleProperty("canvas_width");
		double canvasHeight = viewProperties.getDoubleProperty("canvas_height");
		canvasActions = new CanvasActions(canvasWidth, canvasHeight);
		return canvasActions.getPane();
	}

	private Node initializeSettingsController() {
		settingsController = new SettingsController(stage, viewProperties);
		settingsController.addObserver(this);
		return settingsController.getHBox();

	}

	private void handleKeyInput(KeyCode code) {
		if (code == KeyCode.ENTER){
			runCommandFromInputLine();
		}
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
				runHistoricCommand(curCommand);
			}
		});

		return pastCommandsListView;
	}

	private Node createVariableTableView() {
		variableTableView = new TableView<Variable>();
		variableTableView.setEditable(true);
		TableColumn<Variable, String> variables = new TableColumn<Variable, String>("Variables");
		variables.setEditable(true);

		TableColumn<Variable, String> variableNames = new TableColumn<Variable, String>("Name");
		variableNames.setCellValueFactory(new PropertyValueFactory<Variable, String>("name"));
		TableColumn<Variable, String> variableValues = new TableColumn<Variable, String>("Value");
		variableValues.setCellValueFactory(new PropertyValueFactory<Variable, String>("value"));
		variableValues.setEditable(true);
		//
		// variableValues.setOnEditCommit(
		// new EventHandler<ActionEvent>() {
		// @Override
		// public void handle() {
		//
		// }
		// }
		// );

		variables.getColumns().addAll(variableNames, variableValues);

		variableTableView.getColumns().add(variables);
		variableTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		return variableTableView;
	}

	private Node createUserDefinedTableView() {
		userDefinedTableView = new TableView<UserDefinedCommand>();
		TableColumn<UserDefinedCommand, String> userDefinedCommands = new TableColumn<UserDefinedCommand, String>(
				"User-Defined\nVariables");

		// tableView.setItems(data);
		TableColumn<UserDefinedCommand, String> userDefinedCommandNames = new TableColumn<UserDefinedCommand, String>(

				"Name");
		userDefinedCommandNames.setCellValueFactory(new PropertyValueFactory<UserDefinedCommand, String>("name"));
		TableColumn<UserDefinedCommand, String> userDefinedCommandValues = new TableColumn<UserDefinedCommand, String>(
				"Value");
		userDefinedCommandValues.setCellValueFactory(new PropertyValueFactory<UserDefinedCommand, String>("value"));
		userDefinedCommandValues.setEditable(true);
		userDefinedCommands.getColumns().addAll(userDefinedCommandNames, userDefinedCommandValues);

		userDefinedTableView.getColumns().add(userDefinedCommands);
		userDefinedTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		return userDefinedTableView;
	}

	private Node createCommandInputter() {
		EventHandler<ActionEvent> runCommandHandler = event -> {			
			runCommandFromInputLine();	
		};
		
		double inputPanelHeight = viewProperties.getDoubleProperty("input_panel_height");
		double textFieldWidth = viewProperties.getDoubleProperty("text_field_width");
		double runButtonWidth = viewProperties.getDoubleProperty("run_button_width");

		inputPanel = new InputPanel(inputPanelHeight, textFieldWidth, runButtonWidth, viewProperties,
				runCommandHandler);
		return inputPanel;
	}

	private void runHistoricCommand(String curCommand) {
		String currentCommandLine = curCommand;
		runCommand(currentCommandLine);
	}
	
	private void runCommandFromInputLine() {
		String currentCommandLine = inputPanel.getText();
		inputPanel.clear();
		runCommand(currentCommandLine);
	}

	private void runCommand(String currentCommandLine) {
		//String currentCommandLine = inputPanel.getCurrentCommandLine();
		System.out.println("CUR" +currentCommandLine);
		if (!(currentCommandLine == null) && !(currentCommandLine.length() == 0)) {
			pastCommands.add(currentCommandLine);
			try {
				interpreter.parseInput(currentCommandLine);
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	private Node createErrorConsole() {
		errorConsole = new ErrorConsole(viewProperties.getDoubleProperty("error_font_size"));
		return errorConsole.getErrorMessage();

	}

	// currently only observable this controller observes is settingsController
	// DOES THIS ACCOUNT FOR MY UPDATE THING TOO?
	public void update(Observable obs, Object o) {
		try {
			Method update;
			if (o != null) {
				for (Class c : o.getClass().getInterfaces()) {
					if (c.equals(BoardStateDataSource.class)) {
						update = getClass().getMethod("update", Object.class, BoardStateDataSource.class);
						update.invoke(this, obs, o);
					}
				}
			} else {
				update = getClass().getMethod("update", obs.getClass(), Object.class);
				update.invoke(this, obs, o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void presentError(String errorMessage){
		errorConsole.displayErrorMessage(errorMessage);
	}

	public void update(Object obs, BoardStateDataSource o) {
		canvasActions.removeTurtle();
		canvasActions.setShowTurtle(modelController.getTurtleIsShowing());
		canvasActions.setHeading(turtleTranslator.convertAngle(modelController.getAngle()));
		canvasActions.setPenDown(modelController.getTurtleIsDrawing());
		canvasActions.setXandYLoc(turtleTranslator.convertXCordinate(modelController.getXCoordinate()),
				turtleTranslator.convertYCordinate(modelController.getYCoordinate()));
		canvasActions.addTurtleAtXY();
		canvasActions.drawPath(turtleTranslator.convertLineCordinates(modelController.getLineCoordinates()));
		updateVariables();

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
		if (settingsController.getNewLanguage() != null)
			interpreter.setLanguage(settingsController.getNewLanguage());
	}

	public void setModelController(BoardStateDataSource modelController) {
		this.modelController = modelController;
		updateVariables();
	}

	private void updateVariables() {

		ObservableList<Variable> variableList = createVariablesList();
		variableTableView.setItems(variableList);

		ObservableList<UserDefinedCommand> userDefinedVariableList = createUserDefinedCommandsList();
		userDefinedTableView.setItems(userDefinedVariableList);

	}

	private ObservableList<Variable> createVariablesList() {

		ObservableList<Variable> data = FXCollections.observableArrayList();
		data.add(new Variable("X Coordinate", Double.toString(modelController.getXCoordinate())));
		data.add(new Variable("Y Coordinate", Double.toString(modelController.getYCoordinate())));
		data.add(new Variable("Angle", Double.toString(modelController.getAngle())));
		data.add(new Variable("Turtle is Showing", Boolean.toString(modelController.getTurtleIsShowing())));
		data.add(new Variable("Pen is Down", Boolean.toString(modelController.getTurtleIsDrawing())));

		// add an empty line for separation between environment and user defined
		// variables
		data.add(new Variable("", ""));

		Map<String, String> map = modelController.getUserDefinedVariables();

		for (String s : map.keySet()) {
			if (s.charAt(0) == ':')
				data.add(new Variable(s.substring(1), map.get(s)));
		}
		return data;
	}

	private ObservableList<UserDefinedCommand> createUserDefinedCommandsList() {
		Map<String, String> map = modelController.getUserDefinedVariables();

		ObservableList<UserDefinedCommand> data = FXCollections.observableArrayList();
		for (String s : map.keySet()) {
			if (s.charAt(0) != ':')
				data.add(new UserDefinedCommand(s.substring(1), map.get(s)));
		}
		return data;

	}

	public void setInterpreter(SlogoCommandInterpreter interpreter) {
		this.interpreter = interpreter;
	}
}
