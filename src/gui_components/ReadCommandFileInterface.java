package gui_components;

public interface ReadCommandFileInterface {
	public void getCommandLineFromFile(String myCommand);

	public void getLineTypeFromFile(String string);

	public void getPenDownFromFile(String penDown);

	public void getPenColorFromFile(int string);

	public void getTurtleCountFromFile(String turtleCount);

	public void getLanguageFromFile(String language);

	public void getBackgroundColorFromFile(int string);

	public void getImageURLFromFile(String imageURL);

	public void getPenThicknessFromFile(int string);
}
