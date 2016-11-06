package gui_components;

import general.Properties;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * @author Lucia Martos
 */
public class InputPanel extends HBox {

	private static int INPUT_PANEL_PADDING = 10;
	private Button runButton;
	private TextField commandInput;
	private String currentCommandLine;

	public InputPanel(double inputPanelHeight, double textFieldWidth, double runButtonWidth, Properties viewProperties, EventHandler<ActionEvent> runCommandHandler) {
		super(INPUT_PANEL_PADDING);
		setPrefHeight(inputPanelHeight);


		// set the text box
		commandInput = new TextField();
		commandInput.setPromptText("Enter your command here...");
		commandInput.setPrefWidth(textFieldWidth);
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
	/**
	 * clears the input box
	 */
	public void clear(){
		commandInput.clear();
	}

}
