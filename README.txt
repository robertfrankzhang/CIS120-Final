=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: robertzh
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Collections: Weighing the choice of using 2D Arrays and using Collections as the main storage method of my game board, I chose to ultimately use the ArrayList. I came to the conclusion that due to the extremely dynamic nature of the pieces on the board in 2048, constantly shifting around indexes as blocks are added, deleted, and moved would be an enormous challenge. In contrast, by using ArrayLists to store the Blocks on the grid (with block position on grid as Block properties), shifting blocks would just be a matter of changing property values, and adding and deleting blocks would be as easy as adding/removing Blocks from the List. Additionally, ArrayLists would allow for better encapsulation and abstraction of the Block class and its positional data, allowing for much easier handling especially after I added the originalPosition (ox/y) and currentPosition (cx/y) properties to Block, which aided in smooth animations. With Block fully encapsulated, Blocks could function much better independently from the board, which also increases the ability to perform Unit Tests on them.

In addition to using the ArrayList as my primary collection, I also used TreeMaps to associate months (as numbers) to their name (e.g. January), block values to their color on the board, and scores to their timestamp. I also used TreeSets to sort high scores taken from score.txt in preparation to select them to put on the top 3 leaderboard at the end of every game.

  2. My game uses File I/O in the storing of all historical scores/timestamps. I store two states: score, and the timestamp in which the score was made. At the end of every game, my program writes the new score/timestamp pair into a txt file. And when displaying the top  3 scores, my game reads this txt file and parses it into displayable strings.

  3. I use a significant amount of Inheritance and Subtyping in my game, mainly with the Block class. I have a Block abstract class that implements the core features of a Block, with core properties (positional properties, animation properties, general states), and core methods, like draw, move, and combine. I then have implemented 4 subclasses that have Block as their parent. These subclasses represent wildly different Blocks, with wildly different behaviors: RegularBlock, IceBlock, WildCardBlock, BombBlock. For example, a BombBlock would destroy neighboring blocks if it collided with another BombBlock, while a WildCardBlock would combine with any number block to double its value. To implement these different behaviors, these subclasses use a mixture of inherited methods and overriding custom methods. In the GameBoard, all Blocks are statically typed as Block, and then dynamic dispatch handles the different implementations at runtime.


  4. I tested my Block Class and its various subclasses w/ JUnit, by testing all of their methods. The logic for 2048 Block movement is pretty tricky, especially with the addition of special blocks, so I needed to make sure all of those movements and behaviors work. As explained above, my Block class is highly encapsulated, and extremely independent from the Board and GUI. This makes its behavior very easy to test.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

Block: Abstract class that defines the basic behavior and stores the general state of all Blocks on the board.

RegularBlock: Subclass of Block that customizes functionality to fit the function of the regular numbered block.

WildCardBlock: Subclass of Block that customizes functionality to fit the function of the WildCard Block. (Supposed to combine with any other regularBlock and thus double its value. Destroys itself after colliding with another WildCardBlock. Unable to combine with anything else)

BombBlock: Subclass of Block that customizes functionality to fit the function of the Bomb Block (Supposed to destroy top/right/left/bottom blocks around it after colliding with another bomb block. Unable to combine with anything else).

IceBlock: Subclass of Block that customizes functionality to fit the function of the Ice Block. (Can only move one square at a time. Destroys itself after colliding with another Ice Block. Unable to combine with anything else).

FileIO: Handles all the Reading/Writing/Parsing of the files to keep track of historical scores.

Game: The Main Function that functions the same as the one given in Mushroom of Doom.

GameBoard: The Board of the game that is the Controller of the game, with listeners. Contains game clock and handles game logic and painting onto the screen.

Tests: This class contains all of the Unit Tests of the Block class and its various subclasses


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

Not really, but the logic to making all the blocks react to each other and move correctly, especially with all the different subclasses at play with different behaviors, was pretty challenging at times. In general however, designing the architecture for this project was very fun and rewarding!


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

I think my design is pretty good as it is. Classes are encapsulated well, with as little coupling as possible, and with as limited methods and properties accessible to the outside as possible. I separate the "algorithmic" and "data" side of my code far from the GUI end of it, and reduced the amount of code repetition as much as I can, which made adding features to the game a joy. 


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.

I didn't use any other external libraries from the ones we used in class. All code is written by me (so no copy/paste from online sources). I did get a few images from online however to make my Blocks look snazzy, but since I don't plan on publishing this game, I'm not worried about copyright issues.
