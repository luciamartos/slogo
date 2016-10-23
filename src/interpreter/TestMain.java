package interpreter;

import javafx.application.Application;
import javafx.stage.Stage;
import model.BoardStateController;

public class TestMain extends Application{
	
	String input = "repeat 5 [ sum 5 5 ]";
	String input2 = "set :apple fd 5";
//	String input3 = "sum 5 5";
//	String language = "Chinese";
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BoardStateController controller = new BoardStateController();
		MainInterpreter main = new MainInterpreter();
		
		//controller is passed in three times because there are three different interfaces
		main.setStateDataSource(controller);
		main.setStateUpdater(controller);
		main.setVarDataSource(controller);
		
//		main.setLanguage(language);
		main.parseInput(input);  
//		main.parseInput(input2);  
//		main.parseInput(input3);  
	}
}
