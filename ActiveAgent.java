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
 * (methods) and attributes (field) that make up each class. This includes 
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

/**
 * This abstract class represents the active entities of the game.
 * It is used by the Enemy and Player classes. Its purposes are in
 * position settings and movement.
 */
public abstract class ActiveAgent implements GameObject {
	
	/**
	 * This field represents the position in the row of the object, which
	 * will eventually be used for interactions (killing, moving) and for
	 * the map. It is in the {@link #ActiveAgent(int, int)} constructor,
	 * {@link #setPosition(int, int)}, and {@link #getRow()} methods.
	 */
	private int rowPosition;
	
	/**
	 * This field represents the position in the column of the object, which
	 * will eventually be used for interactions (killing, moving) and for
	 * the map. It is in the {@link #ActiveAgent(int, int)} constructor,
	 * {@link #setPosition(int, int)}, and {@link #getCol()} methods.
	 */
	private int colPosition;
	
	/**
	 * This represents the previous {@link #rowPosition} of the object. It has
	 * two functions: (a) To reset the previous map coordinate to empty
	 * 				  (b) As a "breadcrumb" trail for enemies to look for when in Pursuit
	 * It is used in the {@link #setPosition(int, int)}, {@link #adjustTemps(int, int)},
	 * and {@link #getOldRow()} methods.
	 */
	private int previousRow;
	
	/**
	 * This represents the previous {@link #colPosition} of the object. It has
	 * two functions: (a) To reset the previous map coordinate to empty
	 * 				  (b) As a "breadcrumb" trail for enemies to look for when in Pursuit
	 * It is used in the {@link #setPositions(int, int)}, {@link #adjustTemps(int, int)},
	 * and {@link #getOldCol()} methods.
	 */
	private int previousCol;
	
	/**
	 * This represents the possible future {@link #rowPosition} of the object.
	 * It is meant to help determine if it will be okay to be placed there, in  
	 * {@link #isChangeInRange()}. It is set during  {@link #setPossibleRC(int, int)} and
	 * {@link #adjustTemps(int, int)}. It can be retrieved by {@link #getPossibleRow()}.
	 */
	private int possibleRow;
	
	/**
	 * This represents the possible future {@link #colPosition} of the object.
	 * It is meant to help determine if it will be okay to be placed there, in
	 * {@link #isChangeInRange()}. It is set during {@link #setPossibleRC(int, int)} and
	 * {@link #adjustTemps(int, int)}. It can be retrieved by {@link #getPossibleCol()}.
	 */
	private int possibleCol;
	
	/**
	 * When the value is true, the object can be placed in {@link #possibleRow} and
	 * {@link #possibleCol}. If the value is false, the object cannot be placed there.
	 * It is used to check whether the possible positions are within the grid in the
	 * {@link #isChangeInRange()} method.
	 */
	private boolean isChangeInRange;
	
	/**
	 * This is the constructor, which places the object in the appropriate position according
	 * to the passed arguments.
	 * @param row will be saved as the {@link #rowPosition}, which is the object's position on the row.
	 * @param col will be saved as the {@link #colPosition}, which is the object's position on the column.
	 */
	public ActiveAgent(int row, int col) {
		rowPosition = row;
		colPosition = col;
	}
	
	/**
	 * This method sets the position of the objects. First, the {@link #rowPosition} is saved to
	 * the {@link #previousRow} and the {@link #colPosition} is saved to the {@link #previousCol},
	 * which is necessary to empty the old space, and for the player, leave a trail. The passed arguments
	 * then replace the old values for the {@link #rowPosition} and {@link #colPosition}.
	 * @param row replaces the {@link #rowPosition}.
	 * @param col replaces the {@link #colPosition}.
	 */
	public void setPosition(int row, int col) {
		previousRow = rowPosition;
		previousCol = colPosition;
		rowPosition = row;
		colPosition = col;
	}
	
	/**
	 * This method sets the possible positions of the object. This is specifically for the Enemy class,
	 * to ensure that it doesn't spawn on top of another entity (such as an ally or other). 
	 * @param row is randomly generated and then placed into {@link #possibleRow}.
	 * @param col is randomly generated and then placed into {@link #possibleCol}.
	 */
	public void setPossibleRC(int row, int col) {
		possibleRow = row;
		possibleCol = col;
	}
	
	// Takes given input for direction and sends information
	// to adjustTemps(row, col) to change the "temps"
	// (previous/possible) positions and checks if the change
	// is within the grid [0, 9) and [0, 9)
	/**
	 * This method takes a given integer input for the direction, and subsequently sends the information
	 * to {@link #adjustTemps(int, int)} to change the "temporary" fields {@link #previousRow}, {@link
	 * #previousCol}, {@link #possibleRow}, and {@link #possibleCol}.
	 * @param input is the direction in which the object desires to move in. If the input is 1, it tries to
	 * 			move up; if the input is 2, it tries to move right; if the input is 3, left; if 4, down.
	 */
	public void checkDirection(int input) {
		switch (input) {
			case 1: // up
				adjustTemps(-1, 0);
				break;
			case 2: // right
				adjustTemps( 0, 1);
				break;
			case 3: // left
				adjustTemps( 0,-1);
				break;
			default: // down
				adjustTemps( 1, 0);
		}
	}
	
	/**
	 * This method is passed by the {@link #checkDirection(int)} method. The arguments
	 * for row and col will be 1, 0, or -1, which corresponds to the direction. This method
	 * will change the fields {@link #previousRow}, {@link #previousCol}, {@link #possibleRow},
	 * and {@link #possibleCol}. It also initializes {@link isChangeInRange} to true, then verifies
	 * the boolean in {@link #isChangeInRange()}.
	 * @param row refers to the vertical adjustment of the {@link #rowPosition}.
	 * @param col refers to the horizontal adjustment of the {@link #colPosition}.
	 */
	public void adjustTemps(int row, int col) {
		previousRow = rowPosition;
		previousCol = colPosition;
		possibleRow = rowPosition + row;
		possibleCol = colPosition + col;
		isChangeInRange = true;
		isChangeInRange();
	}
	
	/**
	 * This method checks if the possible new coordinates, {@link #possibleRow} and {@link #possibleCol},
	 * are within the grid--[0,9) for both. If they are, then it sets {@link #isChangeInRange} to true;
	 * otherwise, it sets {@link #isChangeInRange} to false.
	 */
	public void isChangeInRange() {
		if (possibleRow < 9 && possibleCol < 9 && possibleRow >= 0 && possibleCol >= 0) {
			isChangeInRange = true; // if it's within grid, set to true
		}
		else {
			isChangeInRange = false; // if not within grid, set to false
		}
	}
	
	public void setOldRowCol(int row, int col)
	{
	    previousRow = row;
	    previousCol = col;
	}
	
	/**
	 * This method simply returns the {@link #rowPosition} of the object. In other words, the
	 * position on the row of the grid, the vertical coordinate.
	 * @return {@link #rowPosition}
	 */
	public int getRow() {
		return rowPosition;
	}
	
	/**
	 * This method simply returns the {@link #colPosition} of the object. In other words, the
	 * position on the column of the grid, the horizontal coordinate.
	 * @return {@link #colPosition}
	 */
	public int getCol() {
		return colPosition;
	}
	
	/**
	 * This method simply returns the {@link #previousRow} of the object. In other words, the
	 * old position on the row of the grid, the old vertical coordinate. It is used in emptying
	 * the spot and also, for the player, in leaving a trail for the enemies to follow.
	 * @return {@link #previousRow}
	 */
	public int getOldRow() {
		return previousRow;
	}
	
	/**
	 * This method simply returns the {@link #previousCol} of the object. In other words, the old
	 * position on the column of the grid, the old horizontal coordinate. It is used in emptying
	 * the square and also, for the player, in leaving a trail for enemies to follow.
	 * @return {@link #previousCol}
	 */
	public int getOldCol() {
		return previousCol;
	}
	
	/**
	 * This method simply returns the {@link #possibleRow} of the object. In other words, the potential
	 * future position of the row of the grid. It is used in verifying if it's okay to move there according
	 * to the Map.
	 * @return {@link #possibleRow}
	 */
	public int getPossibleRow() {
		return possibleRow;
	}
	
	/**
	 * This method simply returns the {@link #possibleCol} of the object. In other words, the potential
	 * future position on the column of the grid. It is used in verifying if it's okay to move there
	 * according to the Map.
	 * @return {@link #possibleCol}
	 */
	public int getPossibleCol() {
		return possibleCol;
	}
	
	/**
	 * This method simply returns the {@link #isChangeInRange} boolean.
	 * @return {@link #isChangeInRange}
	 */
	public boolean getIsChangeInRange() {
		return isChangeInRange;
	}
}
