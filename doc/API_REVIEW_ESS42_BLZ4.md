Part 1
=====

1) The commands that the front end captures in the command window should be interfaceable with any type of backend. The backend should also be flexible in a way such that any type of front-end (even an iOS app), would be able to fully utilize the API. The backend tree that is used to parse the input text from the front end should not know how it is called (that there is a command console where the user inputs a string). Hence, all APIs cannot be too specific to one specific implementation of the API.

2) There will be a model that is an object which encapsulates all parameters associated with the turtle (x and y coordinates, and direction). This backend model could have an Observable class attached so that it can be observed by any front end who would like to consume the API. The front end would simply act as an Observer to detect any parameter changes to the model and act accordingly based on how the front end would like to display changes.

3) There are many different exceptions that may occur in both the front and backend. First of all, the backend needs to detect any issues with the syntax of the command line inputs. There is a database of valid commands and regular expressions, and any violation should throw different exceptions. For example, if the user typos a command, the user should be alerted. Furthermore, this should be different from an error that may occur from inputting an invalid range of values for an arcsine parameter, or a string input when only a number is allowed. All of these errors would first be caught by the backend parser, which could throw specific and descriptive errors for every type of issue that may arise. The front end should be able to detect any errors that are caught by the backend and display the error in a user-friendly way.

4) We believe that a good API design should be modular, flexible, and useful. In distinctly splitting the front end and back end and giving them as little specificity to the other end as possible, the project would have a good design. Furthermore, the external APIs must only contain high level and useful methods to call. The very minor methods and helper methods should be contained (and only accessible) internally, so that the user can perform useful API calls in an efficient manner. Lastly, a good API should be documented profusely so that any consumer of the API can know which calls to use and how to interface them with their own code.

Part 2
=========
1) Use Cases:

&nbsp;&nbsp;&nbsp;&nbsp;1. Move Turtle -> public method that will move the object around corresponding to input command from the back-end. Want to move up the turtle or move it in a certain direction results in the API being called to move the turtle image towards the proper direction
&nbsp;&nbsp;&nbsp;&nbsp;2. Another use case is to change angles of images, you can rotate an imageview towards a certain direction. If you use the API, you can additionally move the angle or direction upon which the turtle will move itself
&nbsp;&nbsp;&nbsp;&nbsp;3. Another use case is for just user input commands, where the commands are read in from the input box and subsequently recorded and analyzed on the back-end to understand the proper response.
&nbsp;&nbsp;&nbsp;&nbsp;4. History or help manual that indicates the different ways that you can implement different functions, thereby allowing the turtle to move in a bunch of different ways depending on commands. Provides flexibility of both variety of commands for the turtle and reusability of old commands for repeated movements.
&nbsp;&nbsp;&nbsp;&nbsp;5. Setting up the base environment. If you wanted to establish the base image and background, as well as turtle positioning, the API would be good in setting up the initial state and creating everything in preparation for the impending commands.

2) Reflection likely isn't going to be too helpful in this project, nor is Generics but the observables are going to be of critical important, and enumerated states might be helpful in isolating or compartamentalizing commands. So, Observables will definitely be implemented in this project, as it would be incredibly difficult otherwise, and enumerated states might be a design option if it really cleans things up.

3) I'm excited to work on the interpretation of commands and translation of that into directional or rotational orientation/commands. Specifically, the translation from the input, to the back-end, back into a form that the front-end turtle can interpret and execute.

4) I'm worried about building the history or manual command log the most, as it works the most directly with observables and requires all other components of your project to work properly and update correctly.