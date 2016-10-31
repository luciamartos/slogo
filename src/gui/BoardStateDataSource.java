package gui;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import model.PathLine;
/**
 * @author Andrew Bihl
 */
public interface BoardStateDataSource {
	public Color getBackgroundColor();
	public List<PathLine> getLineCoordinates();
	public Map<String, String> getUserDefinedVariables();
}
