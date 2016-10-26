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
	private static final double MIN_TURTLES = 0;
	private static final double MAX_TURTLES = 10;
	private static final double INIT_TURTLES = 1;
	private int newTurtleNumber;

	private VBox vBox;

	private Image newImage;
	private Stage stage;


	public TurtleSettingsController(Stage myStage, Properties viewProperties) {
		stage = myStage;
		vBox = new VBox(7);
		Label lbl = new Label();
		lbl.setText("Turtle settings");
		vBox.getChildren().add(lbl);
		vBox.getChildren().add(initializeTurtleImageSetting(stage));
		vBox.getChildren().add(initializeTurtleNumber());
	}



	private Node initializeTurtleNumber(){
		Slider turtleNumberSlider = new Slider();
		turtleNumberSlider.setMin(MIN_TURTLES);
		turtleNumberSlider.setMax(MAX_TURTLES);
		turtleNumberSlider.setValue(INIT_TURTLES);
		turtleNumberSlider.valueProperty().addListener(new ChangeListener<Number>(){
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				setChanged();
				newTurtleNumber = new_val.intValue();
				notifyObservers();
			}
		});
		return turtleNumberSlider;
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

	public VBox getVBox() {
		return vBox;
	}

	public Image getNewImage() {
		return newImage;
	}

	public Node getTurtleSettingsController() {
		return vBox;
	}
	
	public int getNewTurtleNumber(){
		return newTurtleNumber;
	}

}
