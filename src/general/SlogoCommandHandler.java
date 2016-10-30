package general;
import java.lang.reflect.InvocationTargetException;

import gui.TabViewController;
/**
 * @author Eric Song
 */
public interface SlogoCommandHandler {
	public void parseInput(TabViewController controller, String input);
	public void setLanguage(TabViewController controller, String language);
}
