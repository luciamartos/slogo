package interpreter;

import javafx.application.Application;
import javafx.stage.Stage;
import model.TurtleStateController;
import model.TurtleStateDataSource;

public class TestMain extends Application{
	
	String input = "fd sum 50 less? 10 1";
	
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
