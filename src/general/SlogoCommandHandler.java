package general;
import java.lang.reflect.InvocationTargetException;

import gui.TabViewController;
/**
 * @author Eric Song
 */
public interface SlogoCommandHandler {
	/**
	 * @param controller
	 * @param input
	 * parses the input command from the user
	 */
	public void parseInput(TabViewController controller, String input);
	
	/**
	 * @param controller
	 * @param language
	 * sets the language in which commands can be interpreted
	 */
	public void setLanguage(TabViewController controller, String language);
}
