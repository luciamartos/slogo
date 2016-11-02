package interpreter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DisplayInterpreter extends SubInterpreter{
	
	private SlogoUpdate model;
	private BoardStateUpdater boardStateUpdater;
	
	DisplayInterpreter(SlogoUpdate model, BoardStateUpdater boardStateUpdater){
		this.model = model;
		this.boardStateUpdater = boardStateUpdater;
	}

	@Override
	boolean canHandle(String keyword) {
		return isNonInputDisplayCommand(keyword) || isUnaryDisplayCommand(keyword) || 
				isMultipleInputDisplayCommand(keyword);
	}

	@Override
	double handle(String[] input, String keyword, double[] param, int searchStartIndex)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, InstantiationException {
		if(isNonInputDisplayCommand(keyword)){
			Class<?>[] args = createDoubleArgs(0);
			Method method = this.getClass().getDeclaredMethod(keyword, args);
			return (double) method.invoke(this);
		}
		else if(isUnaryDisplayCommand(keyword)){
			Class<?>[] args = createDoubleArgs(1);
			Method method = this.getClass().getDeclaredMethod(keyword, args);
			return (double) method.invoke(this, param[0]);
		}
		else if(isMultipleInputDisplayCommand(keyword)){
			Class<?>[] args = createDoubleArgs(3);
			Method method = this.getClass().getDeclaredMethod(keyword, args);
			return (double) method.invoke(this, param[0], param[1], param[2]);
		}
		else throw new IllegalArgumentException();
	}

	@Override
	SlogoUpdate getModel() {
		return model;
	}
	
	@Override
	boolean needList() {
		return false;
	}
	
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
	
	double getpencolor(){
		return model.getPenColor();
	}
	
	double getshape(){
		return model.getShape();
	}
	
	boolean isNonInputDisplayCommand(String input){
		return input.equalsIgnoreCase(rb.getString("pc")) ||input.equalsIgnoreCase(rb.getString("sh")) ;
	}
	
	boolean isUnaryDisplayCommand(String input){
		return input.equalsIgnoreCase(rb.getString("setbg")) ||input.equalsIgnoreCase(rb.getString("setpc")) || 
				input.equalsIgnoreCase(rb.getString("setps")) || input.equalsIgnoreCase(rb.getString("setsh"));
	}
	
	boolean isMultipleInputDisplayCommand(String input){
		return input.equalsIgnoreCase(rb.getString("setpalette"));
	}

}
