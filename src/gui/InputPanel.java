package gui;

import general.Properties;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InputPanel extends HBox {

	private static int INPUT_PANEL_PADDING = 10;
	private Button runButton;
	private TextField commandInput;
	private String currentCommandLine;

	// TODO:check if there is a better way to get the width and the height
	// rather than feeding it
	public InputPanel(Properties viewProperties, EventHandler<ActionEvent> runCommandHandler) {
		super(INPUT_PANEL_PADDING);
		setPrefHeight(viewProperties.getDoubleProperty("input_panel_height"));
		setLayoutY(viewProperties.getDoubleProperty("input_panel_y"));
		setLayoutX(viewProperties.getDoubleProperty("input_panel_x"));

		// set the text box
		commandInput = new TextField();
		commandInput.setPromptText("Enter your command here...");
		commandInput.setPrefWidth(viewProperties.getDoubleProperty("text_field_width")); // CHANGE TO COMMAND BUTTON WIDTH
		commandInput.getText();

		// set the run button
		runButton = new Button("Run");
		runButton.setPrefWidth(viewProperties.getDoubleProperty("run_button_width"));
		runButton.setOnAction(event -> {
			currentCommandLine = commandInput.getText();
			runCommandHandler.handle(event);
			commandInput.clear();
		});

		getChildren().addAll(commandInput, runButton);
	}

	// Is this good design..?
	public String getCurrentCommandLine() {
		return currentCommandLine;
	}

}
