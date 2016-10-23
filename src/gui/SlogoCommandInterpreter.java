package gui;
import java.lang.reflect.InvocationTargetException;
/**
 * @author Andrew Bihl
 */
public interface SlogoCommandInterpreter {
	public void parseInput(String input) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
