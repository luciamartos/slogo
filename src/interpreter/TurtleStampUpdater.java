package interpreter;

import model.TurtleStamp;

/**
 * This is an interface that is implemented by BoardStateController.
 * It contains the necessary methods to handle the stamp and clearStamps methods.
 * This interface will be used by the frontend to add stamps to the screen.
 * 
 * @author Ray Song
 *
 */
public interface TurtleStampUpdater {
	public void addTurtleStamp(TurtleStamp stamp);
	public int getNumberOfStamps();
	public void clearTurtleStamps();
}
