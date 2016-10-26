package gui_components;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
 
/**
 * Adaptation of: http://www.java2s.com/Code/Java/JavaFX/WebEngineLoadListener.htm
 * 
 *
 */
public class BrowserView {
	
    private static final double WEB_WIDTH = 800;
	private static final double WEB_HEIGHT = 500;

	public  BrowserView (Stage stage) {
       // stage.getScene().add
        stage.setTitle("Web View");
      stage.setWidth(WEB_WIDTH);
      stage.setHeight(WEB_HEIGHT);
        Scene scene = new Scene(new Group());

        VBox root = new VBox();     

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        webEngine.getLoadWorker().stateProperty().addListener(
            new ChangeListener<State>() {
              @Override public void changed(ObservableValue ov, State oldState, State newState) {

                  if (newState == Worker.State.SUCCEEDED) {
                    stage.setTitle(webEngine.getLocation());
                }
                  
                }
            });
        
        webEngine.load("http://www.cs.duke.edu/courses/compsci308/fall16/assign/03_slogo/commands.php");        
        
        root.getChildren().addAll(browser);
        scene.setRoot(root);

        stage.setScene(scene);
        stage.show();
    }
 
}