package nz.ac.vuw.ecs.swen225.a3.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Item;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Tile;

/**
 * This is for registering new tiles/items/actors from level plug-ins so the program can use them.
 * It provides a way to create instances of them (through reflection using the Class objects). Basic
 * elements created normally (as part of program) also need to be registered.
 *
 * It uses the singleton design pattern so the constructor can not be used to create an instance.
 * Instead the getInstance() method must be used.
 *
 * @author straigfene 300373183
 *
 */
public class MazeElementRegistry {
  private Set<Class<? extends Tile>> registeredTiles;
  private Set<Class<? extends Item>> registeredItems;
  private Set<Class<? extends Actor>> registeredActors;

  private static MazeElementRegistry instance;

  private MazeElementRegistry() {
    registeredTiles = new HashSet<Class<? extends Tile>>();
    registeredItems = new HashSet<Class<? extends Item>>();
    registeredActors = new HashSet<Class<? extends Actor>>();
  }

  /**
   * Provides an instance of this class so that there can only ever be one instance. Use this
   * instead of the constructor.
   *
   * @return the instance of MazeElementRegistry.
   */
  public static MazeElementRegistry getInstance() {
    if (instance == null) {
      instance = new MazeElementRegistry();
    }

    return instance;

  }

  /**
   * clears all classes loaded into the MazeElementRegistry.
   */
  public void clearRegistry() {
    registeredTiles.clear();
    registeredItems.clear();
    registeredActors.clear();
  }

  /**
   * Checks if a class/element is already loaded.
   *
   * @param name
   *          - the name of the class/element
   * @param elementType
   *          - the type of the element (e.g. tile/item/actor)
   * @return whether or not the class is loaded
   */
  public boolean isLoaded(String name, String elementType) {
    if (elementType.toLowerCase().equals("tile")) {
      for (Class<? extends Tile> tileClass : registeredTiles) {
        //System.out.println("class name = " + tileClass.getSimpleName() + "\n");
        if (tileClass.getSimpleName().equals(name)) {
          return true;
        }
      }
      return false;
    } else if (elementType.toLowerCase().equals("item")) {
      for (Class<? extends Item> itemClass : registeredItems) {
        if (itemClass.getSimpleName().equals(name)) {
          return true;
        }
      }
      return false;
    } else if (elementType.toLowerCase().equals("actor")) {
      for (Class<? extends Actor> actorClass : registeredActors) {
        if (actorClass.getSimpleName().equals(name)) {
          return true;
        }
      }
      return false;
    } else {
      return false;
    }
  }

  /**
   * Get a set off the Class objects of all the loaded tiles.
   *
   * @return a set of Class objects of all the loaded tiles
   */
  public Set<Class<? extends Tile>> getRegisteredTiles() {
    // TODO make unmodifiable
    return registeredTiles;
  }

  /**
   * Get a set off the Class objects of all the loaded items.
   *
   * @return a set of Class objects of all the loaded items
   */
  public Set<Class<? extends Item>> getRegisteredItems() {
    // TODO make unmodifiable
    return registeredItems;
  }

  /**
   * Get a set off the Class objects of all the loaded actors.
   *
   * @return a set of Class objects of all the loaded actors
   */
  public Set<Class<? extends Actor>> getRegisteredActors() {
    // TODO make unmodifiable
    return registeredActors;
  }

  /**
   * Register a Tile created normally (not as a plug-in).
   *
   * @param tileType
   *          the Class object of the tile to register
   */
  public void RegisterTile(Class<? extends Tile> tileType) {
    this.registeredTiles.add(tileType);
  }

  /**
   * Register a Item created normally (not as a plug-in).
   *
   * @param itemType
   *          the Class object of the item to register
   */
  public void RegisterItem(Class<? extends Item> itemType) {
    this.registeredItems.add(itemType);
  }

  /**
   * Register a Actor created normally (not as a plug-in).
   *
   * @param actorType
   *          the Class object of the actor to register
   */
  public void RegisterActor(Class<? extends Actor> actorType) {
    this.registeredActors.add(actorType);
  }

  /**
   * Creates a new instance of an actor using the description of that actor (ActorInfo).
   *
   * @param info
   *          the description of the actor to make
   * @return the instance of the actor or null if an new instance could not be made
   */
  public Actor getActorInstance(ActorInfo info) {
    String actorName = info.getName();

    Actor actor = null;

    // look for the Class object of the actor
    for (Class<? extends Actor> actorClass : this.registeredActors) {
      if (actorClass.getSimpleName().equals(actorName)) {

        // try to instantiate the actor using the zero argument constructor and if that does not
        // work (if there
        // is no zero argument constructor) try to use the build method.
        try {
          // attempt with zero argument constructor
          actor = actorClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
          // attempt with build method
          try {

            actor = (Actor) actorClass.getMethod("build", ActorInfo.class).invoke(null,
                new Object[] { info });

          } catch (IllegalAccessException e1) {
            actor = null;
          } catch (IllegalArgumentException e1) {
            actor = null;
          } catch (InvocationTargetException e1) {
            actor = null;
          } catch (NoSuchMethodException e1) {
            actor = null;
          } catch (SecurityException e1) {
            actor = null;
          }

        }
      }
    }

    assert (actor != null);

    return actor;
  }

  /**
   * Creates a new instance of an item using the description of that item (ItemInfo).
   *
   * @param info
   *          the description of the item to make
   * @return the instance of the item or null if an new instance could not be made
   */
  public Item getItemInstance(ItemInfo info) {
    String itemName = info.getName();
    Item item = null;

    // look for the Class object of the actor
    for (Class<? extends Item> itemClass : this.registeredItems) {
      if (itemClass.getSimpleName().equals(itemName)) {

        // try to instantiate the actor using the zero argument constructor and if that does not
        // work (if there
        // is no zero argument constructor) try to use the build method.
        try {
          // attempt with zero argument constructor
          item = itemClass.newInstance();

        } catch (InstantiationException | IllegalAccessException e) {
          // attempt with build method
          try {

            item = (Item) itemClass.getMethod("build", ItemInfo.class).invoke(null,
                new Object[] { info });

          } catch (IllegalAccessException e1) {
            item = null;
          } catch (IllegalArgumentException e1) {
            item = null;
          } catch (InvocationTargetException e1) {
            item = null;
          } catch (NoSuchMethodException e1) {
            item = null;
          } catch (SecurityException e1) {
            item = null;
          }

        }
      }
    }

    assert (item != null);

    return item;
  }

  /**
   * Creates a new instance of an actor using the description of that actor (ActorInfo).
   *
   * @param info
   *          the description of the actor to make
   * @return the instance of the actor or null if an new instance could not be made
   */
  public Tile getTileInstance(TileInfo info) {
    String tileName = info.getName();

    Tile tile = null;

    // look for the Class object of the actor
    for (Class<? extends Tile> tileClass : this.registeredTiles) {
      if (tileClass.getSimpleName().equals(tileName)) {

        // try to instantiate the actor using the zero argument constructor and if that does not
        // work (if there
        // is no zero argument constructor) try to use the build method.
        try {
          // attempt with zero argument constructor
          tile = tileClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
          // attempt with build method
          try {
            tile = (Tile) tileClass.getMethod("build", TileInfo.class).invoke(null,
                new Object[] { info });

          } catch (IllegalAccessException e1) {
            tile = null;
          } catch (IllegalArgumentException e1) {
            tile = null;
          } catch (InvocationTargetException e1) {
            tile = null;
          } catch (NoSuchMethodException e1) {
            tile = null;
          } catch (SecurityException e1) {
            tile = null;
          }

        }
      }
    }

    assert (tile != null);

    return tile;
  }

}
