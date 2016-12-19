**Estimation: before looking at the old code:**

**how long do you think it will take you to complete this new feature?**

Around an hour

**how many files will you need to add or update? Why?**

I believe I will need to update just TabViewController, as it should have enough information from the backend to implement this feature.

**Review: after completing the feature:

**how long did it take you to complete this new feature?**

45 minutes.

**how many files did you need to add or update? Why?**

TabViewController, TurtleStateDataSource, and TurtleStatesController.
TabViewController to visualize the front end, TurtleStateDataSource, because I needed to add a method that changes the shape of the turtle AND give it an id. Previously since we only had to change the image of all active turtles, the id was not specified. The TurtleStatesController needed to be modified to implement the method in the interface.

**did you get it completely right on the first try?**

No, but for the most part.

**Analysis: what do you feel this exercise reveals about your project's design and documentation?

**was it as good (or bad) as you remembered?**

The design was as good as I remembered. It was fairly simple to utilize the interface to make the modifications necessary.

**what could be improved?**

The TabViewController class is way too large (over 500 lines). This made reacquainting with the class extremely difficult and was the majority of the time spent on this extension.

**what would it have been like if you were not familiar with the code at all?**

It would be a lot tougher due to the sheer size of TabViewController. Reading through that behemoth would be very tough even though not too many methods/variables are needed for this feature. In hindsight, I should have broken up that class into many smaller subclasses.