package general;
import java.util.HashMap;

import gui.TabViewController;
import gui.WindowViewController;
import interpreter.MainInterpreter;
import javafx.stage.Stage;
import model.BoardStateController;

/**
 * 
 * @author Eric Song, Andrew Bihl
 */
public class MainController{
	
	private WindowViewController windowViewController;
	private HashMap<TabViewController,BoardStateController> vcMap;
	private MainInterpreter interpreter;

	public MainController(Stage stage) {
    	windowViewController = new WindowViewController(stage);
    	vcMap = new HashMap<TabViewController,BoardStateController>();
    	interpreter = new MainInterpreter();
    	addSlogoInstance();
	}
	
	public void addSlogoInstance(){
		TabViewController viewController = makeTabViewController("Tab 1");
    	BoardStateController modelController = new BoardStateController();
    	vcMap.put(viewController, modelController);
    	
    	viewController.setModelController(modelController);
    	viewController.setInterpreter(interpreter);
    	
    	modelController.addBoardStateListener(viewController);
    	
    	interpreter.setStateDataSource(modelController);
    	interpreter.setVarDataSource(modelController);
    	interpreter.setStateUpdater(modelController);
    	interpreter.setErrorPresenter(viewController);
		
	}
	
	public TabViewController makeTabViewController(String title){
		return windowViewController.makeTabViewController(title);
	}
	
	public void closeTabViewController(TabViewController closedTab){
		windowViewController.closeTabViewController(closedTab);
		vcMap.remove(closedTab);
	}
	

}
