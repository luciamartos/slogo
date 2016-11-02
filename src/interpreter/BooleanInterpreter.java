package interpreter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BooleanInterpreter extends SubInterpreter{
	
	@Override
	boolean canHandle(String keyword) {
		return isUnaryBooleanExpression(keyword) || isBinaryBooleanExpression(keyword);
	}

	@Override
	double handle(String[] input, String keyword, double[] param, int searchStartIndex) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(isUnaryBooleanExpression(keyword)){
			Class<?>[] args = createDoubleArgs(1);
			Method method = this.getClass().getDeclaredMethod(keyword, args);
			return (double) method.invoke(this, param[0]);
		}
		else if(isBinaryBooleanExpression(keyword)){
			Class<?>[] args = createDoubleArgs(2);
			Method method = this.getClass().getDeclaredMethod(keyword, args);
			return (double) method.invoke(this, param[0],param[1]);
		}
		else throw new IllegalArgumentException();
	}
	
	@Override
	SlogoUpdate getModel() {
		return null;
	}
	
	@Override
	boolean needList() {
		return false;
	}

	
	boolean isUnaryBooleanExpression(String input){
		return input.equalsIgnoreCase(rb.getString("not"));
	}
	
	boolean isBinaryBooleanExpression(String input){
		return input.equalsIgnoreCase(rb.getString("less")) || input.equalsIgnoreCase(rb.getString("greater")) ||
				input.equalsIgnoreCase(rb.getString("equal")) || input.equalsIgnoreCase(rb.getString("notequal")) ||
				input.equalsIgnoreCase(rb.getString("and")) || input.equalsIgnoreCase(rb.getString("or"));
	}
	
	double lessthan(double a, double b){
		return (a<b) ? 1 : 0;
	}
	
	double greaterthan(double a, double b){
		return (a>b) ? 1 : 0;
	}
	
	double equal(double a, double b){
		return (a==b) ? 1 : 0;
	}
	
	double notequal(double a, double b){
		return (a!=b) ? 1 : 0;
	}
	
	double and(double a, double b){
		return (a==0&&b==0) ? 1 : 0;
	}
	
	double or(double a, double b){
		return (a==0||b==0) ? 1 : 0;
	}
	
	double not(double a){
		return (a==0) ? 1 : 0;
	}
		
}
