package gui_components;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import general.Main;
import general.Properties;
import gui.ViewImageChooser;
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

public class TurtleSettingsController extends Observable {

	private static final String IMAGE_PATH = "resources/images/";

	private static final int PADDING = 4;

	private static final double MIN_THICKNESS = 1;
	private static final double MAX_THICKNESS = 10;
	private static final double INIT_THICKNESS = 2;


	private Properties viewProperties;
	private HBox hBox;

	private Image newImage;
	private Stage stage;


	public TurtleSettingsController(Stage myStage, Properties viewProperties) {
		stage = myStage;
		this.viewProperties = viewProperties;
		hBox = new HBox(viewProperties.getDoubleProperty("padding"));
	//	hBox.getChildren().add(initializeBackgroundColorSetting());
		//hBox.getChildren().add(initializeLanguageSetting());
		hBox.getChildren().add(initializeTurtleImageSetting(stage));
		hBox.getChildren().add(initializeGetHelpButton());
	}

	private Node initializeGetHelpButton() {
		Button helpButton = createButton("Get help!", viewProperties.getDoubleProperty("help_button_width"));
		helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            BrowserView myView = new BrowserView(new Stage(), viewProperties.getDoubleProperty("help_width"),viewProperties.getDoubleProperty("help_height"));
            }
        });

		return helpButton;
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

	public HBox getHBox() {
		return hBox;
	}

	public Image getNewImage() {
		return newImage;
	}

}
