package gui_components;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import general.Properties;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PenSettingsController extends Observable {

	private static final String IMAGE_PATH = "resources/images/";

	private static final int PADDING = 4;

	private static final double MIN_THICKNESS = 1;
	private static final double MAX_THICKNESS = 10;
	private static final double INIT_THICKNESS = 2;


	private VBox vBox;

	private Color newPenColor;
	private int newPenThickness;
	private String newPenType;

	public PenSettingsController(Properties viewProperties) {
		vBox = new VBox(viewProperties.getDoubleProperty("padding"));
		Label lbl = new Label();
		lbl.setText("Pen settings");
		vBox.getChildren().add(lbl);
		vBox.getChildren().add(initializePenColorSetting());
		vBox.getChildren().add(initializePenThicknessSetting());
		vBox.getChildren().add(initializePenTypeSetting());
	}
	
	public Node getPenSettingsController(){
		return vBox;
	}
	
	
	private Node initializePenTypeSetting() {
		//TitledPane titledPane = new TitledPane();
		//titledPane.setRotate(270);
		ComboBox<String> penTypesComboBox = new ComboBox<String>();
		penTypesComboBox.setVisibleRowCount(3);
		penTypesComboBox.getItems().addAll("solid", "dashed", "dotted");
		penTypesComboBox.setValue("Pen type");

		penTypesComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				if(t1!=null){
				setChanged();
				newPenType = t1;
				notifyObservers();
				}
			}
		});
		//titledPane .setContent(penTypesComboBox);
		return penTypesComboBox;
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
		//VBox tempBox = makeLabel(penColorPicker, "Pen color");
		return penColorPicker;
	}
	
	private Node initializePenThicknessSetting(){
		Slider thicknessSlider = new Slider();
		thicknessSlider.setMin(MIN_THICKNESS);
		thicknessSlider.setMax(MAX_THICKNESS);
		thicknessSlider.setValue(INIT_THICKNESS);
		
		thicknessSlider.valueProperty().addListener(new ChangeListener<Number>(){
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				setChanged();
				newPenThickness = new_val.intValue();
				notifyObservers();
			}
		});
		//VBox tempBox = makeLabel(thicknessSlider, "Pen thickness");
		return thicknessSlider;
	}

	
	private VBox makeLabel(Node backgroundColorPicker, String text) {
		Label lbl = new Label();
		lbl.setText(text);
		VBox tempBox = new VBox(PADDING);
		tempBox.getChildren().addAll(backgroundColorPicker,lbl);
		return tempBox;
	}



	public VBox getNode() {
		return vBox;
	}


	public Color getNewPenColor() {
		return newPenColor;
	}

	public int getNewPenThickness() {
		return newPenThickness;
	}
	
	public String getNewPenType(){
		return newPenType;
	}

}
