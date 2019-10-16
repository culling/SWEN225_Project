package nz.ac.vuw.ecs.swen225.a3.tests.render;

import nz.ac.vuw.ecs.swen225.a3.common.*;
import nz.ac.vuw.ecs.swen225.a3.common.Renderer;
import nz.ac.vuw.ecs.swen225.a3.maze.MazeState_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.ActorInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.items.ItemInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.TileInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.render.Renderer_Impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Some minor tests checking that the 'focus area' functionality of the <code>Renderer</code> works.
 *
 * The <code>MazeState</code>s used in these tests are versions of code purloined from
 * <code>straigfene</code> that I have modified to be suitable here.
 *
 * @author achtenisaa 300437718
 */
public class FocusAreaTests {

  /**
   * Tests that the focus area is stored correctly.
   */
  @Test
  public void test_focusArea() {
    Rectangle focusArea = new Rectangle(1, 1, 9, 9);
    Renderer renderer = new Renderer_Impl();
    renderer.setFocusArea(focusArea);

    Assertions.assertEquals(renderer.getFocusArea(), focusArea);
  }

  /**
   * Tests that custom focus areas are preserved, and disposed of when intended.
   */
  @Test
  public void test_customFocusArea() {
    Rectangle focusArea = new Rectangle(2, 2, 9, 9);
    Renderer renderer = new Renderer_Impl();
    renderer.setFocusArea(focusArea);
    renderer.redraw(new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB).createGraphics(),
        new Dimension(600, 600));

    Assertions.assertEquals(focusArea, renderer.getFocusArea());

    renderer.resetFocusArea();
    renderer.redraw(new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB).createGraphics(),
        new Dimension(600, 600));

    Assertions.assertNotEquals(focusArea, renderer.getFocusArea());
  }

  /**
   * Tests that focus area and cell sizing is calculated correctly for small mazes, even when Chap
   * moves.
   */
  @Test
  public void test_focusAreaMetrics_small() {
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(makeSmallMazeState());

    Renderer renderer = new Renderer_Impl();
    renderer.setMaze(maze.getState());

    Rectangle defaultFocusArea = new Rectangle(0, 0, 9, 9);
    Graphics graphics = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB).createGraphics();
    Dimension dimension = new Dimension(600, 600);

    renderer.redraw(graphics, dimension);
    Assertions.assertEquals(renderer.getFocusArea(), defaultFocusArea);

    maze.moveChap(Direction.EAST);
    renderer.setMaze(maze.getState());
    renderer.redraw(graphics, dimension);
    Assertions.assertEquals(renderer.getFocusArea(), defaultFocusArea);

    maze.moveChap(Direction.SOUTH);
    renderer.setMaze(maze.getState());
    renderer.redraw(graphics, dimension);
    Assertions.assertEquals(renderer.getFocusArea(), defaultFocusArea);

    Assertions.assertEquals(renderer.getCellSize(), 120);

    renderer.setFocusArea(new Rectangle(1, 1, 9, 9));
    renderer.redraw(graphics, dimension);
    Assertions.assertNotEquals(renderer.getFocusArea(), defaultFocusArea);

    Assertions.assertEquals(renderer.getCellSize(), 66);

  }

  /**
   * Tests that focus area and cell sizing is calculated correctly for large mazes, even when Chap
   * moves.
   */
  @Test
  public void test_focusAreaMetrics_large() {
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(makeLargeMazeState());

    Renderer renderer = new Renderer_Impl();
    renderer.setMaze(maze.getState());

    Graphics graphics = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB).createGraphics();
    Dimension dimension = new Dimension(600, 600);

    renderer.redraw(graphics, dimension);
    Assertions.assertEquals(renderer.getFocusArea(), new Rectangle(-3, -3, 9, 9));

    maze.moveChap(Direction.EAST);
    renderer.setMaze(maze.getState());
    renderer.redraw(graphics, dimension);
    Assertions.assertEquals(renderer.getFocusArea(), new Rectangle(-2, -3, 9, 9));

    maze.moveChap(Direction.SOUTH);
    renderer.setMaze(maze.getState());
    renderer.redraw(graphics, dimension);
    Assertions.assertEquals(renderer.getFocusArea(), new Rectangle(-2, -2, 9, 9));

    renderer.setFocusArea(new Rectangle(1, 1, 9, 9));
    renderer.redraw(graphics, dimension);
    Assertions.assertNotEquals(renderer.getFocusArea(), new Rectangle(-2, -2, 9, 9));

    Assertions.assertEquals(renderer.getCellSize(), 66);
  }

  /**
   * Tests that the focus area displays and obscures objects correctly.
   */
  @Test
  public void test_focusAreaObscuration() {
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(makeLargeMazeState());

    Renderer renderer = new Renderer_Impl();
    renderer.setMaze(maze.getState());

    Graphics graphics = new BufferedImage(700, 700, BufferedImage.TYPE_INT_ARGB).createGraphics();
    Dimension dimension = new Dimension(700, 700);

    renderer.setMaze(maze.getState());
    renderer.redraw(graphics, dimension);

    Assertions.assertTrue(renderer.getRenderedItems().contains(maze.getState().getChap()));
    Assertions
        .assertTrue(renderer.getRenderedItems().contains(maze.getState().getItems().toArray()[0]));

    Assertions.assertTrue(renderer.getRenderedTiles().contains(new Point(0, 0)));
    Assertions.assertTrue(renderer.getRenderedTiles().contains(new Point(0, 2)));
    Assertions.assertTrue(renderer.getRenderedTiles().contains(new Point(2, 0)));
    Assertions.assertTrue(renderer.getRenderedTiles().contains(new Point(2, 2)));
    Assertions.assertFalse(renderer.getRenderedTiles().contains(new Point(9, 9)));
    Assertions.assertFalse(renderer.getRenderedTiles().contains(new Point(8, 8)));

    renderer.setFocusArea(new Rectangle(100, 100, 9, 9));
    renderer.redraw(graphics, dimension);

    Assertions.assertFalse(renderer.getRenderedItems().contains(maze.getState().getChap()));
    Assertions
        .assertFalse(renderer.getRenderedItems().contains(maze.getState().getItems().toArray()[0]));
    Assertions.assertFalse(renderer.getRenderedTiles().contains(new Point(0, 0)));
    Assertions.assertFalse(renderer.getRenderedTiles().contains(new Point(0, 2)));
    Assertions.assertFalse(renderer.getRenderedTiles().contains(new Point(2, 0)));
    Assertions.assertFalse(renderer.getRenderedTiles().contains(new Point(2, 2)));

    renderer.setFocusArea(new Rectangle(7, 7, 9, 9));
    renderer.redraw(graphics, dimension);

    Assertions.assertFalse(renderer.getRenderedItems().contains(maze.getState().getChap()));
    Assertions.assertTrue(renderer.getRenderedTiles().contains(new Point(9, 9)));
    Assertions.assertTrue(renderer.getRenderedTiles().contains(new Point(8, 8)));

  }

  /**
   * Tests that the focus area displays and obscures objects correctly in edge cases.
   */
  @Test
  public void test_focusAreaEdgeObscuration() {
    Maze_Impl maze = new Maze_Impl();
    maze.loadState(makeEdgeMazeState());

    Renderer renderer = new Renderer_Impl();
    renderer.setMaze(maze.getState());

    Graphics graphics = new BufferedImage(700, 700, BufferedImage.TYPE_INT_ARGB).createGraphics();
    Dimension dimension = new Dimension(700, 700);

    renderer.redraw(graphics, dimension);

    for (ItemInfo item : maze.getState().getItems()) {
      Assertions.assertFalse(renderer.getRenderedItems().contains(item));
    }

    maze.moveChap(Direction.NORTH);
    renderer.setMaze(maze.getState());
    renderer.redraw(graphics, dimension);

    for (ItemInfo item : maze.getState().getItems()) {
      if (item.getLocation().equals(new Point(5, 0))) {
        Assertions.assertTrue(renderer.getRenderedItems().contains(item));
      } else {
        Assertions.assertFalse(renderer.getRenderedItems().contains(item));
      }
    }

    maze.moveChap(Direction.WEST);
    renderer.setMaze(maze.getState());
    renderer.redraw(graphics, dimension);

    for (ItemInfo item : maze.getState().getItems()) {
      if (item.getLocation().equals(new Point(5, 0)) || item.getLocation().equals(new Point(0, 0))
          || item.getLocation().equals(new Point(0, 5))) {
        Assertions.assertTrue(renderer.getRenderedItems().contains(item));
      } else {
        Assertions.assertFalse(renderer.getRenderedItems().contains(item));
      }
    }

    maze.moveChap(Direction.EAST);
    maze.moveChap(Direction.EAST);
    renderer.setMaze(maze.getState());
    renderer.redraw(graphics, dimension);

    for (ItemInfo item : maze.getState().getItems()) {
      if (item.getLocation().equals(new Point(5, 0)) || item.getLocation().equals(new Point(10, 0))
          || item.getLocation().equals(new Point(10, 5))) {
        Assertions.assertTrue(renderer.getRenderedItems().contains(item));
      } else {
        Assertions.assertFalse(renderer.getRenderedItems().contains(item));
      }
    }

    maze.moveChap(Direction.SOUTH);
    maze.moveChap(Direction.SOUTH);
    renderer.setMaze(maze.getState());
    renderer.redraw(graphics, dimension);

    for (ItemInfo item : maze.getState().getItems()) {
      if (item.getLocation().equals(new Point(5, 10))
          || item.getLocation().equals(new Point(10, 10))
          || item.getLocation().equals(new Point(10, 5))) {
        Assertions.assertTrue(renderer.getRenderedItems().contains(item));
      } else {
        Assertions.assertFalse(renderer.getRenderedItems().contains(item));
      }
    }

    maze.moveChap(Direction.WEST);
    renderer.setMaze(maze.getState());
    renderer.redraw(graphics, dimension);

    for (ItemInfo item : maze.getState().getItems()) {
      if (item.getLocation().equals(new Point(5, 10))) {
        Assertions.assertTrue(renderer.getRenderedItems().contains(item));
      } else {
        Assertions.assertFalse(renderer.getRenderedItems().contains(item));
      }
    }

    maze.moveChap(Direction.WEST);
    renderer.setMaze(maze.getState());
    renderer.redraw(graphics, dimension);

    for (ItemInfo item : maze.getState().getItems()) {
      if (item.getLocation().equals(new Point(5, 10)) || item.getLocation().equals(new Point(0, 10))
          || item.getLocation().equals(new Point(0, 5))) {
        Assertions.assertTrue(renderer.getRenderedItems().contains(item));
      } else {
        Assertions.assertFalse(renderer.getRenderedItems().contains(item));
      }
    }
  }

  /**
   * Construct a smallish MazeState. As this tests the focusArea, only Chap is necessary.
   *
   * @author straigfene (with modifications by me to be suitable here)
   *
   * @return the MazeState
   */
  public MazeState makeSmallMazeState() {
    MazeState state = new MazeState_Impl(4, 5, 1);

    // add Chap
    Map<String, Object> chapFields = new HashMap<>();
    chapFields.put("chipsCollected", 1);
    state.setChap(new ActorInfo_Impl("Chap", null, new Point(2, 2), Direction.SOUTH, chapFields));

    // add inventory
    state.setInventory(new ArrayList<ItemInfo>());

    // add board/tiles
    TileInfo wall = new TileInfo_Impl("Wall", null, new HashMap<String, Object>());
    TileInfo free = new TileInfo_Impl("Free", null, new HashMap<String, Object>());

    // lay them out - this is directly correllated to the actual layout of the board
    state.setTileAt(wall, 0, 0);
    state.setTileAt(wall, 1, 0);
    state.setTileAt(wall, 2, 0);
    state.setTileAt(wall, 3, 0);
    state.setTileAt(wall, 0, 1);
    state.setTileAt(free, 1, 1);
    state.setTileAt(free, 2, 1);
    state.setTileAt(wall, 3, 1);
    state.setTileAt(wall, 0, 2);
    state.setTileAt(free, 1, 2);
    state.setTileAt(free, 2, 2);
    state.setTileAt(wall, 3, 2);
    state.setTileAt(wall, 0, 3);
    state.setTileAt(free, 1, 3);
    state.setTileAt(free, 2, 3);
    state.setTileAt(wall, 3, 3);
    state.setTileAt(wall, 0, 4);
    state.setTileAt(wall, 1, 4);
    state.setTileAt(wall, 2, 4);
    state.setTileAt(wall, 3, 4);

    return state;
  }

  /**
   * Construct a large MazeState - large enough for the focus area to shift around with the player.
   *
   * @author straigfene (with more modifications by me to be suitable here)
   *
   * @return the MazeState
   */
  public MazeState makeLargeMazeState() {
    MazeState state = new MazeState_Impl(10, 10, 1); // typing up all the tiles is so boring

    // add Chap
    Map<String, Object> chapFields = new HashMap<>();
    chapFields.put("chipsCollected", 0);
    state.setChap(new ActorInfo_Impl("Chap", null, new Point(1, 1), Direction.NORTH, chapFields));

    // add inventory
    state.setInventory(new ArrayList<ItemInfo>());

    // add board/tiles
    TileInfo wall = new TileInfo_Impl("Wall", null, new HashMap<String, Object>());
    TileInfo free = new TileInfo_Impl("Free", null, new HashMap<String, Object>());

    // add an key
    Map<String, Object> fields = new HashMap<>();
    fields.put("id", 1);
    state.additem(new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(),
        new Point(1, 2), fields));

    // lay the left bit out [X|_|_]
    state.setTileAt(wall, 0, 0);
    state.setTileAt(wall, 1, 0);
    state.setTileAt(wall, 2, 0);
    state.setTileAt(wall, 3, 0);
    state.setTileAt(wall, 0, 1);
    state.setTileAt(free, 1, 1);
    state.setTileAt(free, 2, 1);
    state.setTileAt(free, 3, 1);
    state.setTileAt(wall, 0, 2);
    state.setTileAt(free, 1, 2);
    state.setTileAt(free, 2, 2);
    state.setTileAt(free, 3, 2);
    state.setTileAt(wall, 0, 3);
    state.setTileAt(free, 1, 3);
    state.setTileAt(free, 2, 3);
    state.setTileAt(free, 3, 3);
    state.setTileAt(wall, 0, 4);
    state.setTileAt(free, 1, 4);
    state.setTileAt(free, 2, 4);
    state.setTileAt(free, 3, 4);
    state.setTileAt(wall, 0, 5);
    state.setTileAt(free, 1, 5);
    state.setTileAt(free, 2, 5);
    state.setTileAt(free, 3, 5);
    state.setTileAt(wall, 0, 6);
    state.setTileAt(free, 1, 6);
    state.setTileAt(free, 2, 6);
    state.setTileAt(free, 3, 6);
    state.setTileAt(wall, 0, 7);
    state.setTileAt(free, 1, 7);
    state.setTileAt(free, 2, 7);
    state.setTileAt(free, 3, 7);
    state.setTileAt(wall, 0, 8);
    state.setTileAt(free, 1, 8);
    state.setTileAt(free, 2, 8);
    state.setTileAt(free, 3, 8);
    state.setTileAt(wall, 0, 9);
    state.setTileAt(wall, 1, 9);
    state.setTileAt(wall, 2, 9);
    state.setTileAt(wall, 3, 9);

    // then the middle [_|X|_]
    state.setTileAt(wall, 4, 0);
    state.setTileAt(wall, 5, 0);
    state.setTileAt(wall, 6, 0);
    state.setTileAt(wall, 7, 0);
    state.setTileAt(free, 4, 1);
    state.setTileAt(free, 5, 1);
    state.setTileAt(free, 6, 1);
    state.setTileAt(free, 7, 1);
    state.setTileAt(free, 4, 2);
    state.setTileAt(free, 5, 2);
    state.setTileAt(free, 6, 2);
    state.setTileAt(free, 7, 2);
    state.setTileAt(free, 4, 3);
    state.setTileAt(free, 5, 3);
    state.setTileAt(free, 6, 3);
    state.setTileAt(free, 7, 3);
    state.setTileAt(free, 4, 4);
    state.setTileAt(free, 5, 4);
    state.setTileAt(free, 6, 4);
    state.setTileAt(free, 7, 4);
    state.setTileAt(free, 4, 5);
    state.setTileAt(free, 5, 5);
    state.setTileAt(free, 6, 5);
    state.setTileAt(free, 7, 5);
    state.setTileAt(free, 4, 6);
    state.setTileAt(free, 5, 6);
    state.setTileAt(free, 6, 6);
    state.setTileAt(free, 7, 6);
    state.setTileAt(free, 4, 7);
    state.setTileAt(free, 5, 7);
    state.setTileAt(free, 6, 7);
    state.setTileAt(free, 7, 7);
    state.setTileAt(free, 4, 8);
    state.setTileAt(free, 5, 8);
    state.setTileAt(free, 6, 8);
    state.setTileAt(free, 7, 8);
    state.setTileAt(wall, 4, 9);
    state.setTileAt(wall, 5, 9);
    state.setTileAt(wall, 6, 9);
    state.setTileAt(wall, 7, 9);

    // and finally the right [_|_|X]
    state.setTileAt(wall, 8, 0);
    state.setTileAt(wall, 9, 0);
    state.setTileAt(free, 8, 1);
    state.setTileAt(wall, 9, 1);
    state.setTileAt(free, 8, 2);
    state.setTileAt(wall, 9, 2);
    state.setTileAt(free, 8, 3);
    state.setTileAt(wall, 9, 3);
    state.setTileAt(free, 8, 4);
    state.setTileAt(wall, 9, 4);
    state.setTileAt(free, 8, 5);
    state.setTileAt(wall, 9, 5);
    state.setTileAt(free, 8, 6);
    state.setTileAt(wall, 9, 6);
    state.setTileAt(free, 8, 7);
    state.setTileAt(wall, 9, 7);
    state.setTileAt(free, 8, 8);
    state.setTileAt(wall, 9, 8);
    state.setTileAt(wall, 8, 9);
    state.setTileAt(wall, 9, 9);

    return state;
  }

  /**
   * Construct a MazeState designed to test edge cases.
   *
   * @author straigfene (with more modifications by me to be suitable here)
   *
   * @return the MazeState
   */
  public MazeState makeEdgeMazeState() {
    MazeState state = new MazeState_Impl(11, 11, 1); // typing up all the tiles is still boring

    // add Chap
    Map<String, Object> chapFields = new HashMap<>();
    chapFields.put("chipsCollected", 0);
    state.setChap(new ActorInfo_Impl("Chap", null, new Point(5, 5), Direction.NORTH, chapFields));

    // add inventory
    state.setInventory(new ArrayList<>());

    // add board/tiles
    TileInfo free = new TileInfo_Impl("Free", null, new HashMap<>());

    // add an key
    Map<String, Object> fields = new HashMap<>();
    fields.put("id", 2);
    state.additem(new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(),
        new Point(0, 0), fields));
    state.additem(new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(),
        new Point(0, 5), fields));
    state.additem(new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(),
        new Point(0, 10), fields));
    state.additem(new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(),
        new Point(5, 0), fields));
    state.additem(new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(),
        new Point(5, 10), fields));
    state.additem(new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(),
        new Point(10, 0), fields));
    state.additem(new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(),
        new Point(10, 5), fields));
    state.additem(new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(),
        new Point(10, 10), fields));

    // lay the left bit out [X|_|_]
    state.setTileAt(free, 0, 0);
    state.setTileAt(free, 1, 0);
    state.setTileAt(free, 2, 0);
    state.setTileAt(free, 3, 0);
    state.setTileAt(free, 0, 1);
    state.setTileAt(free, 1, 1);
    state.setTileAt(free, 2, 1);
    state.setTileAt(free, 3, 1);
    state.setTileAt(free, 0, 2);
    state.setTileAt(free, 1, 2);
    state.setTileAt(free, 2, 2);
    state.setTileAt(free, 3, 2);
    state.setTileAt(free, 0, 3);
    state.setTileAt(free, 1, 3);
    state.setTileAt(free, 2, 3);
    state.setTileAt(free, 3, 3);
    state.setTileAt(free, 0, 4);
    state.setTileAt(free, 1, 4);
    state.setTileAt(free, 2, 4);
    state.setTileAt(free, 3, 4);
    state.setTileAt(free, 0, 5);
    state.setTileAt(free, 1, 5);
    state.setTileAt(free, 2, 5);
    state.setTileAt(free, 3, 5);
    state.setTileAt(free, 0, 6);
    state.setTileAt(free, 1, 6);
    state.setTileAt(free, 2, 6);
    state.setTileAt(free, 3, 6);
    state.setTileAt(free, 0, 7);
    state.setTileAt(free, 1, 7);
    state.setTileAt(free, 2, 7);
    state.setTileAt(free, 3, 7);
    state.setTileAt(free, 0, 8);
    state.setTileAt(free, 1, 8);
    state.setTileAt(free, 2, 8);
    state.setTileAt(free, 3, 8);
    state.setTileAt(free, 0, 9);
    state.setTileAt(free, 1, 9);
    state.setTileAt(free, 2, 9);
    state.setTileAt(free, 3, 9);
    state.setTileAt(free, 0, 10);
    state.setTileAt(free, 1, 10);
    state.setTileAt(free, 2, 10);
    state.setTileAt(free, 3, 10);

    // then the middle [_|X|_]
    state.setTileAt(free, 4, 0);
    state.setTileAt(free, 5, 0);
    state.setTileAt(free, 6, 0);
    state.setTileAt(free, 7, 0);
    state.setTileAt(free, 4, 1);
    state.setTileAt(free, 5, 1);
    state.setTileAt(free, 6, 1);
    state.setTileAt(free, 7, 1);
    state.setTileAt(free, 4, 2);
    state.setTileAt(free, 5, 2);
    state.setTileAt(free, 6, 2);
    state.setTileAt(free, 7, 2);
    state.setTileAt(free, 4, 3);
    state.setTileAt(free, 5, 3);
    state.setTileAt(free, 6, 3);
    state.setTileAt(free, 7, 3);
    state.setTileAt(free, 4, 4);
    state.setTileAt(free, 5, 4);
    state.setTileAt(free, 6, 4);
    state.setTileAt(free, 7, 4);
    state.setTileAt(free, 4, 5);
    state.setTileAt(free, 5, 5);
    state.setTileAt(free, 6, 5);
    state.setTileAt(free, 7, 5);
    state.setTileAt(free, 4, 6);
    state.setTileAt(free, 5, 6);
    state.setTileAt(free, 6, 6);
    state.setTileAt(free, 7, 6);
    state.setTileAt(free, 4, 7);
    state.setTileAt(free, 5, 7);
    state.setTileAt(free, 6, 7);
    state.setTileAt(free, 7, 7);
    state.setTileAt(free, 4, 8);
    state.setTileAt(free, 5, 8);
    state.setTileAt(free, 6, 8);
    state.setTileAt(free, 7, 8);
    state.setTileAt(free, 4, 9);
    state.setTileAt(free, 5, 9);
    state.setTileAt(free, 6, 9);
    state.setTileAt(free, 7, 9);
    state.setTileAt(free, 4, 10);
    state.setTileAt(free, 5, 10);
    state.setTileAt(free, 6, 10);
    state.setTileAt(free, 7, 10);

    // and finally the right [_|_|X]
    state.setTileAt(free, 8, 0);
    state.setTileAt(free, 9, 0);
    state.setTileAt(free, 10, 0);
    state.setTileAt(free, 8, 1);
    state.setTileAt(free, 9, 1);
    state.setTileAt(free, 10, 1);
    state.setTileAt(free, 8, 2);
    state.setTileAt(free, 9, 2);
    state.setTileAt(free, 10, 2);
    state.setTileAt(free, 8, 3);
    state.setTileAt(free, 9, 3);
    state.setTileAt(free, 10, 3);
    state.setTileAt(free, 8, 4);
    state.setTileAt(free, 9, 4);
    state.setTileAt(free, 10, 4);
    state.setTileAt(free, 8, 5);
    state.setTileAt(free, 9, 5);
    state.setTileAt(free, 10, 5);
    state.setTileAt(free, 8, 6);
    state.setTileAt(free, 9, 6);
    state.setTileAt(free, 10, 6);
    state.setTileAt(free, 8, 7);
    state.setTileAt(free, 9, 7);
    state.setTileAt(free, 10, 7);
    state.setTileAt(free, 8, 8);
    state.setTileAt(free, 9, 8);
    state.setTileAt(free, 10, 8);
    state.setTileAt(free, 8, 9);
    state.setTileAt(free, 9, 9);
    state.setTileAt(free, 10, 9);
    state.setTileAt(free, 8, 10);
    state.setTileAt(free, 9, 10);
    state.setTileAt(free, 10, 10);

    return state;
  }
}
