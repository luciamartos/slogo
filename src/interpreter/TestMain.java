package interpreter;

import javafx.application.Application;
import javafx.stage.Stage;
import model.BoardStateController;
import model.TurtleStatesController;

public class TestMain extends Application{
	
	String input2 = "ifelse less? 1000 100 [ fd 100 ] [ fd 50 ]";
	String input = "to [ banana ] [ fd 50 ] ";
	String inputtwo = "banana";
	String language = "Chinese";
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BoardStateController controller = new BoardStateController();
		TurtleStatesController turtleController = new TurtleStatesController(controller);
		MainInterpreter main = new MainInterpreter();
//		TabViewController vc = new TabViewController(primaryStage);
		
		//controller is passed in three times because there are three different interfaces
		main.setStateDataSource(turtleController);
		main.setTurtleStateUpdater(turtleController);
		main.setVarDataSource(controller);
		main.setBoardStateUpdater(controller);
//		main.setErrorPresenter(vc);
		
//		main.setLanguage(language);
		main.parseInput(input);   
		main.parseInput(inputtwo);   
	}
}
