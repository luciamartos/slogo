package model;

import java.util.Map;

import gui.TabViewController;
import gui.WindowViewController;
import interpreter.MainInterpreter;
import interpreter.UserVariablesDataSource;

public class ModelController implements UserVariablesDataSource, TurtleStateUpdater,BoardStateUpdater {
	private BoardStateController boardController;
	private TurtleStatesController turtleController;
	
	public ModelController(MainInterpreter interpreter, TabViewController viewController) {
		
	}
}
