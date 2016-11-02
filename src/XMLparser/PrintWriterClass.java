package XMLparser;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javafx.collections.ObservableList;

/*Adapted from  https://examples.javacodegeeks.com/core-java/io/printwriter/java-printwriter-example/
 * 
 * 
 */

public class PrintWriterClass {
	private static final String MY_PATH = "data/examples/";


	public PrintWriterClass(String fileName, ObservableList<String> commands) {
		PrintWriter pwFile = null;
		try {
			pwFile = new PrintWriter(MY_PATH + fileName);
			for (String myElem : commands) {
				pwFile.write(myElem+"\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} finally {
			// always close the output stream
			if (pwFile != null) {
				pwFile.close();
			}
		}

	}

}