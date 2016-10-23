package gui;

import javafx.scene.image.Image;

//to fix path issues
public class ViewImageChooser {

	public static Image selectImage(String path, double x, double y) {
		return new Image(path, x, y, true, true);
	}

}
