package nz.ac.vuw.ecs.swen225.a3.tests.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.InvalidMazeElementException;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Maze;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.MazeState_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.ActorInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Bug;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Chap;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Enemy;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Chip;
import nz.ac.vuw.ecs.swen225.a3.maze.items.ItemInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Key;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.TileInfo_Impl;

/**
 * Tests for constructing and retrieving information from the MazeState.
 *
 * @author Straigfene
 *
 */
public class MazeStateTests {

  /**
   * Test that a correct description of a tile that contains extra fields is made and that the
   * correct values of these fields can be got back.
   */
  @Test
  public void test_TileInfo_with_fields() {
    Map<String, Object> fields = new HashMap<>();
    fields.put("id", 0);
    fields.put("isOpen", false);
    TileInfo tile = new TileInfo_Impl("LockedDoor", null, fields);

    assertTrue(tile.hasField("isOpen"));
    assertFalse(tile.hasField("invalidField"));

    assertSame(fields.keySet(), tile.getFieldNames());
  }

  /**
   * Test that a correct description of a simple tile that does not contain any extra fields can be
   * constructed
   */
  @Test
  public void test_TileInfo_noFields() {
    TileInfo tile = new TileInfo_Impl("Wall", null, null);

    assertFalse(tile.hasField("isOpen"));
    assertEquals(null, tile.getField("id"));
    assertTrue(tile.getFieldNames().isEmpty());
  }

  /**
   * test that the equals and hashcode methods work
   */
  @Test
  public void test_TileInfo_equals_and_hashCode() {
    Map<String, Object> fields = new HashMap<>();
    fields.put("id", 0);
    fields.put("isOpen", false);
    TileInfo tile = new TileInfo_Impl("LockedDoor", null, fields);
    TileInfo tile2 = new TileInfo_Impl("LockedDoor", null, fields);

    // test equals
    assertTrue(tile.equals(tile));
    assertFalse(tile.equals(null));
    assertFalse(tile.equals(new ItemInfo_Impl("Chip", null, null, null)));
    assertFalse(tile.equals(new TileInfo_Impl("Wall", null, null)));

    // test hashcode
    assertEquals(tile.hashCode(), tile2.hashCode());
    assertEquals(new TileInfo_Impl("Free", null, new HashMap<String, Object>()),
        new TileInfo_Impl("Free", null, null));
  }

  /**
   * Test that you can not make invalid tile data for the MazeState (exception is thrown when you
   * try).
   */
  @Test
  public void test_invalid_TileInfo() {
    try {
      new TileInfo_Impl("InvalidName", null, null);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      new TileInfo_Impl("LockedDoor", null, null);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      Map<String, Object> fields = new HashMap<>();
      fields.put("InvalidField", 3);
      new TileInfo_Impl("LockedDoor", null, fields);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      Map<String, Object> fields = new HashMap<>();
      fields.put("id", "invalid value type");
      new TileInfo_Impl("LockedDoor", null, fields);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      Map<String, Object> fields = new HashMap<>();
      fields.put("id", 0);
      new TileInfo_Impl("LockedDoor", null, fields);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      Map<String, Object> fields = new HashMap<>();
      fields.put("id", 0);
      fields.put("isOpen", "invalid value type");
      new TileInfo_Impl("LockedDoor", null, fields);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      new TileInfo_Impl("ExitDoor", null, null);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      Map<String, Object> fields = new HashMap<>();
      new TileInfo_Impl("ExitDoor", null, fields);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      Map<String, Object> fields = new HashMap<>();
      fields.put("numChipsNeeded", "invalid value type");
      new TileInfo_Impl("ExitDoor", null, fields);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      Map<String, Object> fields = new HashMap<>();
      fields.put("numChipsNeeded", 3);
      new TileInfo_Impl("ExitDoor", null, fields);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      Map<String, Object> fields = new HashMap<>();
      fields.put("numChipsNeeded", 3);
      fields.put("isOpen", "invalid value type");
      new TileInfo_Impl("ExitDoor", null, fields);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }
  }

  /**
   * Test that you can not make invalid item data for the MazeState (exception is thrown when you
   * try).
   */
  @Test
  public void test_invalid_ItemInfo() {
    try {
      new ItemInfo_Impl("InvalidName", null, null, null);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      new ItemInfo_Impl("Key", null, new Point(1, 1), null);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      Map<String, Object> fields = new HashMap<>();
      fields.put("InvalidField", 3);
      new ItemInfo_Impl("Key", null, new Point(1, 1), fields);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      Map<String, Object> fields = new HashMap<>();
      fields.put("id", "invalid value type");
      new ItemInfo_Impl("Key", null, new Point(1, 1), fields);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      new ItemInfo_Impl("Info", null, new Point(1, 1), null);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      Map<String, Object> fields = new HashMap<>();
      fields.put("not text", "rdgat");
      new ItemInfo_Impl("Info", null, new Point(1, 1), fields);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      Map<String, Object> fields = new HashMap<>();
      fields.put("text", 1);
      new ItemInfo_Impl("Info", null, new Point(1, 1), fields);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }
  }

  /**
   * Test that you can not make invalid actor data for the MazeState (exception is thrown when you
   * try).
   */
  @Test
  public void test_invalid_ActorInfo() {
    try {
      new ActorInfo_Impl("InvalidName", null, null, null, null);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      new ActorInfo_Impl("Chap", null, new Point(1, 1), Direction.NORTH, null);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      Map<String, Object> fields = new HashMap<>();
      fields.put("InvalidField", 3);
      new ActorInfo_Impl("Chap", null, new Point(1, 1), Direction.NORTH, fields);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }

    try {
      Map<String, Object> fields = new HashMap<>();
      fields.put("chipsCollected", "invalid value type");
      new ActorInfo_Impl("Chap", null, new Point(1, 1), Direction.NORTH, fields);
      fail("Did not throw InvalidMazeElementException");
    } catch (InvalidMazeElementException e) {
    }
  }

  /**
   * Test that an ActorInfo (description of actor) can be correctly made from the maze.
   */
  @Test
  public void test_valid_ActorInfo() {
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(new TestMazeState());

    maze.getChap().collectChip();
    maze.getChap().collectChip();
    maze.getChap().turn(Direction.NORTH);

    ActorInfo actorInfo = maze.getChap().getInfo();

    String chapString = "ActorInfo_Impl [name=Chap, location=(2, 1), dir=NORTH, fields={chipsCollected=2}]";
    assertEquals(chapString, actorInfo.toString());

    assertSame(null, actorInfo.getField(null));
    assertSame(null, actorInfo.getField("invalid field"));
    assertEquals(2, actorInfo.getField("chipsCollected"));
    assertTrue(actorInfo.hasField("chipsCollected"));
    assertTrue(actorInfo.getFieldNames().contains("chipsCollected"));

    ActorInfo bugInfo = new ActorInfo_Impl("Bug", null, new Point(2, 2), Direction.EAST, null);
    assertFalse(bugInfo.hasField("field"));
    assertSame(null, bugInfo.getField("field"));
    assertEquals(new HashSet<String>(), bugInfo.getFieldNames());
  }

  /**
   * Test that an ItemInfo (description of item) can be correctly made from the maze.
   */
  @Test
  public void test_valid_ItemInfo() {
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(new TestMazeState());
    maze.getChap().turn(Direction.WEST);

    MazeState state = maze.getState();
    ItemInfo keyInfo = null;
    ItemInfo chipInfo = null;
    for (ItemInfo item : state.getItems()) {
      if (item.getName().equals("Key")) {
        keyInfo = item;
      } else if (item.getName().equals("Chip")) {
        chipInfo = item;
      }
    }
    if (keyInfo == null) {
      fail("key info is null");
      return;
    }

    if (chipInfo == null) {
      fail("Chip info is null");
      return;
    }

    String chapString = "ItemInfo_Impl [name=Key, location=(1, 2), fields={id=0}]";
    assertEquals(chapString, keyInfo.toString());

    assertSame(null, keyInfo.getField(null));
    assertSame(null, keyInfo.getField("invalid field"));
    assertEquals(0, keyInfo.getField("id"));
    assertTrue(keyInfo.hasField("id"));
    assertTrue(keyInfo.getFieldNames().contains("id"));

    assertSame(null, chipInfo.getField("id"));
    assertFalse(chipInfo.hasField("id"));
    assertEquals(new HashSet<String>(), chipInfo.getFieldNames());
  }

  /**
   * Test that an asymetrical board can be constructed and the data retrieved to make the maze. Then
   * est the the MazeState can be properly constructed again from the maze.
   */
  @Test
  public void asymetricBoard() {
    MazeState state = new MazeState_Impl(2, 3, 0);

    Map<String, Object> chapFields = new HashMap<>();
    chapFields.put("chipsCollected", 0);
    state.setChap(new ActorInfo_Impl("Chap", null, new Point(0, 1), Direction.SOUTH, chapFields));

    TileInfo wall = new TileInfo_Impl("Wall", null, new HashMap<String, Object>());
    TileInfo free = new TileInfo_Impl("Free", null, new HashMap<String, Object>());

    state.setTileAt(wall, 0, 0);
    state.setTileAt(wall, 1, 0);
    state.setTileAt(free, 0, 1);
    state.setTileAt(free, 1, 1);
    state.setTileAt(wall, 0, 2);
    state.setTileAt(wall, 1, 2);

    Maze maze = new Maze_Impl();
    maze.loadState(state);

    MazeState newState = maze.getState();
  }

  /**
   * Test that the correct MazeState (description of the current state of the maze) can be
   * constructed from the maze.
   */
  @Test
  public void test_making_MazeState_from_maze() {
    MazeState state = makeMazeStateWithItems();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    MazeState newState = maze.getState();

    Maze_Impl newMaze = new Maze_Impl();
    newMaze.loadState(newState);

    assertTrue(newState.equals(state));
  }

  /**
   * test that a simple maze can be loaded from a MazeState.
   *
   * The maze consists of a 4x4 board with wall tiles around the edges and chap. It does not contain
   * any items on the board or in the inventory.
   */
  @Test
  public void test_load_simple_maze() {
    MazeState state = makeSimpleMazeState();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    String boardStr = "Wall	Wall	Wall	Wall	\n" + "Wall	Free	Free	Wall	\n"
        + "Wall	Free	Free	Wall	\n" + "Wall	Wall	Wall	Wall	\n" + "";

    String b = maze.getBoard().toString();
    assertEquals(boardStr, b);

    Chap chap = maze.getChap();

    assertEquals(new Point(1, 1), chap.getLocation());
    assertEquals(Direction.NORTH, chap.getDir());
    assertEquals(1, maze.getNumChips());

  }

  /**
   * test that a maze containing items can be loaded from a MazeState.
   *
   * The maze consists of a 4x4 board with wall tiles around the edges and chap. It has a key a chip
   * and a locked door.
   */
  @Test
  public void test_load_maze_and_items() {
    MazeState state = makeMazeStateWithItems();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    String boardStr = "Wall	Wall	Wall	Wall	\n" + "Wall	Free	Free	Wall	\n"
        + "Wall	Free	LockedDoor	Wall	\n" + "Wall	Wall	Wall	Wall	\n" + "";

    Board b = maze.getBoard();
    assertEquals(boardStr, b.toString());
    assertTrue(b.getItemAt(1, 2) instanceof Key);
    Key key = (Key) b.getItemAt(1, 2);
    assertTrue(key.getId() == 1);
    assertTrue(b.getItemAt(2, 1) instanceof Chip);

    Chap chap = maze.getChap();
    assertEquals(new Point(1, 1), chap.getLocation());
    assertEquals(Direction.EAST, chap.getDir());

    System.out.println(chap.getInventory());
    assertTrue(chap.hasKey(0));

    assertEquals(3, maze.getNumChips());

  }

  /**
   * test that enemies are be loaded from a MazeState correctly.
   */
  @Test
  public void test_load_enemies() {
    MazeState state = new TestMazeState();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    assertTrue(maze.getEnemies().contains(new Bug(maze.getBoard().getTile(4, 3), Direction.WEST)));
  }

  /**
   * Test that the correct TileInfo (description of door) is made for a exitDoor.
   */
  @Test
  public void test_exitDoor_info() {
    MazeState state = new TestMazeState();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    maze.update();

    MazeState newState = maze.getState();

    String tileString = "TileInfo_Impl [name=ExitDoor, fields={isOpen=false, numChipsNeeded=2}]";
    assertEquals(tileString, newState.getTileAt(1, 4).toString());
  }

  /**
   * Test that the program does not crash when trying to get values that do not exist (e.g get
   * enemies when there are none) and when adding values before lists etc have been initialized
   * (lazy initialization).
   */
  @Test
  public void test_getting_and_adding() {
    MazeState state = new MazeState_Impl(5, 5, 3);

    assertEquals(new HashSet<ItemInfo>(), state.getItems());
    assertEquals(new HashSet<ActorInfo>(), state.getEnemies());

    try {
      state.addEnemy(new ActorInfo_Impl("Bug", null, new Point(0, 0), Direction.EAST, null));
      state.additem(new ItemInfo_Impl("Chip", null, new Point(0, 1), null));
      state.addToInventory(new ItemInfo_Impl("Chip", null, new Point(0, 1), null));
    } catch (NullPointerException e) {
      fail("Bug or Chip is Null");
    }

    Map<String, Object> keyFields = new HashMap<>();
    keyFields.put("id", 0);
    ItemInfo keyInfo = new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(), null,
        keyFields);
    state.addToInventory(keyInfo);
    assertTrue(state.getInventory().contains(keyInfo));
  }

  /**
   * Construct a MazeState that contains a 4x4 board with walls around the edges and free tiles in
   * the middle. Put chap on the top left free tile.
   * 
   * @return the MazeState
   */
  public MazeState makeSimpleMazeState() {
    Maze_Impl.init();

    MazeState state = new MazeState_Impl(4, 4, 1);

    // add Chap
    Map<String, Object> chapFields = new HashMap<>();
    chapFields.put("chipsCollected", 0);
    state.setChap(new ActorInfo_Impl("Chap", null, new Point(1, 1), Direction.NORTH, chapFields));

    // add inventory
    state.setInventory(new ArrayList<ItemInfo>());

    // add board/tiles
    TileInfo wall = new TileInfo_Impl("Wall", null, new HashMap<String, Object>());
    TileInfo free = new TileInfo_Impl("Free", null, new HashMap<String, Object>());

    state.setTileAt(wall, 0, 0);
    state.setTileAt(wall, 1, 0);
    state.setTileAt(wall, 2, 0);
    state.setTileAt(wall, 3, 0);
    state.setTileAt(wall, 0, 3);
    state.setTileAt(wall, 1, 3);
    state.setTileAt(wall, 2, 3);
    state.setTileAt(wall, 3, 3);
    state.setTileAt(wall, 0, 1);
    state.setTileAt(wall, 0, 2);
    state.setTileAt(wall, 3, 1);
    state.setTileAt(wall, 3, 2);

    state.setTileAt(free, 1, 1);
    state.setTileAt(free, 1, 2);
    state.setTileAt(free, 2, 1);
    state.setTileAt(free, 2, 2);

    return state;
  }

  /**
   * Construct a more complex MazeState that has the same board except for a door in the bottom
   * right corner. It also adds a key and a chip.
   * 
   * @return the MazeState
   */
  public static MazeState makeMazeStateWithItems() {
    Maze_Impl.init();

    MazeState state = new MazeState_Impl(4, 4, 3);

    // add Chap
    Map<String, Object> chapFields = new HashMap<>();
    chapFields.put("chipsCollected", 2);
    state.setChap(new ActorInfo_Impl("Chap", new ImageIcon("./images/chap_right.png").getImage(),
        new Point(1, 1), Direction.EAST, chapFields));

    // add inventory
    List<ItemInfo> inventory = new ArrayList<ItemInfo>();
    Map<String, Object> key1Fields = new HashMap<>();
    key1Fields.put("id", 0);
    inventory.add(
        new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(), null, key1Fields));
    state.setInventory(inventory);

    // add items
    Map<String, Object> key2Fields = new HashMap<>();
    key2Fields.put("id", 1);
    state.additem(new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(),
        new Point(1, 2), key2Fields));
    state.additem(new ItemInfo_Impl("Chip", new ImageIcon("./images/chip.png").getImage(),
        new Point(2, 1), new HashMap<String, Object>()));

    // add board/tiles
    TileInfo wall = new TileInfo_Impl("Wall", null, new HashMap<String, Object>());
    TileInfo free = new TileInfo_Impl("Free", null, new HashMap<String, Object>());

    Map<String, Object> doorfields = new HashMap<String, Object>();
    doorfields.put("id", 1);
    doorfields.put("isOpen", false);
    TileInfo door = new TileInfo_Impl("LockedDoor", new ImageIcon("./images/door.png").getImage(),
        doorfields);

    state.setTileAt(wall, 0, 0);
    state.setTileAt(wall, 1, 0);
    state.setTileAt(wall, 2, 0);
    state.setTileAt(wall, 3, 0);
    state.setTileAt(wall, 0, 3);
    state.setTileAt(wall, 1, 3);
    state.setTileAt(wall, 2, 3);
    state.setTileAt(wall, 3, 3);
    state.setTileAt(wall, 0, 1);
    state.setTileAt(wall, 0, 2);
    state.setTileAt(wall, 3, 1);
    state.setTileAt(wall, 3, 2);

    state.setTileAt(free, 1, 1);
    state.setTileAt(free, 1, 2);
    state.setTileAt(free, 2, 1);
    state.setTileAt(door, 2, 2);

    return state;
  }

}
