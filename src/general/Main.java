package general;


import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for Slogo. Basic boilerplate code for launching the JavaFX
 * application.
 */
public class Main extends Application {
    private MainController controller;

    @Override
    public void start (Stage stage) {
    	controller = new MainController(stage);
    }

    public static void main (String[] args) {
        launch(args);
    }
}