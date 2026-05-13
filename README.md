# Risk

## The game
You, the player, will only need to worry about two files: the Driver.java file, which you simply run to start playing, and
the Map of Risk.pdf, which you may find useful to orient yourself.

In Risk, you start the game with some territories and some troops which you get to distribute to your territories as you see
fit. Normally, the boardgame requires you to choose your territories---for your ease and time, however, this computer 
version will randomly assign you some territories.

After placing your troops, you will have the opportunity to attack enemy territories adjacent to your own. You may only 
attack from a territory on which you have at least two troops. If you deplete the enemy territory of its troops, then one of
your troops moves onto that territory and you have claimed it! You will also get a troop card and when you reach three of 
those, the game will automatically grant you an additional five troops to place where you like (on one of your own
territories). Each round, you will be granted additional troops to place as well, with the number dependent on
how many territories you already control.

This version of the game doesn't allow the user to move their troops from one territory to another, so place your fresh
troops extra strategically!

You will play against four computer players who will predictably attack the territory near them with the lowest number of
troops. The game ends when one player controls all territories.

## The code
There are five different classes, not counting the Driver that runs the game. Player is an abstract superclass of both 
Computer and User; there are actions that both should take (methods) and attributes that both should have (fields),
but a Computer should automatically perform these actions automatically while a User must be prompted for an input.

There is a Territory class to let us keep track of troops and neighboring Territory borders, as well as which Player
occupies it. The GameManager class is entirely static because everything it does we want it to do without needing to 
create a GameManager object. The GameManager keeps a list of all Territories in the game, which are hardcoded to be 
set up in accordance with the Map of Risk image. The GameManager also keeps a list of all the Players in the game, including
removing a Player who loses all their Territories (and is thus out of the game).

Finally, the Driver is set up to run the GameManager's central method, gameLoop(), until the loop tells it to stop (when
there is a winner!).
