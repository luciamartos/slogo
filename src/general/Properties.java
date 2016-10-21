package general;
import java.util.ResourceBundle;

import gui.ViewController1;

public class Properties {

	protected ResourceBundle properties;

	public Properties(String path) {
		properties = ResourceBundle.getBundle(path);
	}
	
	public String getStringProperty(String propertyName){
		return properties.getString(propertyName);
	}
	
	// TODO: check for missing param and assign default value / errors
	public double getDoubleProperty(String propertyName) {
		return Double.parseDouble(properties.getString(propertyName));
	}

}
