package interpreter;

public class MathInterpreter extends Interpreter{

	
	void parseInput(String input) {
		String[] split = input.split("\\s+");
		String keyword = split[0].toLowerCase();
		
		if(keyword.equals("sum")){
			System.out.println("Sum command");
		}
	}
	
}
