package interpreter;

public class GeneralInterpreter extends SubInterpreter{

	@Override
	boolean canHandle(String keyword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	double handle(String[] input, String keyword, double[] param, int searchStartIndex) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	SlogoUpdate getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	boolean needList() {
		return false;
	}

	
}
