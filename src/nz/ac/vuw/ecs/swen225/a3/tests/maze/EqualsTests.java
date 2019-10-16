package nz.ac.vuw.ecs.swen225.a3.tests.maze;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
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
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.ExitDoor;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Free;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.LockedDoor;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.TileInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Wall;

/**
 * Tests for the equals and hashcode methods for the classes in the maze.
 *
 * @author straigfene
 *
 */
public class EqualsTests {

  /**
   * Test the equals method for the Free class (free tile).
   */
  @Test
  public void freeEquals() {
    Free free = new Free(new Point(0, 0));

    assertFalse(free.equals(null));
    assertFalse(free.equals(new Wall(new Point(0, 0))));

    Free other = new Free(new Point(1, 1));
    other.addItem(new Key(0));
    assertFalse(free.equals(other));

    free.addItem(new Chip());
    assertFalse(free.equals(other));

    free.replaceItem(new Key(0));
    assertFalse(free.equals(other));

    other = new Free(new Point(0, 0));
    other.addItem(new Key(0));
    assertTrue(free.equals(other));

  }

  /**
   * Test the equals method for the ExitDoor class
   */
  @Test
  public void exitDoorEquals() {
    ExitDoor door = new ExitDoor(new Point(0, 0), 4);

    assertTrue(door.equals(door));
    assertFalse(door.equals(null));
    assertFalse(door.equals(new Wall(new Point(0, 0))));

    ExitDoor other = new ExitDoor(new Point(0, 1), 2);
    assertFalse(door.equals(other));

    other = new ExitDoor(new Point(0, 1), 4);
    assertFalse(door.equals(other));

    other = new ExitDoor(new Point(0, 0), 4);
    Chap chap = new Chap(null, 4, null);
    other.interact(chap);
    assertTrue(other.isOpen());
    assertFalse(door.equals(other));

    ExitDoor door2 = new ExitDoor(null, 4);
    other = new ExitDoor(new Point(0, 1), 4);
    assertFalse(door2.equals(other));

    other = new ExitDoor(null, 4);
    assertTrue(door2.equals(other));

    other = new ExitDoor(new Point(0, 0), 4);
    assertTrue(door.equals(other));
  }

  /**
   * Test the hashcode method for the ExitDoor class
   */
  @Test
  public void exitDoorHash() {
    ExitDoor door = new ExitDoor(new Point(1, 0), 4);

    ExitDoor other = new ExitDoor(new Point(0, 1), 4);
    assertFalse(door.hashCode() == other.hashCode());

    other = new ExitDoor(null, 4);
    assertFalse(door.hashCode() == other.hashCode());

    other = new ExitDoor(new Point(1, 0), 4);
    Chap chap = new Chap(null, 4, null);
    other.interact(chap);
    assertTrue(other.isOpen());
    assertFalse(door.hashCode() == other.hashCode());

    other = new ExitDoor(new Point(1, 0), 4);
    assertTrue(door.hashCode() == other.hashCode());
  }

  /**
   * Test the equals method for the LockedDoor class
   */
  @Test
  public void lockedDoorEquals() {
    LockedDoor door = new LockedDoor(new Point(0, 0), 4);

    assertTrue(door.equals(door));
    assertFalse(door.equals(null));
    assertFalse(door.equals(new Wall(new Point(0, 0))));

    LockedDoor other = new LockedDoor(new Point(0, 1), 2);
    assertFalse(door.equals(other));

    other = new LockedDoor(new Point(0, 1), 4);
    assertFalse(door.equals(other));

    other = new LockedDoor(new Point(0, 0), 4);
    Chap chap = new Chap(null, 0, null);
    chap.addToInventry(new Key(4));
    other.interact(chap);
    assertTrue(other.isOpen());
    assertFalse(door.equals(other));

    LockedDoor door2 = new LockedDoor(null, 4);
    other = new LockedDoor(new Point(0, 1), 4);
    assertFalse(door2.equals(other));

    other = new LockedDoor(null, 4);
    assertTrue(door2.equals(other));

    other = new LockedDoor(new Point(0, 0), 4);
    assertTrue(door.equals(other));

  }

  /**
   * Test the hashcode method for the LocedDoor class
   */
  @Test
  public void LockedDoorHash() {
    LockedDoor door = new LockedDoor(new Point(1, 0), 4);

    LockedDoor other = new LockedDoor(new Point(0, 1), 4);
    assertFalse(door.hashCode() == other.hashCode());

    other = new LockedDoor(null, 4);
    assertFalse(door.hashCode() == other.hashCode());

    other = new LockedDoor(new Point(1, 0), 4);
    Chap chap = new Chap(null, 4, null);
    chap.addToInventry(new Key(4));
    other.interact(chap);
    assertTrue(other.isOpen());
    assertFalse(door.hashCode() == other.hashCode());

    other = new LockedDoor(new Point(1, 0), 4);
    assertTrue(door.hashCode() == other.hashCode());
  }

  /**
   * Test the equals method for the Key class
   */
  @Test
  public void KeyEquals() {
    Key key = new Key(3);

    assertTrue(key.equals(key));
    assertFalse(key.equals(null));
    assertFalse(key.equals(new Chip()));
  }

  /**
   * Test the equals method for the ItemInfo class
   */
  @Test
  public void itemInfoEquals() {
    Map<String, Object> keyFields = new HashMap<String, Object>();
    keyFields.put("id", 2);
    ItemInfo key = new ItemInfo_Impl("Key", null, new Point(0, 0), keyFields);
    ItemInfo chip = new ItemInfo_Impl("Chip", null, new Point(1, 0), keyFields);

    assertTrue(key.equals(key));
    assertFalse(key.equals(null));
    assertFalse(key.equals(new Chip()));

    assertFalse(new Chip().equals(chip));

    ItemInfo otherKey = new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(),
        new Point(0, 0), keyFields);
    assertFalse(key.equals(otherKey));

    key = new ItemInfo_Impl("Key", new ImageIcon("./images/chip.png").getImage(), new Point(0, 0),
        keyFields);
    assertFalse(key.equals(otherKey));

    key = new ItemInfo_Impl("Key", null, null, keyFields);
    otherKey = new ItemInfo_Impl("Key", null, new Point(0, 0), keyFields);
    assertFalse(key.equals(otherKey));

    key = new ItemInfo_Impl("Key", null, new Point(1, 0), keyFields);
    assertFalse(key.equals(otherKey));

    assertFalse(key.equals(chip));

    key = new ItemInfo_Impl("Key", null, new Point(1, 0), keyFields);
    otherKey = new ItemInfo_Impl("Key", null, new Point(1, 0), keyFields);
    assertTrue(key.equals(otherKey));
  }

  /**
   * Test the equals method for the Enemy class
   */
  @Test
  public void enemyEquals() {
    Enemy bug = new Bug(null, Direction.EAST);

    assertTrue(bug.equals(bug));
    assertFalse(bug.equals(null));
    assertFalse(bug.equals(new Chip()));

    Enemy other = new Bug(new Free(null), Direction.EAST);
    assertFalse(bug.equals(other));

    other = new Bug(new Free(null), Direction.EAST);
    bug = new Bug(new Wall(null), Direction.WEST);
    assertFalse(bug.equals(other));

    other = new Bug(null, Direction.EAST);
    bug = new Bug(null, Direction.WEST);
    assertFalse(bug.equals(other));
  }

  /**
   * Test the equals method for the ActorInfo class
   */
  @Test
  public void ActorInfoEquals() {
    Map<String, Object> chapFields = new HashMap<String, Object>();
    chapFields.put("chipsCollected", 2);
    ActorInfo chap = new ActorInfo_Impl("Chap", null, new Point(0, 0), Direction.SOUTH, chapFields);
    ActorInfo bug = new ActorInfo_Impl("Bug", null, new Point(1, 0), Direction.SOUTH, chapFields);

    assertTrue(chap.equals(chap));
    assertFalse(chap.equals(null));
    assertFalse(chap.equals(new Chip()));

    assertFalse(new Chip().equals(bug));

    ActorInfo otherChap = new ActorInfo_Impl("Chap", new ImageIcon("./images/key.png").getImage(),
        new Point(0, 0), Direction.SOUTH, chapFields);
    assertFalse(chap.equals(otherChap));

    chap = new ActorInfo_Impl("Chap", new ImageIcon("./images/chip.png").getImage(),
        new Point(0, 0), Direction.SOUTH, chapFields);
    assertFalse(chap.equals(otherChap));

    chap = new ActorInfo_Impl("Chap", null, null, Direction.SOUTH, chapFields);
    otherChap = new ActorInfo_Impl("Chap", null, new Point(0, 0), Direction.SOUTH, chapFields);
    assertFalse(chap.equals(otherChap));

    chap = new ActorInfo_Impl("Chap", null, new Point(1, 0), Direction.SOUTH, chapFields);
    assertFalse(chap.equals(otherChap));

    assertFalse(chap.equals(bug));

    chap = new ActorInfo_Impl("Chap", null, new Point(1, 0), Direction.SOUTH, chapFields);
    otherChap = new ActorInfo_Impl("Chap", null, new Point(1, 0), Direction.EAST, chapFields);
    assertFalse(chap.equals(otherChap));

    otherChap = new ActorInfo_Impl("Chap", null, new Point(1, 0), Direction.SOUTH, chapFields);
    assertTrue(chap.equals(otherChap));

    ActorInfo otherBug = new ActorInfo_Impl("Bug", null, new Point(1, 0), Direction.SOUTH, null);
    assertFalse(otherBug.equals(bug));

    otherBug = new ActorInfo_Impl("Bug", null, new Point(1, 0), Direction.SOUTH,
        new HashMap<String, Object>());
    assertFalse(otherBug.equals(bug));

    Map<String, Object> otherFields = new HashMap<String, Object>();
    otherFields.put("other field", 2);
    otherBug = new ActorInfo_Impl("Bug", null, new Point(1, 0), Direction.SOUTH, otherFields);
    assertFalse(otherBug.equals(bug));

    assertFalse(chap.equals(bug));
  }

  /**
   * Test the equals method for the Enemy class.
   */
  @Test
  public void TestEnemyEquals() {
    Bug enemy = new Bug(null, null);
    Bug other = new Bug(null, null);
    other.setName(null);

    // test with diff name
    assertFalse(other.equals(enemy));
    assertFalse(enemy.equals(other));

    enemy.setName(null);
    assertTrue(enemy.equals(other));

    // test with diff img path
    enemy.setName("Bug");
    other.setName("Bug");
    other.setImgPath(null);
    assertFalse(other.equals(enemy));
    assertFalse(enemy.equals(other));

    enemy.setImgPath(null);
    assertTrue(enemy.equals(other));
  }

  /**
   * Test the equals method for the ActorInfo class
   */
  @Test
  public void MazeStateEquals() {

    MazeState state = makeMazeStateWithItems();

    assertTrue(state.equals(state));
    assertFalse(state.equals(null));
    assertFalse(state.equals("string"));

    MazeState_Impl other = new MazeState_Impl(3, 3, 3);
    assertFalse(state.equals(other));

    other = (MazeState_Impl) makeMazeStateWithItems();

    other.setChap(null);
    assertFalse(other.equals(state));

    state.setChap(null);
    assertTrue(state.equals(other));

    other = (MazeState_Impl) makeMazeStateWithItems();
    state = (MazeState_Impl) makeMazeStateWithItems();

    Map<String, Object> chapFields = new HashMap<>();
    chapFields.put("chipsCollected", 3);
    other.setChap(new ActorInfo_Impl("Chap", new ImageIcon("./images/chap.png").getImage(),
        new Point(1, 1), Direction.SOUTH, chapFields));
    assertFalse(state.equals(other));

    other = (MazeState_Impl) makeMazeStateWithItems();

    state.addEnemy(new ActorInfo_Impl("Bug", new ImageIcon("./images/bug.png").getImage(),
        new Point(1, 2), Direction.EAST, null));
    state.addEnemy(new ActorInfo_Impl("Bug", new ImageIcon("./images/bug.png").getImage(),
        new Point(2, 2), Direction.WEST, null));

    other.addEnemy(new ActorInfo_Impl("Bug", new ImageIcon("./images/bug.png").getImage(),
        new Point(1, 2), Direction.EAST, null));
    other.addEnemy(new ActorInfo_Impl("Bug", new ImageIcon("./images/bug.png").getImage(),
        new Point(2, 2), Direction.NORTH, null));

    assertFalse(state.equals(other));

    other = new MazeState_Impl(5, 5, 3);
    assertFalse(state.equals(other));

    other = new MazeState_Impl(5, 4, 3);
    assertFalse(state.equals(other));

    other = new MazeState_Impl(4, 4, 6);
    assertFalse(state.equals(other));

    other = (MazeState_Impl) makeMazeStateWithItems();
    state = (MazeState_Impl) makeMazeStateWithItems();

    Map<String, Object> key1Fields = new HashMap<>();
    key1Fields.put("id", 4);
    other.addToInventory(
        new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(), null, key1Fields));

    assertFalse(state.equals(other));

    other = (MazeState_Impl) makeMazeStateWithItems();
    other.additem(new ItemInfo_Impl("Chip", new ImageIcon("./images/chip.png").getImage(),
        new Point(3, 3), new HashMap<String, Object>()));

    assertFalse(state.equals(other));

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
    state.setChap(new ActorInfo_Impl("Chap", new ImageIcon("./images/chap.png").getImage(),
        new Point(1, 1), Direction.SOUTH, chapFields));

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
