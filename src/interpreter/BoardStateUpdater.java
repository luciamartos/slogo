package interpreter;

public interface BoardStateUpdater {
	public void setBackgroundColor(int colorIndex);
	public void addColorToPalette(int index, int red, int green, int blue);
	public void resetBoard();
}
