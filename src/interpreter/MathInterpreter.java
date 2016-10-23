package interpreter;

public class MathInterpreter extends SubInterpreter{
	
	private final double PI = 3.14159;
	
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
		return Math.sin(Math.toRadians(convertAngle(degrees)));
	}
	
	double cosine(double degrees){
		return Math.cos(Math.toRadians(convertAngle(degrees)));
	}
	
	double tangent(double degrees){
		return Math.tan(Math.toRadians(convertAngle(degrees)));
	}
	
	double arctangent(double degrees){
		return Math.atan(Math.toRadians(convertAngle(degrees)));
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
