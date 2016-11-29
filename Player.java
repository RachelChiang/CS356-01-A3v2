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

/**
 * @author Rachel
 *
 */
public class Player extends ActiveAgent implements Serializable {
	
	private int lives;
	private int ammo;
	// Did the bullet connect
		
	public Player() { // The Constructor
		super(8, 0);
		lives = 3;
		ammo = 1;
	}
	
	// Takes player's input (see GE and UI classes)
	// and checks if they're within the grid. If they are, the "temp" position variables
	// are changed
	public void move(int input) {
		super.checkDirection(input);
	}
	
	public void reload() {
		ammo = 1;
	}
	
	public void reduceAmmo() {
		ammo = 0;
	}
	
	public void loseLife() {
		lives -= 1;
	}
	
	public void resetPlayer() {
		super.setPosition(8, 0);
	}
	
	public int getAmmo() {
		return ammo;
	}
	
	public int getLives(){
		return lives; 
	}

    @Override
    public GameObject makeCopy()
    {
        Player copy = new Player();
        copy.lives = this.lives;
        copy.ammo = this.ammo;
        copy.setPosition(this.getRow(), this.getCol());
        copy.setOldRowCol(this.getOldRow(), this.getOldCol());
        copy.setPossibleRC(this.getPossibleRow(), this.getPossibleCol());
        return copy;
    }
	
    @Override
    public void reinstate(GameObject entity)
    {
        this.lives = ((Player) entity).lives;
        this.ammo = ((Player) entity).ammo;
        this.setPosition(((Player) entity).getRow(), ((Player) entity).getCol());
        this.setOldRowCol(((Player) entity).getOldRow(), ((Player) entity).getOldCol());
        this.setPossibleRC(((Player) entity).getPossibleRow(), ((Player) entity).getPossibleCol());
    }
}
