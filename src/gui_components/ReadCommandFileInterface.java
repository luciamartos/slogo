package gui_components;

public interface ReadCommandFileInterface {
	public void getCommandLineFromFile(String myCommand);

	public void getLineTypeFromFile(int lineStyle);

	public void getPenDownFromFile(String penDown);

	public void getPenColorFromFile(int penColor);

	public void getTurtleCountFromFile(String turtleCount);

	public void getLanguageFromFile(String language);

	public void getBackgroundColorFromFile(int backgroundColor);

	public void getImageURLFromFile(String imageURL);

	public void getPenThicknessFromFile(int penThickness);
}
