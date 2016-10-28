package gui;

import java.io.File;

import javafx.scene.image.Image;

//to fix path issues
public class FileChooserPath {

	public static Image selectImage(String path, double x, double y) {
		return new Image(path, x, y, true, true);
	}
	
	public static File selectFile(String path){
		return new File(path);
	}

}
