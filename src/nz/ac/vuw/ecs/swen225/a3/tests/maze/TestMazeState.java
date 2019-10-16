package nz.ac.vuw.ecs.swen225.a3.tests.maze;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;

import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.ActorInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.items.ItemInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.TileInfo_Impl;

/**
 * A premade maze used for testing the game logic (maze package).
 *
 * @author straigfene
 *
 */
public class TestMazeState implements MazeState {

  private int height = 6;
  private int width = 6;
  private int numChips = 2;

  private TileInfo[][] board;
  private ActorInfo chap;
  private List<ItemInfo> inventory;
  private Set<ItemInfo> items;
  private Set<ActorInfo> enemies;

  /**
   * Construct a maze that has a chip sealed off in a small room with a bug walking in front of the
   * door. It also contains a key to open the door and an information item. There is also a exit
   * that is behind an exit door.
   */
  public TestMazeState() {
    Maze_Impl.init();
    // make chap
    Map<String, Object> chapFields = new HashMap();
    chapFields.put("chipsCollected", 0);
    chap = new ActorInfo_Impl("Chap", new ImageIcon("./images/chap.png").getImage(),
        new Point(2, 1), Direction.SOUTH, chapFields);

    inventory = new ArrayList<ItemInfo>();

    // make items
    items = new HashSet<ItemInfo>();
    Map<String, Object> keyFields = new HashMap();
    keyFields.put("id", 0);
    items.add(new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(),
        new Point(1, 2), keyFields));

    items.add(new ItemInfo_Impl("Chip", new ImageIcon("./images/chip.png").getImage(),
        new Point(4, 1), null));
    items.add(new ItemInfo_Impl("Chip", new ImageIcon("./images/chip.png").getImage(),
        new Point(3, 4), null));

    items.add(new ItemInfo_Impl("Exit", new ImageIcon("./images/exit.png").getImage(),
        new Point(1, 5), null));

    Map<String, Object> infoFields = new HashMap();
    infoFields.put("text", "some example info text");
    items.add(new ItemInfo_Impl("Info", new ImageIcon("./images/info.png").getImage(),
        new Point(1, 1), infoFields));

    // make enemies
    enemies = new HashSet<ActorInfo>();
    enemies.add(new ActorInfo_Impl("Bug", new ImageIcon("./images/???").getImage(), new Point(4, 3),
        Direction.WEST, null));

    // make board
    TileInfo wall = new TileInfo_Impl("Wall", null, new HashMap<String, Object>());
    TileInfo free = new TileInfo_Impl("Free", null, new HashMap<String, Object>());

    Map<String, Object> doorFields = new HashMap();
    doorFields.put("id", 0);
    doorFields.put("isOpen", false);
    TileInfo door = new TileInfo_Impl("LockedDoor", new ImageIcon("./images/door.png").getImage(),
        doorFields);

    Map<String, Object> exitFields = new HashMap();
    exitFields.put("numChipsNeeded", 2);
    exitFields.put("isOpen", false);
    TileInfo exit = new TileInfo_Impl("ExitDoor", new ImageIcon("./images/exitdoor.png").getImage(),
        exitFields);

    TileInfo[][] board = { { wall, wall, wall, wall, wall, wall },
        { wall, free, free, wall, free, wall }, { wall, free, free, wall, door, wall },
        { wall, free, free, free, free, wall }, { wall, exit, wall, free, free, wall },
        { wall, free, wall, wall, wall, wall } };

    this.board = board;

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
  public TileInfo getTileAt(int x, int y) {
    return board[y][x];
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
  public List<ItemInfo> getInventory() {
    return inventory;
  }

  @Override
  public Set<ActorInfo> getEnemies() {
    return enemies;
  }

  @Override
  public Set<ItemInfo> getItems() {
    return items;
  }

  // @Override
  // public void setHeight(int height) {}
  //
  // @Override
  // public void setWidth(int width) {}

  @Override
  public void setNumChips(int num) {
  }

  @Override
  public void setTileAt(TileInfo tile, int x, int y) {
  }

  @Override
  public void setChap(ActorInfo chap) {
  }

  @Override
  public void setInventory(List<ItemInfo> inventory) {
  }

  @Override
  public void addToInventory(ItemInfo item) {
  }

  @Override
  public void additem(ItemInfo item) {
  }

  @Override
  public void addEnemy(ActorInfo enemy) {
  }

}
