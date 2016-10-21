package interpreter;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestMain extends Application{
	
	String input = "random 100";
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		MainInterpreter main = new MainInterpreter();
		main.parseInput(input);
	}
}
