package gui;

import java.io.File;

import javafx.scene.image.Image;

/**
 * @author Eric Song
 * to prevent path issues with chosing files and images
 */
public class FileChooserPath {

	public static Image selectImage(String path, double x, double y) {
		return new Image(path, x, y, true, true);
	}
	
	public static File selectFile(String path){
		return new File(path);
	}

}
