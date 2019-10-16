package nz.ac.vuw.ecs.swen225.a3.maze.tiles;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Chap;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Info;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Item;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Pickupable;

import javax.swing.*;

/**
 * A free tile that actors (chap) can walk onto and interact with and can contain an item that can
 * also be interacted with.
 *
 * @author straigfene
 *
 */
public class Free extends Tile {

  /**
   * The item (e.g key, chip, etc.) that is on this tile. If this tile is empty this value is null.
   */
  Item item; // TODO make this optional

  /**
   * Constructor.
   *
   * @param location
   *          -the location of the tile
   */
  public Free(Point location) {
    super(location);
    name = "Free";
  }

  /**
   * Zero argument constructor.
   */
  public Free() {
    super(null);
    name = "Free";
  }

  /**
   * Determines if this tile contains an item.
   *
   * @return whether or not it conatins an item
   */
  public boolean containsItem() {
    return item != null;
  }

  /**
   * Gets the item on this tile. Returns null if there is no item on this tile.
   *
   * @return the item
   */
  public Item getItem() {
    return item;
  }

  /**
   * Adds an item to this tile only if it does not already contain an item.
   *
   * @param item
   *          - the item to add
   * @return whether or not the item was added
   */
  public boolean addItem(Item item) {
    if (this.item == null) {
      this.item = item;

      assert (this.item == item) : "Iten was not added";
      return true;
    } else {
      return false;
    }
  }

  /**
   * Replace the current item with a new item.
   *
   * @param item
   *          - the new item to replace with
   */
  public void replaceItem(Item item) {
    this.item = item;
  }

  @Override
  public boolean isFree() {
    return true;
  }

  /**
   * If there is an item on this tile interact with that item and if the item is picked up remove it
   * from this tile.
   */
  @Override
  public boolean interact(Actor actor) {
    Item oldItem = this.item;

    if (actor == null) {
      throw new IllegalArgumentException("actor is null");
    }

    if (item == null) {
      return false;
    }

    if (!(actor instanceof Chap)) {
      return false;
    }
    Chap chap = (Chap) actor;

    if (item instanceof Interactable) {
      Interactable iItem = (Interactable) item;
      boolean interacted = iItem.interact(chap);

      // if item was picked up remove it from this tile
      if (interacted && item instanceof Pickupable) {
        item = null;
      }
      return true;
    }

    assert (item == null || item == oldItem) : "different item is now on tile";
    return false;

  }

  @Override
  public boolean leave(Actor actor) {
    if (actor == null) {
      throw new IllegalArgumentException("actor is null");
    }

    if (item == null) {
      return false;
    }

    if (!(actor instanceof Chap)) {
      return false;
    }

    if (item instanceof Interactable) {
      Interactable iItem = (Interactable) item;
      iItem.leave(actor);
      return true;
    }

    return false;
  }

  @Override
  public TileInfo getInfo() {
    return new TileInfo_Impl(name, null, null);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((item == null) ? 0 : item.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Free other = (Free) obj;
    if (item == null) {
      if (other.item != null)
        return false;
    } else if (!item.equals(other.item) || !location.equals(other.location))
      return false;
    return true;
  }

}
