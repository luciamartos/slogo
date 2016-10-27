# API_CHANGES - TEAM 18

### Frontend Status Quo
+ WindowViewController - oversees the entire Frontend, branches into ViewController(controller that is in charge of each window)

+ TabViewController - main controller for each tab
	+ Calls translator(gets data from backend, changes into coordinates that frontend implements)
	+ After translation, information is passed onto GUI, which calls Canvas Action

+ SettingsController - initializes all the different controls (pen, workspace, general turtle settings)
	+ TurtleController
	+ PenController 
	+ WorkspaceController 
	+ GeneralController 
	+ SettingsController

+ CanvasAction
	+ Calls translator(gets data from backend, changes into coordinates that frontend implements)
	
**Major frontend changes during past two weeks**  

+ Addition of multiple controllers for each action
+ Splitting ViewController into multiple small controllers
+ Use of interfaces to communicate communication between frontend and backend
	
### Backend Status Quo
+ Model Controller
	+ Keeps track of all Models and their current states, central point where View Controller and Main Interpreter gets the current board state information
	+ Utilizes multiple interfaces to communicate with the View Controller and the Main Interpreter

+ Main Interpreter
	+ Accepts a single String input, internally parses statement and instantiates/updates the SlogoUpdate model which is then passed off to the Model Controller
	+ Utilizes multiple subclasses to parse different commands accordingly
	+ Utilizes interface to directly send over error messages to View Controller

**Major backend changes during past two weeks **

+ Implementation of a separate Model Controller, as opposed to having controllers directly handle the model and pass the model around
+  Integrating Main Interpreter into a single class that accepts a single String and parsing the input accordingly, as opposed to dividing it into an Actionable interpreter and a Non-actionable interpreter
	
### Changes to API
+ Before: a single data model where ViewController could directly get information from. 
+ Now, all information is passed through the Model Controller, and anything that wants to access model information has to do so through the Model Controller. This is because we did not want the View Controller to be constantly updated.
+ No link between model and any other class but the Model Controller
+ Interpreter is no longer divided into an Actionable interpreter and a Non-actionable interpreter, but a single interpreter that always returns a double value, and updates the model only on actionable commands.
	
### Upcoming Significant Design Changes
+ Model will be separated into separate states and separate turtles
+ One board state controller will manage one board, and one set of turtles on that board - this is to allow for multiple tabs running
+ Interpreter will be overhauled to get rid of multiple if-statements, which will be replaced by a Collection of sub-classes that are queried on whether they can handle the given input, and if so, handle them accordingly.
