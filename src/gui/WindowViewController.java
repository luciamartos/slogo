package gui;

import java.util.ArrayList;
import java.util.List;

import general.Properties;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class WindowViewController {
	
	private static final String VIEW_PROPERTIES_PACKAGE = "resources.properties/";
	private static final Paint BACKGROUND_COLOR_SCENE = Color.ALICEBLUE;

	private Properties viewProperties;
	private Stage stage;
	private TabPane tabPane;
	private List<TabViewController> tabViewControllerList;
	
	public WindowViewController(Stage stage){
		viewProperties = new Properties(VIEW_PROPERTIES_PACKAGE + "View");
		setupStage();
		tabViewControllerList = new ArrayList<TabViewController>();

	}
	
	public TabViewController makeTabViewController(String tabTitle){
		TabViewController tabViewController = new TabViewController(tabPane,viewProperties,tabTitle);
		tabViewControllerList.add(tabViewController);
		return tabViewController;
	}
	
	private void setupStage(){
		double appWidth = viewProperties.getDoubleProperty("app_width");
		double appHeight = viewProperties.getDoubleProperty("app_height");
		stage = new Stage();
		Group sceneRoot = new Group();
		BorderPane borderPane = new BorderPane();
		tabPane = new TabPane();
		borderPane.setCenter(tabPane);		
		sceneRoot.getChildren().add(borderPane);
		stage.setTitle(viewProperties.getStringProperty("title"));
		Scene scene = new Scene(sceneRoot, appWidth, appHeight, BACKGROUND_COLOR_SCENE);
		borderPane.prefHeightProperty().bind(scene.heightProperty());
		borderPane.prefWidthProperty().bind(scene.widthProperty());
		stage.setScene(scene);
		stage.show();

	}

}
