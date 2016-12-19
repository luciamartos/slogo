package interpreter;

import model.TurtleStamp;

public interface TurtleStampUpdater {
	public void addTurtleStamp(TurtleStamp stamp);
	public int getNumberOfStamps();
	public void clearTurtleStamps();
}
