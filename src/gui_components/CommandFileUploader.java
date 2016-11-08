package gui_components;

import gui.FileChooserPath;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
/**
 * @author Lucia Martos
 */
public class CommandFileUploader extends FileChooser {

	private String myCommandLine;
	//private String MY_PATH = "data/examples/simple/";	
	public CommandFileUploader(ReadCommandFile myInterface, String initVal, String filePath) {
		super(myInterface, initVal, filePath);
	}
	
	protected void executeActionsFromFile() {
		myCommandLine = "";
		readFile();
	}

	protected void readFile() {
		File file = new File(myPath +selectedFilename);
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
}
