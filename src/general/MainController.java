package general;
import gui.ViewController;
import interpreter.MainInterpreter;
import javafx.stage.Stage;
import model.BoardStateController;

/**
 * 
 * @author Eric Song, Andrew Bihl
 */
public class MainController {
	
	private ViewController viewController;
	private BoardStateController modelController;
	private MainInterpreter interpreter;

	public MainController(Stage stage) {
    	modelController = new BoardStateController();
    	viewController = new ViewController(stage);
    	interpreter = new MainInterpreter();
    	
    	viewController.setModelController(modelController);
    	viewController.setInterpreter(interpreter);
    	
    	modelController.addBoardStateListener(viewController);
    	
    	interpreter.setStateDataSource(modelController);
    	interpreter.setVarDataSource(modelController);
    	interpreter.setStateUpdater(modelController);
   
	}

}
