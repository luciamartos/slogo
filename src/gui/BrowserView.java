package gui;
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
 
public class BrowserView {

    public  BrowserView (Stage stage) {
       // stage.getScene().add
        stage.setTitle("Web View");
      stage.setWidth(300);
      stage.setHeight(300);
        Scene scene = new Scene(new Group());

        VBox root = new VBox();     

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(browser);
        
        webEngine.getLoadWorker().stateProperty().addListener(
            new ChangeListener<State>() {
              @Override public void changed(ObservableValue ov, State oldState, State newState) {

                  if (newState == Worker.State.SUCCEEDED) {
                    stage.setTitle(webEngine.getLocation());
                    System.out.println("called");
                }
                  
                }
            });
        webEngine.load("http://www.cs.duke.edu/courses/compsci308/fall16/assign/03_slogo/commands.php");        
        
        webEngine.loadContent("<b>asdf</b>");

        root.getChildren().addAll(scrollPane);
        scene.setRoot(root);

        stage.setScene(scene);
        stage.show();
    }
 
}