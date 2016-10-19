package interpreter;

import commandModel.CommandModel;

public abstract class Interpreter {
	
	private CommandModel model;
	
	abstract void parseInput(String input);
	
	public CommandModel getModel(){
		return model;
	}
}
