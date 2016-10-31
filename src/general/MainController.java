package general;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import gui.SlogoCommandInterpreter;
import gui.TabViewController;
import gui.WindowViewController;
import interpreter.MainInterpreter;
import javafx.stage.Stage;
import model.BoardStateController;

/**
 * 
 * @author Eric Song, Andrew Bihl
 */
public class MainController implements NewSlogoInstanceCreator, SlogoCommandHandler{
	
	private WindowViewController windowViewController;
	private HashMap<TabViewController,BoardStateController> vcMap;
	private MainInterpreter interpreter;

	public MainController(Stage stage) {
    	windowViewController = new WindowViewController(stage, this);
    	vcMap = new HashMap<TabViewController,BoardStateController>();
    	interpreter = new MainInterpreter();
    	addSlogoInstance();
	}
	
	@Override
	public void addSlogoInstance(){
		TabViewController viewController = makeTabViewController("Tab "+(vcMap.keySet().size()+1));
    	BoardStateController modelController = new BoardStateController();
    	vcMap.put(viewController, modelController);
    	
    	viewController.setBoardStateDataSource(modelController);
    	viewController.setCommandHandler(this);
    	
    	modelController.addBoardStateListener(viewController);
		
	}
	
	public TabViewController makeTabViewController(String title){
		return windowViewController.makeTabViewController(title);
	}
	
	public void closeTabViewController(TabViewController closedTab){
		windowViewController.closeTabViewController(closedTab);
		vcMap.remove(closedTab);
	}
	
	private void updateInterpreter(TabViewController viewController,BoardStateController modelController){
		interpreter.setStateDataSource(modelController);
    	interpreter.setVarDataSource(modelController);
    	interpreter.setStateUpdater(modelController);
    	interpreter.setErrorPresenter(viewController);
	}

	@Override
	public void parseInput(TabViewController viewController, String input){
		BoardStateController modelController = vcMap.get(viewController);
		updateInterpreter(viewController, modelController);
		try{
		interpreter.parseInput(input);
		}
		catch(Exception e){
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
