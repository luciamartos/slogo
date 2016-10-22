package interpreter;

import javafx.application.Application;
import javafx.stage.Stage;
import model.TurtleStateController;

public class TestMain extends Application{
	
	String input = "asdfasdf sum 10 20";
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		TurtleStateController controller = new TurtleStateController();
//		TurtleState state = new TurtleState();
		MainInterpreter main = new MainInterpreter(controller);
		main.parseInput(input);
	}
}
