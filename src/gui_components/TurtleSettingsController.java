package gui_components;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import general.Main;
import general.Properties;
import gui.FileChooserPath;
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
	private static final double MIN_SPEED = 0;
	private static final double MAX_SPEED = 1000;
	private static final double INIT_SPEED = 10;
	private int newAnimationSpeed;

	private VBox vBox;

	private String newImage;


	public TurtleSettingsController(Properties viewProperties) {
		vBox = new VBox(7);
		vBox.getChildren().add(new Label("Turtle settings"));
		vBox.getChildren().add(initializeTurtleImageSetting());
		vBox.getChildren().add(initializeAnimationSpeed());
	}



	private Node initializeAnimationSpeed(){
		Slider turtleNumberSlider = new Slider();
		turtleNumberSlider.setMin(MIN_SPEED);
		turtleNumberSlider.setMax(MAX_SPEED);
		turtleNumberSlider.setValue(INIT_SPEED);
		turtleNumberSlider.valueProperty().addListener(new ChangeListener<Number>(){
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				setChanged();
				newAnimationSpeed = new_val.intValue();
				notifyObservers();
			}
		});
		return turtleNumberSlider;
	}
	
	private Node initializeTurtleImageSetting() {
		ComboBox<String> shapesComboBox = new ComboBox<String>();
		shapesComboBox.setVisibleRowCount(3);
//		File dataDirectory = new File(IMAGE_PATH);
//		File[] dataFiles = dataDirectory.listFiles();
//		for (File file : dataFiles) {
//			shapesComboBox.getItems().add(file.getName());
//		}
		shapesComboBox.getItems().addAll("elephant", "turtle", "pig", "frog");
		shapesComboBox.setValue("Change Shape");

		shapesComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				if(t1!=null){
				setChanged();
				newImage = t1;
				notifyObservers();
				}
			}
		});
		return shapesComboBox;

	}

	public VBox getNode() {
		return vBox;
	}

	public String getNewImage() {
		return newImage;
	}

//	public Node getTurtleSettingsController() {
//		return vBox;
//	}
	
	public int getNewAnimationSpeed(){
		return newAnimationSpeed;
	}

}
