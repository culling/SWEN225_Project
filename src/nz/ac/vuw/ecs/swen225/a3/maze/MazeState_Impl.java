package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;

/**
 * Implements the MazeState interface and describes the state of a maze (a level) including the
 * board, items, chap, and enemies.
 *
 * @author straigfene
 *
 */
public class MazeState_Impl implements MazeState {
  private int height;
  private int width;
  private int numChips;

  private TileInfo[][] board;
  private ActorInfo chap;
  private List<ItemInfo> inventory;
  private Set<ItemInfo> items;
  private Set<ActorInfo> enemies;

  /**
   * Constructor.
   * 
   * @param width
   *          -the width of the board
   * @param height
   *          -the height of the board
   * @param numChips
   *          -the total number of chips in the level
   */
  public MazeState_Impl(int width, int height, int numChips) {
    if (width <= 0) {
      throw new IllegalArgumentException("invalid width");
    }
    if (height <= 0) {
      throw new IllegalArgumentException("invalid height");
    }

    this.height = height;
    this.width = width;
    this.numChips = numChips;
    board = new TileInfo[width][height];
    items = new HashSet<ItemInfo>();
    enemies = new HashSet<ActorInfo>();
    inventory = new ArrayList<ItemInfo>();
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getNumChips() {
    return numChips;
  }

  @Override
  public List<ItemInfo> getInventory() {
    return inventory;
  }

  @Override
  public TileInfo getTileAt(int x, int y) {
    return board[x][y];
  }

  @Override
  public TileInfo[][] getBoard() {
    return board;
  }

  @Override
  public ActorInfo getChap() {
    return chap;
  }

  @Override
  public Set<ActorInfo> getEnemies() {
    return enemies;
  }

  @Override
  public Set<ItemInfo> getItems() {
    return items;
  }

  @Override
  public void setNumChips(int num) {
    numChips = num;
  }

  /**
   * Sets the board. For testing only.
   * 
   * @param b
   *          the new board
   */
  public void setBoard(TileInfo[][] b) {
    board = b;
  }

  @Override
  public void setTileAt(TileInfo tile, int x, int y) {
    // make sure tile is not null

    if (tile == null) {
      throw new IllegalArgumentException("tile is null");
    }

    // make sure x and y are on board
    if (x < 0 || x >= width || y < 0 || y >= height) {
      throw new IllegalArgumentException("position is out of bounds");
    }

    board[x][y] = tile;
  }

  @Override
  public void setInventory(List<ItemInfo> inventory) {
    // check not null
    if (inventory == null) {
      throw new IllegalArgumentException("inventory is null");
    }

    this.inventory = inventory;
  }

  @Override
  public void addToInventory(ItemInfo item) {
    // check item not null
    if (item == null) {
      throw new IllegalArgumentException("item is null");
    }

    inventory.add(item);
  }

  // TODO make copies of arguments

  @Override
  public void additem(ItemInfo item) {
    // check item is not null
    if (item == null) {
      throw new IllegalArgumentException("item is null");
    }

    items.add(item);
  }

  @Override
  public void setChap(ActorInfo chap) {
    this.chap = chap;
  }

  @Override
  public void addEnemy(ActorInfo enemy) {
    // check enemy is not null
    if (enemy == null) {
      throw new IllegalArgumentException("enemy is null");
    }

    enemies.add(enemy);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.deepHashCode(board);
    result = prime * result + ((chap == null) ? 0 : chap.hashCode());
    result = prime * result + (enemies.hashCode());
    result = prime * result + height;
    result = prime * result + (inventory.hashCode());
    result = prime * result + (items.hashCode());
    result = prime * result + numChips;
    result = prime * result + width;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MazeState_Impl other = (MazeState_Impl) obj;
    if (height != other.height)
      return false;
    if (width != other.width)
      return false;
    if (numChips != other.numChips)
      return false;
    if (!Arrays.deepEquals(board, other.board))
      return false;
    if (chap == null) {
      if (other.chap != null)
        return false;
    } else if (!chap.equals(other.chap))
      return false;
    if (!enemies.equals(other.enemies))
      return false;
    if (!inventory.equals(other.inventory))
      return false;
    if (!items.equals(other.items)) {
      // List<ItemInfo> itemList = new ArrayList<ItemInfo>();
      // itemList.addAll(items);
      // List<ItemInfo> oItemList = new ArrayList<ItemInfo>();
      // oItemList.addAll(other.items);
      // System.out.println(itemList.get(0).equals(oItemList.get(0)));
      // System.out.println(itemList.get(1).equals(oItemList.get(1)));

      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "MazeState_Impl [height=" + height + ", width=" + width + ", numChips=" + numChips
        + ", board=" + boardString() + ", chap=" + chap + ", inventory=" + inventory + ", items="
        + items + ", enemies=" + enemies + "]";
  }

  private String boardString() {
    String returnString = new String();
    returnString += "{";
    /*
     * for(int row = 0; row < height; row++) { for(int col = 0; col < width; col++) {
     *
     * String tileString = board[row][col].toString(); if((row != 0 ) && (col != 0)) { returnString
     * += ", "; } returnString += tileString; } }
     */
    returnString += "}";
    return returnString;
  }

}
