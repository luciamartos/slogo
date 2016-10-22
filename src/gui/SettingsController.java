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
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SettingsController extends Observable {

	private static final String IMAGE_PATH = "resources/images/";

	private Properties viewProperties;
	private HBox hBox;
	private Button imageButton;

	private String newBackgroundColor;
	private Color newPenColor;
	private Image newImage;

	public SettingsController(Stage stage, Properties viewProperties) {
		this.viewProperties = viewProperties;
		initializeHBox();
		initializePenColorSetting();
		initializeBackgroundColorSetting();
		initializeTurtleImageSetting(stage);
	}

	private void initializeHBox() {
		hBox = new HBox(viewProperties.getDoubleProperty("padding"));
		hBox.setLayoutX(viewProperties.getDoubleProperty("settings_x"));
		hBox.setLayoutY(viewProperties.getDoubleProperty("settings_y"));
	}

	private void initializePenColorSetting() {
		TextField colorInput = createTextBox("Change Pen Color", 200);
		Button colorButton = createButton("Set", viewProperties.getDoubleProperty("run_button_width"));
		colorButton.setOnAction(event -> {
			Color tempColor = checkValidColor(colorInput.getText());
			setChanged();
			if (tempColor == null) {
				notifyObservers("Invalid Pen Color: " + colorInput.getText());
			} else {
				newPenColor = tempColor;
				notifyObservers();
			}
			colorInput.clear();
		});
		hBox.getChildren().addAll(colorInput, colorButton);
	}

	private void initializeBackgroundColorSetting() {
		// set the text box
		TextField colorInput = createTextBox("Change Background Color", 200);

		// set the run button
		Button colorButton = createButton("Set", viewProperties.getDoubleProperty("run_button_width"));
		colorButton.setOnAction(event -> {
			setChanged();
			if (checkValidColor(colorInput.getText()) == null) {
				notifyObservers("Invalid Background Color: " + colorInput.getText());
			} else {
				newBackgroundColor = colorInput.getText().toLowerCase();
				notifyObservers();
			}
			colorInput.clear();
		});
		hBox.getChildren().addAll(colorInput, colorButton);
	}

	private Color checkValidColor(String colorText) {
		try {
			Field field = Class.forName("javafx.scene.paint.Color").getField(colorText.toUpperCase());
			return (Color) field.get(null);
		} catch (Exception e) {
			return null;
		}
	}

	private void initializeTurtleImageSetting(Stage stage) {
		imageButton = createButton("Change Turtle Image", 200);
		hBox.getChildren().add(imageButton);
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				FileChooser fileChooser = new FileChooser();
				File file = fileChooser.showOpenDialog(stage);
				setChanged();
				try {
					Image image = new Image(IMAGE_PATH + file.getName(), 50, 50, true, true);
					newImage = image;
					notifyObservers();
				} catch (Exception ex) {
					notifyObservers("The file you selected is not a valid image file: " + file.getName());
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

	public HBox getHBox() {
		return hBox;
	}

	public String getNewBackgroundColor() {
		return newBackgroundColor;
	}

	public Color getNewPenColor() {
		return newPenColor;
	}

	public Image getNewImage() {
		return newImage;
	}

}
