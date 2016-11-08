package gui_components;

import fileIO.XMLReader;
/**
 * @author Lucia Martos
 */
public class EnvironmentFileUploader extends FileChooser {
	private String MY_PATH = "data/examples/workspace_settings/";
	public EnvironmentFileUploader(ReadCommandFile myInterface, String initVal, String filePath) {
		super(myInterface, initVal, filePath);
	}

	@Override
	protected void executeActionsFromFile() {
		myInterface.loadBoard(myPath +selectedFilename);
		
//		XMLReader myReader = new XMLReader(MY_PATH + selectedFilename);
//		
//		int myID = myInterface.getTurtleID();
//		myInterface.getImageURLFromFile(myReader.getImageURL(myID));
//		myInterface.getBackgroundColorFromFile(myReader.getBackgroundColor());
//		myInterface.getLanguageFromFile(myReader.getLanguage());
//		myInterface.getTurtleCountFromFile(myReader.getTurtleCount());
//		myInterface.getPenColorFromFile(myReader.getPenColor());
//		myInterface.getPenDownFromFile(myReader.getPenDown(myID));
//		myInterface.getLineTypeFromFile(myReader.getLineStyle());	
//		myInterface.getPenThicknessFromFile(myReader.getPenThickness());
	}


}

	