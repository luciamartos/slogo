//package interpreter;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Arrays;
//
//import regularExpression.ProgramParser;
//
//public class NewNonControlInterpreter extends SubInterpreter{
//	
//	private double interpretTurtleCommand(String[] input, String keyword, int searchStartIndex) throws ClassNotFoundException, 
//	InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, 
//	IllegalArgumentException, InvocationTargetException{
//		double[] param;
//		Class interpreterClass = Class.forName(rb.getString("TurtleCommandInterpreterLabel"));
//		Object obj = interpreterClass.getDeclaredConstructor(SlogoUpdate.class, TurtleStateUpdater.class)
//				.newInstance(model, stateUpdater);
//		Class[] args;
//		TurtleCommandInterpreter interpreter = new TurtleCommandInterpreter(model, stateUpdater);
//		
//		if(interpreter.isNonInputTurtleCommand(keyword)){
//			return handleNonInputKeywordWithModel(keyword, searchStartIndex, interpreterClass, obj, interpreter);
//		}
//		else if(interpreter.isUnaryTurtleCommand(keyword)){
//			currSearchIndex = searchStartIndex+1;
//			param = parseParam(input, searchStartIndex+1, 1);
//			args = createDoubleArgs(1);
//			Method method = interpreterClass.getDeclaredMethod(keyword, args);
//			double res = (double) method.invoke(obj, param[0]);
//			System.out.println(res);
//			model = interpreter.getModel();
//			return res;
//		}
//		else if(interpreter.isBinaryTurtleCommand(keyword)){
//			currSearchIndex = searchStartIndex+2;
//			param = parseParam(input, searchStartIndex+1, 2);
//			args = createDoubleArgs(2);
//			Method method = interpreterClass.getDeclaredMethod(keyword, args);
//			double res = (double) method.invoke(obj, param[0], param[1]);
//			System.out.println(res);
//			model = interpreter.getModel();
//			return res;
//		}
//		else throw new IllegalArgumentException();
//	}
//
//	
//	private double interpretTurtleQuery(String[] input, String keyword, int searchStartIndex) throws ClassNotFoundException, 
//	InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
//		Class interpreterClass = Class.forName(rb.getString("TurtleQueryInterpreterLabel"));
//		Object obj = interpreterClass.getDeclaredConstructor(SlogoUpdate.class).newInstance(model);
//		TurtleCommandInterpreter interpreter = new TurtleCommandInterpreter(model, stateUpdater);
//		if(interpreter.isTurtleQuery(keyword)){
//			return handleNonInputKeywordWithModel(keyword, searchStartIndex, interpreterClass, obj, interpreter);
//		}
//		else throw new IllegalArgumentException();
//	}
//	
//	private double interpretMathCommand(String[] input, String keyword, int searchStartIndex) throws ClassNotFoundException, InstantiationException, 
//	IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
//		Class interpreterClass = Class.forName(rb.getString("MathInterpreterLabel"));
//		Object obj = interpreterClass.newInstance();
//		MathInterpreter interpreter = new MathInterpreter();
//		
//		if(interpreter.isNonInputMathExpression(keyword)){
//			return handleNonInputKeyword(keyword, searchStartIndex, interpreterClass, obj);
//		}
//		else if(interpreter.isUnaryMathExpression(keyword)){
//			return handleUnaryKeyword(input, keyword, searchStartIndex, interpreterClass, obj);
//		}
//		else if(interpreter.isBinaryMathExpression(keyword)){
//			return handleBinaryKeyword(input, keyword, searchStartIndex, interpreterClass, obj);
//		}
//		else throw new IllegalArgumentException();
//	}
//	
//	private double interpretBooleanCommand(String[] input, String keyword, int searchStartIndex) throws ClassNotFoundException, InstantiationException, 
//	IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
//		Class interpreterClass = Class.forName(rb.getString("BooleanInterpreterLabel"));
//		Object obj = interpreterClass.newInstance();
//		BooleanInterpreter interpreter = new BooleanInterpreter();
//
//		if(interpreter.isUnaryBooleanExpression(keyword)){
//			return handleUnaryKeyword(input, keyword, searchStartIndex, interpreterClass, obj);
//		}
//		else if(interpreter.isBinaryBooleanExpression(keyword)){
//			return handleBinaryKeyword(input, keyword, searchStartIndex, interpreterClass, obj);
//		}
//		else throw new IllegalArgumentException();
//	}
//	
//	private double handleNonInputKeywordWithModel(String keyword, int searchStartIndex, Class interpreterClass, Object obj,
//			TurtleCommandInterpreter interpreter)
//			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//		currSearchIndex = searchStartIndex;
//		Class[] args;
//		args = createDoubleArgs(0);
//		Method method = interpreterClass.getDeclaredMethod(keyword, args);
//		double res =  (double) method.invoke(obj);
//		System.out.println(res);
//		model = interpreter.getModel();
//		return res;
//	}
//
//	private double handleBinaryKeyword(String[] input, String keyword, int searchStartIndex, Class interpreterClass,
//			Object obj) throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
//			IllegalAccessException, InvocationTargetException {
//		currSearchIndex = searchStartIndex+2;
//		double[] param;
//		Class[] args;
//		param = parseParam(input, searchStartIndex+1, 2);
//		args = createDoubleArgs(2);
//		Method method = interpreterClass.getDeclaredMethod(keyword, args);
//		double res = (double) method.invoke(obj, param[0], param[1]);
//		System.out.println(res);
//		return res;
//	}
//
//	private double handleUnaryKeyword(String[] input, String keyword, int searchStartIndex, Class interpreterClass,
//			Object obj) throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
//			IllegalAccessException, InvocationTargetException {
//		currSearchIndex = searchStartIndex+1;
//		double[] param;
//		Class[] args;
//		param = parseParam(input, searchStartIndex+1, 1);
//		args = createDoubleArgs(1);
//		Method method = interpreterClass.getDeclaredMethod(keyword, args);
//		double res = (double) method.invoke(obj, param[0]);
//		System.out.println(res);
//		return res;
//	}
//
//	private double handleNonInputKeyword(String keyword, int searchStartIndex, Class interpreterClass, Object obj)
//			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//		currSearchIndex = searchStartIndex;
//		Class[] args;
//		args = createDoubleArgs(0);
//		Method method = interpreterClass.getDeclaredMethod(keyword, args);
//		double res = (double) method.invoke(obj);
//		System.out.println(res);
//		return res;
//	}
//	
//	private double[] parseParam(String[] input, int startSearchIndex, int numOfParams) throws ClassNotFoundException, 
//	NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, 
//	IllegalArgumentException, InvocationTargetException{
//		double[] res = new double[numOfParams];
//		int index=0;
//		for(int i=startSearchIndex;i<startSearchIndex+numOfParams;i++){
//			if(isDouble(input[i])){
//				double temp = Double.parseDouble(input[i]);
//				res[index++] = temp;
//			}
//			else{
//				//recursive parsing of input statement
//				res[index++] = interpretCommand(input, i);
//			}
//		}
//		return res;
//	}
//	
//	String[] createParsedArray(String[] in, ProgramParser lang){
//		String[] out = new String[in.length];
//		for(int i=0;i<in.length;i++){
//			out[i] = lang.getSymbol(in[i]);
//		}
//		return out;
//	}
//	
//}
