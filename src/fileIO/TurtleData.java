package fileIO;

public class TurtleData {
	private double x;
	private double y;
	private boolean drawing;
	private int penSize;
	private int penType;
	private int shapeIndex;
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public boolean isDrawing(){
		return drawing;
	}
	
	public int getPenSize(){
		return penSize;
	}
	
	public int getPenType(){
		return penType;
	}
	
	public int getShapeIndex(){
		return shapeIndex;
	}
	
	public void setShapeIndex(int imageIndex) {
		this.shapeIndex = imageIndex;
	}
	
	public void setDrawing(boolean b) {
		this.drawing = b;
	}
	public void setPenSize(int penThickness) {
		this.penSize = penThickness;
	}
	public void setPenType(int lineStyle) {
		this.penType = lineStyle;
		
	}
	public void setX(double xLoc) {
		this.x = xLoc;
	}
	public void setY(double yLoc) {
		this.y = yLoc;
	}
}
