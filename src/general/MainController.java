package general;
import gui.ViewController;
import javafx.stage.Stage;
import model.BoardStateController;

/**
 * 
 * @author Eric Song
 */
public class MainController {
	
	private ViewController viewController;
	private BoardStateController modelController;

	public MainController(Stage stage) {
    	viewController = new ViewController(stage);
    	modelController = new BoardStateController();
    	viewController.setDataSource(modelController);
	}

}
