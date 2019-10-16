package nz.ac.vuw.ecs.swen225.a3.common;

import java.util.List;
import java.util.Set;

/**
 * Contains the maze (board) and metadata about the maze
 * 
 * @author straigfene
 *
 */
public interface MazeState {
  /**
   * Gets the height of the board.
   * 
   * @return the height.
   */
  int getHeight();

  /**
   * Gets the width of the board.
   * 
   * @return the width
   */
  int getWidth();

  /**
   * Gets the total number of chips in the level (chips on board + in inventory).
   * 
   * @return the number of chips
   */
  int getNumChips();

  /**
   * Gets info about the tile at the given position on the board.
   * 
   * @param x
   *          the x coord of the tile
   * @param y
   *          the y coord of the tile
   * @return the data/info about the tile
   */
  TileInfo getTileAt(int x, int y);

  /**
   * Gets the board (a 2D array of tileInfo)
   * 
   * @return the board
   */
  TileInfo[][] getBoard();

  /**
   * Gets info about Chap.
   * 
   * @return the data/info about chap
   */
  ActorInfo getChap();

  /**
   * Gets the inventory of chap.
   * 
   * @return a list of ItemInfo about the items in the inventory
   */
  List<ItemInfo> getInventory();

  /**
   * Gets a list of enemies on the board.
   * 
   * @return a set of ActorInfo about the enemies
   */
  Set<ActorInfo> getEnemies();

  /**
   * Gets a set of all the items on the board items include: keys, chips, etc
   * 
   * @return a set of ItemInfo about the items on the board
   */
  Set<ItemInfo> getItems();

  // /**
  // * Sets the height of the board.
  // * @param height
  // */
  // void setHeight(int height);
  //
  // /**
  // * Sets the width of the board.
  // * @param width
  // */
  // void setWidth(int width);

  /**
   * records the total number of chips in the board.
   * 
   * @param num
   *          -number of chips
   */
  void setNumChips(int num);

  /**
   * Sets the tile at the position on the board.
   * 
   * @param tile
   *          - info about the tile
   * @param x
   *          -the x coord
   * @param y
   *          -the y coord
   */
  void setTileAt(TileInfo tile, int x, int y);

  /**
   * Sets info on Chap (the player character).
   * 
   * @param chap
   *          -info about chap
   */
  void setChap(ActorInfo chap);

  /**
   * Sets the items in the inventory.
   * 
   * @param inventory
   */
  void setInventory(List<ItemInfo> inventory);

  /**
   * Adds an item to the inventory.
   * 
   * @param item
   */
  void addToInventory(ItemInfo item);

  /**
   * Adds an item to the board.
   * 
   * @param item
   */
  void additem(ItemInfo item);

  /**
   * Adds an enemy to the board.
   * 
   * @param enemy
   */
  void addEnemy(ActorInfo enemy);

}
