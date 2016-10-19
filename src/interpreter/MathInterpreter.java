package interpreter;

import java.util.concurrent.ThreadLocalRandom;

public class MathInterpreter extends SubInterpreter{
	
	private final double PI = 3.14159;
	
	void parseInput(String[] input) {
//		String keyword = split[0].toLowerCase();//<---Put in MainInterpreter
		//parse command, get number of Parameters. 
		int numberOfParameters;
		for (int i = 1; i <= numberOfParameters; i++){ //Handle case of not ENOUGH parameters
			String s = input[i];
			if (//string can be converted to double){
				double param = Double.parseDouble(s);
			double param = mainController.parseInput(s);
					
		}
		
		if(keyword.equals("sum")){
			
			System.out.println("Sum command");
		}
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
	
	double max(double max){
		return Math.random() * (max+1);
	}
	
	double minus(double a){
		return -a;
	}
	
	double getPI(){
		return PI;
	}
	
}
