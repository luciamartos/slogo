package general;
import gui.ViewController;
import javafx.stage.Stage;

/**
 * 
 * @author Eric Song
 */
public class MainController {
	
	private ViewController viewController;
//	private ModelController modelController;

	public MainController(Stage stage) {
    	viewController = new ViewController(stage);
//    	modelController = new ModelController();
	}

}
