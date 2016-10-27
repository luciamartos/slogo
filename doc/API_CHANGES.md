# API_CHANGES - TEAM 18

### Frontend

WindowViewController = oversees the entire Frontend, branches into ViewController(controller that is in charge of each window)

TabViewController - main controller for each tab
	+ Calls translator(gets data from backend, changes into coordinates that frontend implements)
	+ After translation, information is passed onto GUI, which calls Canvas Action

SettingsController - initializes all the different controls (pen, workspace, general turtle settings)
	+ TurtleController
	+ PenController 
	+ WorkspaceController 
	+ GeneralController 
	+ SettingsController

CanvasAction
	+ Calls translator(gets data from backend, changes into coordinates that frontend implements)
	
+ Major changes
	+ Addition of multiple controllers for each action
	+ Splitting ViewController into multiple small controllers
	+ Use of interfaces to communicate communnication between frontend and backend
	
+ Changes to API
	
	
	


### Backend

what changes that have been made to the APIs
if those changes are major or minor (justify your distinction based on how much they affected your team mate's code)
if those changes are for better or worse (if for the worse, is there a way to improve it or was the original API overly optimistic)
if your foresee any significant changes coming in the next few days as you finish the project (based on your experience and the fact that you now know all the features to be implemented)