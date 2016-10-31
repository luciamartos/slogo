package gui_components;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;

public abstract class FileChooser {

	protected ComboBox<String> fileSelect;
	protected String selectedFilename;
	protected ReadCommandFileInterface myInterface;
	protected String myPath;

	public FileChooser(ReadCommandFileInterface myInterface, String initVal, String filePath) {
		this.myInterface = myInterface;
		myPath = filePath;
		createComboBox(initVal);
		}
	
		protected void createComboBox(String initVal){
			fileSelect = new ComboBox<>();
			fileSelect.setPrefWidth(120);
			fileSelect.setVisibleRowCount(3);
			fileSelect.setValue(initVal);
			File dataDirectory = new File(myPath);
			File[] dataFiles = dataDirectory.listFiles();
			for (File file : dataFiles) {
				fileSelect.getItems().add(file.getName());
			}
			selectedFilename = null;
			fileSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
				selectedFilename = newValue;

				executeActionsFromFile();
	        });
		}
		protected abstract void executeActionsFromFile();
		
		public Node getFileUploaderButton() {
			return fileSelect;
		}

	}


