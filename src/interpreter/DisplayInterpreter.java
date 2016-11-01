package interpreter;

import java.lang.reflect.InvocationTargetException;

public class DisplayInterpreter extends SubInterpreter{

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
		return index;
	}
	
	double setpencolor(double index){
		return index;
	}
	
	double setpensize(double pixels){
		return pixels;
	}
	
	double setshape(double index){
		return index;
	}
	
	double setpalette(double index, double rgb){
		return index;
	}
	
	double pencolor(){
		return 0;
	}
	
	double shape(){
		return 0;
	}

}
