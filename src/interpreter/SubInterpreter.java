package interpreter;

import java.util.ResourceBundle;

public abstract class SubInterpreter {
	
	private final String DEFAULT_RESOURCE_PACKAGE = "resources/properties/";
	private final String PROPERTIES_TITLE = "Interpreter";
	ResourceBundle rb = rb = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+PROPERTIES_TITLE);
	
	boolean isNonInputTurtleCommand(String input){
		return input.equalsIgnoreCase(rb.getString("pd")) || input.equalsIgnoreCase(rb.getString("pu")) ||
				input.equalsIgnoreCase(rb.getString("st")) || input.equalsIgnoreCase(rb.getString("ht")) ||
				input.equalsIgnoreCase(rb.getString("home")) || input.equalsIgnoreCase(rb.getString("cs"));
	}
	
	boolean isUnaryTurtleCommand(String input){
		return input.equalsIgnoreCase(rb.getString("fd")) || input.equalsIgnoreCase(rb.getString("bk")) ||
				input.equalsIgnoreCase(rb.getString("lt")) || input.equalsIgnoreCase(rb.getString("rt")) ||
				input.equalsIgnoreCase(rb.getString("seth"));
	}
	
	boolean isBinaryTurtleCommand(String input){
		return input.equalsIgnoreCase(rb.getString("towards")) || input.equalsIgnoreCase(rb.getString("setxy"));
	}
	
	boolean isTurtleQuery(String input){
		return input.equalsIgnoreCase(rb.getString("xcor")) || input.equalsIgnoreCase(rb.getString("ycor")) ||
				input.equalsIgnoreCase(rb.getString("heading")) || input.equalsIgnoreCase(rb.getString("ispendown")) ||
				input.equalsIgnoreCase(rb.getString("isshowing"));
	}
	
	boolean isNonInputMathExpression(String input){
		return input.equalsIgnoreCase(rb.getString("pi"));
	}
	
	boolean isUnaryMathExpression(String input){
		return input.equalsIgnoreCase(rb.getString("sin")) || input.equalsIgnoreCase(rb.getString("cos")) ||
				input.equalsIgnoreCase(rb.getString("tan")) || input.equalsIgnoreCase(rb.getString("atan")) ||
				input.equalsIgnoreCase(rb.getString("log")) || input.equalsIgnoreCase(rb.getString("rand"));
	}
	
	boolean isBinaryMathExpression(String input){
		return input.equalsIgnoreCase(rb.getString("sum")) || input.equalsIgnoreCase(rb.getString("diff")) ||
				input.equalsIgnoreCase(rb.getString("prod")) || input.equalsIgnoreCase(rb.getString("quo")) ||
				input.equalsIgnoreCase(rb.getString("minus")) || input.equalsIgnoreCase(rb.getString("remain")) || 
				input.equalsIgnoreCase(rb.getString("pwr")) ;
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
	

}
