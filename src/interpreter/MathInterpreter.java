package interpreter;

public class MathInterpreter extends SubInterpreter{
	
	private final double PI = 3.14159;
	
	@Override
	void parseInput(String[] input) {

	}
	
	double sum(double a, double b){
		return a+b;
	}
	
	double diff(double a, double b){
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
	
	double max(double max){
		return Math.random() * (max+1);
	}
	
	double sin(double degrees){
		return Math.sin(Math.toRadians(degrees));
	}
	
	double cos(double degrees){
		return Math.cos(Math.toRadians(degrees));
	}
	
	double tan(double degrees){
		return Math.tan(Math.toRadians(degrees));
	}
	
	double atan(double degrees){
		return Math.atan(Math.toRadians(degrees));
	}
	
	double log(double a){
		return Math.log(a);
	}
	
	double pow(double base, double exp){
		return Math.pow(base, exp);
	}
	
	
	double getPI(){
		return PI;
	}

	
}
