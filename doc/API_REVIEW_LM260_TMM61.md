# Part I

### What about your API/design is intended to be flexible?
The fact that we are separating the Controllers in to ViewController and ModelController which are trying to make the MainView and the Interpreter be very general. The API should have no idea how the backend is getting the information. It should only know the current state and the next state that it needs to change to.

### How is your API/design encapsulating your implementation decisions?
We are going to have a DisplayConfigurate object that contains all the information and the ViewController will have to access this

### What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?
The errors which the backend will look out for and display will be in one of the following categories: 
* Command does not exist (if the string is not a registered command eg. ketchup 10 )
* Values are not what expected (if you call a command and its not followed by what it needs to run eg. move potato)
This information will then be sent  to the front end and displayed on the screen.

### Why do you think your API/design is good (also define what your measure of good is)?
It is flexible and could be extended and it is independent of the back end. 

# Part II

### Come up with at least five use cases for your part (it is absolutely fine if they are useful for both teams).
* 'fd 50' is inputed which the view controller (the view controller adds this to the history array) then calls the backend and will then receive the updated grid which is then going to update the view. 
* 'hello' is inputed this will be sent to the backend in the same way and the backend will let the front end know that it was an error and then the front end will display the error.
* empty string will work the same way as hello and then display a different error that says the command was empty

### How do you think at least one of the "advanced" Java features will help you implement your design?
We were thinking of using binding (observer and observable) if a change is detected in the state of the grid and the grid will then be changed instantaneously. Another option for using this would be for when we have the list of the past commands and if we press something then it will be perceived and that command will be executed. 

### What feature/design problem are you most excited to work on?
Learning about binding, this is something that I still dont fully understand and I really want to get to know how this will benefit our project.

### What feature/design problem are you most worried about working on?
The connection between the front end and the back end is worrying me.
