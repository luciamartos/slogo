package interpreter;

public interface TurtleStateUpdater {
	public void applyChanges(SlogoUpdate update);
	public void addUserDefinedVariable(String varName, String userInput);
}
