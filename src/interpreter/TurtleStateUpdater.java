package interpreter;

import java.util.List;

/**
 * @author Andrew Bihl
 */
public interface TurtleStateUpdater {
	public void applyChanges(SlogoUpdate update);
	public void setActiveTurtles(List<Integer> activeTurtles);
}
