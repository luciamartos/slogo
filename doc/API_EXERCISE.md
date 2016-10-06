# API EXERCISE: TEAM 18

### Members
+ Andrew Bihl (atb26)
+ Lucia Martos Jimenez (lm260)
+ Eric Song (ess42)
+ Ray Song (ys101)

### Critique of Cell Society

+ Should not be part of API
	+ setValue() in Cell
	+ setRule() in Cell
	+ setRT() in Cell - not something that another program should be able to see

+ Part of External API
	+ setSimulation()
	+ pauseSimulation()
	+ startSimulation()
	+ saveBoard()
	

+ Part if Internal API
	+ getProbCatch()
	+ toString()
	+ getFishReproductionInterval()


### Overall Plan for SLogo

+ Parsing will take place in the Interpreter class, which will accept **String inputs** as keys to a pre-defined Properties file.
+ The result of parsing is a String value that corresponds to the command that is to be run. This String value will then be used to call the subsequent methods. 
+ Errors are detected by the Interpreter class as well as each package class (ex: Math, Boolean...). 
	+ Nonexistent commands will be detected by the Interpreter class  - syntax error
	+ Numerical/value/token errors will be detected by each package class.
+ Each method in a given class will run a pre-defined algorithm that carries out the function of its namesake.
+ A listener on the frontend side will wait for changes to the backend, and visualize changes accordingly.

### Create APIs

+ External
	+ Frontend: drawTurtle(), drawWindow(), accessHTMLPage()
	+ Backend: Interpretation methods (ex: forward(), back())

+ Internal
	+ Frontend: updateView(), Draw(), Move()
	+ Backend: parseInput(), Interpretation methods (ex: forward(), back()) + Helper Methods that help with the calculation (ex: recursive process that runs through the command). 


### Use Cases

```java
	parseInput();
	interpretSubCommand();
	checkError();
	handleMotion();
	updateView();
	updateCommandHistory();	
```

```java
	parseInput();
	interpretSubCommand();
	checkError();
	displayError();
```

```java
	parseInput();
	interpretSubCommand();
	checkError();
	handleMotion();
	updateView();
	updateCommandHistory();	
	
```

```java
	parseInput();
	interpretSubCommand();
	checkError();
	handleColorChange();
	updateView();
	updateCommandHistory();	
	
```
 

