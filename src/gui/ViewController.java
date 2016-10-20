package gui;

import java.util.ResourceBundle;

import general.Properties;
import javafx.stage.Stage;

/**
 * 
 * @author Eric Song
 */
public class ViewController {
	
	private static final String VIEW_PROPERTIES_PACKAGE = "resources.properties/";
	private Properties viewProperties;
	
	MainView view;

	public ViewController(Stage stage) {
		viewProperties = new Properties(VIEW_PROPERTIES_PACKAGE + "View");
		view = new MainView(viewProperties);
		setupStage(stage);
	}
	
	private void setupStage(Stage stage){
		stage.setTitle(viewProperties.getStringProperty("title"));
        stage.setScene(view.getScene());
        stage.show();
	}

}
