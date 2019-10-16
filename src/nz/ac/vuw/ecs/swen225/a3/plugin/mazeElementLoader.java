package nz.ac.vuw.ecs.swen225.a3.plugin;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.io.File;

import nz.ac.vuw.ecs.swen225.a3.common.LoadPluginException;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Item;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Tile;

/**
 * Loads maze element classes (items/tiles/actors) from level files using a URLClassLoader.
 *
 * @author straigfene 300373183
 *
 */
public class mazeElementLoader {

  private static mazeElementLoader instance;
  private URLClassLoader loader;
  private String path = "";
  private MazeElementRegistry registry;

  /**
  * To keep track of which level file it is loading from
  */
  private int currentLevel;

  /**
  * Default constructor
  */
  private mazeElementLoader() {
    registry = MazeElementRegistry.getInstance();
  }

  /**
   * Get Instance of the maze element loader.
   *
   * @return a maze element loader singleton
   */
  public static mazeElementLoader getInstance() {
    if (instance == null) {
      instance = new mazeElementLoader();
    }
    return instance;
  }

  /**
  * Set the level file (plug-in) that will be searched to find the classes to load.
  *
  * @param levelnum
  *          the level.
  */
  public void SetLevelToLoadFrom(int levelnum) {
    //System.out.println("set path");
    if (levelnum < 0) {
      throw new IllegalArgumentException("level is negative");
    }

    File currentDirFile = new File(".");
    String helper = currentDirFile.getAbsolutePath();
    String currentDir = helper.substring(0, helper.length() - 2);

    path = "file:" + currentDir + "/levels/level-" + levelnum + "/classes.jar";

    // check if this level exists
    String filePath = path.substring(5);
    if (!(new File(filePath)).exists()) {
      throw new LoadPluginException("level does not exist");
    }

    try {
      URL url = new URL(path);

      if (loader != null) {
        loader.close();
      }

      loader = new URLClassLoader(new URL[] { url });

      currentLevel = levelnum;

    } catch (MalformedURLException e) {
      e.printStackTrace();
      throw new LoadPluginException("invalid url", e);
    } catch (IOException e) {
      throw new LoadPluginException("I/O exception", e);
    }
  }

  /**
   * Gets the current level that the loader will search in to find the classes to load.
   *
   * @return the level
   */
  public int getCurrentLevel() {
    return currentLevel;
  }

  /**
   * Gets the current path that the classLoader is loading from.
   *
   * @return the file path that the classes are being loaded from
   */
  public String getPath() {
    return path;
  }

  /**
   * Load and register a tile class
   *
   * @param tileName
   *          the name of the tile - this should be the same as the name of the class of that tile
   *          (e.g. "Wall", "Free", "LockedDoor", etc.)
   */
  public void loadTile(String tileName) {
    String className = "tiles." + tileName; // TODO work out what the class name will be

    // check if class is already loaded and if so don't reload it
    if (registry.isLoaded(tileName, "tile")) {
      return;
    }

    // load the class
    try {
      Class<? extends Tile> tileClass = (Class<? extends Tile>) loader.loadClass(className);

      registry.RegisterTile(tileClass);

    } catch (ClassNotFoundException e) {
      throw new LoadPluginException("invalid name" + className, e);
    }

  }

  /**
   * Load and register a item class
   *
   * @param itemName
   *          the name of the item - this should be the same as the name of the class of that item
   *          (e.g. "Key", "Chip", etc.)
   */
  public void loadItem(String itemName) {
    String className = "items." + itemName; // TODO work out what the class name will be

    // check if class is already loaded and if so don't reload it
    if (registry.isLoaded(itemName, "item")) {
      return;
    }

    // load the class
    try {
      Class<? extends Item> tileClass = (Class<? extends Item>) loader.loadClass(className);

      registry.RegisterItem(tileClass);

    } catch (ClassNotFoundException e) {
      throw new LoadPluginException("invalid name", e);
    }

  }

  /**
   * Load and register a actor class
   *
   * @param actorName
   *          the name of the actor - this should be the same as the name of the class of that actor
   *          (e.g. "Chap", "Bug", etc.)
   */
  public void loadActor(String actorName) {
    String className = "actors." + actorName; // TODO work out what the class name will be

    // check if class is already loaded and if so don't reload it
    if (registry.isLoaded(actorName, "actor")) {
      return;
    }

    // load the class
    try {
      Class<? extends Actor> tileClass = (Class<? extends Actor>) loader.loadClass(className);

      registry.RegisterActor(tileClass);

    } catch (ClassNotFoundException e) {
      throw new LoadPluginException("invalid name", e);
    }

  }

}
