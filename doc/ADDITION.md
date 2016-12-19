# Addition to SLogo Review

### Estimation
+ I had estimated that the entire process would take about 2 hours, considering how they are entirely new additions to the program, and also considering how I hadn't looked into my SLogo work for almost a month now.
+ I had also estimated that I would need to change 2-3 files at most, because they are confined to relatively small areas when it comes to programming features.

### Review
+ The entire process took about an hour, which beat my expectations. I also ended up changing 6-7 different files, which was a lot more than I thought.
	+ The reason for my estimations being off was because I had underestimated how well-designed my SLogo backend was, and I can say this with confidence because even after a month, I really do think that my SLogo backend implementation is beautiful. It uses abstract classes and interfaces seamlessly, so the only big change that I had to make was to create a new Sub-Interpreter that extended from an abstract class 
	+ Moreover, because call hierarchies and control flows were extremely clear, the fact that I had lost all muscle memory with regards to Slogo did not matter, because it was only a matter of reading into the already well-written code that I had authored a month ago.
	+ The number of changed files was larger than I thought, because I had to create a separate class called TurtleStamp, as well as a new interface to account for such changes. While I could have appended them to an existing class and interface, I wanted to adhere to the same design principles that I had stuck to a month ago, which was to keep functions as modularized as possible.

+ Despite having well-written code that made the process so much easier for me, it actually took multiple tries for me to get everything figured out. This included first creating a TurtleStamp class, then deleting it because I thought I could include the functionalities in an existing class, then creating the same class that I had deleted because I realized that I do indeed need a separate class. 


### Analysis
+ I can say with absolute confidence that my SLogo code was as good as I remember it to be, because the Main Interpreter looped through all Sub-Interpreters that had a common method, so as long as I extended the abstract class (**SubInterpreter.java**) and implemented all of the methods that I was required to, it was guaranteed that this new class would also be compatible with the Main Interpreter. Moreover, because the interfaces clearly defined all control flows and call hierarchies, it was a matter of minutes before I found where I had to add new interfaces, new data structures, and new methods.

+ The only downside to this addition was how couldn't actually visualize the changes that I had made to the backend, because there were no frontend functions to correspond to my changes. How our SLogo worked was that I would add all changes (such as PathLines) to a List, which the frontend would read from and visualize on the screen. I completed everything up to creating a List of TurtleStamps and populating it, so the frontend would have no difficulty in reading from this list and implementing the stamps on the screen.

+ From a complete stranger's perspective, the idea of extending the abstract class (**SubInterpreter.java**) and not having to change anything on the Main Interpreter may not seem very intuitive. However, because the loop that is run within Main Interpreter (within the *interpretCommand()* method) is not only succinct but also very easy to read, I can confidently say that the user will not have a hard time understanding the code and implementing new features, just as I did.

