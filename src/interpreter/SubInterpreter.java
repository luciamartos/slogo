package interpreter;

import slogo_update.slogoUpdate;

public abstract class SubInterpreter {
	
	private slogoUpdate model;
	
	abstract void parseInput(String input);
	
}
