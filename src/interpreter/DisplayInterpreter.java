package interpreter;

import java.lang.reflect.InvocationTargetException;

public class DisplayInterpreter extends SubInterpreter{
	
	private SlogoUpdate model;
	private BoardStateUpdater boardStateUpdater;
	
	DisplayInterpreter(SlogoUpdate model, BoardStateUpdater boardStateUpdater){
		this.model = model;
		this.boardStateUpdater = boardStateUpdater;
	}

	@Override
	boolean canHandle(String keyword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	double handle(String[] input, String keyword, double[] param, int searchStartIndex)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, InstantiationException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	SlogoUpdate getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	boolean needList() {
		return false;
	}
	
	//TODO: Fill out meaningful code for the methods below, once the interface is complete
	double setbackground(double index){
		boardStateUpdater.setBackgroundColor((int)index);
		return index;
	}
	
	double setpencolor(double index){
		model.setPenColor((int)index);
		return index;
	}
	
	double setpensize(double pixels){
		model.setPenSize((int)pixels);
		return pixels;
	}
	
	double setshape(double index){
		model.setShape((int)index);
		return index;
	}
	
	double setpalette(double index, double red, double green, double blue){
		boardStateUpdater.addColorToPalette((int)index, (int)red, (int)green, (int)blue);
		return index;
	}
	
	double pencolor(){
		return model.getPenColor();
	}
	
	double shape(){
		return model.getShape();
	}

}
