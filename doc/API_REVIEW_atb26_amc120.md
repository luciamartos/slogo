# Backend API Review

## Part 1

###### 1. What about your API/design is intended to be flexible?

The interpreting layer and (separately) the model should be as modular as possible. There are a view features we envision to achieve this.

- The interpreting layer can have support for different commands swapped in an out.

- The interpreting layer is separated from the logic of controllers and the model. In this way it would be very easy for another project to import and use the interpreting package.

- The model will just hold primitive values for different attributes that the front end controller(s) can convert to visual information. For example, the model will hold RGB information for color, but not an actual Java Color object. This way it is more flexible and not tied to a visual implementation.

###### 2. How is your API/design encapsulating your implementation   decisions?

- The interpreting layer will act as a single modular unit to which a string is passed (representing a user's command input) and from which a data object is received that represents changes to be made.

- Interpretation of commands will be separated into conceptual categories like "math" or "turtle movement" so that support for different types of commands could be added or removed the same way Java packages are imported as needed.

- The model will hold simple values not specific to any Java libraries or objects.

###### 3. What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?

- The interpreting layer has to recognize invalid commands: either commands not supported or commands with invalid parameters. It will do this by breaking out of the recursive interpreting process and send an event to a delegate that conforms to an exception handling interface.

###### 4. Why do you think your API/design is good (also define what your measure of good is)?

- The only thing that is explicitly tied down to our implementation details is the controller layer. The model only holds primitive information and the view will only be modified by a controller that interprets the model's data.

## Part 2

###### Come up with at least five use cases for your part (it is absolutely fine if they are useful for both teams).

- User inputs a command and the turtle model is updated.

- User inputs a command and an error is thrown to a specified error handling class.

- Other class needs to get the current state of the model.

- User inputs a command and a trait of the environment is changed.

- Other class access the command history to export.

###### How do you think at least one of the "advanced" Java features will help you implement your design?

-  Reflection to send the interpretation of a sub-command to the specific interpreting class should handle it without having to use a huge switch statement. I can just map the commands to a class path for the interpreting class that can handle that type of command.

###### What feature/design problem are you most excited to work on?

- The recursive interpreting logic should be fun to implement.

###### What feature/design problem are you most worried about working on?

- Organizing the way the model stores types of information. 
