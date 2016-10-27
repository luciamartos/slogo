package gui_components;

import java.util.Observable;

import general.Properties;
import gui.TabViewController;
import gui.ViewImageChooser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsController extends Observable {

	private static final String IMAGE_PATH = "resources/images/";

	private static final int PADDING = 4;

	private static final double MIN_THICKNESS = 1;
	private static final double MAX_THICKNESS = 10;
	private static final double INIT_THICKNESS = 2;

	private PenSettingsController penSettingsController;
	private TurtleSettingsController turtleSettingsController;
	private WorkspaceSettingsController workspaceSettingsController;
	private GeneralSettingsController generalSettingsController;
	private Properties viewProperties;
	private HBox hBox;

	public SettingsController(Properties viewProperties) {
		this.viewProperties = viewProperties;
		initializeSettingsControllers();
		hBox = new HBox(viewProperties.getDoubleProperty("padding"));
		hBox.getChildren().add(penSettingsController.getVBox());
		hBox.getChildren().add(workspaceSettingsController.getVBox());
		hBox.getChildren().add(turtleSettingsController.getVBox());
		hBox.getChildren().add(generalSettingsController.getVBox());
	}

	private void initializeSettingsControllers() {
		 penSettingsController = new PenSettingsController(viewProperties);
		 turtleSettingsController = new TurtleSettingsController(viewProperties);
		 workspaceSettingsController = new WorkspaceSettingsController(viewProperties);
		 generalSettingsController = new GeneralSettingsController(viewProperties);
	}
	
	public PenSettingsController getPenSettingsController(){
		return penSettingsController;
	}
	
	public TurtleSettingsController getTurtleSettingsController(){
		return turtleSettingsController;
	}
	
	public WorkspaceSettingsController getWorkspaceSettingsController(){
		return workspaceSettingsController;
	}
	
	public GeneralSettingsController getGeneralSettingsController(){
		return generalSettingsController;
	}


	public HBox getHBox() {
		return hBox;
	}


}
