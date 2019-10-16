package nz.ac.vuw.ecs.swen225.a3.tests.plugins;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Point;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.ActorInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Chap;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Item;
import nz.ac.vuw.ecs.swen225.a3.maze.items.ItemInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.TileInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.plugin.MazeElementRegistry;
import nz.ac.vuw.ecs.swen225.a3.plugin.mazeElementLoader;

/**
 * Tests for registering classes from the levels as plug-ins and for creating instances of these new
 * classes.
 * 
 * @author straigfene 300373183
 *
 */
public class RegistryTests {

  /**
   * Test that isLoaded returns false when a class is not loaded.
   */
  @Test
  public void testIsLoaded_false() {
    MazeElementRegistry registry = MazeElementRegistry.getInstance();

    registry.clearRegistry();
    assertFalse(registry.isLoaded("TestTile", "tile"));
    assertFalse(registry.isLoaded("TestItem", "item"));
    assertFalse(registry.isLoaded("TestActor", "actor"));

  }

  /**
   * Test that isLoaded() returns false when the type (tile/item/actor) is invalid.
   */
  @Test
  public void testIsLoaded_invalidType() {
    MazeElementRegistry registry = MazeElementRegistry.getInstance();

    assertFalse(registry.isLoaded("TestTile", "invalidtype"));

  }

  /**
   * Test that isLoaded returns false when a class is not loaded.
   */
  @Test
  public void testIsLoaded_true() {
    mazeElementLoader loader = mazeElementLoader.getInstance();
    MazeElementRegistry registry = MazeElementRegistry.getInstance();
    registry.clearRegistry();

    loader.SetLevelToLoadFrom(86);
    loader.loadItem("TestItem");
    loader.loadActor("TestActor");
    loader.SetLevelToLoadFrom(87);
    loader.loadTile("TestTile");

    assertTrue(registry.isLoaded("TestTile", "tile"));
    assertTrue(registry.isLoaded("TestItem", "item"));
    assertTrue(registry.isLoaded("TestActor", "actor"));

  }

  /**
   * Test that an instance of an actor that has been loaded from a level folder can be gotten and
   * used.
   */
  @Test
  public void testInstatiateLoadedActor() {
    mazeElementLoader loader = mazeElementLoader.getInstance();
    MazeElementRegistry registry = MazeElementRegistry.getInstance();
    registry.clearRegistry();

    // load actor
    loader.SetLevelToLoadFrom(86);
    loader.loadActor("TestActor");

    ActorInfo info = new ActorInfo_Impl("TestActor",
        new ImageIcon("./levels/level-86/img.png").getImage(), new Point(1, 1), Direction.EAST,
        null);

    Actor actorInstance = registry.getActorInstance(info);

    assertEquals(Direction.EAST, actorInstance.getDir());
  }

  /**
   * Test that an instance of an actor that has no build method but has a zero argument constructor
   * and has been loaded from a level folder can be gotten and used.
   */
  @Test
  public void testInstatiateLoadedActorWithZeroArgumentConstructor() {
    mazeElementLoader loader = mazeElementLoader.getInstance();
    MazeElementRegistry registry = MazeElementRegistry.getInstance();
    registry.clearRegistry();

    // load actor
    loader.SetLevelToLoadFrom(86);
    loader.loadActor("TestActor2");

    ActorInfo info = new ActorInfo_Impl("TestActor2",
        new ImageIcon("./levels/level-86/img.png").getImage(), new Point(1, 1), Direction.EAST,
        null);

    Actor actorInstance = registry.getActorInstance(info);

    assertEquals(Direction.NORTH, actorInstance.getDir());
  }

  /**
   * Test that trying to get an instance of an actor that is invalid (has no build method or zero
   * argument constructor) and has been loaded from a level folder throws an assertion error.
   */
  @Test
  public void testInstatiateInvalidLoadedActor() {
    mazeElementLoader loader = mazeElementLoader.getInstance();
    MazeElementRegistry registry = MazeElementRegistry.getInstance();
    registry.clearRegistry();

    // load actor
    loader.SetLevelToLoadFrom(86);
    loader.loadActor("InvalidActor");

    ActorInfo info = new ActorInfo_Impl("InvalidActor",
        new ImageIcon("./levels/level-86/img.png").getImage(), new Point(1, 1), Direction.EAST,
        null);

    try {
      Actor actorInstance = registry.getActorInstance(info);
      fail("did not throw assertion error");
    } catch (AssertionError e) {
      return;
    }

  }

  /**
   * Test that trying to get an instance of an actor that is invalid (has build method but it takes
   * wrong parameters) and has been loaded from a level folder throws an assertion error.
   */
  @Test
  public void testInstatiateInvalidLoadedActor_2() {
    mazeElementLoader loader = mazeElementLoader.getInstance();
    MazeElementRegistry registry = MazeElementRegistry.getInstance();
    registry.clearRegistry();

    // load actor
    loader.SetLevelToLoadFrom(86);
    loader.loadActor("InvalidBuildActor");

    ActorInfo info = new ActorInfo_Impl("InvalidBuildActor",
        new ImageIcon("./levels/level-86/img.png").getImage(), new Point(1, 1), Direction.EAST,
        null);

    try {
      Actor actorInstance = registry.getActorInstance(info);
      fail("did not throw assertion error");
    } catch (AssertionError e) {
      return;
    }

  }

  /**
   * Test that trying to get an instance of an actor that is invalid (has build method but it is not
   * static) and has been loaded from a level folder throws an assertion error.
   */
  @Test
  public void testInstatiateInvalidLoadedActor_3() {
    mazeElementLoader loader = mazeElementLoader.getInstance();
    MazeElementRegistry registry = MazeElementRegistry.getInstance();
    registry.clearRegistry();

    // load actor
    loader.SetLevelToLoadFrom(86);
    loader.loadActor("InvalidBuildActor2");

    ActorInfo info = new ActorInfo_Impl("InvalidBuildActor2",
        new ImageIcon("./levels/level-86/img.png").getImage(), new Point(1, 1), Direction.EAST,
        null);

    try {
      Actor actorInstance = registry.getActorInstance(info);
      fail("did not throw assertion error");
    } catch (AssertionError e) {
      return;
    }

  }

  /**
   * Test that an instance of an tile that has been loaded from a level folder can be gotten and
   * used using the zero argument constructor.
   */
  @Test
  public void testInstatiateLoadedTileWithConstructor() {
    mazeElementLoader loader = mazeElementLoader.getInstance();
    MazeElementRegistry registry = MazeElementRegistry.getInstance();
    registry.clearRegistry();

    // load actor
    loader.SetLevelToLoadFrom(87);
    loader.loadTile("TestTile");

    TileInfo info = new TileInfo_Impl("TestTile", null, null);

    Tile tileInstance = registry.getTileInstance(info);

    assertEquals("TestTile", tileInstance.getName());
  }

  /**
   * Test that an instance of an tile that has been loaded from a level folder can be gotten and
   * used using the build method.
   */
  @Test
  public void testInstatiateLoadedTileWithBuild() {
    mazeElementLoader loader = mazeElementLoader.getInstance();
    MazeElementRegistry registry = MazeElementRegistry.getInstance();
    registry.clearRegistry();

    // load actor
    loader.SetLevelToLoadFrom(87);
    loader.loadTile("TestTileBuild");

    TileInfo info = new TileInfo_Impl("TestTileBuild", null, null);

    Tile tileInstance = registry.getTileInstance(info);

    assertEquals("TestTileBuild", tileInstance.getName());
  }

  /**
   * Test that an instance of an item that has been loaded from a level folder can be gotten and
   * used using the zero argument constructor.
   */
  @Test
  public void testInstatiateLoadedItem() {
    mazeElementLoader loader = mazeElementLoader.getInstance();
    MazeElementRegistry registry = MazeElementRegistry.getInstance();
    registry.clearRegistry();

    // load tile
    loader.SetLevelToLoadFrom(86);
    loader.loadItem("TestItem");

    // get instance
    ItemInfo info = new ItemInfo_Impl("TestItem", null, null, null);
    Item itemInstance = registry.getItemInstance(info);

    // test using the tile
    Interactable tile = (Interactable) itemInstance;
    Chap chap = new Chap(null, 0, null);
    tile.interact(chap);
    assertFalse(chap.isAlive());
  }

  /**
   * Test that an instance of an item that has been loaded from a level folder can be gotten and
   * used using the build method.
   */
  @Test
  public void testInstatiateLoadedItemWithBuild() {
    mazeElementLoader loader = mazeElementLoader.getInstance();
    MazeElementRegistry registry = MazeElementRegistry.getInstance();
    registry.clearRegistry();

    // load tile
    loader.SetLevelToLoadFrom(86);
    loader.loadItem("TestItemBuild");

    // get instance
    ItemInfo info = new ItemInfo_Impl("TestItemBuild", null, null, null);
    Item itemInstance = registry.getItemInstance(info);

    // test using the tile
    Interactable tile = (Interactable) itemInstance;
    Chap chap = new Chap(null, 0, null);
    tile.interact(chap);
    assertEquals(1, chap.getChipsCollected());
  }

  // TODO test if build method is not static throws error
  // TODO test if build takes wrong arguments throws error
  // TODO test making info object about loaded class with required feilds

}
