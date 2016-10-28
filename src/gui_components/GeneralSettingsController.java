package gui_components;

import java.util.Observable;

import general.Properties;
import gui.ViewImageChooser;
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
import javafx.stage.Stage;

public class GeneralSettingsController extends Observable implements ReadCommandFileInterface {
	private Properties viewProperties;
	private VBox vBox;

	private Image newImage;
	private String newCommandString; 

	public GeneralSettingsController(Properties viewProperties) {
		this.viewProperties = viewProperties;
		vBox = new VBox(viewProperties.getDoubleProperty("padding"));
		vBox.getChildren().add(initializeUndoButton());
		vBox.getChildren().add(initalizeFileLoader());
		vBox.getChildren().add(initalizeCommandFileLoader());
		vBox.getChildren().add(initializeGetHelpButton());
	}

	private Node initalizeCommandFileLoader() {
		CommandFileUploader myUploader = new CommandFileUploader();
		return myUploader.getCommandFileUploaderButton();
	}

	private Node initalizeFileLoader() {
		ComboBox<String> fileLoaderComboBox = new ComboBox<String>();
		fileLoaderComboBox.setVisibleRowCount(3);
		fileLoaderComboBox.getItems().addAll("test.xml");
		fileLoaderComboBox.setValue("Pick file");

		fileLoaderComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				if (t1 != null) {
					setChanged();
					
					notifyObservers();
				}
			}
		});
		return fileLoaderComboBox;
	}

	private Node initializeUndoButton() {
		Button undoButton = createButton("Undo", viewProperties.getDoubleProperty("help_button_width"));
		undoButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				setChanged();
				System.out.println("UNDO ACTION");
				notifyObservers();
			}
		});
		return undoButton;
	}

	private Node initializeGetHelpButton() {
		Button helpButton = createButton("Get help!", viewProperties.getDoubleProperty("help_button_width"));
		helpButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				BrowserView myView = new BrowserView(new Stage(),viewProperties.getDoubleProperty("help_width"), viewProperties.getDoubleProperty("help_height"));
			}
		});

		return helpButton;
	}

	private Button createButton(String text, double width) {
		Button button = new Button(text);
		button.setPrefWidth(width);
		return button;
	}

	public VBox getVBox() {
		return vBox;
	}

	public Image getNewImage() {
		return newImage;
	}

	@Override
	public void getCommandLineFromFile(String myCommand) {
		setChanged();
		newCommandString = myCommand;
		notifyObservers();
	}

}
