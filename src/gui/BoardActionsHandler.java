package gui;

public interface BoardActionsHandler {
	public void undo();
	public void setBackgroundColor(int colorIndex);
	public void loadBoardFromFile(String xmlFile);
}
