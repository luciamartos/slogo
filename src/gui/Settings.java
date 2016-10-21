package gui;

import java.io.File;

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
import javafx.stage.FileChooser;

public class Settings {

	private Properties viewProperties;
	private VBox vBox;
	private Button imageButton;

	public Settings(Properties viewProperties) {
		this.viewProperties = viewProperties;
		initializeVBox();
		initializePenColorSetting();
		initializeBackgroundColorSetting();
		initializeTurtleImageSetting();
	}

	private void initializeVBox() {
		vBox = new VBox(viewProperties.getDoubleProperty("padding"));
		vBox.setLayoutX(viewProperties.getDoubleProperty("settings_x"));
		vBox.setLayoutY(viewProperties.getDoubleProperty("settings_y"));
	}

	private void initializePenColorSetting() {
		HBox hBox = new HBox(viewProperties.getDoubleProperty("padding"));

		// set the text box
		TextField colorInput = createTextBox("Change Pen Color",200);

		// set the run button
		Button colorButton = createButton("Set",viewProperties.getDoubleProperty("run_button_width"));
		//TODO: make the prompt text equal to current setting?
		// colorButton.setOnAction(event -> {
		// currentCommandLine = colorInput.getText();
		// runCommandHandler.handle(event);
		// colorInput.clear();
		// });
		hBox.getChildren().addAll(colorInput, colorButton);
		vBox.getChildren().add(hBox);
	}
	
	private void initializeBackgroundColorSetting() {
		HBox hBox = new HBox(viewProperties.getDoubleProperty("padding"));

		// set the text box
		TextField colorInput = createTextBox("Change Background Color",200);

		// set the run button
		Button colorButton = createButton("Set",viewProperties.getDoubleProperty("run_button_width"));
		//TODO: make the prompt text equal to current setting?
		// colorButton.setOnAction(event -> {
		// currentCommandLine = colorInput.getText();
		// runCommandHandler.handle(event);
		// colorInput.clear();
		// });
		hBox.getChildren().addAll(colorInput, colorButton);
		vBox.getChildren().add(hBox);
	}
	
	private void initializeTurtleImageSetting() {
		imageButton = createButton("Change Turtle Image", 200);
		vBox.getChildren().add(imageButton);

	}
	
	public void turtleImageSetAction(EventHandler<ActionEvent> event){
		imageButton.setOnAction(event);
	}
	
	private TextField createTextBox(String text, double width){
		TextField textField = new TextField();
		textField.setPromptText(text);
		textField.setPrefWidth(width); // CHANGE TO COMMAND BUTTON WIDTH
		return textField;
	}
	
	private Button createButton(String text, double width){
		Button button = new Button(text);
		button.setPrefWidth(width);
		return button;
	}

	public VBox getVBox() {
		return vBox;
	}

}
