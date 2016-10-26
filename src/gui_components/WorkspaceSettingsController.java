package gui_components;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import general.Main;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class WorkspaceSettingsController extends Observable {

	private static final String IMAGE_PATH = "resources/images/";

	private static final int PADDING = 4;

	private static final double MIN_THICKNESS = 1;
	private static final double MAX_THICKNESS = 10;
	private static final double INIT_THICKNESS = 2;


	private Properties viewProperties;
	private VBox vBox;
	private Button imageButton;

	private Color newBackgroundColor;
	private Color newPenColor;
	private Image newImage;
	private String newLanguage;
	private Stage stage;
	private double newPenThickness;
	private String newPenType;

	public WorkspaceSettingsController(Stage myStage, Properties viewProperties) {
		stage = myStage;
		this.viewProperties = viewProperties;
		vBox = new VBox(7);
		Label lbl = new Label();
		lbl.setText("Workspace settings");
		vBox.getChildren().add(lbl);
		vBox.getChildren().add(initializeBackgroundColorSetting());
		vBox.getChildren().add(initializeLanguageSetting());
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
		//VBox tempBox = makeLabel(backgroundColorPicker, "Background color");
		return backgroundColorPicker;
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
		//VBox tempBox = makeLabel(languageSelect, "Set language");
		return languageSelect;
	}

	
	private VBox makeLabel(Node backgroundColorPicker, String text) {
		Label lbl = new Label();
		lbl.setText(text);
		VBox tempBox = new VBox(PADDING);
		tempBox.getChildren().addAll(backgroundColorPicker,lbl);
		return tempBox;
	}


	private Button createButton(String text, double width) {
		Button button = new Button(text);
		button.setPrefWidth(width);
		return button;
	}

	public VBox getVBox() {
		return vBox;
	}

	public Color getNewBackgroundColor() {
		return newBackgroundColor;
	}

	public String getNewLanguage(){
		return newLanguage;
	}

}
