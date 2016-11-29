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
 * 
 * Pink Panthers
 * Dominique Acuna
 * Rachel Chiang (Captain)
 * Danyel Gil
 * Seongmin Kim
 * Kartik Soni
 */


//import java.util.Random;
import java.io.Serializable;

/**
 * 
 * The Map class creates a 9x9 grid. First this grid is made up of empty positions, and next
 * the rooms are positioned at a specific location on the grid. Also places the various items
 * on the grid along with the briefcase and enemies. Both the starting location and vision
 * of the player are set in this class.
 * @author Pink Panthers
 *
 */
public class Map implements Serializable, GameObject {
	
	/**
	 * This field holds the different integer identifiers for all the different possible things that can be
	 * held on the map, including emptiness. It is primarily used for display.
	 * 1 Room, 2 Empty, 3 Enemy, 4 Player, 5-7 Item, 8 Briefcase
	 */
	private int[][] position = new int[9][9];
	
	/**
	 * This field holds boolean values that correspond to those of {@link #position}. If the space is
	 * occupied, it is true, else it is false.
	 */
	private boolean[][] isTaken = new boolean[9][9];
	
	/**
	 * This field holds boolean values for printing the player's vision, which is determined in
	 * {@link #setVision()} and {@link #setPlayerVision(boolean, int)}.
	 */
	private boolean[][] isDark = new boolean[9][9];
	
	/**
	 * These fields hold the player's row and column positions. It is saved in
	 * {@link #placePlayer(int, int)}, and it is used to set the player's vision and for look in
	 * {@link #setPlayerVision(boolean, int)}.
	 */
	private int playerRow, playerCol;
	
	/**
	 * These fields hold the briefcase's row and column positions. It is saved in
	 * {@link #placeBriefCase(int, int)}
	 */
	private int briefCaseRow, briefCaseCol;
	
	/**
	 * This field represents whether there is an item in the given position or not. It is used
	 * in {@link #checkItemPlayerPositions(int, int)} and can be retrieved with {@link #getIsItem()}.
	 */
	private boolean isItem;
	
	/**
	 * This method initially sets all of the values in the {@link #position} array to 2, which is empty.
	 */
	public void emptyPositions() {
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				position[i][j] = 2;
			}
		}
	}
	
	/**
	 * This method uses {@link #position} to create rooms (denoted by the integer 1) in the desired spaces:
	 * with the rows and columns being the combinations of 1, 4, and 7.
	 */
	public void createRooms() {
		for (int i=1; i<8; i+=3) {
			for (int j=1; j<8; j+=3) {
				position[i][j] = 1;
			}
		}
	}
	
	/**
	 * This method saves the integers row and col to {@link #briefCaseRow} and {@link #briefCaseCol},
	 * respectively, and then places the briefcase in {@link #position}, which is denoted by the integer 8.
	 * 
	 * @param row int that corresponds with R(or x)
	 * @param col int that corresponds with C(or y)
	 */
	public void placeBriefCase(int row, int col){
		briefCaseRow = row;
		briefCaseCol = col;
		position[briefCaseRow][briefCaseCol] = 8;
	}
	
	/**
	 * This method breaks up the three items that are placed on the map into three different
	 * cases: invulnerability (5), radar (6), and ammo (7) into {@link #position}.
	 * @param row int that corresponds with R(or x)
	 * @param col int that corresponds with C(or y)
	 * @param itemID int that corresponds with the type of item. (0) invuln, (1) radar, (2) ammo
	 */
	public void placeItem(int row, int col, int itemID) {
		switch (itemID) {
			case 0: // invinc
				position[row][col] = 5;
				break;
			case 1: // radar
				position[row][col] = 6;
				break;
			case 2: // ammo
				position[row][col] = 7;
				break;
		}
	}
	
	/**
	 * The placePlayer method takes the player's coordinates as arguments and uses them
	 * to save it into {@link @playerRow} and {@link #playerCol} and sets the corresponding
	 * coordinates in the {@link #position} array to 4.
	 * @param row int that corresponds with R(or x)
	 * @param col int that corresponds with C(or y)
	 */
	public void placePlayer(int row, int col) {
		playerRow = row;
		playerCol = col;
		position[playerRow][playerCol] = 4;
	}
	
	/**
	 * This method sets the {@link #position} to the enemy's value, 3.
	 * @param row int that corresponds with R(or x)
	 * @param col int that corresponds with C(or y)
	 */
	public void placeEnemy(int row, int col) {
		position[row][col] = 3;
	}
	
	/**
	 * This method uses {@link #emptyPositions()} and {@link #createRooms()} to create the map.
	 */
	public void createMap() {
		emptyPositions();
		createRooms();
	}
	
	/**
	 * This method sets the darkness of the building. If {@link #position} is a room (1), the value of the
	 * {@link #isDark} is always false. It also calls {@link #setPlayerVision(boolean, int)}.
	 * 
	 */
	public void setVision() {
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				if (position[i][j] == 1) {
					isDark[i][j] = false;
				}
				else {
					isDark[i][j] = true;
				}
				setPlayerVision(false, 0);
			}
		}
	}
	
	/**
	 * This method uses the boolean look and int direction to create the appropriate vision. 
	 * If the look is false, then the player has normal vision; that is, the player can only
	 * see 1 space adjacent in all four directions. If look is true, then there is also a direction
	 * 1-4 passed, and it will increase the vision to 1 more square.
	 * 
	 * @param look boolean, determines whether the player chose the action to look
	 * 			ahead one extra space or not
	 * @param direction int, determines the direction the player chose to look in
	 */
	public void setPlayerVision(boolean look, int direction) {
		isDark[playerRow][playerCol] = false;
		if (playerRow+1<9) { // down
			isDark[playerRow+1][playerCol] = false;
			if (look && direction == 4) {
				isDark[playerRow+2][playerCol] = false;
			}
		}
		if (playerRow-1>=0) { // up
			isDark[playerRow-1][playerCol] = false;
			if (look && direction == 1) {
				isDark[playerRow-2][playerCol] = false;
			}
		}
		if (playerCol+1<9) { // right
			isDark[playerRow][playerCol+1] = false;
			if (look && direction == 2) {
				isDark[playerRow][playerCol+2] = false;
			}
		}
		if (playerCol-1>=0) { // left
			isDark[playerRow][playerCol-1] = false;
			if (look && direction == 3) {
				isDark[playerRow][playerCol-2] = false;
			}
		}
	}
	
	/**
	 * This method checks the values of {@link #position} to the fill the {@link #isTaken} array with
	 * boolean values. As long as it is not empty, the value will be true.
	 */
	public void isTaken() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (position[j][i] == 1) { // room
					isTaken[j][i] = true;
				}
				else if (position[j][i] == 2) { // empty
					isTaken[j][i] = false;
				}
				else if (position[j][i] == 3) { // enemy
					isTaken[j][i] = true;
				}
				else if (position[j][i] == 4) { // player
					isTaken[j][i] = true;
				}
				else if (position[j][i] >= 5 && position[j][i] <= 7) { // item
					isTaken[j][i] = true;
				}
				else if (position[j][i] == 8) { // Briefcase
					isTaken[j][i] = true;
				}
			}
		}
	}
	
	/**
	 * This method is used for clearing the previous {@link #position} to empty (2).
	 * 
	 * @param row the desired row position to be cleared
	 * @param col the desired column position
	 */
	public void resetPosition(int row, int col) {
		position[row][col] = 2;
	}
	
	/**
	 * This method uses integers nextPlayerRow and nextPlayerCol to check if a
	 * player is coming up to an item, and if the player is, then {@link #isItem} is set to true.
	 * 
	 * @param nextPlayerRow int used to check the row position
	 * @param nextPlayerCol int used to check the column position
	 */
	public void checkItemPlayerPositions(int nextPlayerRow, int nextPlayerCol) {
		isItem = false;
		if (position[nextPlayerRow][nextPlayerCol] >= 5) {
			isItem = true;
		}
	}
	
	/**
	 * This method returns {@link #position} which is used for the map's display.
	 * 
	 * @return {@link #position}
	 */
	public int[][] getPositionVal() {
		return position;
	}
	
	/**
	 * This method returns the value of the specified position in {@link #position}. This is used
	 * for player movement.
	 * 
	 * @param row int of row
	 * @param col int of column
	 * @return {@link #position}
	 */
	public int getPositionValue(int row, int col) {
		return position[row][col];
	}
	
	/**
	 * This method returns the row that the briefcase is positioned at
	 * 
	 * @return int {@link #briefCaseRow}
	 */
	public int getBriefcaseRow() {
		return briefCaseRow;
	}
	
	/**
	 * This method returns the column that the briefcase is positioned at
	 * 
	 * @return int {@link #briefCaseCol}
	 */
	public int getBriefcaseCol() {
		return briefCaseCol;
	}
	
	/**
	 * This method returns whether a space on the grid is taken by any of the following:
	 * a room, a player, an enemy, the briefcase, and the items.
	 * 
	 * @param row int used to x coordinate in the isTaken array
	 * @param col int used to y coordinate in the isTaken array
	 * @return {@link #isTaken} the value of the corresponding position in the isTaken array
	 */
	public boolean getIsTaken(int row, int col) {
		isTaken();
		return isTaken[row][col];
	}
	
	/**
	 * This method returns whether an item is next to the user, making it so that the user
	 * is able to pick up the item.
	 * 
	 * @return {@link #isItem} value true for when an item is one spot from the player. Otherwise false.
	 */
	public boolean getIsItem() {
		return isItem;
	}
	
	/**
	 * This method returns the set value of the boolean isDark.
	 * 
	 * @return the array {@link #isDark}
	 */
	public boolean[][] getVision() {
		return isDark;
	}

    @Override
    public GameObject makeCopy()
    {
        Map copy = new Map();
        
        copy.position = new int[9][9];
        copy.isTaken = new boolean[9][9];
        copy.isDark = new boolean[9][9];
        
        for (int i = 0; i < position.length; ++i)
        {
            for (int j = 0; j < position.length; ++j)
            {
                copy.position[i][j] = this.getPositionValue(i, j);
                copy.isTaken[i][j] = this.getIsTaken(i, j);
                copy.isDark[i][j] = this.getVision()[i][j];
            }
        }
        
        copy.playerRow = this.playerRow;
        copy.playerCol = this.playerCol;
        copy.briefCaseRow = this.briefCaseRow;
        copy.briefCaseCol = this.briefCaseCol;
        copy.isItem = this.isItem;
        return copy;
    }

    @Override
    public void reinstate(GameObject entity)
    {
        for (int i = 0; i < isTaken.length; ++i)
        {
            for (int j = 0; j < isTaken.length; ++j)
            {
                if (position[i][j] != 1 && position[i][j] != 8)
                {
       //             resetPosition(i, j);
                    this.isTaken[i][j] = ((Map) entity).getIsTaken(i, j);
                    this.position[i][j] = ((Map) entity).getPositionValue(i, j);
                    this.isDark[i][j] = ((Map) entity).getVision()[i][j];
                }
            }
        }
        
        this.playerRow = ((Map) entity).playerRow;
        this.playerCol = ((Map) entity).playerCol;
        this.briefCaseRow = ((Map) entity).briefCaseRow;
        this.briefCaseCol = ((Map) entity).briefCaseCol;
        this.isItem = ((Map) entity).isItem;
    }
	
}
