package gui_components;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;

public class CommandFileUploader {

	private ComboBox<String> fileSelect;
	private String selectedFilename;
	private String myCommandLine;

	public CommandFileUploader() {
		fileSelect = new ComboBox<>();
		fileSelect.setPrefWidth(120);
		fileSelect.setVisibleRowCount(3);
		fileSelect.setValue("Command file");
		File dataDirectory = new File("data/examples/simple/");
		File[] dataFiles = dataDirectory.listFiles();
		for (File file : dataFiles) {
			fileSelect.getItems().add(file.getName());
		}
		selectedFilename = null;
		fileSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
			selectedFilename = newValue;
			myCommandLine = "";
			readFile();
        });
	}

	private void readFile() {
		File file = new File(getClass().getResource("selectedFilename").getPath());
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				myCommandLine += " " + sc.nextLine();
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Node getCommandFileUploaderButton() {
		return fileSelect;
	}

	public String getCommandLine() {
		return myCommandLine;
	}

}
