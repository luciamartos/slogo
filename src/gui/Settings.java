package gui;

import general.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public class Settings {

	private VBox vBox;

	public Settings(Properties viewProperties) {
		setupVBox();
		setupColorList();
		
	}

	private void setupVBox() {
		vBox = new VBox(10);
		vBox.setLayoutX(615);
		vBox.setLayoutY(15);
	}
	
	private void setupColorList(){
		ObservableList<String> options = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3");
		ComboBox<String> comboBox = new ComboBox<String>(options);
		vBox.getChildren().add(comboBox);
	}

	public VBox getVBox() {
		return vBox;
	}

}
