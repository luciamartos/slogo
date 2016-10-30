package interpreter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TurtleCommandInterpreter extends SubInterpreter{
	
	private SlogoUpdate model;
	private TurtleStateUpdater stateUpdater;
	
	TurtleCommandInterpreter(SlogoUpdate model, TurtleStateUpdater stateUpdater){
		this.model = model;
		this.stateUpdater = stateUpdater;
	}
	
	@Override
	boolean canHandle(String keyword) {
		return isNonInputTurtleCommand(keyword) || isUnaryTurtleCommand(keyword) || isBinaryTurtleCommand(keyword);
	}
	
	@Override
	double handle(String[] input, String keyword, double[] param, int searchStartIndex) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {
		if(isNonInputTurtleCommand(keyword)){
			Class[] args = createDoubleArgs(0);
			Method method = this.getClass().getDeclaredMethod(keyword, args);
			return (double) method.invoke(this);
		}
		else if(isUnaryTurtleCommand(keyword)){
			Class[] args = createDoubleArgs(1);
			Method method = this.getClass().getDeclaredMethod(keyword, args);
			return (double) method.invoke(this, param[0]);
		}
		else if(isBinaryTurtleCommand(keyword)){
			Class[] args = createDoubleArgs(2);
			Method method = this.getClass().getDeclaredMethod(keyword, args);
			return (double) method.invoke(this, param[0], param[1]);
		}
		else throw new IllegalArgumentException();
	}
	
	boolean isNonInputTurtleCommand(String input){
		return input.equalsIgnoreCase(rb.getString("pd")) || input.equalsIgnoreCase(rb.getString("pu")) ||
				input.equalsIgnoreCase(rb.getString("st")) || input.equalsIgnoreCase(rb.getString("ht")) ||
				input.equalsIgnoreCase(rb.getString("home")) || input.equalsIgnoreCase(rb.getString("cs"));
	}
	
	boolean isUnaryTurtleCommand(String input){
		return input.equalsIgnoreCase(rb.getString("fd")) || input.equalsIgnoreCase(rb.getString("bk")) ||
				input.equalsIgnoreCase(rb.getString("lt")) || input.equalsIgnoreCase(rb.getString("rt")) ||
				input.equalsIgnoreCase(rb.getString("seth"));
	}
	
	boolean isBinaryTurtleCommand(String input){
		return input.equalsIgnoreCase(rb.getString("towards")) || input.equalsIgnoreCase(rb.getString("setxy"));
	}
	
	double forward(double pixels){
		model.moveForward(pixels);
		return pixels;
	}
	
	double backward(double pixels){
		model.moveBackward(pixels);
		return pixels;
	}
	
	double left(double degrees){
		model.rotateCounterClockwise(degrees);
		return degrees;
	}
	
	double right(double degrees){
		model.rotateClockwise(degrees);
		return degrees;
	}
	
	double setheading(double degrees){
		return model.setAngle(degrees);
	}
	
	double settowards(double x, double y){
		return model.turnToward(x, y);
	}
	
	double setposition(double x, double y){
		double tempX = model.getXCoordinate();
		double tempY = model.getYCoordinate();
		model.moveTo(x, y);
		return Math.abs(x-tempX) + Math.abs(y-tempY);
	}
	
	double penup(){
		model.putPenUp();
		return 1;
	}
	
	double pendown(){
		model.putPenDown();
		return 0;
	}
	
	double showturtle(){
		model.show();
		return 1;
	}
	
	double hideturtle(){
		model.hide();
		return 0;
	}
	
	double home(){
		double tempX = model.getXCoordinate();
		double tempY = model.getYCoordinate();
		model.moveTo(0, 0);
		return Math.abs(tempX) + Math.abs(tempY);
	}
	
	
	double clearscreen(){
		double tempX = model.getXCoordinate();
		double tempY = model.getYCoordinate();
		model.moveTo(0, 0);
		stateUpdater.resetBoard();  //this will clear the trail of paths
		return Math.abs(tempX) + Math.abs(tempY);
	}
	
	SlogoUpdate getModel(){
		return model;
	}

}
