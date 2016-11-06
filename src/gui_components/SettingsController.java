package gui_components;

import java.util.Observable;

import general.NewSlogoInstanceCreator;
import general.Properties;
import gui.TabViewController;
import gui.FileChooserPath;
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

/**
 * @author Lucia Martos, Eric Song
 */
public class SettingsController extends Observable {
	private PenSettingsController penSettingsController;
	private TurtleSettingsController turtleSettingsController;
	private WorkspaceSettingsController workspaceSettingsController;
	private GeneralSettingsController generalSettingsController;
	private Properties viewProperties;
	private HBox hBox;
	private NewSlogoInstanceCreator instanceCreator;
	private SaveWorkspaceInterface myInterface;

	public SettingsController(Properties viewProperties, NewSlogoInstanceCreator instanceCreator, SaveWorkspaceInterface myInterface) {
		this.instanceCreator = instanceCreator;
		this.viewProperties = viewProperties;
		this.myInterface = myInterface;
		initializeSettingsControllers();
		hBox = new HBox(viewProperties.getDoubleProperty("padding"));
		hBox.getChildren().add(penSettingsController.getNode());
		hBox.getChildren().add(workspaceSettingsController.getNode());
		hBox.getChildren().add(turtleSettingsController.getNode());
		hBox.getChildren().add(generalSettingsController.getNode());
	}

	private void initializeSettingsControllers() {
		 penSettingsController = new PenSettingsController(viewProperties);
		 turtleSettingsController = new TurtleSettingsController(viewProperties);
		 workspaceSettingsController = new WorkspaceSettingsController(viewProperties);
		 generalSettingsController = new GeneralSettingsController(viewProperties,instanceCreator, myInterface);
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


	public HBox getNode() {
		return hBox;
	}


}
