package general;
import gui.TabViewController;
import interpreter.MainInterpreter;
import javafx.stage.Stage;
import model.BoardStateController;

/**
 * 
 * @author Eric Song, Andrew Bihl
 */
public class MainController {
	
	private TabViewController viewController;
	private BoardStateController modelController;
	private MainInterpreter interpreter;

	public MainController(Stage stage) {
    	modelController = new BoardStateController();
    	viewController = new TabViewController(stage);
    	interpreter = new MainInterpreter();
    	
    	viewController.setModelController(modelController);
    	viewController.setInterpreter(interpreter);
    	
    	modelController.addBoardStateListener(viewController);
    	
    	interpreter.setStateDataSource(modelController);
    	interpreter.setVarDataSource(modelController);
    	interpreter.setStateUpdater(modelController);
    	interpreter.setErrorPresenter(viewController);
	}

}
