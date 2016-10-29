package interpreter;

public class TurtleQueryInterpreter extends SubInterpreter{

	private SlogoUpdate model;
	
	TurtleQueryInterpreter(SlogoUpdate model){
		this.model = model;
	}
	
	@Override
	boolean canHandle(String keyword) {
		return isTurtleQuery(keyword);
	}

	@Override
	double handle(String[] input, String keyword, double[] param, int searchStartIndex) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	boolean isTurtleQuery(String input){
		return input.equalsIgnoreCase(rb.getString("xcor")) || input.equalsIgnoreCase(rb.getString("ycor")) ||
				input.equalsIgnoreCase(rb.getString("heading")) || input.equalsIgnoreCase(rb.getString("ispendown")) ||
				input.equalsIgnoreCase(rb.getString("isshowing"));
	}
	
	double xcoordinate(){
		return model.getXCoordinate();
	}
	
	double ycoordinate(){
		return model.getYCoordinate();
	}
	
	double heading(){
		return model.getAngle();
	}
	
	double ispendown(){
		return (model.getTurtleShouldDraw()) ? 1 : 0;
	}
	
	double isshowing(){
		return (model.getTurtleShouldShow()) ? 1 : 0;
	}
}
