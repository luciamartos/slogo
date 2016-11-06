package gui;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Modified from
 * http://stackoverflow.com/questions/23190049/how-to-make-a-text-content-disappear-after-some-time-in-javafx
 * 
 * @author Eric Song
 */
public class FadingTransition {

	public FadingTransition() {}

	/**
	 * @param node
	 * @return an object that can that can blink and then fadenodes
	 */
	public SequentialTransition setupNode(Node node) {
		Timeline blinker = createBlinker(node);
		FadeTransition fader = createFader(node);

		return new SequentialTransition(node, blinker, fader);
	}

	private Timeline createBlinker(Node node) {
		Timeline blink = new Timeline(
				new KeyFrame(Duration.seconds(0), new KeyValue(node.opacityProperty(), 1, Interpolator.DISCRETE)),
				new KeyFrame(Duration.seconds(0.5), new KeyValue(node.opacityProperty(), 0, Interpolator.DISCRETE)),
				new KeyFrame(Duration.seconds(1), new KeyValue(node.opacityProperty(), 1, Interpolator.DISCRETE)));
		blink.setCycleCount(3);

		return blink;
	}

	private FadeTransition createFader(Node node) {
		FadeTransition fade = new FadeTransition(Duration.seconds(0.6), node);
		fade.setFromValue(1);
		fade.setToValue(0);

		return fade;
	}

}
