package interpreter;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestMain extends Application{
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		String input = "sum 10 12";
		MathInterpreter mi = new MathInterpreter();
		mi.parseInput(input);
	}
}
