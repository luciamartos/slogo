package interpreter;

import javafx.application.Application;
import javafx.stage.Stage;
import model.TurtleStateController;

public class TestMain extends Application{
	
	String input = "+ 100 sum 100 sum 100 sum 100 sum 100 100";
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		TurtleStateController controller = new TurtleStateController();
		MainInterpreter main = new MainInterpreter();
		main.parseInput(input, controller);
	}
}
