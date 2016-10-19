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
		setupStage(stage);
		
	}
	
	private void setupStage(Stage stage){
		stage.setTitle("SLOGO"); //AppResources.APP_TITLE.getResource()
        stage.setScene(view.getScene());
        stage.show();
	}

}
