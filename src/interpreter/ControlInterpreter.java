package interpreter;

import java.lang.reflect.InvocationTargetException;

public class ControlInterpreter extends SubInterpreter{
	
	int repCount;
	
	@Override
	boolean canHandle(String keyword) {
		return isControl(keyword);
	}

	@Override
	double handle(String[] input, String keyword, double[] param, int searchStartIndex)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, InstantiationException {
		return 0;
//		if(keyword.equalsIgnoreCase(rb.getString("makevar"))){
//			currSearchIndex = searchStartIndex+2;
//			if(parsed[searchStartIndex+1].equalsIgnoreCase(rb.getString("VariableLabel"))){
//				varDataSource.addUserDefinedVariable(input[searchStartIndex+1], Double.toString(param[0]));
//				System.out.println(param[0]);
//				return param[0];
//			}
//			else{
//				String errorMessage = "Illegal Variable detected: '" + input[searchStartIndex+1] + "' is not a variable!";
//				System.out.println(errorMessage);
//				errorPresenter.presentError(errorMessage);
//				throw new IllegalArgumentException();
//			}
//		}
//		
//		else if(keyword.equalsIgnoreCase(rb.getString("repeat"))){
//			repCount = 0;
//			double res = 0;
//			String[] temp = listQueue.peek();
//			for(int i=0;i<param[0];i++){
//				res = interpretCommand(temp, 0);
//				repCount++;
//			}
//			listQueue.remove();
//			System.out.println(res);
//			currSearchIndex = searchStartIndex+2;
//			return res;
//		}
//		
//		//TODO: Implement other controls other than set and repeat
//		else return 0;
	}

	@Override
	SlogoUpdate getModel() {
		return null;
	}

	boolean isControl(String input){
	return input.equalsIgnoreCase(rb.getString("makevar")) || input.equalsIgnoreCase(rb.getString("repeat")) ||
			input.equalsIgnoreCase(rb.getString("dotimes")) || input.equalsIgnoreCase(rb.getString("for")) ||
			input.equalsIgnoreCase(rb.getString("if")) || input.equalsIgnoreCase(rb.getString("ifelse"))|| 
			input.equalsIgnoreCase(rb.getString("to")) ;
	}
}
