package interpreter;

import java.util.List;

/**
 * @author Andrew Bihl
 */
public interface TurtleStateUpdater {
	public void applyChanges(SlogoUpdate update);
	public void setActiveTurtles(List<Integer> activeTurtles);
	//Creates stamps of currently active turtles, return shape index of most recently created and active turtle
	public int createTurtleStamps();
	public boolean clearTurtleStamps();
}
