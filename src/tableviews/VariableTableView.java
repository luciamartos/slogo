package tableviews;

import gui.Variable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class VariableTableView<S> extends TableView<S> {

	public VariableTableView() {
		setEditable(true);
		TableColumn<Variable, String> variables = new TableColumn<Variable, String>("Variables");
		variables.setEditable(true);

		TableColumn<Variable, String> variableNames = new TableColumn<Variable, String>("Name");
		variableNames.setCellValueFactory(new PropertyValueFactory<Variable, String>("name"));
		TableColumn<Variable, String> variableValues = new TableColumn<Variable, String>("Value");
		variableValues.setCellValueFactory(new PropertyValueFactory<Variable, String>("value"));
		variableValues.setEditable(true);
	}

}
