# API Review (Lab)

### Participants
+ Ray Song (ys101) (Me)
+ Noel Moon (nm142)

### Part 1

+ The backend API must be flexible enough to account for multiple languages and all corresponding commands for the given language.
	+ As such, it must have an internal way of interpreting the commands and passing them over to the front-end. Our team's API implements a separate Interpreter class, which must be open to all possible languages. This will likely be implemented by referring to a Properties file.
	+ The MainModel object, which is an aggregation of all the commands that the turtle will refer to on a given turn, must also be flexible in order to account for all types of commands and the different variable values that will ensue as a result.
	
+ Besides the features that are directly used to communicate with the frontend - namely, the MainModel class - everything else will remain under the hood, that is, as an internal API.
	+ The Interpreter class, which handles the bulk of the workload for our program, will remain hidden from the user, because the user has no reason to toggle with the specific features of the Interpreter - all the user needs is the result of the command, not *how* the command is actually handled.
	+ Conversely, the MainModel class will be exposed to the frontend because it is the medium that is used to communicate between the frontend and the backend. If the user so wanted, the user could also see this model as well, because it will be implemented as a public class that is passed around - think of it as a volleyball that is continuously tossed between the two sides, the backend and the frontend - which means that anyone else could also come and grab it. 

+ We expect there to be multiple exceptions on the backend, especially when it comes to handling bogus inputs and illogical inputs. 
	+ The most salient example is a command that is not included in any language; for example, "qwbeafuiqewl" or "apple orange bear" are two commands that would not be accepted in any way, because none of the words correspond to the keywords in our Properties file.
	+ Another exception, and one that's more difficult to catch, is one where the keywords are correct, but they are organized in a way that the interpreter cannot handle. For example, "50 fd" is a command that will throw an error because the correct syntax for moving the turtle 50 pixels forward is "fd 50".
	+ A much more subtle exception would be one where the command is nested, and while the individual commands seem to make sense, the aggregated command does not. For example, "fd fd 50" is a command that does not make sense, because fd accepts an integer input for the number of steps that the turtle will take, but "fd 50" is not an integer representation according to our model. 
	+ As such, we plan to handle our exceptions by creating a customized Exception class that will not only signal an exception, but also tell the user specifically what error had occurred: invalid input or syntax error.
	
+ Our design and API enables us to build a flexible system that can provide the backbone of a SLogo implementation that can accept multiple inputs, as well as nested commands that may be a little complex to break down.
	+ Through the use of Properties files that are separated according to different languages, our team will allow users to switch fluently between multiple languages, because the resource for each language will already be embedded within the project.
	+ Through the use of a regex parser and a recursive algorithm that divides the command into an "actionable" command and an "in-actionable" command, our Interpreter will be able to throw exceptions when a given command is invalid, and otherwise translate the command into a MainModel object that will be successfully passed onto the frontend.
	
### Part 2

+ **Use cases**
	1. fd 50
	+ This is a perfectly valid input that will be handled by the interpreter, and the results will be passed off to the frontend.
2.  (null input)
	+ Instead of throwing a NullPointerException, our program will check for null inputs and return an exception that reminds the user that the input cannot be null.
3. awugileagfwe 
	+ When the input does not correspond with any of the pre-defined commands in the Properties files, the program will return an invalid input exception.
4. fd RED
	+ The command fd requires an integer input after it, but RED is not compatible with this command. As such, the interpreter will recursively parse the command and return a syntax error exception. 
5. fd fd RED 
	+ fd is an "actionable" command according to our definition, which means that whatever comes after cd must be an "in-actionable" command that complements the actionable command. Because there are two consecutive actionable commands, the first of which does not have a complementing in-actionable command, the interpreter will return a syntax error exception.

+ **Advanced Java features **
	+ Based on what we learned in class today, as well as the team discussions that we've been having, the observer/observable pattern will come in extremely handy to our project. 
	+ The frontend will be the observer, while the backend (especially the MainModel object) will be observable. This way, the frontend wouldn't have to query the backend every single second; rather, the frontend would read into the MainModel object that it receives, after it observes that the backend has completed an interpretation.
	
+ I am most excited about working on the interpreter class, because unlike my past contributions to my CellSociety team, the backend is very much oriented towards algorithms. Unlike the past project, where most of my time was spent looking into online documentations and examples, I feel that this time I will be spending a lot of time with the actual coding of the Interpreter class - something that comes as a pleasant change for me.

+ On the other hand, I am most worried about the communication between the front-end and the back-end teams, especially because it's the first time I've worked with a specific set of APIs, or any APIs, for that matter. Also, the division between the front-end and the back-end seems to be much more clear compared to Cell Society, which means that communication would be that much more difficult.
