package nz.ac.vuw.ecs.swen225.a3.common;

import java.util.List;
import java.util.Observer;

import nz.ac.vuw.ecs.swen225.a3.maze.items.Item;

/**
 * This acts as an interface between the maze (board and logic of the game) and the rest of the
 * program. Other parts of the program will interact with the maze through this interface and not
 * directly access any parts of the maze.
 * 
 * @author straigfene
 *
 */
public interface Maze {
  // public TileInfo getTile();
  // public void setTile();

  /**
   * Add an observer (Application) to this maze so that it will be notified when events such as win,
   * lose, stand-on-info occur.
   * 
   * @param observer
   *          - the observer (application)
   */
  public void addObserverAppl(Observer observer);

  /**
   * Moves Chap one place in the given direction and makes him interact with whatever is on the tile
   * (e.g pickup key etc.). If the move is invalid (there is a wall) he will not move but will still
   * attempt to interact with the tile (e.g open door).
   * 
   * @param dir
   *          - The direction to move chap in
   */
  public void moveChap(Direction dir);

  /**
   * Loads a level from a given MazeState.
   * 
   * @param mazeState
   *          - the current state of the maze
   */
  public void loadState(MazeState mazeState);

  /**
   * Gets the number of chips still in the maze (that Chap has not picked up)
   * 
   * @return the number of chips
   */
  public int getChipsLeft();
  
  /**
   * Gets the current inventory.
   * @return the inventory
   */
  public List<Item> getInventory();

  /**
   * Gets the contents of the maze and metadata about the maze.
   * 
   * @return a MazeState
   */
  public MazeState getState();

  /**
   * Updates the maze
   */
  // ***Note: this will not be needed until the enemies are implemented***
  public void update();
}