package gui;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Observable;

import general.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
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
	private String newLanguage;

	public SettingsController(Stage stage, Properties viewProperties) {
		this.viewProperties = viewProperties;
		hBox = new HBox(viewProperties.getDoubleProperty("padding"));
		hBox.getChildren().add(initializePenColorSetting());
		hBox.getChildren().add(initializeBackgroundColorSetting());
		hBox.getChildren().add(initializeLanguageSetting());
		hBox.getChildren().add(initializeTurtleImageSetting(stage));
	}


	private Node initializePenColorSetting() {
		TextField colorInput = createTextBox("Change Pen Color", 150);
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
		HBox tempBox =  new HBox();
		tempBox.getChildren().addAll(colorInput, colorButton);
		return tempBox;
	}

	private Node initializeBackgroundColorSetting() {
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
		HBox tempBox =  new HBox();
		tempBox.getChildren().addAll(colorInput, colorButton);
		return tempBox;
	}

	private Color checkValidColor(String colorText) {
		try {
			Field field = Class.forName("javafx.scene.paint.Color").getField(colorText.toUpperCase());
			return (Color) field.get(null);
		} catch (Exception e) {
			return null;
		}
	}
	
	private Node initializeLanguageSetting() {
		// set the text box
		TextField languageInput = createTextBox("Change Language", 180);

		// set the run button
		Button languageButton = createButton("Set", viewProperties.getDoubleProperty("run_button_width"));
		//TODO: implement language button
//		languageButton.setOnAction(event -> {
//			setChanged();
//			if (checkValidColor(languageInput.getText()) == null) {
//				notifyObservers("Invalid Background Color: " + languageInput.getText());
//			} else {
//				newBackgroundColor = languageInput.getText().toLowerCase();
//				notifyObservers();
//			}
//			languageInput.clear();
//		});
		HBox tempBox =  new HBox();
		tempBox.getChildren().addAll(languageInput, languageButton);
		return tempBox;
	}

	private Node initializeTurtleImageSetting(Stage stage) {
		ComboBox<String> shapesComboBox = new ComboBox<String>();
		shapesComboBox.getItems().addAll("Elephant", "Turtle", "Pig");
		shapesComboBox.setValue("Turtle");
		
		
		//imageButton = createButton("Change Image", 120);
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				
				
//				FileChooser fileChooser = new FileChooser();
//				File file = fileChooser.showOpenDialog(stage);
//				setChanged();
//				try {
//					Image image = new Image(IMAGE_PATH + file.getName(), 50, 50, true, true);
//					newImage = image;
//					notifyObservers();
//				} catch (Exception ex) {
//					notifyObservers("The file you selected is not a valid image file: " + file.getName());
//				}
			}
		};
	//	imageButton.setOnAction(event);
		return imageButton;

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
