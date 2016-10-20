package interpreter;

import slogo_update.slogoUpdate;

public class MainInterpreter {
	
	private slogoUpdate model;
	
	public MainInterpreter(){
		model = new slogoUpdate();
	}
	
	public void parseInput(String input){
		String[] split = input.split("\\s+");
		interpretCommand(split);
	}
	
	public void interpretCommand(String[] input){
		//use reflection to decide which one to call
		
		String keyword = input[0].toLowerCase();
		
		//parse command, get number of Parameters. 
		int numberOfParameters = input.length-1;
		for (int i = 1; i <= numberOfParameters; i++){  //Handle case of not ENOUGH parameters
			String s = input[i];
			if(isDouble(s)){
				double param = Double.parseDouble(s);
			}
			else{
				String[] substring = new String[0];
				interpretCommand(substring);
			}	
					
		}
		
		if(keyword.equals("sum")){
			System.out.println("Sum command");
		}
	}
	
	private boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public slogoUpdate getModel(){
		return model;
	}
}
