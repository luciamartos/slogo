package general;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import gui.SlogoCommandInterpreter;
import gui.TabViewController;
import gui.WindowViewController;
import interpreter.MainInterpreter;
import javafx.stage.Stage;
import model.BoardStateController;
import model.TurtleStatesController;

/**
 * @author Eric Song, Andrew Bihl
 */
public class MainController implements NewSlogoInstanceCreator, SlogoCommandHandler {

	private WindowViewController windowViewController;
	private HashMap<TabViewController, BoardStateController> vcMap;
	private MainInterpreter interpreter;

	public MainController(Stage stage) {
		windowViewController = new WindowViewController(stage, this);
		vcMap = new HashMap<TabViewController, BoardStateController>();
		interpreter = new MainInterpreter();
		addSlogoInstance();
	}

	@Override
	public void addSlogoInstance() {
		TabViewController viewController = makeTabViewController("Tab " + (vcMap.keySet().size() + 1));
		BoardStateController modelController = new BoardStateController();
		TurtleStatesController turtleStatesController = modelController.getTurtleStatesController();
		vcMap.put(viewController, modelController);
		viewController.setCommandHandler(this);
		initializeControllerConnections(modelController, turtleStatesController, viewController);
	}

	/**
	 * @param boardController
	 * @param turtleStatesController
	 * @param viewController
	 *            hooks up all of the interfaces between the view controller and
	 *            model controller
	 */
	public static void initializeControllerConnections(BoardStateController boardController,
			TurtleStatesController turtleStatesController, TabViewController viewController) {
		viewController.setBoardStateDataSource(boardController);
		viewController.setBoardActionsHandler(boardController);
		viewController.setTurtleStateDataSource(turtleStatesController);
		viewController.setTurtleActionsHandler(turtleStatesController);
		viewController.updateVariables();

		boardController.addBoardStateListener(viewController);
		turtleStatesController.addObserver(viewController);
	}

	/**
	 * @param title
	 * @return makes and returns new tab view controller
	 */
	public TabViewController makeTabViewController(String title) {
		return windowViewController.makeTabViewController(title);
	}

	/**
	 * @param closedTab
	 *            performs necessary garbage collection when tab view is closed
	 */
	public void closeTabViewController(TabViewController closedTab) {
		windowViewController.closeTabViewController(closedTab);
		vcMap.remove(closedTab);
	}

	/**
	 * @param viewController
	 * @param modelController
	 *            since we are using only one interpreter, hook the interpreter
	 *            up to the respective view and model controllers
	 */
	private void updateInterpreter(TabViewController viewController, BoardStateController modelController) {
		interpreter.setStateDataSource(modelController.getTurtleStatesController());
		interpreter.setVarDataSource(modelController);
		interpreter.setTurtleStateUpdater(modelController.getTurtleStatesController());
		interpreter.setBoardStateUpdater(modelController);
		interpreter.setErrorPresenter(viewController);
		interpreter.setTurtleStampUpdater(modelController);
	}

	@Override
	public void parseInput(TabViewController viewController, String input) {
		BoardStateController modelController = vcMap.get(viewController);
		updateInterpreter(viewController, modelController);
		try {
			interpreter.parseInput(input);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void setLanguage(TabViewController viewController, String language) {
		BoardStateController modelController = vcMap.get(viewController);
		updateInterpreter(viewController, modelController);
		interpreter.setLanguage(language);
	}

}
