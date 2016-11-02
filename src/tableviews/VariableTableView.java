package tableviews;

import gui.Variable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class VariableTableView<Variable> extends TableView<Variable> {
	
	TableColumn<Variable, String> variables;

	public VariableTableView(String title, String leftSubTitle, String rightSubTitle) {
		setEditable(true);
		managedProperty().bind(this.visibleProperty());

		variables = new TableColumn<Variable, String>(title);
		variables.setEditable(true);

		TableColumn<Variable, String> variableNames = new TableColumn<Variable, String>(leftSubTitle);
		variableNames.setCellValueFactory(new PropertyValueFactory<Variable, String>("name"));
		TableColumn<Variable, String> variableValues = new TableColumn<Variable, String>(rightSubTitle);
		variableValues.setCellValueFactory(new PropertyValueFactory<Variable, String>("value"));
		variableValues.setEditable(true);

		variables.getColumns().addAll(variableNames, variableValues);

		getColumns().add(variables);
		setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	public void setTitle(String title){
		variables.setText(title);
	}

}
