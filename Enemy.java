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
 * 
 * Pink Panthers
 * Dominique Acuna
 * Rachel Chiang (Captain)
 * Danyel Gil
 * Seongmin Kim
 * Kartik Soni
 */


import java.io.Serializable;
import java.util.*;
/**
 * 
 * This class represents the enemies, the ninjas, who attempt to kill the user, and follow the user
 * if the game is being played in hard mode. It extends the abstract class ActiveAgent. The enemy
 * may be killed as well, by the user with a bullet.
 * 
 * @author Pink Panthers
 *
 */

public class Enemy extends ActiveAgent implements Serializable {
	
	/**
	 * This field represents the enemy's ability to stab the player, which depends on whether the
	 * player is in an adjacent square. It is found in the {@link #Enemy()}, {@link #checkPlayer(int, int)},
	 * and {@link #getStab()} methods.
	 */
	private boolean canStab;
	
	/**
	 * This field represents the enemy's value of whether it has been killed or not. All enemies
	 * begin as not dead in the {@link #Enemy()} constructor, which can be changed in the {@link #dies()}
	 * class. The value can be retrieved in {@link #getDeathVal()}.
	 */
	private boolean isDead;
	
	/**
	 * When the game is set to hard mode, the enemy can switch into pursuit mode, during which
	 * it will follow the player. It can be changed in the {@link #findPlayer(int, int, int, int)}
	 * method and can be retrieved in {@link #getMode()}.
	 */
	private boolean pursuitMode;
	
	/**
	 * This field represents the direction that the enemy was in so that the randomized
	 * {@link #createMoveLocation()} method could be skipped, and we can select directly
	 * where the enemy is going to move. When in hard mode, the GameEngine essentially uses it
	 * in such a way: Enemy.checkDirection(frontDirection). The frontDirection is saved after the
	 * enemy finds the player within two adjacent squares in {@link #findPlayer(int, int, int, int)}.
	 */
	private int frontDirection;
	
	/**
	 * This field represents the row that the Enemy is spawned into, used in {@link #spawnEnemy()}.
	 */
	private int spawnRow;
	
	/**
	 * This field represents the column that the Enemy is spawned into, used in {@link #spawnEnemy()}.
	 */
	private int spawnCol;
	
	/**
	 * This is the constructor. The first is just temporary, as the actual positions will be randomly
	 * generated. The booleans are defaulted at false, and the {@link #frontDirection} at 1.
	 */
	public Enemy() {
		super(0, 0);
		isDead = false;
		canStab = false;
		pursuitMode = false;
		frontDirection = 1;
	}
	
	/**
	 * This method randomly creates locations in which the enemy may be spawned at.
	 * The row must be in [0, 6), whereas the column must be [3, 9), in other words,
	 * the enemy must spawn three squares away from the Player's original spawning point.
	 * It calls the {@link #setPossibleRC(int, int)}.
	 */
	public void spawnPossibleLocation() {
		Random rng = new Random();
		super.setPossibleRC(rng.nextInt(6), rng.nextInt(6)+3); // [0,6), [3,9)
	}
	
	/**
	 * This method actually places the enemies in their spawn point, with the {@link #spawnRow} saving
	 * {@link #getPossibleRow()} and {@link #spawnCol} saving {@link #getPossibleCol()}. The official
	 * positions are saved with the {@link #setPosition(int, int)} method.
	 */
	public void spawnEnemy() {
		spawnRow = super.getPossibleRow();
		spawnCol = super.getPossibleCol();
		super.setPosition(super.getPossibleRow(), super.getPossibleCol());
	}
	
	/**
	 * This method checks all four directly adjacent squares for the player and changes
	 * {@link #canStab} to true if the player is indeed in one of the adjacent squares.
	 * @param playerRow is used to check adjacent positions directly north/up and south/down of the enemy.
	 * @param playerCol is used to check adjacent positions directly east/right and west/left of the enemy.
	 */
	public void checkPlayer(int playerRow, int playerCol) {
		int checkNorth = super.getRow() - 1;
		int checkSouth = super.getRow() + 1;
		int checkEast = super.getCol() + 1;
		int checkWest = super.getCol() - 1;
		// check north
		if (checkNorth == playerRow && super.getCol()==playerCol) {
			canStab=true;
		}
		// check south
		else if (checkSouth == playerRow && super.getCol() == playerCol) {
			canStab=true;
		}
		// check east
		else if (super.getRow() == playerRow && checkEast == playerCol) {
			canStab=true;
		}
		// check west
		else if (super.getRow() == playerRow && checkWest == playerCol) {
			canStab=true;
		}
		// on top of. May be removed later when movement is fixed.
		else if (super.getRow() == playerRow && super.getCol() == playerCol) {
			canStab=true;
		}
	}
	
	/**
	 * This method resets the {@link #canStab} boolean to false.
	 */
	public void resetStab() {
		canStab = false;
	}
	
	/**
	 * This method randomly selects the enemy's direction and sends it as the argument to
	 * {@link #checkDirection(int)}.
	 */
	public void createMoveLocation() {
		Random rng = new Random();
		int direction = rng.nextInt(4) + 1;
		super.checkDirection(direction);
	}
	
	/**
	 * The enemy will check two squares around it, trying to find if the player is
	 * contained within one of those squares or was previously in that square. If either
	 * of those are right, then {@link #pursuitMode} is turned on, and the {@link #frontDirection}
	 * is set to the appropriate value, which will then be used in the GameEngine's enemyMove(int)
	 * method to directly move the enemy. Consequently, the enemy will follow the player until
	 * the line of sight is broken. As it is, the LoS can only be broken with turns around rooms
	 * and if the player is being followed by multiple enemies.
	 * The purpose of checking the previous coordinates of the player is that the LoS is broken way too
	 * easily for it to be considered hard at all had this method only checked for the player's current
	 * position.
	 * @param playerRow is used to check the player's row position is close to that of the enemy
	 * @param playerCol is used to check if the player's column position is close to the enemy's
	 * @param playerOldRow is used to check if the player's previous row is close to that of the enemy.
	 * @param playerOldCol is used to check if the player's previous column is close to the enemy's
	 */
	public void findPlayer(int playerRow, int playerCol, int playerOldRow, int playerOldCol) {
		pursuitMode = false;
		for (int i = 1; i <= 2; i++) {
			if (super.getRow() - i == playerRow && super.getCol() == playerCol) {
				pursuitMode = true;
				frontDirection = 1;
			}
			else if (super.getRow() - i == playerOldRow && super.getCol() == playerOldCol) {
				pursuitMode = true;
				frontDirection = 1;
			}
			
			if (super.getRow() == playerRow && super.getCol() + i == playerCol) {
				pursuitMode = true;
				frontDirection = 2;
			}
			else if (super.getRow() == playerOldRow && super.getCol() + i == playerOldCol) {
				pursuitMode = true;
				frontDirection = 2;
			}
			
			if (super.getRow() == playerRow && super.getCol() - i == playerCol) {
				pursuitMode = true;
				frontDirection = 3;
			}
			else if (super.getRow() == playerOldRow && super.getCol() - i == playerOldCol) {
				pursuitMode = true;
				frontDirection = 3;
			}
			
			if (super.getRow() + i == playerRow && super.getCol() == playerCol) {
				pursuitMode = true;
				frontDirection = 4;
			}
			else if (super.getRow() + i == playerOldRow && super.getCol() == playerOldCol) {
				pursuitMode = true;
				frontDirection = 4;
			}
		}
		
	}
	
	/**
	 * This method sets the {@link #isDead} value to true when the enemy has been shot by the player.
	 */
	public void dies() {
		isDead = true;
	}
	
	/**
	 * This method simply returns the {@link #isDead} value, which determines whether any movement
	 * or actions whatsoever will come from this particular entity.
	 * @return {@link #isDead}
	 */
	public boolean getDeathVal() {
		return isDead;
	}
	
	/**
	 * This method simply returns the {@link #canStab} value, which will either kill the player (if it
	 * is true) or not (if it is false).
	 * @return {@link #canStab}
	 */
	public boolean getStab() {
		return canStab;
	}
	
	/**
	 * This method simply returns the {@link #pursuitMode} value, which will determine whether the enemy
	 * will follow (if true) or not (if false).
	 * @return {@link #pursuitMode}
	 */
	public boolean getMode() {
		return pursuitMode;
	}

	/**
	 * This method simply returns the {@link #frontDirection} integer, which determines which direction the
	 * enemy will move in. This is specifically used to directly move the enemy in the case that
	 * {@link #pursuitMode} is true.
	 * @return {@link #frontDirection}
	 */
	public int getFrontDirection() {
		return frontDirection;
	}
	
	/**
	 * This method simply returns the {@link #spawnRow} of the enemy.
	 * @return {@link #spawnRow}
	 */
	public int getSpawnRow() {
		return spawnRow;
	}
	
	/**
	 * This method simply returns the {@link #spawnCol} of the enemy.
	 * @return {@link #spawnCol}
	 */
	public int getSpawnCol() {
		return spawnCol;
	}

    @Override
    public GameObject makeCopy()
    {
        Enemy copy = new Enemy();
        copy.canStab = this.canStab;
        copy.isDead = this.isDead;
        copy.pursuitMode = this.pursuitMode;
        copy.frontDirection = this.frontDirection;
        copy.setPosition(this.getRow(), this.getCol());
        copy.setOldRowCol(this.getOldRow(), this.getOldCol());
        copy.setPossibleRC(this.getPossibleRow(), this.getPossibleCol());
        return copy;
    }

    @Override
    public void reinstate(GameObject entity)
    {
        this.canStab = ((Enemy) entity).canStab;
        this.isDead = ((Enemy) entity).isDead;
        this.pursuitMode = ((Enemy) entity).pursuitMode;
        this.frontDirection = ((Enemy) entity).frontDirection;
        this.setPosition(((Enemy) entity).getRow(), ((Enemy) entity).getCol());
        this.setOldRowCol(((Enemy) entity).getOldRow(), ((Enemy) entity).getOldCol());
        this.setPossibleRC(((Enemy) entity).getPossibleRow(), ((Enemy) entity).getPossibleCol());
    }
}
