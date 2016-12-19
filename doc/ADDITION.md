### Estimation:
#### Before looking at the old code:

###### How long do you think it will take you to complete this new feature?

I think it will take about an hour.

###### How many files will you need to add or update? Why?

I'll probably need to update about 7 files. Two interfaces (interpreter–model, view–model). The interpreter will then need two additional classes changed to recognize the command. The model will need two classes changed in order to create a static copy of the turtle making the stamp, and the controller will have to keep track of the stamps. I expect there will be at least one class that I have not thought about here.

### Review:
##### After completing the feature:

###### How long did it take you to complete this new feature?

About an hour.

###### How many files did you need to add or update? Why?

I had to update 7:

* TurtleStateDataSource
 * Add ```public List<Integer> getTurtleStampIDs();``` to the interface. This would allow the front-end to know which inactive turtles are really stamps and change the way stamps are presented. In the implementation I just made, they just look like inactive turtles that cannot be toggled.
* MainInterpreter
  * I had to add a line to instantiate a new type of SubInterpreter called TurtleStampInterpreter
* TurtleStateUpdater
  * Had to add ```public int createTurtleStamps();``` and ```public boolean clearTurtleStamps();``` to the interface so that the interpreter layer can tell the model to do these things when needed.
* TurtleState
  * I added a method that creates a stamp of the turtle, which is like a clone except without properties set that only apply to turtles that can move (like drawing).
* TurtleStatesController
  * This class had to implement the code to create stamps of active turtles and get rid of them, as well as serve up the current turtle stamps to the front end.
* TabViewController
  * This class had to be modified so that it does nothing further if a user tries to toggle a stamp-turtle to active.
* TurtleActionsHandler
  * Had to change the return value of toggleTurtle(int id) to boolean so that the back end controller can tell the front end if anything was actually toggled or if the "turtle" was really a stamp.

I had to make one new class:
* TurtleStampInterpreter
  * This class is able to recognize the commands "stamp" and "clearstamps". It is a sub-class of SubInterpreter. It fit nicely into the existing structure of our interpreter. I made a separate sub-class (as opposed to adding to an existing one) because this one didn't quite fit with any of the existing SubInterpreters.


###### Did you get it completely right on the first try?

*  I almost did, aside from some minor coding errors and one front-end issue. It went about as smoothly as I could hope. The front-end doesn't remove the stamps when they are cleared, but the back-end is working fine (I left in print statement for you to see). I also had to make the front-end recognize a toggle that didn't go through (when the user tries to toggle a stamp).

### Analysis: what do you feel this exercise reveals about your project's design and documentation?

* It's a decent design. The interpreter is easy to add to, and the interfaces in which communication occurs between layers is pretty straight forward. I'm not completely clear with what was happening on the front-end, but I suppose this was an exercise for the back-end anyway.

###### Was it as good (or bad) as you remembered?

* It was as good as I remembered.

###### What could be improved?

* The sub interpreters could use reflection in the canHandle() method. Write now it is hard-coded to check a list of strings matching the method names. Then later when the method handle() is called, reflection is used to handle the command. To improve the design, the reflection could also be used to see if the class responds to a given selector instead of doing a String comparison.

* The model could also afford to have a bit more careful error checking, like checking for invalid or null IDs. Right now it assumes in many places that the calls being made are valid.

###### What would it have been like if you were not familiar with the code at all?

* The biggest thing that a new user may not pick up that would significantly help is to know how the interpreting layer is set up. We planned out from day 1 the idea of SubInterpreters, and this design is what made the extension easy. However, a new user may not intuitively see what was going on. If he instead tried to modify an existing subinterpreter where it didn't really fit or didn't understand the use of reflection, the task would become much harder. It would make sense to have documentation explaining the way the interpreter layer works together.
