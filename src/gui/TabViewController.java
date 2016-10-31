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
import general.NewSlogoInstanceCreator;
import general.Properties;
import general.SlogoCommandHandler;
import gui_components.ErrorConsole;
import gui_components.GeneralSettingsController;
import gui_components.InputPanel;
import gui_components.PenSettingsController;
import gui_components.SettingsController;
import gui_components.TitleBox;
import gui_components.TurtleSettingsController;
import gui_components.UserDefinedCommand;
import gui_components.WorkspaceSettingsController;
import interpreter.ErrorPresenter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tableviews.VariableTableView;

/**
 * 
 * @author LuciaMartos, Eric Song
 */
public class TabViewController implements Observer, ErrorPresenter {
	private Properties viewProperties;

	private static final double HIDE_SHOW_BUTTON_WIDTH = 140;
	private TitleBox titleBox;
	private InputPanel inputPanel;
	private ObservableList<String> pastCommands;
	private CanvasActions canvasActions;
	private SettingsController settingsController;
	// private WorkspaceSettingsController workspaceSettingsController;
	// private PenSettingsController penSettingsController;
	// private TurtleSettingsController turtleSettingsController;
	private ErrorConsole errorConsole;
	private BoardStateDataSource modelController;
	private SlogoCommandHandler commandHandler;
	private TurtleDataTranslator turtleTranslator;
	private ListView<String> pastCommandsListView;
	private Tab tab;
	private NewSlogoInstanceCreator instanceCreator;

	TableView<Variable> defaultVariableTableView;
	TableView<Variable> userDefinedTableView;

	public TabViewController(TabPane tabPane, Properties viewProperties, String tabTitle,
			NewSlogoInstanceCreator instanceCreator) {
		this.instanceCreator = instanceCreator;
		this.viewProperties = viewProperties;
		setupTab(tabTitle);
		tabPane.getTabs().add(tab);
		turtleTranslator = new TurtleDataTranslator(viewProperties.getDoubleProperty("canvas_width"),
				viewProperties.getDoubleProperty("canvas_height"), getImageWidth(), getImageHeight());
	}

	private void setupTab(String tabTitle) {
		tab = new Tab();
		tab.setText(tabTitle);
		tab.setContent(setupBoxes());
	}

	public Tab getTab() {
		return tab;
	}

	private Node setupBoxes() {
		VBox windowBox = new VBox(15);
		HBox canvasAndTablesBox = new HBox(15);
		VBox canvasAndCommandsBox = new VBox(15);
		HBox settingsBox = new HBox(15);
		canvasAndTablesBox.setAlignment(Pos.CENTER);

		windowBox.setPadding(new Insets(15, 15, 15, 15));
		windowBox.getChildren().add(createTitleBox());
		windowBox.getChildren().add(canvasAndTablesBox);
		windowBox.getChildren().add(settingsBox);
		windowBox.getChildren().add(createErrorConsole());
		windowBox.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

		canvasAndTablesBox.getChildren().add(canvasAndCommandsBox);
		canvasAndTablesBox.getChildren().add(createPastCommandsListView());
		VBox leftTableBox = new VBox(15);
		leftTableBox.getChildren().add(createDefaultVariableTableView());
		leftTableBox.getChildren().add();
		canvasAndTablesBox.getChildren().add(leftTableBox);
		canvasAndTablesBox.getChildren().add(createUserDefinedTableView());

		canvasAndCommandsBox.getChildren().add(createCanvas());
		canvasAndCommandsBox.getChildren().add(createCommandInputter());
		// HBox.setHgrow(box2, Priority.ALWAYS);

		Node settingsController = initializeSettingsController();
		settingsBox.getChildren().add(createViewSelector());
		settingsBox.getChildren().add(settingsController);

		return windowBox;
	}

	private Node createViewSelector() {
		// initialise buttons
		VBox hideShowButtons = new VBox(2);
		Button historicCommands = createButton("Hide history", HIDE_SHOW_BUTTON_WIDTH);
		Button variables = createButton("Hide variables", HIDE_SHOW_BUTTON_WIDTH);
		Button userVariables = createButton("Hide user variables", HIDE_SHOW_BUTTON_WIDTH);
		Button drawSettings = createButton("Hide settings", HIDE_SHOW_BUTTON_WIDTH);

		// initialise button actions
		hideShowButtonActions(historicCommands, pastCommandsListView, "Hide history", "Show history");
		hideShowButtonActions(variables, defaultVariableTableView, "Hide variables", "Show variables");
		hideShowButtonActions(userVariables, userDefinedTableView, "Hide user variables", "Show user variables");
		hideShowButtonActions(drawSettings, settingsController.getNode(), "Hide settings", "Show settings");

		hideShowButtons.getChildren().addAll(historicCommands, userVariables, variables, drawSettings);
		return hideShowButtons;
	}

	private void hideShowButtonActions(Button button, Node view, String hide, String show) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (button.getText().equals(hide)) {
					button.setText(show);
					view.setVisible(false);
				} else {
					button.setText(hide);
					view.setVisible(true);
				}
			}
		});
	}

	private Node createTitleBox() {
		double padding = viewProperties.getDoubleProperty("padding");
		double width = viewProperties.getDoubleProperty("title_box_width");
		double height = viewProperties.getDoubleProperty("title_box_height");
		String title = "SLOGO";
		titleBox = new TitleBox(padding, padding, width, height, title);
		return titleBox;
	}

	public Node createCanvas() {
		double canvasWidth = viewProperties.getDoubleProperty("canvas_width");
		double canvasHeight = viewProperties.getDoubleProperty("canvas_height");
		canvasActions = new CanvasActions(canvasWidth, canvasHeight, getImageWidth(), getImageHeight());
		return canvasActions.getPane();
	}

	private Node initializeSettingsController() {
		settingsController = new SettingsController(viewProperties, instanceCreator);
		settingsController.addObserver(this);
		settingsController.getPenSettingsController().addObserver(this);
		settingsController.getWorkspaceSettingsController().addObserver(this);
		settingsController.getGeneralSettingsController().addObserver(this);
		settingsController.getTurtleSettingsController().addObserver(this);
		return settingsController.getNode();
	}

	private void handleKeyInput(KeyCode code) {
		if (code == KeyCode.ENTER) {
			runCommandFromInputLine();
		}
	}

	private Node createPastCommandsListView() {
		pastCommandsListView = new ListView<String>();
		pastCommandsListView.managedProperty().bind(pastCommandsListView.visibleProperty());
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

	private Node createDefaultVariableTableView() {
		defaultVariableTableView = new VariableTableView();
		return defaultVariableTableView;
	}

	private Node createUserDefinedTableView() {
		userDefinedTableView = new TableView<Variable>();
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

	private Button createButton(String text, double width) {
		Button button = new Button(text);
		button.setPrefWidth(width);
		return button;
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
		// String currentCommandLine = inputPanel.getCurrentCommandLine();
		if (!(currentCommandLine == null) && !(currentCommandLine.length() == 0)) {
			pastCommands.add(currentCommandLine);
			commandHandler.parseInput(this, currentCommandLine);
		}
	}

	private Node createErrorConsole() {
		errorConsole = new ErrorConsole(viewProperties.getDoubleProperty("error_font_size"));
		return errorConsole.getErrorMessage();

	}

	// currently only observable this controller observes is settingsController
	// DOES THIS ACCOUNT FOR MY UPDATE THING TOO?
	public void update(Observable obs, Object o) {
		if (o != null) {
			errorConsole.displayErrorMessage(o.toString());
		}
		try {
			Method update;
				for (Class c : obs.getClass().getInterfaces()) {
					if (c.equals(BoardStateDataSource.class)) {
						update = getClass().getMethod("update", BoardStateDataSource.class, Object.class);
						update.invoke(this, obs, o);
						return;
					}
				}
				update = getClass().getMethod("update", obs.getClass(), Object.class);
				update.invoke(this, obs, o);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void presentError(String errorMessage) {
		errorConsole.displayErrorMessage(errorMessage);
	}

	public void update(BoardStateDataSource obs, Object o) {
		canvasActions.removeTurtle();
		canvasActions.setShowTurtle(modelController.getTurtleIsShowing());
		canvasActions.setHeading(turtleTranslator.convertAngle(modelController.getAngle()));
		canvasActions.setPenDown(modelController.getTurtleIsDrawing());
		// canvasActions.setPenColor(modelController.getPenColor());
		// canvasActions.setBackgroundColorCanvas(modelController.getBackgroundColor());
		// canvasActions.setPenThickness(modelController.getPenThickness());
		canvasActions.setXandYLoc(turtleTranslator.convertXImageCordinate(modelController.getXCoordinate()),
				turtleTranslator.convertYImageCordinate(modelController.getYCoordinate()));
		canvasActions.setPathLine(turtleTranslator.convertLineCordinates(modelController.getLineCoordinates()));
		canvasActions.animatedMovementToXY();
		// canvasActions.addTurtleAtXY();
		canvasActions.drawPath();
		updateVariables();

	}

	public void update(GeneralSettingsController obs, Object o) {
		if(obs.getNewCommandLineFromFile()!=null)
			 runCommand(obs.getNewCommandLineFromFile());
	}

	public void update(TurtleSettingsController obs, Object o) {
		if (obs.getNewImage() != null)
			canvasActions.changeImage(obs.getNewImage(), modelController.getXCoordinate(),
					modelController.getYCoordinate());
	}

	public void update(WorkspaceSettingsController obs, Object o) {
		if (obs.getNewBackgroundColor() != null)
			canvasActions.setBackgroundColorCanvas(obs.getNewBackgroundColor());
		if (obs.getNewLanguage() != null)
			commandHandler.setLanguage(this, obs.getNewLanguage());
	}

	public void update(PenSettingsController obs, Object o) {
		if (obs.getNewPenColor() != null)
			canvasActions.setPenColor(obs.getNewPenColor());
		if (obs.getNewPenType() != null)
			canvasActions.setPenType(obs.getNewPenType());
		if (obs.getNewPenThickness() != 0)
			canvasActions.setPenThickness(obs.getNewPenThickness());
	}
	

	public void setModelController(BoardStateDataSource modelController) {
		this.modelController = modelController;
		updateVariables();
	}

	private void updateVariables() {

		ObservableList<Variable> defaultVariableList = createDefaultVariablesList();
		defaultVariableTableView.setItems(defaultVariableList);
		
		ObservableList<Variable> userDefinedVariablesList = createUserDefinedVariablesList();
		defaultVariableTableView.setItems(userDefinedVariablesList);

		ObservableList<UserDefinedCommand> userDefinedVariableList = createUserDefinedCommandsList();
		userDefinedTableView.setItems(userDefinedVariableList);

	}

	private ObservableList<Variable> createDefaultVariablesList() {

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
	
	private ObservableList<Variable> createUserDefinedVariablesList() {

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

	private ObservableList<Variable> createUserDefinedCommandsList() {
		Map<String, String> map = modelController.getUserDefinedVariables();

		ObservableList<Variable> data = FXCollections.observableArrayList();
		for (String s : map.keySet()) {
			if (s.charAt(0) != ':')
				data.add(new Variable(s.substring(1), map.get(s)));
		}
		return data;

	}

	public void setCommandHandler(SlogoCommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}

	// WHY CANT WE JUST PASS VIEWPROPERTIES TO INTERPRETER?
	public double getImageWidth() {
		return viewProperties.getDoubleProperty("image_width");
	}

	public double getImageHeight() {
		return viewProperties.getDoubleProperty("image_height");
	}
}
