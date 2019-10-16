package nz.ac.vuw.ecs.swen225.a3.tests.persistence;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.ActorInfo_Impl;

/**
 * A class used for testing that implements the MazeState interface.
 *
 * @author cullingene
 *
 */
public class TestMazeState implements MazeState {
  private int height = 0;
  private int width = 0;
  private int numChips = 0;
  private TileInfo[][] board;
  private List<ItemInfo> inventory = new ArrayList<ItemInfo>();
  private Set<ItemInfo> items = new HashSet<ItemInfo>();
  private ActorInfo chap = getChap();
  private Set<ActorInfo> enemies = new HashSet<ActorInfo>();

  /**
   * Constructor for test maze state. Default height and width of 0
   */
  public TestMazeState() {
    this.height = 0;
    this.width = 0;
    board = getBoard();
  }

  /**
   * Constructor for test maze state.
   * 
   * @param height
   *          of test maze state
   * @param width
   *          of test maze state
   */
  public TestMazeState(int height, int width) {
    this.height = height;
    this.width = width;
    board = getBoard();
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
  public void setNumChips(int num) {
    this.numChips = num;
  }

  @Override
  public TileInfo getTileAt(int x, int y) {
    TileInfo tile = board[x][y];
    return tile;
  }

  @Override
  public Set<ItemInfo> getItems() {
    Set<ItemInfo> itemsInfoSet = new HashSet<ItemInfo>();
    return itemsInfoSet;
  }

  @Override
  public ActorInfo getChap() {
    if (chap == null) {
      Point point = new Point(1, 1);
      Direction direction = Direction.EAST;
      Map<String, Object> fields = new HashMap<String, Object>();
      fields.put("chipsCollected", 0);

      ActorInfo actorInfo = new ActorInfo_Impl("Chap", null, point, direction, fields);
      return actorInfo;
    }

    return chap;
  }

  @Override
  public Set<ActorInfo> getEnemies() {
    return enemies;
  }

  @Override
  public void setTileAt(TileInfo tileInfo, int x, int y) {
    board[x][y] = tileInfo;
  }

  @Override
  public void setChap(ActorInfo chap) {
    this.chap = chap;
  }

  @Override
  public void additem(ItemInfo item) {
    items.add(item);
  }

  @Override
  public void addEnemy(ActorInfo enemy) {
    enemies.add(enemy);
  }

  @Override
  public List<ItemInfo> getInventory() {
    return inventory;
  }

  @Override
  public void setInventory(List<ItemInfo> inventory) {
    this.inventory = inventory;
  }

  @Override
  public TileInfo[][] getBoard() {
    // Lazy Initialization
    if (board == null) {
      board = new TileInfo[width][height];
      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          TileInfo tileInfo = new TestTileInfo();
          board[row][col] = tileInfo;
        }
      }
      return board;
    }

    return board;
  }

  @Override
  public void addToInventory(ItemInfo item) {
    inventory.add(item);
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
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {

        String tileString = board[row][col].toString();
        if ((row != 0) && (col != 0)) {
          returnString += ", ";
        }
        returnString += tileString;
      }
    }
    returnString += "}";
    return returnString;
  }

}
