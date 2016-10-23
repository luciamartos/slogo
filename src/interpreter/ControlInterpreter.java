package interpreter;

public class ControlInterpreter extends SubInterpreter{
	
	/**
	 * MakeVariable = make|set
		Repeat = repeat
		DoTimes = dotimes
		For = for
		If = if
		IfElse = ifelse
		MakeUserInstruction = to
	 */
	
	double makevariable(String var, double input){
		return input;
	}
	
}
