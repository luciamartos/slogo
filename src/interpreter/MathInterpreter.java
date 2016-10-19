package interpreter;

import java.util.concurrent.ThreadLocalRandom;

public class MathInterpreter extends Interpreter{
	
	private final double PI = 3.14159;
	
	void parseInput(String input) {
		String[] split = input.split("\\s+");
		String keyword = split[0].toLowerCase();
		
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
