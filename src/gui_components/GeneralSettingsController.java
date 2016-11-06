package gui_components;

import java.util.Observable;

import fileIO.XMLReader;
import fileIO.XMLWriter;
import general.NewSlogoInstanceCreator;

import general.Properties;
import gui.FileChooserPath;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 * @author Lucia Martos
 */

//This entire file is part of my masterpiece.
//Lucia Martos Jimenez
//The reason I chose this file as my master piece is because it shows the use of our binding pattern and interfaces and it is an
//example of the way that I arranged the creation of the different parts of the GUI
//I think its a good class because the components are very clearly segregated and jobs are delegated between different classes

public class GeneralSettingsController extends Observable implements ReadCommandFileInterface {
	private Properties viewProperties;
	private HBox hBox;
	private SaveWorkspaceInterface myInterface;
	private String newCommandString;
	private NewSlogoInstanceCreator instanceCreator;

	public GeneralSettingsController(Properties viewProperties, NewSlogoInstanceCreator instanceCreator, SaveWorkspaceInterface myInterface) {
		this.instanceCreator = instanceCreator;
		this.viewProperties = viewProperties;
		this.myInterface = myInterface;
		
		populateHBox(viewProperties.getDoubleProperty("padding"));
		
	}

	private void populateHBox(double padding) {
		VBox vBox1 = new VBox(padding);
		vBox1.getChildren().addAll(initializeUndoButton(),initalizeFileLoader(),initalizeCommandFileLoader());

		VBox vBox2 = new VBox(padding);
		vBox2.getChildren().addAll(initializeAddTabButton(),initializeGetHelpButton(),initializeSaveWorskpaceButton());

		VBox vBox3 = new VBox(padding);
		vBox3.getChildren().add(initializeSaveHistoricCommandsButton());
		
		hBox = new HBox(padding);
		hBox.getChildren().addAll(vBox1, vBox2,vBox3);		
	}

	private Node initializeSaveWorskpaceButton() {
		Button saveWorkspace = createButton(viewProperties.getStringProperty("Save_workspace"), viewProperties.getDoubleProperty("loads_button_width"));
		saveWorkspace.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				myInterface.saveWorkspace();
			}
		});
		return saveWorkspace;
	}
	
	private Node initializeSaveHistoricCommandsButton() {
		Button saveHistoricCommands = createButton(viewProperties.getStringProperty("Save_history"), viewProperties.getDoubleProperty("loads_button_width"));
		saveHistoricCommands.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				myInterface.saveHistoricCommands();
			}
		});
		return saveHistoricCommands;	
	}

	private Node initializeAddTabButton() {
		Button addTab = createButton(viewProperties.getStringProperty("add_tab"), viewProperties.getDoubleProperty("help_button_width"));
		addTab.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				instanceCreator.addSlogoInstance();
			}
		});
		return addTab;
	}

	private Node initalizeCommandFileLoader() {
		CommandFileUploader myUploader = new CommandFileUploader(this, viewProperties.getStringProperty("Command_file"), viewProperties.getStringProperty("command_file_uploader_path"));
		return myUploader.getFileUploaderButton();
	}

	private Node initalizeFileLoader() {
		EnvironmentFileUploader myUploader = new EnvironmentFileUploader(this, viewProperties.getStringProperty("Settings file"), viewProperties.getStringProperty("settings_file_uploader_path"));
		return myUploader.getFileUploaderButton();
	}
	

	private Node initializeUndoButton() {
		Button undoButton = createButton(viewProperties.getStringProperty("Undo"), viewProperties.getDoubleProperty("help_button_width"));
		undoButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				setChanged();
				System.out.println("UNDO ACTION"); //this feature was never implemented
				notifyObservers();
			}
		});
		return undoButton;
	}

	private Node initializeGetHelpButton() {
		Button helpButton = createButton(viewProperties.getStringProperty("get_help"), viewProperties.getDoubleProperty("help_button_width"));
		helpButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				BrowserView myView = new BrowserView(new Stage(), viewProperties.getDoubleProperty("help_width"),
				viewProperties.getDoubleProperty("help_height"));
			}
		});
		return helpButton;
	}

	private Button createButton(String text, double width) {
		Button button = new Button(text);
		button.setPrefWidth(width);
		return button;
	}

	public Node getNode() {
		return hBox;
	}

	@Override
	public void getCommandLineFromFile(String myCommand) {
		setChanged();
		newCommandString = myCommand;
		notifyObservers();
	}

	public String getNewCommandLineFromFile() {
		return newCommandString;
	}

	@Override
	public void loadBoard(String string) {
		myInterface.loadBoard(string);	
	}

}
