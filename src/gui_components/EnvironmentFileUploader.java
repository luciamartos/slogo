package gui_components;

import XMLparser.XMLReader;

public class EnvironmentFileUploader extends FileChooser {
	private String MY_PATH = "data/examples/workspace_settings/";
	public EnvironmentFileUploader(ReadCommandFileInterface myInterface, String initVal, String filePath) {
		super(myInterface, initVal, filePath);
	}

	@Override
	protected void executeActionsFromFile() {
		XMLReader myReader = new XMLReader(MY_PATH + selectedFilename);
		
		myInterface.getImageURLFromFile(myReader.getImageURL());
		myInterface.getBackgroundColorFromFile(myReader.getBackgroundColor());
		myInterface.getLanguageFromFile(myReader.getLanguage());
		myInterface.getTurtleCountFromFile(myReader.getTurtleCount());
		myInterface.getPenColorFromFile(myReader.getPenColor());
		myInterface.getPenDownFromFile(myReader.getPenDown());
		myInterface.getLineTypeFromFile(myReader.getLineStyle());	
		myInterface.getPenThicknessFromFile(myReader.getPenThickness());
	}


}
