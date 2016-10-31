package interpreter;

public interface BoardStateUpdater {
	public void setBackgroundColorIndex(int i);
	public void addColorToPalette(int index, int red, int green, int blue);
	public void resetBoard();
}
