package interpreter;
import java.util.Map;
/**
 * @author Andrew Bihl
 */

public interface UserVariablesDataSource {
	public Map<String, String> getUserDefinedVariables();
	/**
	 * 
	 * @param key
	 * @return the String that the variable represents. This object will be null if there is no such variable stored.
	 */
	public String getUserDefinedVariable(String key);
	public void addUserDefinedVariable(String varName, String userInput);
}
