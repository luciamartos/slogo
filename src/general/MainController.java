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
    	viewController = new ViewController(stage);
    	modelController = new BoardStateController();
    	interpreter = new MainInterpreter();
    	
    	viewController.setDataSource(modelController);
    	viewController.setInterpreter(interpreter);
    	
    	interpreter.setStateDataSource(modelController);
    	interpreter.setVarDataSource(modelController);
    	interpreter.setStateUpdater(modelController);
   
	}

}
