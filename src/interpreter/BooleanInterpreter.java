package interpreter;

public class BooleanInterpreter extends SubInterpreter{
	
	@Override
	boolean canHandle(String keyword) {
		return isUnaryBooleanExpression(keyword) || isBinaryBooleanExpression(keyword);
	}

	@Override
	double handle(String[] input, String keyword, double[] param, int searchStartIndex) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	boolean isUnaryBooleanExpression(String input){
		return input.equalsIgnoreCase(rb.getString("not"));
	}
	
	boolean isBinaryBooleanExpression(String input){
		return input.equalsIgnoreCase(rb.getString("less")) || input.equalsIgnoreCase(rb.getString("greater")) ||
				input.equalsIgnoreCase(rb.getString("equal")) || input.equalsIgnoreCase(rb.getString("notequal")) ||
				input.equalsIgnoreCase(rb.getString("and")) || input.equalsIgnoreCase(rb.getString("or"));
	}
	
	boolean isControl(String input){
		return input.equalsIgnoreCase(rb.getString("makevar")) || input.equalsIgnoreCase(rb.getString("repeat")) ||
				input.equalsIgnoreCase(rb.getString("dotimes")) || input.equalsIgnoreCase(rb.getString("for")) ||
				input.equalsIgnoreCase(rb.getString("if")) || input.equalsIgnoreCase(rb.getString("ifelse"))|| 
				input.equalsIgnoreCase(rb.getString("to")) ;
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
