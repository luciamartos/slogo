package gui_components;

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
	public InputPanel(double inputPanelHeight, double textFieldWidth, double runButtonWidth, Properties viewProperties, EventHandler<ActionEvent> runCommandHandler) {
		super(INPUT_PANEL_PADDING);
		setPrefHeight(inputPanelHeight);


		// set the text box
		commandInput = new TextField();
		commandInput.setPromptText("Enter your command here...");
		commandInput.setPrefWidth(textFieldWidth); // CHANGE TO COMMAND BUTTON WIDTH
		commandInput.getText();

		// set the run button
		runButton = new Button("Run");
		runButton.setPrefWidth(runButtonWidth);
		runButton.setOnAction(runCommandHandler);

		getChildren().addAll(commandInput, runButton);
	}

	public String getText() {
		return commandInput.getText();
	}
	public void clear(){
		commandInput.clear();
	}

}
