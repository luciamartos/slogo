package interpreter;

import java.util.Map;

public interface UserVariablesDataSource {
	public Map<String, String> getUserDefinedVariables();
	public void addUserDefinedVariable(String varName, String userInput);
}
