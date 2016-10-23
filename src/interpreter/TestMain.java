package interpreter;

import javafx.application.Application;
import javafx.stage.Stage;
import model.BoardStateController;

public class TestMain extends Application{
	
	String input = "set potato 100";
	String input2 = "sum potato 2";
//	String language = "Chinese";
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BoardStateController controller = new BoardStateController();
		//controller is passed in three times because there are three different interfaces
		MainInterpreter main = new MainInterpreter();
		main.setStateDataSource(controller);
		main.setStateUpdater(controller);
		main.setVarDataSource(controller);
//		main.setLanguage(language);
		main.parseInput(input);  
		main.parseInput(input2);  
	}
}
