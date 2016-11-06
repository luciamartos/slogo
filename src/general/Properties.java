package general;
import java.util.ResourceBundle;

/**
 * 
 * @author Eric Song
 * Used to obtain a resource file and return its properties as java data types
 */
public class Properties {

	protected ResourceBundle properties;

	public Properties(String path) {
		properties = ResourceBundle.getBundle(path);
	}
	
	/**
	 * @param propertyName
	 * @return string version of the parameter
	 */
	public String getStringProperty(String propertyName){
		return properties.getString(propertyName);
	}
	
	/**
	 * @param propertyName
	 * @return double version of the parameter
	 */
	public double getDoubleProperty(String propertyName) {
		return Double.parseDouble(properties.getString(propertyName));
	}

}
