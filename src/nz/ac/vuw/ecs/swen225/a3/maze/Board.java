package nz.ac.vuw.ecs.swen225.a3.maze;

import java.awt.Point;
import java.util.Arrays;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Chap;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Item;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Free;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Tile;

/**
 * The current Board, it contains the tiles and items in the level.
 *
 * @author straigfene 300373183
 *
 */
public class Board {

  private Tile[][] board;
  private int width;
  private int height;

  /**
   * Constructor. Takes the width t=and height and initializes an empty board but does not fill it.
   *
   * @param width
   *          -the width of the board
   * @param height
   *          -the height of the board
   */
  public Board(int width, int height) {
    this.width = width;
    this.height = height;
    board = new Tile[width][height];
  }

  /**
   * Gets the width of the board
   *
   * @return the width
   */
  public int getWidth() {
    return width;
  }

  /**
   * Gets the height of the board.
   *
   * @return the height
   */
  public int getHeight() {
    return height;
  }

  /**
   * Gets the tile at a position.
   *
   * @param x
   *          -x coord of the tile
   * @param y
   *          -y coord of the tile
   * @return the tile or null if there is not tile at that position
   */
  public Tile getTile(int x, int y) {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      return null;
    }

    return board[x][y];
  }

  /**
   * Gets the item at a position.
   *
   * @param x
   *          -x coord of the item
   * @param y
   *          -y coord of the item
   * @return the Item at the given position
   */
  public Item getItemAt(int x, int y) {
    if (!(board[x][y] instanceof Free)) {
      return null;
    } else {
      Free free = (Free) board[x][y];
      return free.getItem();
    }
  }

  /**
   * Add an item that chap can interact with to the board. Does not replace items if there is
   * already an item at the given location and items can not be placed on walls or doors.
   *
   * @param item
   *          -The item to be added
   * @param x
   *          -x coord of the position to put the item
   * @param y
   *          -y coord of the position to put the item
   * @return false if the item could not be added or true if it was added.
   */
  public boolean addItem(Item item, int x, int y) {
    if (item == null) {
      throw new IllegalArgumentException("item is null");
    }
    if (x < 0 || x >= width || y < 0 || y >= height) {
      return false;
    }

    // can only add items to Free tiles
    Tile tile = board[x][y];
    if (!(tile instanceof Free)) {
      return false;
    }

    Free free = (Free) tile;
    return free.addItem(item);
  }

  /**
   * puts a tile on the board (using the position in the tile) and connects it to the tiles already
   * there.
   *
   * @param tile
   *          -the new tile
   */
  public void setTile(Tile tile) {
    // preconditions
    if (tile == null) {
      throw new IllegalArgumentException("tile is null");
    }
    if (tile.getLocation() == null) {
      throw new IllegalArgumentException("tile location is null");
    }

    int x = tile.getLocation().x;
    int y = tile.getLocation().y;

    if (x < 0 || x >= width || y < 0 || y >= height) {
      throw new IllegalArgumentException("tile out of bounds");
    }

    // set tile
    board[x][y] = tile;

    // connect tile to those already on board
    if (y - 1 >= 0) {
      tile.connectNorth(board[x][y - 1]);
    }
    if (y + 1 < height) {
      tile.connectSouth(board[x][y + 1]);
    }
    if (x - 1 >= 0) {
      tile.connectWest(board[x - 1][y]);
    }
    if (x + 1 < width) {
      tile.connectEast(board[x + 1][y]);
    }
  }

  /**
   * Fill the board with tiles and items described by a MazeState object.
   *
   * @param mazeState
   *          -the description of the board
   */
  public void load(MazeState mazeState) {
    if (mazeState == null) {
      throw new IllegalArgumentException("mazeState is null");
    }

    // make board - add tiles to board
    for (int row = 0; row < mazeState.getHeight(); row++) {
      for (int col = 0; col < mazeState.getWidth(); col++) {
        Tile tile = Tile.makeTile(mazeState.getTileAt(col, row), new Point(col, row));
        setTile(tile);
      }
    }

    // put items on board
    for (ItemInfo itemInfo : mazeState.getItems()) {
      Item item = Item.makeItem(itemInfo);
      addItem(item, itemInfo.getLocation().x, itemInfo.getLocation().y);
    }

  }

  /**
   * Add info on the current state of this board to the MazeState. board info includes the tiles and
   * items.
   *
   * @param state
   *          - the state of the maze
   */
  public void addBoardInfoToSate(MazeState state) {
    if (state == null) {
      throw new IllegalArgumentException("state is null");
    }

    for (int row = 0; row < width; row++) {
      for (int col = 0; col < height; col++) {

        // add tile
        state.setTileAt(board[row][col].getInfo(), row, col);

        // add item (if it exists)
        if (board[row][col] instanceof Free) {
          Free tile = (Free) board[row][col];
          if (tile.containsItem()) {
            state.additem(tile.getItem().getInfo(new Point(row, col)));
          }
        }
      }
    }

  }

  /**
   * Makes a string representation on the board (a grid of the names of the tiles).
   *
   * @return string representation
   */
  public String toString() {
    StringBuilder strBoard = new StringBuilder();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        strBoard.append(board[col][row].getName() + "\t");
      }
      strBoard.append("\n");
    }

    return strBoard.toString();
  }

}
