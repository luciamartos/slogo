package gui_components;

import java.util.Observable;

import XMLparser.XMLWriter;
import general.NewSlogoInstanceCreator;

import general.Properties;
import gui.FileChooserPath;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.XMLReader;

public class GeneralSettingsController extends Observable implements ReadCommandFileInterface {
	private Properties viewProperties;
	private HBox hBox;
	private boolean newTab;
	private String newImageURL;
	private int newBackgroundColor;
	private String newLanguage;
	private int newTurtleCount;
	private int newPenColor;
	private boolean newPenDown;
	private int newLineStyle;
	private int newPenThickness;
	private SaveWorkspaceInterface myInterface;
	
	private Image newImage;
	private String newCommandString;
	private NewSlogoInstanceCreator instanceCreator;

	public GeneralSettingsController(Properties viewProperties, NewSlogoInstanceCreator instanceCreator, SaveWorkspaceInterface myInterface) {
		this.instanceCreator = instanceCreator;
		this.viewProperties = viewProperties;
		this.myInterface = myInterface;

		newPenThickness = -1;
		newBackgroundColor = -1;
		newPenColor = -1;
		VBox vBoxLeft = new VBox(viewProperties.getDoubleProperty("padding"));
		vBoxLeft.getChildren().add(initializeUndoButton());
	//	vBoxLeft.getChildren().add(initalizeFileLoader());
		vBoxLeft.getChildren().add(initalizeCommandFileLoader());

		VBox vBoxRight = new VBox(viewProperties.getDoubleProperty("padding"));
		vBoxRight.getChildren().add(initializeAddTabButton());
		vBoxRight.getChildren().add(initializeGetHelpButton());
		vBoxRight.getChildren().add(initializeSaveWorkspaceButton());

		hBox = new HBox(viewProperties.getDoubleProperty("padding"));
		hBox.getChildren().addAll(vBoxLeft, vBoxRight);
	}

	private Node initializeSaveWorkspaceButton() {
		Button saveWorkspace = createButton(viewProperties.getStringProperty("Save_workspace"), viewProperties.getDoubleProperty("load_worskpace_button_width"));
		saveWorkspace.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				myInterface.saveWorkspace();
			}
		});
		return saveWorkspace;
	}

	private Node initializeAddTabButton() {
		Button addTab = createButton("Add Tab", viewProperties.getDoubleProperty("help_button_width"));
		addTab.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				instanceCreator.addSlogoInstance();
			}
		});
		return addTab;
	}

	private Node initalizeCommandFileLoader() {
		CommandFileUploader myUploader = new CommandFileUploader(this, "Command file", "data/examples/loops/");
		return myUploader.getFileUploaderButton();
	}

	private Node initalizeFileLoader() {
		EnvironmentFileUploader myUploader = new EnvironmentFileUploader(this, "Settings file", "data/examples/workspace_settings/");
		return myUploader.getFileUploaderButton();
	}

	private Node initializeUndoButton() {
		Button undoButton = createButton(viewProperties.getStringProperty("Undo"), viewProperties.getDoubleProperty("help_button_width"));
		undoButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				setChanged();
				System.out.println("UNDO ACTION");
				notifyObservers();
			}
		});
		return undoButton;
	}

	private Node initializeGetHelpButton() {
		Button helpButton = createButton(viewProperties.getStringProperty("get_help"), viewProperties.getDoubleProperty("help_button_width"));
		helpButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				BrowserView myView = new BrowserView(new Stage(), viewProperties.getDoubleProperty("help_width"),
						viewProperties.getDoubleProperty("help_height"));
			}
		});

		return helpButton;
	}

	private Button createButton(String text, double width) {
		Button button = new Button(text);
		button.setPrefWidth(width);
		return button;
	}

	public Node getNode() {
		return hBox;
	}


	@Override
	public void getCommandLineFromFile(String myCommand) {
		setChanged();
		newCommandString = myCommand;
		notifyObservers();
	}

	public String getNewCommandLineFromFile() {
		return newCommandString;
	}

//	@Override
//	public void getLineTypeFromFile(int lineStyle) {
//		setChanged();
//		newLineStyle = lineStyle;
//		notifyObservers();
//	}
//
//	public int getNewPenType() {
//		return newLineStyle;
//	}
//
//	@Override
//	public void getPenDownFromFile(String penDown) {
//		setChanged();
//		if(penDown.equals("no")) newPenDown = false;
//		else{
//			newPenDown =true;
//		}
//		notifyObservers();
//	}
//
//	public boolean getNewPenDown() {
//		return newPenDown;
//	}
//
//	@Override
//	public void getPenColorFromFile(int penColor) {
//		setChanged();
//		newPenColor =  penColor;
//		notifyObservers();
//	}
//
//	public int getNewPenColor() {
//		return newPenColor;
//	}
//
//	@Override
//	public void getTurtleCountFromFile(String turtleCount) {
//		setChanged();
//		newTurtleCount = Integer.parseInt(turtleCount);
//		notifyObservers();
//	}
//
//	public int getNewTurtleCount() {
//		return newTurtleCount;
//	}
//
//	@Override
//	public void getLanguageFromFile(String language) {
//		setChanged();
//		newLanguage = language;
//		notifyObservers();
//	}
//
//	public String getNewLanguage() {
//		return newLanguage;
//	}
//
//	@Override
//	public void getBackgroundColorFromFile(int backgroundColor) {
//		setChanged();
//		newBackgroundColor = backgroundColor;
//		notifyObservers();
//	}
//
//	public int getNewBackgroundColor() {
//		return newBackgroundColor;
//	}
//	
//	@Override
//	public void getImageURLFromFile(String imageURL) {
//		setChanged();
//		newImageURL = imageURL;
//		notifyObservers();
//	}
//	
//	public Image getNewImage() {
//		Image image = FileChooserPath.selectImage(newImageURL, 50, 50);
//		return image;
//	}
//
//	@Override
//	public void getPenThicknessFromFile(int penThickness) {
//		setChanged();
//		newPenThickness = penThickness;
//		notifyObservers();
//	}
//	
//	public int getNewPenThickness(){
//		return newPenThickness;
//	}
//
//	@Override
//	public int getTurtleID() {
//		// TODO Auto-generated method stub
//		return 0;
//	}

}
