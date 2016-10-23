package interpreter;

import java.util.Map;

public interface UserVariablesDataSource {
	public Map<String, String> getUserDefinedVariables();
	public String getUserDefinedVariable(String key);
	public void addUserDefinedVariable(String varName, String userInput);
}
