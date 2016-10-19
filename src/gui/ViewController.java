package gui;

import javafx.stage.Stage;

/**
 * 
 * @author Eric Song
 */
public class ViewController {
	
	MainView view;

	public ViewController(Stage stage) {
		view = new MainView();
		stage.setTitle("SLOGO"); //AppResources.APP_TITLE.getResource()
        stage.setScene(view.getScene());
        stage.show();
	}

}
