package gui;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Observable;

import general.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SettingsController extends Observable {

	private Properties viewProperties;
	private VBox vBox;
	private Button imageButton;

	private String newBackgroundColor;
	private Color newPenColor;
	private File newImage;

	public SettingsController(Stage stage, Properties viewProperties) {
		this.viewProperties = viewProperties;
		initializeVBox();
		initializePenColorSetting();
		initializeBackgroundColorSetting();
		initializeTurtleImageSetting(stage);
	}

	private void initializeVBox() {
		vBox = new VBox(viewProperties.getDoubleProperty("padding"));
		vBox.setLayoutX(viewProperties.getDoubleProperty("settings_x"));
		vBox.setLayoutY(viewProperties.getDoubleProperty("settings_y"));
	}

	private void initializePenColorSetting() {
		HBox hBox = new HBox(viewProperties.getDoubleProperty("padding"));
		TextField colorInput = createTextBox("Change Pen Color", 200);
		Button colorButton = createButton("Set", viewProperties.getDoubleProperty("run_button_width"));
		colorButton.setOnAction(event -> {
			try {
				Field field = Class.forName("javafx.scene.paint.Color").getField(colorInput.getText().toUpperCase());
				newPenColor = (Color) field.get(null);
				notifyObservers();
			} catch (Exception e) {
				notifyObservers("Invalid Color");
			}
			setChanged();
			colorInput.clear();
		});
		hBox.getChildren().addAll(colorInput, colorButton);
		vBox.getChildren().add(hBox);
	}

	private void initializeBackgroundColorSetting() {
		HBox hBox = new HBox(viewProperties.getDoubleProperty("padding"));

		// set the text box
		TextField colorInput = createTextBox("Change Background Color", 200);

		// set the run button
		Button colorButton = createButton("Set", viewProperties.getDoubleProperty("run_button_width"));
		colorButton.setOnAction(event -> {
			try {
				Field field = Class.forName("javafx.scene.paint.Color").getField(colorInput.getText().toUpperCase());
				Color tempColor = (Color) field.get(null);
				newBackgroundColor = colorInput.getText().toLowerCase();
				notifyObservers();
				
			} catch (Exception e) {
				notifyObservers("Invalid Color");
			}
			setChanged();
			colorInput.clear();
		});
		hBox.getChildren().addAll(colorInput, colorButton);
		vBox.getChildren().add(hBox);
	}

	private void initializeTurtleImageSetting(Stage stage) {
		imageButton = createButton("Change Turtle Image", 200);
		vBox.getChildren().add(imageButton);
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				FileChooser fileChooser = new FileChooser();
				File file = fileChooser.showOpenDialog(stage);
				if (file != null && file.isFile()) {
					newImage = file;
					setChanged();
					notifyObservers();
				}
			}
		};
		imageButton.setOnAction(event);

	}

	private TextField createTextBox(String text, double width) {
		TextField textField = new TextField();
		textField.setPromptText(text);
		textField.setPrefWidth(width); // CHANGE TO COMMAND BUTTON WIDTH
		return textField;
	}

	private Button createButton(String text, double width) {
		Button button = new Button(text);
		button.setPrefWidth(width);
		return button;
	}

	public VBox getVBox() {
		return vBox;
	}

	public String getNewBackgroundColor() {
		return newBackgroundColor;
	}

	public Color getNewPenColor() {
		return newPenColor;
	}

	public File getNewImage() {
		return newImage;
	}

}
