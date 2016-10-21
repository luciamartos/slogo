package interpreter;

public class BooleanInterpreter extends SubInterpreter{

	@Override
	void parseInput(String[] input) {
		// TODO Auto-generated method stub
		
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
