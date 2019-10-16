package nz.ac.vuw.ecs.swen225.a3.maze.items;

import java.awt.Point;
import java.util.Map;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.a3.plugin.MazeElementRegistry;

/**
 * An item that can be on the board that actors can interact with. Items include key, chip, info,
 * and exit.
 *
 * In order to be able to make instances of implementations of this implementations must have either
 * a zero argument constructor or a public static build(Iteminfo) method.
 *
 * @author straigfene 300373183
 *
 */
public interface Item {

  /**
   * Gets data about the item as an ItemInfo object. (must be given the location as items do not
   * know their location).
   * 
   * @param location
   *          -the location of the item
   * @return data about the item
   */
  ItemInfo getInfo(Point location);

  /**
   * Makes a new item for the data provided.
   * 
   * @param itemInfo
   *          -the data/info about the new item
   * @return the new item
   */
  static Item makeItem(ItemInfo itemInfo) {

    MazeElementRegistry registry = MazeElementRegistry.getInstance();
    Item item = registry.getItemInstance(itemInfo);

    assert (item != null) : "item is null";

    return item;
  }

}
