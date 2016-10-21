package interpreter;

public class BooleanInterpreter extends SubInterpreter{

	@Override
	void parseInput(String[] input) {
		// TODO Auto-generated method stub
		
	}
	
	double lessThan(double a, double b){
		return (a<b) ? 1 : 0;
	}
	
	double greaterThan(double a, double b){
		return (a>b) ? 1 : 0;
	}
	
	double equal(double a, double b){
		return (a==b) ? 1 : 0;
	}
	
	double notEqual(double a, double b){
		return (a!=b) ? 1 : 0;
	}
	
	double and(boolean a, boolean b){
		return (a&&b) ? 1 : 0;
	}
	
	double or(boolean a, boolean b){
		return (a||b) ? 1 : 0;
	}
	
	double not(boolean a){
		return (!a) ? 1 : 0;
	}
	
		
}
