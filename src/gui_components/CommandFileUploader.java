package gui_components;

import gui.FileChooserPath;
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
	private ReadCommandFileInterface myInterface;
	private String MY_PATH = "data/examples/loops/";

	public CommandFileUploader(ReadCommandFileInterface myInterface) {
		this.myInterface = myInterface;
		fileSelect = new ComboBox<>();
		fileSelect.setPrefWidth(120);
		fileSelect.setVisibleRowCount(3);
		fileSelect.setValue("Command file");
		File dataDirectory = new File(MY_PATH);
		File[] dataFiles = dataDirectory.listFiles();
		for (File file : dataFiles) {
			fileSelect.getItems().add(file.getName());
		}
		selectedFilename = null;
		fileSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
			selectedFilename = newValue;
			myCommandLine = "";
			readFile();
			System.out.println(myCommandLine);
        });
	}

	private void readFile() {
		File file = new File(MY_PATH +selectedFilename);
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				myCommandLine += " " + sc.nextLine();
			}
			myInterface.getCommandLineFromFile(myCommandLine);
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Node getCommandFileUploaderButton() {
		return fileSelect;
	}
}
