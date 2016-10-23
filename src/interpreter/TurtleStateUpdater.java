package interpreter;
/**
 * @author Andrew Bihl
 */
public interface TurtleStateUpdater {
	public void applyChanges(SlogoUpdate update);
	public void resetBoard();
}
