package gui;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import model.PathLine;
import model.RGBColor;
/**
 * @author Andrew Bihl
 */
public interface BoardStateDataSource {
	public Color getBackgroundColor();
	public Iterator<PathLine> getPaths();
	public Map<String, String> getUserDefinedVariables();
	public Map<Integer, RGBColor> getColorMap();
}
