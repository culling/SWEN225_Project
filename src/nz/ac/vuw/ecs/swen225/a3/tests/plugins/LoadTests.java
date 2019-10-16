package nz.ac.vuw.ecs.swen225.a3.tests.plugins;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.common.LoadPluginException;
import nz.ac.vuw.ecs.swen225.a3.plugin.MazeElementRegistry;
import nz.ac.vuw.ecs.swen225.a3.plugin.mazeElementLoader;

/**
 * Tests for loading classes from the levels as plug-ins.
 *
 * @author straigfene 300373183
 *
 */
public class LoadTests {

  /**
   * Test that the MazeElementLoader can find the correct file path to load the classes in a level
   * from.
   */
  @Test
  public void testSettingLoadPath() {
    mazeElementLoader loader = mazeElementLoader.getInstance();
    loader.SetLevelToLoadFrom(87);

    String path = loader.getPath().substring(5);
    assertTrue((new File(path)).exists());
    assertEquals(87, loader.getCurrentLevel());
  }

  /**
   * Test that a tile can be loaded and registered from a jar file in a level-k folder.
   */
  @Test
  public void testLoadTile() {
    mazeElementLoader loader = mazeElementLoader.getInstance();
    loader.SetLevelToLoadFrom(87);

    loader.loadTile("TestTile");

    assertTrue(MazeElementRegistry.getInstance().isLoaded("TestTile", "tile"));

  }

  /**
   * Test that item classes can be loaded and registered correctly.
   */
  @Test
  public void testLoadItem() {
    mazeElementLoader loader = mazeElementLoader.getInstance();
    loader.SetLevelToLoadFrom(86);

    loader.loadItem("TestItem");

    assertTrue(MazeElementRegistry.getInstance().isLoaded("TestItem", "item"));
  }

  /**
   * Test that actor classes can be loaded and registered correctly.
   */
  @Test
  public void testLoadActor() {
    mazeElementLoader loader = mazeElementLoader.getInstance();
    loader.SetLevelToLoadFrom(86);

    loader.loadActor("TestActor");

    assertTrue(MazeElementRegistry.getInstance().isLoaded("TestActor", "actor"));
  }

  /**
   * Test that the path the loader is loading classes from changes correctly switching to loading
   * the next level.
   */
  @Test
  public void testChangingPath() {
    MazeElementRegistry registry = MazeElementRegistry.getInstance();
    registry.clearRegistry();

    mazeElementLoader loader = mazeElementLoader.getInstance();

    // load from test level 86
    loader.SetLevelToLoadFrom(86);
    loader.loadActor("TestActor");
    assertTrue(MazeElementRegistry.getInstance().isLoaded("TestActor", "actor"));

    // load from test level 87
    loader.SetLevelToLoadFrom(87);
    loader.loadTile("TestTile");
    assertTrue(MazeElementRegistry.getInstance().isLoaded("TestTile", "tile"));

  }

  /**
   * Test that when trying to set a level to load from that is negative an exception is thrown.
   */
  @Test
  public void testsetinvalidLevelToLoad_1() {
    mazeElementLoader loader = mazeElementLoader.getInstance();

    try {
      loader.SetLevelToLoadFrom(-1);
      fail("did not throw exception");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  /**
  * Test Invalid level to load.
  * Test that when trying to set a level to load from that does not exist an exception is thrown.
  */
  @Test
  public void testsetinvalidLevelToLoad_2() {
    mazeElementLoader loader = mazeElementLoader.getInstance();

    try {
      loader.SetLevelToLoadFrom(90);
      fail("did not throw exception");
    } catch (LoadPluginException e) {
      return;
    }
  }

  /**
   * Test that when trying to load a tile class that does not exist in the level classes.jar file an
   * exception is thrown.
   */
  @Test
  public void testloadInvalidTile() {
    mazeElementLoader loader = mazeElementLoader.getInstance();
    MazeElementRegistry.getInstance().clearRegistry();

    loader.SetLevelToLoadFrom(86);
    try {
      loader.loadTile("TestTile");
      fail("did not throw exception");
    } catch (LoadPluginException e) {
      return;
    }
  }

  /**
   * Test that when trying to load a tile class that does not exist in the level classes.jar file an
   * exception is thrown.
   */
  @Test
  public void testloadInvalidItem() {
    mazeElementLoader loader = mazeElementLoader.getInstance();

    loader.SetLevelToLoadFrom(87);
    try {
      loader.loadItem("TestItem");
      fail("did not throw exception");
    } catch (LoadPluginException e) {
      return;
    }
  }

  /**
   * Test that when trying to load a tile class that does not exist in the level classes.jar file an
   * exception is thrown.
   */
  @Test
  public void testloadInvalidActor() {
    mazeElementLoader loader = mazeElementLoader.getInstance();

    loader.SetLevelToLoadFrom(87);
    try {
      loader.loadActor("TestActor");
      fail("did not throw exception");
    } catch (LoadPluginException e) {
      return;
    }
  }


}
