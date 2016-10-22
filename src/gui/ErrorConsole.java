package gui;

import javafx.animation.SequentialTransition;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class ErrorConsole {

	private Label errorMessage;
	private SequentialTransition blinkThenFade;
	
	public ErrorConsole(double errorLabelX, double errorLabelY, double errorFontSize) {
		errorMessage = new Label();
		errorMessage.setStyle("-fx-text-fill: red;");
		errorMessage.setLayoutX(errorLabelX);
		errorMessage.setLayoutY(errorLabelY);
		errorMessage.setFont(Font.font("Verdana", errorFontSize));
		FadingTransition transition = new FadingTransition();
		blinkThenFade = transition.setupNode(errorMessage);

	}

	// TODO: error which clears the message
	public void displayErrorMessage(String myError) {
		blinkThenFade.stop();
		errorMessage.setText(myError);
		blinkThenFade.play();
	}

	public Label getErrorMessage(){
		return errorMessage;
	}
}
