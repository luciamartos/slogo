package gui_components;

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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


/**
 * @author Lucia Martos, Eric Song
 *
 */
public class WorkspaceSettingsController extends Observable {
	private Properties viewProperties;
	private VBox vBox;
	private Color newBackgroundColor;
	private String newLanguage;

	public WorkspaceSettingsController(Properties viewProperties) {
		this.viewProperties = viewProperties;
		vBox = new VBox(viewProperties.getDoubleProperty("padding"));
		Label lbl = new Label();
		lbl.setText("Workspace settings");
		vBox.getChildren().add(lbl);
		vBox.getChildren().add(initializeBackgroundColorSetting());
		vBox.getChildren().add(initializeLanguageSetting());
	}

	
	private Node initializeBackgroundColorSetting() {
		ColorPicker backgroundColorPicker = new ColorPicker();
		backgroundColorPicker.setValue(Color.WHITE);
		backgroundColorPicker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				setChanged();
				newBackgroundColor = backgroundColorPicker.getValue();
				notifyObservers();
			}
		});
		//VBox tempBox = makeLabel(backgroundColorPicker, "Background color");
		return backgroundColorPicker;
	}


	private Node initializeLanguageSetting() {
		ComboBox<String> languageSelect = new ComboBox<String>();
		languageSelect.setVisibleRowCount(3);
		String rawLanguage = viewProperties.getStringProperty("languages");
		rawLanguage = rawLanguage.replaceAll("\\s","");
		List<String> languages = Arrays.asList(rawLanguage.split(","));
		languageSelect.getItems().addAll(languages);
		languageSelect.setValue("English");
		languageSelect.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				setChanged();
				newLanguage = languageSelect.getValue();
				notifyObservers();
			}
		});
		return languageSelect;
	}

	public VBox getNode() {
		return vBox;
	}

	public Color getNewBackgroundColor() {
		return newBackgroundColor;
	}

	public String getNewLanguage(){
		return newLanguage;
	}

}
