package tableviews;

import java.util.HashMap;
import java.util.Map;

import gui.BoardStateDataSource;
import gui.TurtleStateDataSource;
import gui.Variable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import model.RGBColor;

public class TableViewController {
	VariableTableView turtleVariableTableView, userDefinedVariableTableView, userDefinedCommandTableView,
			colorTableView;

	public TableViewController() {
		turtleVariableTableView = new VariableTableView("", "Name", "Value");
		userDefinedVariableTableView = new VariableTableView("User Defined Variables", "Name", "Value");
		userDefinedCommandTableView = new VariableTableView("User Defined Commands", "Name", "Value");
		colorTableView = new VariableTableView("Parameter Mappings", "ID Number", "Value");

	}
	
	public void updateMapList(Map<Integer, RGBColor> colorMap, Map<Integer, String> penTypeMap, Map<Integer, String> shapeMap) {
		ObservableList<Variable> data = FXCollections.observableArrayList();
		
		data.add(new Variable("COLOR (R, G, B)", ""));
		for(Integer i: colorMap.keySet()){
			String hexColor = "("+colorMap.get(i).getRed()+", "+colorMap.get(i).getGreen()+", "+colorMap.get(i).getBlue()+")";
			data.add(new Variable(Integer.toString(i),hexColor));
		}
		
		data.add(new Variable("",""));
		
		data.add(new Variable("PEN TYPE", ""));
		for(Integer i: penTypeMap.keySet()){
			data.add(new Variable(Integer.toString(i),penTypeMap.get(i)));
		}
		
		data.add(new Variable("",""));
		
		data.add(new Variable("SHAPE", ""));
		for(Integer i: shapeMap.keySet()){
			data.add(new Variable(Integer.toString(i),shapeMap.get(i)));
		}
		colorTableView.setItems(data);
	}

	public void updateTurtleVariablesList(TurtleStateDataSource turtleStateDataSource, int currentlySelectedID) {
		turtleVariableTableView.setTitle("Turtle "+currentlySelectedID+" Parameters");
		ObservableList<Variable> data = FXCollections.observableArrayList();
		data.add(new Variable("X Coordinate",
				Double.toString(turtleStateDataSource.getXCoordinate(currentlySelectedID))));
		data.add(new Variable("Y Coordinate",
				Double.toString(turtleStateDataSource.getYCoordinate(currentlySelectedID))));
		data.add(new Variable("Angle", Double.toString(turtleStateDataSource.getAngle(currentlySelectedID))));
		data.add(new Variable("Turtle is Showing",
				Boolean.toString(turtleStateDataSource.getTurtleIsShowing(currentlySelectedID))));
		data.add(new Variable("Pen is Down",
				Boolean.toString(turtleStateDataSource.getTurtleIsDrawing(currentlySelectedID))));

		turtleVariableTableView.setItems(data);
	}

	public void updateUserDefinedVariablesList(BoardStateDataSource boardStateDataSource) {

		ObservableList<Variable> data = FXCollections.observableArrayList();
		Map<String, String> map = boardStateDataSource.getUserDefinedVariables();

		for (String s : map.keySet()) {
			if (s.charAt(0) == ':')
				data.add(new Variable(s.substring(1), map.get(s)));
		}
		userDefinedVariableTableView.setItems(data);
	}

	public void updateUserDefinedCommandsList(BoardStateDataSource boardStateDataSource) {
		Map<String, String> map = boardStateDataSource.getUserDefinedVariables();

		ObservableList<Variable> data = FXCollections.observableArrayList();
		for (String s : map.keySet()) {
			if (s.charAt(0) != ':')
				data.add(new Variable(s, map.get(s)));
		}
		userDefinedCommandTableView.setItems(data);

	}

	public VariableTableView getDefaultVariableTableView() {
		return turtleVariableTableView;
	}

	public VariableTableView getUserDefinedVariableTableView() {
		return userDefinedVariableTableView;
	}

	public VariableTableView getUserDefinedCommandTableView() {
		return userDefinedCommandTableView;
	}

	public VariableTableView getColorTableView() {
		return colorTableView;
	}
	
	
}