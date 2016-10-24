package gui;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import general.Properties;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SettingsController extends Observable {

	private static final String IMAGE_PATH = "resources/images/";

	private static final int PADDING = 4;

	private Properties viewProperties;
	private HBox hBox;
	private Button imageButton;

	private Color newBackgroundColor;
	private Color newPenColor;
	private Image newImage;
	private String newLanguage;
	private Stage stage;

	public SettingsController(Stage myStage, Properties viewProperties) {
		stage = myStage;
		this.viewProperties = viewProperties;
		hBox = new HBox(viewProperties.getDoubleProperty("padding"));
		hBox.getChildren().add(initializePenColorSetting());
		hBox.getChildren().add(initializeBackgroundColorSetting());
		hBox.getChildren().add(initializeLanguageSetting());
		hBox.getChildren().add(initializeTurtleImageSetting(stage));
		hBox.getChildren().add(initializeGetHelpButton());
	}
	
	private Node initializeGetHelpButton() {
		Button helpButton = createButton("Get help!", viewProperties.getDoubleProperty("help_button_width"));
		helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            BrowserView myView = new BrowserView(stage);
            }
        });

		return helpButton;
	}

	private Node initializePenColorSetting() {
		ColorPicker penColorPicker = new ColorPicker();
		penColorPicker.setValue(Color.BLACK);
		penColorPicker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				setChanged();
				newPenColor = penColorPicker.getValue();
				notifyObservers();
			}
		});
		VBox tempBox = makeLabelForComboBox(penColorPicker, "Pen color");
		return tempBox;
	}

	private Node initializeBackgroundColorSetting() {
		ColorPicker backgroundColorPicker = new ColorPicker();
		backgroundColorPicker.setValue(Color.WHITE);
		backgroundColorPicker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				setChanged();
				newBackgroundColor = backgroundColorPicker.getValue();
				notifyObservers();
			}
		});
		VBox tempBox = makeLabelForComboBox(backgroundColorPicker, "Background color");
		return tempBox;
	}


	private Node initializeLanguageSetting() {
		ComboBox<String> languageSelect = new ComboBox<String>();
		languageSelect.setVisibleRowCount(3);
		String rawLanguage = viewProperties.getStringProperty("languages");
		rawLanguage = rawLanguage.replaceAll("\\s","");
		List<String> languages = Arrays.asList(rawLanguage.split(","));
		languageSelect.getItems().addAll(languages);
		languageSelect.setValue("English");
		languageSelect.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				setChanged();
				newLanguage = languageSelect.getValue();
				notifyObservers();
			}
		});
		VBox tempBox = makeLabelForComboBox(languageSelect, "Set language");
		return tempBox;
	}

	private Node initializeTurtleImageSetting(Stage stage) {
		ComboBox<String> shapesComboBox = new ComboBox<String>();
		shapesComboBox.setVisibleRowCount(3);
		shapesComboBox.getItems().addAll("elephant", "turtle", "pig", "frog");
		shapesComboBox.setValue("Change Shape");

		shapesComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				if(t1!=null){
				setChanged();
				Image image = ViewImageChooser.selectImage(IMAGE_PATH+t1+".png", 50, 50);
				newImage = image;
				notifyObservers();
				}
			}
		});
		return shapesComboBox;

	}
	
	private VBox makeLabelForComboBox(Node backgroundColorPicker, String text) {
		Label lbl = new Label();
		lbl.setText(text);
		VBox tempBox = new VBox(PADDING);
		tempBox.getChildren().addAll(backgroundColorPicker,lbl);
		return tempBox;
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

	public Color getNewBackgroundColor() {
		return newBackgroundColor;
	}

	public Color getNewPenColor() {
		return newPenColor;
	}
	
	public String getNewLanguage(){
		return newLanguage;
	}

	public Image getNewImage() {
		return newImage;
	}

}
