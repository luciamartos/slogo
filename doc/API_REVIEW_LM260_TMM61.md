## What about your API/design is intended to be flexible?
The fact that we are separating the Controllers in to ViewController and ModelController which are trying to make the MainView and the Interpreter be very general. The API should have no idea how the backend is getting the information. It should only know the current state and the next state that it needs to change to.

	1.	How is your API/design encapsulating your implementation decisions?


	1.	What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?
The errors which the backend will look out for and display will be in one of the following categories: 
	•	Command does not exist (if the string is not a registered command eg. ketchup 10 ) 
	•	Values are not what expected (if you call a command and its not followed by what it needs to run eg. move potato) 
This information will then be sent  to the front end and displayed on the screen.

	1.	Why do you think your API/design is good (also define what your measure of good is)?
It is flexible and could be extended and it is independent of the back end. 