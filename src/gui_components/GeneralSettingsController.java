// This entire file is part of my masterpiece.
// Eric Song
//
// This code visualizes various settings buttons as well as hooks up different ActionHandlers.
// I think this class is well designed because it is not only clean and maintainable but also 
// effectively delegates tasks to other classes. For example, the class is consistent with the
// observer/observable design pattern in that it extends Observable so that any observer can 
// be notified when there is an undo request or a command input. Furthermore it implements the
// ReadCommandFile interface with Override methods to allow for the CommandFileUploader to call
// methods inside this interface

package gui_components;

import java.util.Observable;
import general.NewSlogoInstanceCreator;
import general.Properties;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Lucia Martos, Eric Song
 */
public class GeneralSettingsController extends Observable implements ReadCommandFile {
	private static final String WORKSPACE_SETTINGS = "data/examples/workspace_settings/";
	private Properties viewProperties;
	private HBox hBox;
	private SaveWorkspaceInterface myInterface;
	private String newCommandString;
	private NewSlogoInstanceCreator instanceCreator;
	private boolean undo;

	public GeneralSettingsController(Properties viewProperties, NewSlogoInstanceCreator instanceCreator,
			SaveWorkspaceInterface myInterface) {
		this.instanceCreator = instanceCreator;
		this.viewProperties = viewProperties;
		this.myInterface = myInterface;

		VBox leftBox = new VBox(viewProperties.getDoubleProperty("padding"));
		leftBox.getChildren().add(initializeUndoButton());
		leftBox.getChildren().add(initalizeFileLoader());
		leftBox.getChildren().add(initalizeCommandFileLoader());

		VBox middleBox = new VBox(viewProperties.getDoubleProperty("padding"));
		middleBox.getChildren().add(initializeAddTabButton());
		middleBox.getChildren().add(initializeGetHelpButton());
		middleBox.getChildren().add(initializeSaveWorskpaceButton());

		VBox rightBox = new VBox(viewProperties.getDoubleProperty("padding"));
		rightBox.getChildren().add(initializeSaveHistoricCommandsButton());

		hBox = new HBox(viewProperties.getDoubleProperty("padding"));
		hBox.getChildren().addAll(leftBox, middleBox, rightBox);
	}

	private Node initializeSaveWorskpaceButton() {
		Button saveWorkspace = createButton(viewProperties.getStringProperty("Save_workspace"),
				viewProperties.getDoubleProperty("loads_button_width"));

		saveWorkspace.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				myInterface.saveWorkspace();
			}
		});
		return saveWorkspace;
	}

	private Node initializeSaveHistoricCommandsButton() {
		Button saveHistoricCommands = createButton(viewProperties.getStringProperty("Save_history"),
				viewProperties.getDoubleProperty("loads_button_width"));
		saveHistoricCommands.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				myInterface.saveHistoricCommands();
			}
		});
		return saveHistoricCommands;
	}

	private Node initializeAddTabButton() {
		Button addTab = createButton("Add Tab", viewProperties.getDoubleProperty("help_button_width"));
		addTab.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				instanceCreator.addSlogoInstance();
			}
		});
		return addTab;
	}

	private Node initalizeCommandFileLoader() {
		CommandFileUploader myUploader = new CommandFileUploader(this, "Command file",
				viewProperties.getStringProperty("command_file_uploader_path"));
		return myUploader.getFileUploaderButton();
	}

	private Node initalizeFileLoader() {
		EnvironmentFileUploader myUploader = new EnvironmentFileUploader(this, "Settings file", WORKSPACE_SETTINGS);
		return myUploader.getFileUploaderButton();
	}

	private Node initializeUndoButton() {
		Button undoButton = createButton(viewProperties.getStringProperty("Undo"),
				viewProperties.getDoubleProperty("help_button_width"));
		undoButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				setChanged();
				undo = true;
				notifyObservers();
			}
		});
		return undoButton;
	}

	private Node initializeGetHelpButton() {
		Button helpButton = createButton(viewProperties.getStringProperty("get_help"),
				viewProperties.getDoubleProperty("help_button_width"));
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

	public boolean isUndo() {
		return undo;
	}
}
