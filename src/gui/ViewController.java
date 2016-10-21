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
	private Stage stage;
	
	MainView view;

	public ViewController(Stage stage) {
		this.stage = stage;
		viewProperties = new Properties(VIEW_PROPERTIES_PACKAGE + "View");
		view = new MainView(this);
		setupStage();
	}
	
	private void setupStage(){
		stage.setTitle(viewProperties.getStringProperty("title"));
        stage.setScene(view.getScene());
        stage.show();
	}
	
	public Properties getProperties(){
		return viewProperties;
	}
	
	public Stage getStage(){
		return stage;
	}
}
