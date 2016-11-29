/**
 * CS 141: Introduction to Programming and Problem Solving
 * Professor: Edwin Rodr&iacute;guez
 *
 * Group Project
 *
 * Escape the Dungeon for CS 141
 *
 * The purpose of this game is to test our ability to use object-oriented
 * programming. The game consists of multiple classes that make up the
 * different aspects incorporated. Within them are the different behaviors
 * (methods) and attributes (fields) that make up each class. This includes 
 * ActiveAgent, Briefcase, Enemy, GameEngine, Map, Player, PowerUp, and
 * UserInterface. The class ActiveAgent adjusts the positions when directed.
 * The Briefcase class determines where the briefcase will be placed. The
 * Enemy class places the enemies and stabs the player when it comes close,
 * and it contains Pursuit Mode during which enemies follow the player and
 * it is activated when Hard Mode is on. The GameEngine contains all of the
 * essentials to run the game itself. The Map keeps track of the positions
 * of the Player, Enemies, and Briefcase. The Player class keeps track of
 * the player's lives as well as the ammo. The PowerUp class contains the
 * invincibility, radar, and ammo. Lastly, the UserInterface contains all
 * menus and interaction with the operator.
 */


import java.util.*;

/**
 * This class represents a user interface for the user to access
 * it consist of all them menu, the introduction, and what should be displayed.
 */
public class UserInterface {
	
	/**
     * This field represents the state of the map, if the objects on the map are
     * visible or not to the user. Its default value is set to false, and it can 
     * changed through {@link #isDebugMode}.
     */
	private boolean isDebugMode;
	
	/**
	 * This field represents the state of the briefcase and if it is visible to the
	 * user, it's true, and it can be changed through {@link #canSeeBriefcase}.
	 */
	private boolean canSeeBriefcase;
	
	/**
     * This field checks to see if the input entered is valid. Its default is generally
     * started as true, and it is switched to false when the user inputs a value, used to
     * terminate input request loops. It is used in {@link #displayGameMenu()},
     * {@link #setDifficulty()}, {@link #selectAction(boolean)}, and {@link #selectDirection()}.
     */
	private boolean invalidInput;
	
	/**
     * This field is for when the input isn't valid. It is used in {@link #displayGameMenu()},
     * {@link #setDifficulty()}, {@link #selectAction(boolean)}, and {@link #selectDirection()}.
     */
	private String badInputStr= "Invalid input. Please try again.";
	private Scanner sc = new Scanner(System.in);
	
	/**
	 * This constructor sets the default values of {@link #isDebugMode} and {@link #canSeeBriefcase}.
	 */
	public UserInterface() {
		isDebugMode = true;
		canSeeBriefcase = false;
	}
	
	/**
     * This method is created to display the game menu, returning an
     * integer value which will be plugged inside the setUp constructor
     * inside the GameEngine class, giving a short welcome text, and
     * allowing the user to select a new game or to load the previous game.
     * This method sets {@link #invalidInput} to true and while it is true, it loops.
     * It catches poor inputs and displays {@link #badInputStr}.
     * @return input
     */
	public int displayGameMenu() {
		invalidInput = true;
		int input = 1;
		while (invalidInput) {
			try {
				invalidInput = false;
				System.out.printf("+-------------------------+\n" +
						  "*%-25s*\n" +
						  "*%-25s*\n" +
						  "*%-25s*\n" +
						  "*%-25s*\n" +
						  "*%-25s*\n" +
						  "+-------------------------+\n" +
						  "|%-25s|\n" +
		//				  "|%-25s|\n" +
						  "+-------------------------+\n",
						  "     Welcome, agent.     ",
						  "OBJECTIVE:", "Locate and retrieve the", "diamond hidden in one of",
						  "the rooms.",
						  "1 New Game", "2 Load Game");
				input = sc.nextInt();
				if (input < 1 || input > 2) {
					System.out.println(badInputStr);
					invalidInput = true;
				}
			} catch (InputMismatchException ime) {
				System.out.println(badInputStr);
				invalidInput = true;
				sc.next();
			}
		}
		return input;
	}
	
	/**
     * this method prints and asks the user if he wants to play on
     * hard difficulty or not by asking for input using {@link #sc}. If yes it plugs it into 
     * the setUp from the GameEngine. If the user input does not meet the criteria, it uses 
     * {@link #badInputStr} to print that user hasn't entered the valid input. The field
     * {@link #invalidInput} is set to true, and while it is true, it runs.
     * @return returnValue
     */
	public boolean setDifficulty() {
		sc = new Scanner(System.in);
		int selection = 2;
		boolean returnValue = false;
		invalidInput = true;
		while (invalidInput) {
			try {
				System.out.println("Do you want to play in Hard mode?\n1 Yes\t2 No");
				selection = sc.nextInt();
				if (selection == 1) {
					System.out.println("Hard mode is on.");
					returnValue = true;
					invalidInput = false;
				}
				else if (selection == 2) {
					System.out.println("Hard mode is off.");
					returnValue = false;
					invalidInput = false;
				}
				else {
					System.out.println(badInputStr);
					invalidInput = true;
				}
			} catch (InputMismatchException ime) {
				System.out.println(badInputStr);
				invalidInput = true;
				sc.next();
			}
		}
		return returnValue;
	}
	
	/**
     * this method takes in the boolean @param outerMenu which when set to true,
     * displays the outerMenu which prints the options for the player to move,
     * shoot, look, check Stats, debug, save, or quit, and asks for the user input
     * for which option the user wants to pick, and if the {@link #isDebugMode} is true,
     * it will display the extra option to display the key. If the input is wrong, it prints
     * {@link #badInputStr}.
     * This field uses {@link #invalidInput} and is set to true to run.
     */
	public int selectAction(boolean outerMenu) {
		sc = new Scanner(System.in);
		int input = 1;
		invalidInput = true;
		while ( invalidInput ) {
			invalidInput = false;
			try {
				if (outerMenu) {
					System.out.print("Select an action:" +
							"\n\t 1 Move" +
							"\n\t 2 Shoot" +
							"\n\t 3 Look" +
							"\n\t 4 Check Stats" +
							"\n\t 5 Toggle Debug" +
			//				"\n\t6 Save Game" +
							"\n\t 7 Quit\n");
					if (isDebugMode) {
						System.out.print("\t 8 View Key\n");
					}
					System.out.print("\t 9 Save Checkpoint" +
					        "\n\t10 Load Last Checkpoint\n");
					input = sc.nextInt();
					if (input < 1) {
						invalidInput = true;
						System.out.println(badInputStr);
					}
					if (!(isDebugMode)) {
						if (input > 7) {
							invalidInput = true;
							System.out.println(badInputStr);
						}
					}
					else {
						if (input > 10) {
							System.out.println(badInputStr);
							invalidInput = true;
						}
					}
				}
			} catch (InputMismatchException ime) {
				System.out.println(badInputStr);
				invalidInput = true;
				sc.next();
			}
		}
		
		return input;
	}
	
	/**
	 * this method toggles the value of {@link #isDebugMode}, which affects the darkness's display.
	 */
	public void changeDebug() {
	    /*
		if (!(isDebugMode)) {
			isDebugMode = true;
		}
		else {
			isDebugMode = false;
		}*/
	    System.out.println("Sorry this function is disabled for this demo!");
	}
	
	/**
     * this method displays the key to user, explaining what
     * the characters stand for inside the map
     */
	public void viewKey() {
		System.out.println("+-------------------------+"); // 25 + 2
		System.out.println("|           KEY           |");
		System.out.print  ("| R: Room                 |\n" +
						   "| P: Player               |\n" +
						   "| E: Enemy                |\n" +
						   "| O: Objective (Diamond)  |\n" +
						   "| I: Item Invulnerability |\n" +
						   "| D: Item Radar           |\n" +
						   "| A: Item Ammo            |\n" +
						   "+-------------------------+\n");
	}
	
	/**
     * In this method, {@link #invalidInput} is set to true, it runs, displaying the options for 
     * choosing direction and @return int value which is plug into the game engine
     */
	public int selectDirection() {
		sc = new Scanner(System.in);
		invalidInput = true;
		int selection = 1;
		while (invalidInput) {
			System.out.println("Select direction:" +
					"\n\t1 Up (W)" +
					"\n\t2 Right (D)" +
					"\n\t3 Left (A)" +
					"\n\t4 Down (S)" +
					"\n\t5 Return to Action Selection (R)");
			if (sc.hasNextInt()) {
				selection = sc.nextInt();
				if (selection > 0 && selection <= 5) {
					invalidInput = false;
				}
				else {
					System.out.println(badInputStr);
					invalidInput = true;
				}
			}
			else if (sc.hasNext()) {
				String input = sc.next();
				char inputChar[] = input.toCharArray();
				inputChar[0] = Character.toLowerCase(inputChar[0]);
				switch (inputChar[0]) {
					case 'w':
						selection = 1;
						invalidInput = false;
						break;
					case 'd':
						selection = 2;
						invalidInput = false;
						break;
					case 'a':
						selection = 3;
						invalidInput = false;
						break;
					case 's':
						selection = 4;
						invalidInput = false;
						break;
					case 'r':
						selection = 5;
						invalidInput = false;
						break;
					default:
						System.out.println(badInputStr);
						invalidInput = true;
				}
			}
		}
		return selection;
	}
	
	/**
     * This constructor takes all the positions of the map, room, player, and enemy
     * from the GameEngine and prints it for the user to see. If {@link #isDebugMod} is
     * false, the darkness (lack of vision) is displayed as [*], except for the player, rooms,
     * and adjacent squares. If it is true, there is no darkness, and everything is visible.
     * @param mapPositions
     * @param mapVision
     */
	public void displayMap(int[][] mapPositions, boolean[][] mapVision) {
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				switch(mapPositions[i][j]) {
					case 1: // room
						System.out.print("[R]");
						break;
						
					case 2: // empty
						if (!(mapVision[i][j]) || isDebugMode) {
							System.out.print("[ ]");
						}
						else {
							System.out.print("[*]");
						}
						break;
						
					case 3: // Enemy
						if (!(mapVision[i][j]) || isDebugMode) {
							System.out.print("[E]");
						}
						else {
							System.out.print("[*]");
						}
						break;
						
					case 4: // Player
						System.out.print("[P]");
						break;
						
					// ITEMS
					case 5: // Invincibility
						if (!(mapVision[i][j]) || isDebugMode) {
							System.out.print("[I]");
						}
						else {
							System.out.print("[*]");
						}
						break;
					case 6: // Radar
						if (!(mapVision[i][j]) || isDebugMode) {
							System.out.print("[D]");
						}
						else {
							System.out.print("[*]");
						}
						break;
					case 7: // Ammo
						if (!(mapVision[i][j]) || isDebugMode) {
							System.out.print("[A]");
						}
						else {
							System.out.print("[*]");
						}
						break;
					case 8: // Briefcase, (if visible as such)
						if (isDebugMode || canSeeBriefcase) {
							System.out.print("[O]");
						}
						else {
							System.out.print("[R]");
						}
				}
			}
			System.out.println();
		}
	}
	
	/**
     * this method takes in the the @param turnNumber
     * and the displays it to the user
     */
	public void displayTurnNum(int turnNumber) {
		System.out.printf("%16s%-11d\n", "TURN:  ", turnNumber);
	}
	
	/**
     * when a player enters a direction for movement from the {@link #selectDirection()},
     * if that direction is blocked by a room or is outside of the confines of the map,
     * this message is displayed.
     */
	public void displayNoMovement() {
		System.out.println("You cannot move in that direction. Please try again.");
	}
	
	/**
     * this is when the player reaches the final goal of the game
     * and finds the diamond
     */
	public void displayWin() {
		System.out.println("You found the diamond! You win!");
	}
	
	/**
     * this method displays how many lives the player has left
     * by taking in the @param livesLeft from the gameEngine
     * or will display -1 when the player dies
     */
	public void displayDeath(int livesLeft) {
		System.out.println("You have been killed.");
		if (livesLeft < 0) {
			System.out.println("You have failed your mission. Game Over.");
		}
		else {
			System.out.println("-1 Life" + 
					"\nLives remaining: " + livesLeft);
		}
	}
	
	/**
     * this method takes in @param remainingLives and
     * @param remainingAmmo and displays it to the user
     * when he wants to see his stats.
     */
	public void displayStats (int remainingLives, int remainingAmmo, boolean isHard) {
		System.out.println("+-------------------------+"); // 25 + 2
		System.out.printf ("|        Lives:  %d        |" +
						 "\n|         Ammo:  %d        |\n",
						 remainingLives, remainingAmmo);
		String difficulty;
		if (!isHard) {
			difficulty = "Easy";
		}
		else {
			difficulty = "Hard";
		}
		System.out.printf("|   Difficulty:  %s     |\n", difficulty);
	}
	
	/**
     * This constructor takes @param playerRow and
     * @param playerCol and displays is it
     */
	public void displayPlayerPosition (int playerRow, int playerCol) {
		System.out.printf("| Player Position: R%d, C%d |\n", playerRow, playerCol);
		System.out.println("+-------------------------+");
	}
	
	/**
     * this @param itemID and depending on what item it is
     * prints the description of the item
     */
	public void displayItemPickUp (int itemID) {
		switch (itemID) {
		case 0: // invincibility
			System.out.println("You encounter a star that throws sparkles on you." +
					"\nIt grants you invincibility for 5 turns.");
			break;
		case 1: // radar
			canSeeBriefcase = true;
			System.out.println("You find a piece of paper on the floor with a code scribbled on it." +
					"\nIt tells you the location of the briefcase!");
			break;
		case 2: // ammo
			System.out.println("You find a bullet on the floor.");
			break;
		}
	}
	
	/**
     * this constructor takes in the  @param inTurnsLeft
     * and displays it to the user
     */
	public void displayInvulnTurns(int inTurnsLeft) {
		System.out.println("Turns invulnerable remaining: " + inTurnsLeft);
	}
	
	/**
     * this constructor takes in @param row and @param col
     * for the user and prints it
     */
	public void displayBriefcaseLocation(int row, int col) {
		System.out.println("Coordinates: R" + row + ", C" + col);
	}
	
	/**
     * take @param remainingAmmo and checks to see if
     * you have ammo or not if not prints out ammo added
     * else prints you can't add more ammo
     */
	public void displayReload (int remainingAmmo) {
		if (remainingAmmo == 0) {
			System.out.println("You reload your gun.");
		}
		else {
			System.out.println("Unfortunately, you're full on ammo." +
					"\nFurthermore, your tailor forgot to install pockets into your suit." +
					"\nThus, you are forced to throw the bullet away.");
		}
	}
	
	/**
     * this method displays if the player killed an enemy or not
     */
	public void displayShootResult() {
		System.out.println("You killed an enemy!\nRemaining Ammo: 0");
	}
	
	/**
     * this method displays when the player can't shoot (when there is no ammo left)
     */
	public void displayNoShoot() {
		System.out.println("You can't shoot.");
	}
	
	/**
     * this method displays if the user didn't hit anything
     */
	public void displayNoHit() {
		System.out.println("You hit nothing.\nRemaining Ammo: 0");
	}
	
	/**
     * this method prints that you can't look again
     * in the same turn
     */
	public void displayNoLook() {
		System.out.println("You have already looked this turn. Please use a different command.");
	}
	
	/**
     * this method displays when you quit the game
     *
     */
	public void displayQuit() {
		System.out.println("You have quit the game.");
	}
	
	public void displayCheckpointSaved(int turn)
	{
	    System.out.println("You have created a checkpoint at turn " + turn + ".");
	}

    public void displayCheckpointLoaded(int turn) {
        System.out.println("You have loaded the most recent checkpoint from turn " + turn + ".");
    }

    public void displayNoCheckpoint() {
        System.out.println("You have not yet saved a checkpoint.");
    }
}
