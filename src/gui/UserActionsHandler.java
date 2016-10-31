package gui;

public interface UserActionsHandler {

	public void undo();
	public void setBackgroundColor(String hex);
	public void setPenColor(String hex);
	public void setPenThickness(double thickness);
	public void setPenType(String type);
	public void setShape(String shape);
	public void toggleTurtle(int id);
	
}
