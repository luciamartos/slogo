package gui;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import fileIO.CommandPrinter;
import fileIO.FileIOController;
import fileIO.XMLWriter;
import general.MainController;
import general.NewSlogoInstanceCreator;
import general.Properties;
import general.SlogoCommandHandler;
import gui_components.ErrorConsole;
import gui_components.GeneralSettingsController;
import gui_components.InputPanel;
import gui_components.PenSettingsController;
import gui_components.SaveWorkspaceInterface;
import gui_components.SettingsController;
import gui_components.TitleBox;
import gui_components.TurtleSettingsController;
import gui_components.WorkspaceSettingsController;
import interpreter.ErrorPresenter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.PathLine;
import model.RGBColor;
import tableviews.TableViewController;
import tableviews.VariableTableView;

/**
 * 
 * @author Lucia Martos, Eric Song
 */
public class TabViewController implements Observer, ErrorPresenter, SaveWorkspaceInterface {
	private Properties viewProperties;

	private static final double HIDE_SHOW_BUTTON_WIDTH = 140;
	private TitleBox titleBox;
	private InputPanel inputPanel;
	private ObservableList<String> pastCommands;
	private CanvasActions canvasActions;
	private SettingsController settingsController;
	private ErrorConsole errorConsole;

	private BoardStateDataSource boardStateDataSource;
	private TurtleStateDataSource turtleStateDataSource;
	private BoardActionsHandler boardActionsHandler;
	private TurtleActionsHandler turtleActionsHandler;
	private SlogoCommandHandler commandHandler;

	private TableViewController tableViewController;
	private TurtleDataTranslator turtleTranslator;
	private ListView<String> pastCommandsListView;
	private Tab tab;
	private NewSlogoInstanceCreator instanceCreator;
	private int currentlySelectedID;
	private Map<Integer, RGBColor> colorMap;
	private Map<Integer, String> penTypeMap;
	private Map<Integer, String> shapeMap;

	TableView<Variable> defaultVariableTableView;
	TableView<Variable> userDefinedVariableTableView;
	TableView<Variable> userDefinedCommandTableView;
	TableView<Variable> colorTableView;

	public TabViewController(TabPane tabPane, Properties viewProperties, String tabTitle,
			NewSlogoInstanceCreator instanceCreator) {
		this.instanceCreator = instanceCreator;
		this.viewProperties = viewProperties;

		populatePenTypeMap();
		populateShapeMap();

		setupTab(tabTitle);
		tabPane.getTabs().add(tab);
		turtleTranslator = new TurtleDataTranslator(viewProperties.getDoubleProperty("canvas_width"),
				viewProperties.getDoubleProperty("canvas_height"), getImageWidth(), getImageHeight());
	}

	private void populateShapeMap() {
		String[] types = viewProperties.getStringProperty("shape_types").split(" ");
		shapeMap = new HashMap<Integer, String>();
		for (int i = 0; i < types.length; i++) {
			shapeMap.put(i, types[i]);
		}
	}

	private void populatePenTypeMap() {
		String[] types = viewProperties.getStringProperty("pen_type").split(" ");
		penTypeMap = new HashMap<Integer, String>();
		for (int i = 0; i < types.length; i++) {
			penTypeMap.put(i, types[i]);
		}
		tableViewController = new TableViewController();
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
		leftTableBox.getChildren().add(tableViewController.getDefaultVariableTableView());
		leftTableBox.getChildren().add(tableViewController.getColorTableView());
		canvasAndTablesBox.getChildren().add(leftTableBox);

		VBox rightTableBox = new VBox(15);
		rightTableBox.getChildren().add(tableViewController.getUserDefinedVariableTableView());
		rightTableBox.getChildren().add(tableViewController.getUserDefinedCommandTableView());
		canvasAndTablesBox.getChildren().add(rightTableBox);

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
		hideShowButtonActions(userVariables, userDefinedCommandTableView, "Hide user variables", "Show user variables");
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

	private Node createCanvas() {
		double canvasWidth = viewProperties.getDoubleProperty("canvas_width");
		double canvasHeight = viewProperties.getDoubleProperty("canvas_height");
		canvasActions = new CanvasActions(canvasWidth, canvasHeight, getImageWidth(), getImageHeight());
		return canvasActions.getPane();
	}

	private Node initializeSettingsController() {
		settingsController = new SettingsController(viewProperties, instanceCreator, this);
		settingsController.addObserver(this);
		settingsController.getPenSettingsController().addObserver(this);
		settingsController.getWorkspaceSettingsController().addObserver(this);
		settingsController.getGeneralSettingsController().addObserver(this);
		settingsController.getTurtleSettingsController().addObserver(this);
		canvasActions.setAnimationSpeed(settingsController.getTurtleSettingsController().getNewAnimationSpeed());
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

	@Override
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
					updateVariables();
					return;
				} else if (c.equals(TurtleStateDataSource.class)) {
					update = getClass().getMethod("update", TurtleStateDataSource.class, Object.class);
					update.invoke(this, obs, o);
					updateVariables();
					return;
				}
			}
			update = getClass().getMethod("update", obs.getClass(), Object.class);
			update.invoke(this, obs, o);
			updateVariables();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void presentError(String errorMessage) {
		errorConsole.displayErrorMessage(errorMessage);
	}

	/**
	 * @param obs
	 * @param o
	 *            performs all necessary updates when something in
	 *            TurtleStateDataSource changes
	 */
	public void update(TurtleStateDataSource obs, Object o) {
		Iterator<Integer> turtleIds = turtleStateDataSource.getTurtleIDs();
		List<Integer> activeTurtleIds = turtleStateDataSource.getActiveTurtleIDs();
		while (turtleIds.hasNext()) {
			int currId = turtleIds.next();

			if (!canvasActions.turtleExists(currId))
				initializeTurtle(currId);

			canvasActions.setTurtleImage(currId, shapeMap.get(turtleStateDataSource.getShape(currId)));
			canvasActions.setTurtleActive(currId, activeTurtleIds.contains(currId));
			canvasActions.animatedMovementToXY(currId,
					turtleTranslator.convertXImageCordinate(turtleStateDataSource.getXCoordinate(currId)),
					turtleTranslator.convertYImageCordinate(turtleStateDataSource.getYCoordinate(currId)),
					turtleTranslator.convertAngle(turtleStateDataSource.getAngle(currId)),
					turtleStateDataSource.getTurtleIsShowing(currId));
		}
	}

	private void initializeTurtle(int currId) {

		EventHandler<MouseEvent> e = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				turtleActionsHandler.toggleTurtle(currId);
				currentlySelectedID = currId;
				updateVariables();
				event.consume();
			}
		};

		canvasActions.initializeTurtle(currId,
				turtleTranslator.convertXImageCordinate(turtleStateDataSource.getXCoordinate(currId)),
				turtleTranslator.convertYImageCordinate(turtleStateDataSource.getYCoordinate(currId)),
				turtleTranslator.convertAngle(turtleStateDataSource.getAngle(currId)),
				turtleStateDataSource.getTurtleIsShowing(currId), e);

	}

	/**
	 * @param obs
	 * @param o
	 *            performs all necessary updates when something in
	 *            BoardStateDataSource changes
	 */
	public void update(BoardStateDataSource obs, Object o) {
		colorMap = boardStateDataSource.getColorMap();
		Iterator<PathLine> pathLine = obs.getPaths();
		if (pathLine.hasNext() == false) {
			// TODO: Paint fresh rectangle to background color.
		}
		while (pathLine.hasNext()) {
			PathLine currPathLine = pathLine.next();
			canvasActions.drawPath(
					Color.rgb(currPathLine.getPenColor().getRed(), currPathLine.getPenColor().getGreen(),
							currPathLine.getPenColor().getBlue()),
					currPathLine.getPenThickness(), turtleTranslator.convertXCordinate(currPathLine.getX1()),
					turtleTranslator.convertYCordinate(currPathLine.getY1()),
					turtleTranslator.convertXCordinate(currPathLine.getX2()),
					turtleTranslator.convertYCordinate(currPathLine.getY2()),
					penTypeMap.get(currPathLine.getPenType()));
		}
		canvasActions.setBackgroundColorCanvas(colorMap.get(obs.getBackgroundColorIndex()));
	}

	/**
	 * @param obs
	 * @param o
	 *            performs all necessary updates when something in
	 *            GeneralSettingsController changes
	 */
	public void update(GeneralSettingsController obs, Object o) {
		if (obs.getNewCommandLineFromFile() != null)
			runCommand(obs.getNewCommandLineFromFile());
	}

	/**
	 * @param obs
	 * @param o
	 *            performs all necessary updates when something in
	 *            TurtleSettingsController changes
	 */
	public void update(TurtleSettingsController obs, Object o) {
		if (obs.getNewImage() != null) {
			int myShape = 0;
			for (Integer myElem : shapeMap.keySet()) {
				if (shapeMap.get(myElem).equals(obs.getNewImage())) {
					myShape = myElem;
					break;
				}
			}
			turtleActionsHandler.setShape(myShape);

		}
		canvasActions.setAnimationSpeed(obs.getNewAnimationSpeed());

	}

	/**
	 * @param obs
	 * @param o
	 *            performs all necessary updates when something in
	 *            WorkspaceSettingsController changes
	 */
	public void update(WorkspaceSettingsController obs, Object o) {
		colorMap = boardStateDataSource.getColorMap();
		if (obs.getNewBackgroundColor() != null)
			canvasActions.setBackgroundColorCanvas(new RGBColor(obs.getNewBackgroundColor().getRed() * 255,
					obs.getNewBackgroundColor().getGreen() * 255, obs.getNewBackgroundColor().getBlue() * 255));
		if (obs.getNewLanguage() != null)
			commandHandler.setLanguage(this, obs.getNewLanguage());
	}

	/**
	 * @param obs
	 * @param o
	 *            performs all necessary updates when something in
	 *            PenSettingsController changes
	 */
	public void update(PenSettingsController obs, Object o) {
		if (obs.getNewPenColor() != null) {
			int i = 0;
			if (!colorMap.containsValue(new RGBColor(obs.getNewPenColor()))) {
				while (true) {
					if (!colorMap.containsKey(i))
						break;
					i++;
				}
				boardStateDataSource.addColorToPalette(i, (int) (obs.getNewPenColor().getRed() * 255),
						(int) (obs.getNewPenColor().getBlue() * 255), (int) (obs.getNewPenColor().getGreen() * 255));
				turtleActionsHandler.setPenColor(i);
			}
			if (i == 0) {
				for (Integer myElem : colorMap.keySet()) {
					if (colorMap.get(myElem).equals(obs.getNewPenColor())) {
						turtleActionsHandler.setPenColor(myElem);
						break;
					}
				}
			}
		}

		if (obs.getNewPenType() != null && penTypeMap.containsValue(obs.getNewPenType())) {
			for (Integer myElem : penTypeMap.keySet()) {
				if (penTypeMap.get(myElem).equals(obs.getNewPenType())) {
					turtleActionsHandler.setPenType(myElem);
					break;
				}
			}
		}
		if (obs.getNewPenThickness() != 0)
			turtleActionsHandler.setPenThickness(obs.getNewPenThickness());
	}

	/**
	 * updates all variables in the TableViews based on the information from the
	 * turtle and board state data sources
	 */
	public void updateVariables() {
		tableViewController.updateTurtleVariablesList(turtleStateDataSource, currentlySelectedID);
		tableViewController.updateUserDefinedVariablesList(boardStateDataSource);
		tableViewController.updateUserDefinedCommandsList(boardStateDataSource);
		tableViewController.updateMapList(colorMap, penTypeMap, shapeMap);
	}

	@Override
	public void saveWorkspace() {
		DateFormat df = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
		XMLWriter myWriter = new XMLWriter("workspace_at_" + reportDate, boardStateDataSource, turtleStateDataSource);

	}

	@Override
	public void saveHistoricCommands() {
		DateFormat df = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
		CommandPrinter myWriter = new CommandPrinter("history_at_" + reportDate, pastCommands);

	}

	@Override
	public void loadBoard(String string) {
		FileIOController.loadBoardWithFile(string, this);
	}

	//// setters and getters

	public void setBoardStateDataSource(BoardStateDataSource boardStateDataSource) {
		this.boardStateDataSource = boardStateDataSource;
		colorMap = boardStateDataSource.getColorMap();
	}

	public void setTurtleStateDataSource(TurtleStateDataSource turtleStateDataSource) {
		this.turtleStateDataSource = turtleStateDataSource;
		Iterator<Integer> turtleIds = turtleStateDataSource.getTurtleIDs();
		while (turtleIds.hasNext()) {
			int currId = turtleIds.next();
			initializeTurtle(currId);
		}

		currentlySelectedID = turtleStateDataSource.getTurtleIDs().next();
	}

	public void setBoardActionsHandler(BoardActionsHandler boardActionsHandler) {
		this.boardActionsHandler = boardActionsHandler;
	}

	public void setTurtleActionsHandler(TurtleActionsHandler turtleActionsHandler) {
		this.turtleActionsHandler = turtleActionsHandler;
	}

	public void setCommandHandler(SlogoCommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}

	public double getImageWidth() {
		return viewProperties.getDoubleProperty("image_width");
	}

	public double getImageHeight() {
		return viewProperties.getDoubleProperty("image_height");
	}
}
