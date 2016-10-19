package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InputPanel extends HBox {

	private static final int PADDING = 15;
	private static int RUN_BUTTON_WIDTH = 50;
	private static int INPUT_PANEL_PADDING = 10;
	private Button runButton;
	private TextField commandInput;
	private String currentCommandLine;

	// TODO:check if there is a better way to get the width and the height
	// rather than feeding it
	public InputPanel(double height, double appWidth, double appHeight, EventHandler<ActionEvent> runCommandHandler) {
		super(INPUT_PANEL_PADDING);
		setPrefHeight(height);
		setLayoutY(appHeight - height - PADDING);
		setLayoutX(PADDING);

		// set the text box
		double width = appWidth - RUN_BUTTON_WIDTH - (PADDING * 2);
		commandInput = new TextField();
		commandInput.setPromptText("Enter your command here...");
		commandInput.setPrefWidth(width); // CHANGE TO COMMAND BUTTON WIDTH
		commandInput.getText();

		// set the run button
		runButton = new Button("Run");
		runButton.setPrefWidth(RUN_BUTTON_WIDTH);
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
