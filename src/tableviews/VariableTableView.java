package tableviews;

import gui.Variable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class VariableTableView<Variable> extends TableView<Variable> {

	public VariableTableView() {
		setEditable(true);
		managedProperty().bind(this.visibleProperty());

		TableColumn<Variable, String> variables = new TableColumn<Variable, String>("Variables");
		variables.setEditable(true);

		TableColumn<Variable, String> variableNames = new TableColumn<Variable, String>("Name");
		variableNames.setCellValueFactory(new PropertyValueFactory<Variable, String>("name"));
		TableColumn<Variable, String> variableValues = new TableColumn<Variable, String>("Value");
		variableValues.setCellValueFactory(new PropertyValueFactory<Variable, String>("value"));
		variableValues.setEditable(true);

		variables.getColumns().addAll(variableNames, variableValues);

		getColumns().add(variables);
		setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

}
