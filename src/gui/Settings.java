package gui;

import general.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Settings {

	private Properties viewProperties;
	private VBox vBox;

	public Settings(Properties viewProperties) {
		this.viewProperties = viewProperties;
		intializeVBox();
		intializePenColorSetting();
		intializeBackgroundColorSetting();

	}

	private void intializeVBox() {
		vBox = new VBox(viewProperties.getDoubleProperty("padding"));
		vBox.setLayoutX(viewProperties.getDoubleProperty("settings_x"));
		vBox.setLayoutY(viewProperties.getDoubleProperty("settings_y"));
	}

	private void intializePenColorSetting() {
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
	
	private void intializeBackgroundColorSetting() {
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
