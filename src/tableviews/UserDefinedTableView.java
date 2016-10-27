package tableviews;

import gui_components.UserDefinedCommand;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserDefinedTableView<UserDefinedCommand> extends TableView<UserDefinedCommand> {

	public UserDefinedTableView() {
		TableColumn<UserDefinedCommand, String> userDefinedCommands = new TableColumn<UserDefinedCommand, String>(
				"User-Defined\nVariables");

		// tableView.setItems(data);
		TableColumn<UserDefinedCommand, String> userDefinedCommandNames = new TableColumn<UserDefinedCommand, String>(

				"Name");
		userDefinedCommandNames.setCellValueFactory(new PropertyValueFactory<UserDefinedCommand, String>("name"));
		TableColumn<UserDefinedCommand, String> userDefinedCommandValues = new TableColumn<UserDefinedCommand, String>(
				"Value");
		userDefinedCommandValues.setCellValueFactory(new PropertyValueFactory<UserDefinedCommand, String>("value"));
		userDefinedCommandValues.setEditable(true);
		userDefinedCommands.getColumns().addAll(userDefinedCommandNames, userDefinedCommandValues);

		getColumns().add(userDefinedCommands);
		setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

}
