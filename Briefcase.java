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
import java.util.Random;

/**
 * 
 * The Briefcase class represents the briefcase that the player is
 * searching for. All of the fields used represent the random location
 * of the briefcase in the grid.
 * @author PinkPanthers
 *
 */
public class Briefcase implements Serializable {
	Random rand = new Random();
	
	/**
	 * This field represents the briefcase's possible row coordinates. The array provides all
	 * possible options. The value is randomly selected with {@link #rowIdx}, and called in the
	 * constructor {@link #Briefcase()}.
	 */
	private int[] rowArray = {1, 4, 7};
	
	/**
	 * This field randomly selects 0, 1, 2, which represent the integers 1, 4, and 7 in the array
	 * {@link #rowArray}.
	 */
	private int rowIdx = rand.nextInt(rowArray.length);
	
	/**
	 * This field represents the briefcase's row coordinate. The array chooses which coordinate to use
	 * randomly selected by {@link #rowIdx} for the {@link #rowArray}.
	 */
	private int r = rowArray[rowIdx];
	
	/**
	 * This field represents the briefcase's possible column coordinates. The array provides all
	 * possible options. The value is randomly selected with {@link #colIdx}, and called in the
	 * constructor {@link #Briefcase()}.
	 */
	private int[] colArray = {1, 4, 7};
	
	/**
	 * This field randomly selects 0, 1, 2, which represent the integers 1, 4, and 7 in the array
	 * {@link #colArray}.
	 */
	private int colIdx = rand.nextInt(colArray.length);
	
	/**
	 * This field assigns the briefcase's row coordinate. The array chooses which coordinate to use,
	 * which is randomly selected by {@link #colIdx} for the {@link #colArray}.
	 */
	private int c = colArray[colIdx];
	
	/**
	 * The default constructor for the Briefcase class. It assigns values based on the
	 * random selection of the arrays, using all of the fields.
	 */
	public Briefcase() {
		r = rowArray[rowIdx];
		c = colArray[colIdx];
	}
	
	private Briefcase(int row, int col)
	{
	    r = row;
	    c = col;
	}
	
	/**
	 * This method is called in GameEngine for the Map to place the briefcase. It returns
	 * {@link #r} which is the row where the Briefcase will be placed.
	 * @return r It returns the row.
	 */
	public int getRandomRow() {
		return r;
	}
	
	/**
	 * This method is called in the GameEngine for the Map to place the briefcase. It returns
	 * {@link #c} which is the row where the Briefcase will be placed.
	 * @return c It returns the column.
	 */
	public int getRandomCol() {
		return c;
	}
}
