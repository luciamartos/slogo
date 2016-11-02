package model;

import javafx.scene.paint.Color;

public class RGBColor {
	private int red;
	private int green;
	private int blue;
	
	public RGBColor(int red, int green, int blue){
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public RGBColor(double red, double green, double blue){
		this.red = (int) red;
		this.green = (int) green;
		this.blue = (int) blue;
	}
	
	public RGBColor(Color color){
		red = (int) color.getRed();
		blue = (int) color.getBlue();
		green = (int) color.getGreen();
	}
	public int getBlue() {
		return blue;
	}

	public int getGreen() {
		return green;
	}

	public int getRed() {
		return red;
	}
}
