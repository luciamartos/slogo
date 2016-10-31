package gui_components;

public interface ReadCommandFileInterface {
	public void getCommandLineFromFile(String myCommand);

	public void getLineTypeFromFile(String lineStyle);

	public void getPenDownFromFile(String penDown);

	public void getPenColorFromFile(String penColor);

	public void getTurtleCountFromFile(String turtleCount);

	public void getLanguageFromFile(String language);

	public void getBackgroundColorFromFile(String backgroundColor);

	public void getImageURLFromFile(String imageURL);

	public void getPenThicknessFromFile(String penThickness);
}
