package interpreter;

import slogo_update.slogoUpdate;

public class MainInterpreter {
	
	private slogoUpdate model;
	
	public MainInterpreter(){
		model = new slogoUpdate();
	}
	
	public void interpretCommand(String input){
		String[] split = input.split("\\s+");
		parseInput(split);
	}
	
	public void parseInput(String[] input){
		
	}
	
	public slogoUpdate getModel(){
		return model;
	}
}
