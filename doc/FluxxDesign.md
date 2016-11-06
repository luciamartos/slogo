# Fluxx Design: Slogo Team 18

### Members
+ Ray Song(ys101)
+ Eric Song (ess42)
+ Andrew Bihl (atb26)
+ Lucia Martos (lm260)

### Design

+ Card Superclass
	* Action subclass
	* Goal subclass
	* Keeper subclass
		* Actionable keepers
		* Non-actionable keepers
	* Creeper subclass
		* Double/triple/quadruple creepers
	* Rule subclass
		* Play rules
		* Draw rules

+ Player Superclass
	+ List of current keepers/creepers deployed by the player
	+ Should have a "hand" - a list of cards owned by the player

+ Table Superclass
	* Table may be able to contain the Player class
	* Contains list of current rules/goals deployed throughout the game - "active cards"

+ Deck class
	+ List of cards that will be drawn by the players
	+ Includes *draw()* method, and maybe a *shuffle()* method