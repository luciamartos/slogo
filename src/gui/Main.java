package gui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for Slogo. Basic boilerplate code for launching the JavaFX
 * application.
 */
public class Main extends Application {
    private MainView mainView;

    @Override
    public void start (Stage stage) {
    	mainView = new MainView();
        stage.setTitle("SLOGO"); //AppResources.APP_TITLE.getResource()
        stage.setScene(mainView.getScene());
        stage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}