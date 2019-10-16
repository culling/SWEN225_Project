package nz.ac.vuw.ecs.swen225.a3.tests.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.MazeState_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.ActorInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Bug;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Chap;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Enemy;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Chip;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Exit;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Info;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Item;
import nz.ac.vuw.ecs.swen225.a3.maze.items.ItemInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Key;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Pickupable;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Door;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.ExitDoor;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Free;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.LockedDoor;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.TileInfo_Impl;

/**
 * Tests for the maze part of the program (game logic).
 *
 * @author straigfene 300373183
 */
public class MazeTests {

  /**
   * Test that the toString method in the Board returns an accurate representation of the tiles on
   * the board.
   */
  @Test
  public void test_Board_toString() {
    MazeState state = makeSimpleMazeState();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    String boardStr = "Wall	Wall	Wall	Wall	\n" + "Wall	Free	Free	Wall	\n"
        + "Wall	Free	Free	Wall	\n" + "Wall	Wall	Wall	Wall	\n" + "";

    String b = maze.getBoard().toString();
    assertEquals(boardStr, b);
  }

  /**
   * Test that chap can make simple valid moves into free tiles.
   */
  @Test
  public void test_move_Chap_1() {
    MazeState state = makeSimpleMazeState();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    // move chap east then south
    maze.moveChap(Direction.EAST);
    maze.moveChap(Direction.SOUTH);

    // check chap is now in new location
    assertEquals(new Point(2, 2), maze.getChap().getLocation());
  }

  /**
   * Test that chap does not move onto wall tiles.
   */
  @Test
  public void test_move_Chap_2() {
    MazeState state = makeSimpleMazeState();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    maze.moveChap(Direction.SOUTH);
    maze.moveChap(Direction.SOUTH); // attempt to move onto wall
    maze.moveChap(Direction.WEST); // attempt to move onto wall
    maze.moveChap(Direction.EAST);

    // check chap is now in new location
    assertEquals(new Point(2, 2), maze.getChap().getLocation());
  }

  /**
   * Test that chap picks up a key if he walks onto a cell containing the key and that the key goes
   * into chaps inventory.
   */
  @Test
  public void test_pickup_key() {
    MazeState state = makeSimpleMazeState();
    Map<String, Object> keyFields = new HashMap<>();
    keyFields.put("id", 1);
    state.additem(new ItemInfo_Impl("Key", null, new Point(1, 2), keyFields));
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    // move chap to tile containing key
    maze.moveChap(Direction.SOUTH);
    maze.moveChap(Direction.EAST);

    assertTrue(maze.getChap().hasKey(1));
    Free keyTile = (Free) maze.getBoard().getTile(1, 2);
    assertFalse(keyTile.containsItem());
  }

  /**
   * test that chap picks up a chip if he walks onto a cell containing it and that it is added to
   * the total number of chips that Chap has.
   */
  @Test
  public void test_pickup_chip() {
    MazeState state = makeSimpleMazeState();
    state.additem(new ItemInfo_Impl("Chip", null, new Point(2, 1), null));
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    // move chap to tile containing chip
    maze.moveChap(Direction.EAST);

    assertEquals(1, maze.getChap().getChipsCollected());
    Free chipTile = (Free) maze.getBoard().getTile(2, 1);
    assertFalse(chipTile.containsItem());
  }

  /**
   * Test that chap must have the key for a locked door in his inventory to be able to open the door
   * and that he can not walk through a closed door
   */
  @Test
  public void test_open_locakedDoor() {
    MazeState state = makeSimpleMazeState();

    Map<String, Object> fields = new HashMap<>();
    fields.put("id", 1);
    fields.put("isOpen", false);
    state.additem(new ItemInfo_Impl("Key", null, new Point(2, 1), fields));
    state.setTileAt(new TileInfo_Impl("LockedDoor", null, fields), 2, 2);

    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    // try to walk through door without key
    maze.moveChap(Direction.SOUTH);
    maze.moveChap(Direction.EAST);
    maze.moveChap(Direction.EAST);

    assertFalse(maze.getBoard().getTile(2, 2).isFree());

    // pickup key
    maze.moveChap(Direction.NORTH);
    maze.moveChap(Direction.EAST);

    assertTrue(maze.getChap().hasKey(1));

    // walk through door
    maze.moveChap(Direction.SOUTH);

    assertFalse(maze.getChap().hasKey(1));

    assertEquals(new Point(2, 2), maze.getChap().getLocation());
    assertTrue(maze.getBoard().getTile(2, 2).isFree());
  }

  /**
   * Test that chap must have collected all chips to be able to open the exit door and that he can
   * not walk through it when it is closed.
   */
  @Test
  public void test_open_exitDoor() {
    MazeState state = makeSimpleMazeState();

    Map<String, Object> fields = new HashMap<>();
    fields.put("numChipsNeeded", 1);
    fields.put("isOpen", false);
    state.additem(new ItemInfo_Impl("Chip", null, new Point(2, 1), null));
    state.setTileAt(new TileInfo_Impl("ExitDoor", null, fields), 2, 2);

    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    // try to walk through door without picking up all chips
    maze.moveChap(Direction.SOUTH);
    maze.moveChap(Direction.EAST);
    maze.moveChap(Direction.EAST);

    assertFalse(maze.getBoard().getTile(2, 2).isFree());

    // pickup chip
    maze.moveChap(Direction.NORTH);
    maze.moveChap(Direction.EAST);

    // walk through door
    maze.moveChap(Direction.SOUTH);

    assertEquals(new Point(2, 2), maze.getChap().getLocation());
    assertTrue(maze.getBoard().getTile(2, 2).isFree());
  }

  /**
   * test that observers of the maze (the application) are notifide when the level is won (when chap
   * stands on the exit).
   */
  @Test
  public void test_win() {
    final String[] notification = new String[1];

    // create maze
    // - 4 by 4 with walls around edges and exit in bottom right
    MazeState mazeState = makeSimpleMazeState();
    mazeState.additem(new ItemInfo_Impl("Exit", null, new Point(2, 2), null));
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(mazeState);
    maze.addObserverAppl(new Observer() {
      @Override
      public void update(Observable arg0, Object arg1) {
        notification[0] = (String) arg1;
      }

    });

    // move chap onto exit
    maze.moveChap(Direction.SOUTH);
    maze.moveChap(Direction.EAST); // stand on exit and win game

    // check that observers of maze were notified of win
    assertSame("Won", notification[0]);
  }

  /**
   * test that observers of the maze (the application) are notifide when the level is won (when chap
   * stands on the exit).
   */
  @Test
  public void test_stand_on_info() {
    final String[] notification = new String[2];

    // create maze
    // - 4 by 4 with walls around edges and info item in bottom right
    MazeState mazeState = makeSimpleMazeState();
    Map<String, Object> fields = new HashMap<>();
    fields.put("text", "example info text");
    mazeState.additem(new ItemInfo_Impl("Info", null, new Point(2, 2), fields));

    Maze_Impl maze = new Maze_Impl();
    maze.loadState(mazeState);
    maze.addObserverAppl(new Observer() {
      @Override
      public void update(Observable arg0, Object arg1) {
        String[] args = (String[]) arg1;
        notification[0] = args[0];
        notification[1] = args[1];
      }

    });

    // move chap onto exit
    maze.moveChap(Direction.SOUTH);
    maze.moveChap(Direction.EAST); // stand on info

    // check that observers of maze were notified of standing on info tile and told what the text on
    // the tile is
    assertSame("Information", notification[0]);
    assertSame("example info text", notification[1]);

  }

  // TODO make sure bug cannot interact with keys, exit, info
  // TODO test bug kills chap

  /**
   * Test that a bug does not interact with doors or other tiles.
   */
  @Test
  public void test_bug_interaction() {
    MazeState state = new TestMazeState();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    Bug bug = null;
    for (Enemy b : maze.getEnemies()) {
      bug = (Bug) b;
      break;
    }
    if (bug == null) {
      fail("Bug is null");
      return;
    }

    assertFalse(maze.getBoard().getTile(4, 2).interact(bug));

    // lockedDoor
    bug.move(Direction.NORTH, null);
    Door door = (Door) maze.getBoard().getTile(4, 2);
    assertFalse(door.isOpen());
    assertEquals(new Point(4, 3), bug.getLocation());

    // free tile
    bug.move(Direction.SOUTH, null);
    bug.move(Direction.WEST, null);
    Free free = (Free) maze.getBoard().getTile(3, 4);
    assertTrue(free.containsItem());

    // ExitDoor
    bug.move(Direction.NORTH, null);
    bug.move(Direction.WEST, null);
    bug.move(Direction.WEST, null);
    bug.move(Direction.SOUTH, null);
    Door exitDoor = (Door) maze.getBoard().getTile(1, 4);
    assertFalse(exitDoor.isOpen());
  }

  /**
   * Test that a bug does not interact with keys, chips, exit, or info.
   */
  @Test
  public void test_bug_interaction_with_items() {
    Bug bug = new Bug(new Free(new Point(0, 0)), Direction.WEST);
    Key key = new Key(0);
    Chip chip = new Chip();
    Exit exit = new Exit();
    Info info = new Info("text");

    assertFalse(key.interact(bug));
    assertFalse(chip.interact(bug));
    assertFalse(exit.interact(bug));
    assertFalse(info.interact(bug));
  }

  /**
   * Test that a bug moves forward until it hits a wall then turns around and keeps going.
   */
  @Test
  public void test_bug_movement() {
    MazeState state = new TestMazeState();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    Bug bug = null;
    for (Enemy b : maze.getEnemies()) {
      bug = (Bug) b;
      break;
    }
    if (bug == null) {
      fail("Bug is null");
      return;
    }

    maze.update();
    assertEquals(new Point(3, 3), bug.getLocation());

    maze.update();
    assertEquals(new Point(2, 3), bug.getLocation());

    maze.update();
    assertEquals(new Point(1, 3), bug.getLocation());

    maze.update();
    assertEquals(new Point(1, 3), bug.getLocation());
    assertEquals(Direction.EAST, bug.getDir());

    maze.update();
    assertEquals(new Point(2, 3), bug.getLocation());
  }

  /**
   * Test that when a bug walks into chap it kills chap and the level is lost
   */
  @Test
  public void test_bug_kills_chap_1() {
    String[] notification = new String[1];

    Maze_Impl maze = new Maze_Impl();
    maze.loadState(new TestMazeState());
    maze.addObserverAppl(new Observer() {
      @Override
      public void update(Observable arg0, Object arg1) {
        String args = (String) arg1;
        notification[0] = args;
      }
    });

    // move chap into path of bug
    maze.getChap().move(Direction.SOUTH, maze.getBoard());
    maze.getChap().move(Direction.SOUTH, maze.getBoard());

    // move bug into chap
    maze.update();
    maze.update();

    assertFalse(maze.getChap().isAlive());
    assertEquals("Lost", notification[0]);
  }

  /**
   * Test that when chap walks into a bug he is killed and the level is lost
   */
  @Test
  public void test_bug_kills_chap_2() {
    String[] notification = new String[1];

    Maze_Impl maze = new Maze_Impl();
    maze.loadState(new TestMazeState());
    maze.addObserverAppl(new Observer() {
      @Override
      public void update(Observable arg0, Object arg1) {
        String args = (String) arg1;
        notification[0] = args;
      }
    });

    // move chap into bug
    maze.moveChap(Direction.SOUTH);
    maze.moveChap(Direction.SOUTH);
    maze.moveChap(Direction.EAST);
    maze.moveChap(Direction.EAST);

    assertFalse(maze.getChap().isAlive());
    assertEquals("Lost", notification[0]);
  }

  // /**
  // * Test that chap can only pickup pickupable items and test the removing a key from inventory
  // */
  // @Test
  // public void test_inventory() {
  // Maze_Impl maze = new Maze_Impl();
  // maze.loadState(makeSimpleMazeState());
  //
  // assertFalse(maze.getChap().addToInventry(new Exit()));
  // assertTrue(maze.getChap().getInventory().isEmpty());
  //
  // maze.getChap().addToInventry(new Key(0));
  // maze.getChap().addToInventry(new Key(1));
  // maze.getChap().addToInventry(new Key(2));
  //
  // assertTrue(maze.getChap().hasKey(2));
  //
  // maze.getChap().removeKeyFromInventory(2);
  // assertFalse(maze.getChap().hasKey(2));
  // assertTrue(maze.getChap().hasKey(1));
  // assertTrue(maze.getChap().hasKey(0));
  // }

  /**
   * Test putting items on free tiles - can not add more than one item.
   */
  @Test
  public void test_putting_items_on_tile() {
    Free free = new Free(new Point(0, 0));

    free.addItem(new Key(1));
    assertTrue(free.getItem().equals(new Key(1)));

    assertFalse(free.addItem(new Chip()));
    assertTrue(free.getItem().equals(new Key(1)));

    Chip chip = new Chip();
    free.replaceItem(chip);
    assertTrue(free.getItem().equals(chip));
  }

  /**
   * Test that two tiles next to each other horizontally can be connected together
   */
  @Test
  public void test_connecting_tiles_horizontal() {
    Tile tile1 = new Free(new Point(0, 0));
    Tile tile2 = new Free(new Point(1, 0));

    assertFalse(tile1.connectEast(null));
    assertFalse(tile2.connectWest(null));

    try {
      tile2.connectEast(tile1);
      fail("Tiles connected");
    } catch (IllegalArgumentException e) {
    }

    try {
      tile1.connectEast(new Free(new Point(0, 1)));
      fail("Tiles connected");
    } catch (IllegalArgumentException e) {
    }

    try {
      tile1.connectWest(tile2);
      fail("Tiles connected");
    } catch (IllegalArgumentException e) {
    }

    try {
      tile2.connectWest(new Free(new Point(0, 1)));
      fail("Tiles connected");
    } catch (IllegalArgumentException e) {
    }

    assertTrue(tile1.connectEast(tile2));
    assertSame(tile2, tile1.getEast());
    assertSame(tile1, tile2.getWest());

    tile1 = new Free(new Point(0, 0));
    tile2 = new Free(new Point(1, 0));
    assertTrue(tile2.connectWest(tile1));
    assertSame(tile1, tile2.getWest());
    assertSame(tile2, tile1.getEast());
  }

  /**
   * Test that two tiles next to each other vertically can be connected together
   */
  @Test
  public void test_connecting_tiles_vertically() {
    Tile tile1 = new Free(new Point(0, 0));
    Tile tile2 = new Free(new Point(0, 1));

    assertFalse(tile1.connectNorth(null));
    assertFalse(tile2.connectSouth(null));

    try {
      tile2.connectSouth(tile1);
      fail("Tiles connected");
    } catch (IllegalArgumentException e) {
    }

    try {
      tile1.connectSouth(new Free(new Point(1, 0)));
      fail("Tiles connected");
    } catch (IllegalArgumentException e) {
    }

    try {
      tile1.connectNorth(tile2);
      fail("Tiles connected");
    } catch (IllegalArgumentException e) {
    }

    try {
      tile2.connectNorth(new Free(new Point(1, 1)));
      fail("Tiles connected");
    } catch (IllegalArgumentException e) {
    }

    assertTrue(tile1.connectSouth(tile2));
    assertSame(tile2, tile1.getSouth());
    assertSame(tile1, tile2.getNorth());

    tile1 = new Free(new Point(0, 0));
    tile2 = new Free(new Point(0, 1));
    assertTrue(tile2.connectNorth(tile1));
    assertSame(tile1, tile2.getNorth());
    assertSame(tile2, tile1.getSouth());
  }

  /**
   * Test that when trying to get a tile from a location that is not on the board null is returned
   * and no exception is thrown.
   */
  @Test
  public void testGetOutOfBoundsTile() {
    MazeState state = makeSimpleMazeState();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    assertSame(null, maze.getBoard().getTile(-1, 0));
    assertSame(null, maze.getBoard().getTile(4, 0));
    assertSame(null, maze.getBoard().getTile(0, -1));
    assertSame(null, maze.getBoard().getTile(0, 4));
  }

  /**
   * test that the program does not crash when try to get the item from a tile that is not of type
   * Free (only Free tiles contain items). Instead it should return null.
   */
  @Test
  public void testGetItemFromNonFreeTile() {
    MazeState state = makeSimpleMazeState();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    assertSame(null, maze.getBoard().getItemAt(0, 0)); // get item from Wall
  }

  /**
   * Test that when an item is attempted to be put on the board out of bounds that no exception is
   * thrown and instead it return false;
   */
  @Test
  public void testAddItemOutOfBounds() {
    MazeState state = makeSimpleMazeState();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    Item item = new Chip();
    assertFalse(maze.getBoard().addItem(item, -1, 0));
    assertFalse(maze.getBoard().addItem(item, 4, 0));
    assertFalse(maze.getBoard().addItem(item, 0, -1));
    assertFalse(maze.getBoard().addItem(item, 0, 4));
  }

  /**
   * test that is an item is null it is not added to the board and an exception is thrown.
   */
  @Test
  public void testAddNullItem() {
    MazeState state = makeSimpleMazeState();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    try {
      maze.getBoard().addItem(null, 2, 2);
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  /**
   * Test that the program does not crash when attempt to add an item to a tile that is not of type
   * Free (only Free tiles can contain an item).
   */
  @Test
  public void testAddItemToNonFree() {
    MazeState state = makeSimpleMazeState();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    assertFalse(maze.getBoard().addItem(new Chip(), 0, 0)); // try to add to Wall
  }

  /**
   * Test that when attempt to set a tile on the board to null an exception is thrown.
   */
  @Test
  public void testSetNullTile() {
    MazeState state = makeSimpleMazeState();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    try {
      maze.getBoard().setTile(null);
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  /**
   * Test that when attempt to set a tile on the board to a tile with a null location an exception
   * is thrown.
   */
  @Test
  public void testSetNullTileLocation() {
    MazeState state = makeSimpleMazeState();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    try {
      Tile free = new Free();
      maze.getBoard().setTile(free);
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  /**
   * Test that when attempt to set a tile on the board to a tile with a location that is not on the
   * board an exception is thrown.
   */
  @Test
  public void testSetInvlidTileLocation() {
    MazeState state = makeSimpleMazeState();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    try {
      Tile free = new Free(new Point(-1, 0));
      maze.getBoard().setTile(free);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }

    try {
      Tile free = new Free(new Point(4, 0));
      maze.getBoard().setTile(free);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }

    try {
      Tile free = new Free(new Point(0, -1));
      maze.getBoard().setTile(free);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }

    try {
      Tile free = new Free(new Point(0, 4));
      maze.getBoard().setTile(free);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
  }

  /**
   * Test that an exception is thrown when try to load null MazeState to the board;
   */
  @Test
  public void testLoadNull() {

    Board board = new Board(4, 4);

    try {
      board.load(null);
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  /**
   * Test that an exception is thrown when try to add the description of the board to a null
   * MazeState;
   */
  @Test
  public void testAddInfoToNullState() {
    MazeState state = makeSimpleMazeState();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    try {
      maze.getBoard().addBoardInfoToSate(null);
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  /**
   * Test that when leaving a tile containing an info item an event is fired.
   */
  @Test
  public void testLeaveInfoItem() {
    MazeState_Impl state = makeSimpleMazeState();
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(state);

    final String[] notification = new String[1];
    maze.addObserverAppl(new Observer() {
      @Override
      public void update(Observable arg0, Object arg1) {
        if (arg1 instanceof String) {
          notification[0] = (String) arg1;
        }
      }

    });

    Item info = new Info("info text");
    maze.getBoard().addItem(info, 2, 1);

    maze.moveChap(Direction.EAST);
    maze.moveChap(Direction.WEST); // leave tile

    assertEquals("LeaveInformation", notification[0]);

  }

  /**
   * Test that the preconditions of Info item are enforced. e.g that an error is thrown when try to
   * interact with null and does not interact with enemies.
   */
  @Test
  public void testInfoPreconditions() {
    Info info = new Info("info text");
    Bug bug = new Bug(null, Direction.EAST);

    assertFalse(info.interact(bug));
    assertFalse(info.leave(bug));

    try {
      info.interact(null);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {

    }

    try {
      info.leave(null);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {

    }

  }

  /**
   * Test that the preconditions of Free tile are enforced. e.g that an error is thrown when try to
   * interact with null and does not interact with enemies.
   */
  @Test
  public void testFreePreconditions() {
    Free free = new Free();
    Bug bug = new Bug(null, Direction.EAST);
    Chap chap = new Chap(null, 0, Direction.EAST);

    // item that is not interactable
    Item item = new Item() {
      @Override
      public ItemInfo getInfo(Point location) {
        return null;
      }
    };

    free.addItem(item);

    assertFalse(free.interact(chap));
    assertFalse(free.leave(chap));
    assertFalse(free.leave(bug));

    try {
      free.interact(null);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {

    }

    try {
      free.leave(null);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {

    }

  }

  /**
   * Test that when the exit door is open the getInfo() method returns a TileInfo with the image of
   * the open door.
   */
  @Test
  public void testExitDoorGetsOpenImg() {
    ExitDoor eDoor = new ExitDoor(null, 1);
    Chap chap = new Chap(null, 1, Direction.EAST);

    eDoor.open(chap);
    TileInfo info = eDoor.getInfo();

    assertEquals(new ImageIcon("./images/exitdoor_unlocked.png").getImage(), info.getImage());
  }

  /**
   * Test that the preconditions of ExitDoor tile are enforced. e.g that an error is thrown when try
   * to open it with null.
   */
  @Test
  public void testExitPreconditions() {
    ExitDoor eDoor = new ExitDoor(null, 1);

    try {
      eDoor.open(null);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {

    }

  }

  /**
   * Test that the pre and post conditions of Chap are enforced with exceptions and assertions.
   */
  @Test
  public void testChapPreAndPostConditions() {
    Chap chap = new Chap(null, 1, Direction.WEST);

    try {
      chap.setLocation(null);
      fail("assertion error was not thrown");
    } catch (AssertionError e) {
    }

    try {
      chap.addToInventry(null);
      fail("IllegalArgumentException was not thrown");
    } catch (IllegalArgumentException e) {
    }

    try {
      chap.turn(null);
      fail("IllegalArgumentException was not thrown");
    } catch (IllegalArgumentException e) {
    }

    try {
      chap.move(null, null);
      fail("IllegalArgumentException was not thrown");
    } catch (IllegalArgumentException e) {
    }

    try {
      Chap.build(null);
      fail("IllegalArgumentException error was not thrown");
    } catch (IllegalArgumentException e) {
    }

  }

  /**
   * Test that when removing a key from chaps inventory the correct key is found and removed and
   * nothing else is removed.
   */
  @Test
  public void testRemoveKeyFromInventory() {
    Chap chap = new Chap(null, 1, Direction.WEST);
    Key key1 = new Key(1);
    Key key2 = new Key(2);
    Pickupable item = new Pickupable() {
      @Override
      public ItemInfo getInfo(Point location) {
        return null;
      }
    };

    chap.addToInventry(key1);
    chap.addToInventry(item);
    chap.addToInventry(key2);

    chap.removeKeyFromInventory(2);

    assertTrue(chap.getInventory().contains(key1));
    assertTrue(chap.getInventory().contains(item));
    assertFalse(chap.getInventory().contains(key2));
  }

  /**
   * Test that the preconditions of Enemy are enforced. e.g that an error is thrown when try to
   * interact with null.
   */
  @Test
  public void testEnemyPreConditions() {
    Enemy enemy = new Bug(null, null);
    Actor actor = new Bug(null, null);

    assertFalse(enemy.interact(actor));
    assertFalse(enemy.leave(actor));

    try {
      enemy.interact(null);
      fail("IllegalArgumentException error was not thrown");
    } catch (IllegalArgumentException e) {
    }

    try {
      enemy.setLocation(null);
      fail("assertion error was not thrown");
    } catch (AssertionError e) {
    }
  }

  /**
   * Construct a MazeState that contains a 4x4 board with walls around the edges and free tiles in
   * the middle. Put chap on the top left free tile.
   * 
   * @return the MazeState
   */
  public MazeState_Impl makeSimpleMazeState() {
    Maze_Impl.init();
    MazeState_Impl state = new MazeState_Impl(4, 4, 1);

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
