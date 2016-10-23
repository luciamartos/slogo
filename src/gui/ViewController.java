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

	TableView<EnvironmentVariable> environmentTableView;
	TableView<UserDefinedVariable> userDefinedTableView;

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
		box2.getChildren().add(createEnvironmentTableView());
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

	private Node createEnvironmentTableView() {
		environmentTableView = new TableView<EnvironmentVariable>();
		TableColumn<EnvironmentVariable, String> variables = new TableColumn<EnvironmentVariable, String>(
				"Environment\nVariables");

		TableColumn<EnvironmentVariable, String> variableNames = new TableColumn<EnvironmentVariable, String>("Name");
		variableNames.setCellValueFactory(new PropertyValueFactory<EnvironmentVariable, String>("name"));
		TableColumn<EnvironmentVariable, String> variableValues = new TableColumn<EnvironmentVariable, String>("Value");
		variableValues.setCellValueFactory(new PropertyValueFactory<EnvironmentVariable, String>("value"));
		variables.getColumns().addAll(variableNames, variableValues);

		environmentTableView.getColumns().add(variables);
		environmentTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		return environmentTableView;
	}

	private Node createUserDefinedTableView() {
		userDefinedTableView = new TableView<UserDefinedVariable>();
		TableColumn<UserDefinedVariable, String> userDefinedCommands = new TableColumn<UserDefinedVariable, String>(
				"User-Defined\nVariables");

		// tableView.setItems(data);
		TableColumn<UserDefinedVariable, String> userDefinedCommandNames = new TableColumn<UserDefinedVariable, String>(
				"Name");
		userDefinedCommandNames.setCellValueFactory(new PropertyValueFactory<UserDefinedVariable, String>("name"));
		TableColumn<UserDefinedVariable, String> userDefinedCommandValues = new TableColumn<UserDefinedVariable, String>(
				"Value");
		userDefinedCommandValues.setCellValueFactory(new PropertyValueFactory<UserDefinedVariable, String>("value"));
		userDefinedCommands.getColumns().addAll(userDefinedCommandNames, userDefinedCommandValues);

		userDefinedTableView.getColumns().add(userDefinedCommands);
		userDefinedTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		return userDefinedTableView;
	}
	//
	// private String[][] getUserDefinedVariableNamesAndVars(HashMap<String,
	// String> myMap) {
	// String[] userDefinedVars = new String[myMap.size()];
	// String[] userDefinedNames = new String[myMap.size()];
	// int i = 0;
	// for (String elem : myMap.keySet()) {
	// userDefinedVars[i] = elem;
	// userDefinedNames[i] = myMap.get(elem);
	// i++;
	// }
	//
	// return new String[][] { userDefinedVars, userDefinedNames };
	// }

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

		double inputPanelHeight = viewProperties.getDoubleProperty("input_panel_height");
		double textFieldWidth = viewProperties.getDoubleProperty("text_field_width");
		double runButtonWidth = viewProperties.getDoubleProperty("run_button_width");

		inputPanel = new InputPanel(inputPanelHeight, textFieldWidth, runButtonWidth, viewProperties,
				runCommandHandler);
		return inputPanel;
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
			if (o != null){
				for (Class c : o.getClass().getInterfaces()){
					if (c.equals(BoardStateDataSource.class)){
						update = getClass().getMethod("update", Object.class, BoardStateDataSource.class);
						update.invoke(this, obs, o);
					}
				}
			}
			else{
				update = getClass().getMethod("update", obs.getClass(), Object.class);
				update.invoke(this, obs, o);
			}
		} catch (Exception e) {
			e.printStackTrace();	
		}
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
	}

	public void setModelController(BoardStateDataSource modelController) {
		this.modelController = modelController;
		updateVariables();
	}

	private void updateVariables() {

		ObservableList<EnvironmentVariable> environmentVariableList = createEnvironmentVariablesList();
		environmentTableView.setItems(environmentVariableList);

		ObservableList<UserDefinedVariable> userDefinedVariableList = createUserDefinedVariablesList();
		userDefinedTableView.setItems(userDefinedVariableList);

	}

	private ObservableList<EnvironmentVariable> createEnvironmentVariablesList() {

		ObservableList<EnvironmentVariable> data = FXCollections.observableArrayList();
		data.add(new EnvironmentVariable("X Coordinate", Double.toString(modelController.getXCoordinate())));
		data.add(new EnvironmentVariable("Y Coordinate", Double.toString(modelController.getYCoordinate())));
		data.add(new EnvironmentVariable("Angle", Double.toString(modelController.getAngle())));
		data.add(new EnvironmentVariable("Turtle is Showing", Boolean.toString(modelController.getTurtleIsShowing())));
		data.add(new EnvironmentVariable("Pen is Down", Boolean.toString(modelController.getTurtleIsDrawing())));
		return data;
	}

	private ObservableList<UserDefinedVariable> createUserDefinedVariablesList() {
		Map<String, String> map = modelController.getUserDefinedVariables();

		ObservableList<UserDefinedVariable> data = FXCollections.observableArrayList();
		for (String s : map.keySet()) {
			data.add(new UserDefinedVariable(s, map.get(s)));
		}
		return data;

	}

	public void setInterpreter(SlogoCommandInterpreter interpreter) {
		this.interpreter = interpreter;
	}
}
