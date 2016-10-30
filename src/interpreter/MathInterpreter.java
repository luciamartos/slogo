package interpreter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MathInterpreter extends SubInterpreter{
	
	private final double PI = 3.14159;
	
	@Override
	boolean canHandle(String keyword) {
		return isNonInputMathExpression(keyword) || isUnaryMathExpression(keyword) || isBinaryMathExpression(keyword);
	}

	@Override
	double handle(String[] input, String keyword, double[] param, int searchStartIndex) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(isNonInputMathExpression(keyword)){
			Class[] args = createDoubleArgs(0);
			Method method = this.getClass().getDeclaredMethod(keyword, args);
			return (double) method.invoke(this);
		}
		else if(isUnaryMathExpression(keyword)){
			Class[] args = createDoubleArgs(1);
			Method method = this.getClass().getDeclaredMethod(keyword, args);
			return (double) method.invoke(this, param[0]);
		}
		else if(isBinaryMathExpression(keyword)){
			Class[] args = createDoubleArgs(2);
			Method method = this.getClass().getDeclaredMethod(keyword, args);
			return (double) method.invoke(this, param[0],param[1]);
		}
		else throw new IllegalArgumentException();
	}
	
	@Override
	SlogoUpdate getModel() {
		return null;
	}
	
	boolean isNonInputMathExpression(String input){
		return input.equalsIgnoreCase(rb.getString("pi"));
	}
	
	boolean isUnaryMathExpression(String input){
		return input.equalsIgnoreCase(rb.getString("minus")) ||input.equalsIgnoreCase(rb.getString("sin")) || 
				input.equalsIgnoreCase(rb.getString("cos")) || input.equalsIgnoreCase(rb.getString("tan")) || 
				input.equalsIgnoreCase(rb.getString("atan")) || input.equalsIgnoreCase(rb.getString("log")) || 
				input.equalsIgnoreCase(rb.getString("rand"));
	}
	
	boolean isBinaryMathExpression(String input){
		return input.equalsIgnoreCase(rb.getString("sum")) || input.equalsIgnoreCase(rb.getString("diff")) ||
				input.equalsIgnoreCase(rb.getString("prod")) || input.equalsIgnoreCase(rb.getString("quo")) ||
				input.equalsIgnoreCase(rb.getString("remain")) || input.equalsIgnoreCase(rb.getString("pwr")) ;
	}
	
	double sum(double a, double b){
		return a+b;
	}
	
	double difference(double a, double b){
		return a-b;
	}
	
	double product(double a, double b){
		return a*b;
	}
	
	double quotient(double a, double b){
		return a/b;
	}
	
	double remainder(double a, double b){
		return a%b;
	}
	
	double minus(double a){
		return -a;
	}
	
	double random(double max){
		return (Math.random() * ((max) + 1));
	}
	
	double sine(double degrees){
		return Math.sin(Math.toRadians(degrees));
	}
	
	double cosine(double degrees){
		return Math.cos(Math.toRadians(degrees));
	}
	
	double tangent(double degrees){
		return Math.tan(Math.toRadians(degrees));
	}
	
	double arctangent(double degrees){
		return Math.atan(Math.toRadians(degrees));
	}
	
	double naturallog(double a){
		return Math.log(a);
	}
	
	double power(double base, double exp){
		return Math.pow(base, exp);
	}
	
	double pi(){
		return PI;
	}
	
}
