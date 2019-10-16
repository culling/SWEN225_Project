package nz.ac.vuw.ecs.swen225.a3.maze.actors;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Item;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Key;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Pickupable;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Door;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Tile;

import javax.swing.*;

/**
 * The playable character. It moves around the board and interacts with items and tiles.
 *
 * @author straigfene
 *
 */
public class Chap extends Observable implements Actor {

  private Tile currentTile;
  private int chipsCollected;
  private List<Item> inventory;
  private Direction facing;

  private boolean alive;

  /**
   * The constructor.
   *
   * @param location
   *          -The tile chap is on
   * @param chips
   *          -the number of chips the have been collected (when starting a new level this will be
   *          zero).
   * @param dir
   *          -the direction chap is facing
   */
  public Chap(Tile location, int chips, Direction dir) {
    currentTile = location;
    chipsCollected = chips;
    inventory = new ArrayList<Item>();
    facing = dir;
    alive = true;
  }

  @Override
  public void setLocation(Tile location) {
    this.currentTile = location;

    assert (currentTile != null) : "location was not set or was set to null";
  }

  @Override
  public Point getLocation() {
    return currentTile.getLocation();
  }

  @Override
  public Direction getDir() {
    return facing;
  }

  /**
   * Gets the number of chips that have been collected so far.
   *
   * @return the number of chips
   */
  public int getChipsCollected() {
    return chipsCollected;
  }

  /**
   * Gets a read-only view of Chaps inventory.
   *
   * @return List of items in inventory
   */
  public List<Item> getInventory() {
    return Collections.unmodifiableList(inventory);
  }

  /**
   * Checks if chap currently has a key with the given id in his inventory.
   *
   * @param keyId
   *          -the id of the key to check for
   * @return whether or not chap has the key
   */
  public boolean hasKey(int keyId) {
    return inventory.contains(new Key(keyId));
  }

  /**
   * Adds a pickupable item to Chaps inventory.
   *
   * @param item
   *          - the item to add
   * @return whether or not the item was added.
   */
  public boolean addToInventry(Pickupable item) {
    int origInvSize = inventory.size();

    if (item == null) {
      throw new IllegalArgumentException("item is null");
    }

    boolean added = inventory.add(item);

    assert (inventory.size() == origInvSize + 1) : "inventory size did not increase";
    assert (inventory.contains(item)) : "item was not added";

    return added;
  }

  /**
   * Removes a key with the given id from the inventory if it is in the inventory.
   *
   * @param keyId
   *          -the id of the key
   */
  public void removeKeyFromInventory(int keyId) {
    int origInvSize = inventory.size();

    for (int i = 0; i < inventory.size(); i++) {
      if (inventory.get(i) instanceof Key) {
        Key key = (Key) inventory.get(i);
        if (key.getId() == keyId) {
          inventory.remove(i);
          break;
        }
      }
    }

    assert (inventory.size() == origInvSize - 1 || origInvSize == 0) : "key was not removed";
  }

  /**
   * Collects a single chip.
   *
   * @return whether or not the chip was collected.
   */
  public boolean collectChip() {
    chipsCollected++;
    return true;
  }

  /**
   * Kills chap and notifies all observers of this.
   */
  public void die() {
    this.setChanged();
    alive = false;
    this.notifyObservers("Lost");
  }

  /**
   * Checks if chap is alive.
   *
   * @return whether or no chap is alive
   */
  public boolean isAlive() {
    return alive;
  }

  /**
   * Records that a change in state has occurred (so that observers can be notified of the change).
   */
  public void change() {
    this.setChanged();
  }

  @Override
  public ActorInfo getInfo() {
    Map<String, Object> fields = new HashMap<>();
    fields.put("chipsCollected", chipsCollected);

    String imagePath = "./images/chap";

    imagePath += getImageDirectionPostfix(facing);

    return new ActorInfo_Impl("Chap", new ImageIcon(imagePath).getImage(), getLocation(), facing,
        fields);
  }

  /**
   * Builds an instance of a this using the information in an ActorInfo. The instance that is
   * returned has no location.
   * 
   * @param info
   *          the description of the this
   * @return an instance of this
   */
  public static Chap build(ActorInfo info) {
    if (info == null) {
      throw new IllegalArgumentException("info is null");
    }

    return new Chap(null, (int) info.getField("chipsCollected"), info.getFacingDir());
  }

  /**
   * Gets a map of the names of all the fields and their types (as Class objects) of this actor that
   * are required to have a value.
   *
   * Used for checking the validity of ActorInfo (the description of an actor). The ActorInfo object
   * MUST provide a value for these fields.
   * 
   * @return a map of field names to their types
   */
  public static Map<String, Class<?>> getRequiredFeilds() {
    Map<String, Class<?>> required = new HashMap<String, Class<?>>();
    required.put("chipsCollected", int.class);
    return required;
  }

  @Override
  public void turn(Direction dir) {
    if (dir == null) {
      throw new IllegalArgumentException("dir is null");
    }
    facing = dir;
  }

  @Override
  public void move(Direction dir, Board board) {
    if (dir == null) {
      throw new IllegalArgumentException("dir is null");
    }
    turn(dir);
    move(board);
  }

  @Override
  public void move(Board board) {
    // find new tile to move to
    Tile newTile = currentTile;
    if (facing == Direction.NORTH) {
      newTile = currentTile.getNorth();
    }
    if (facing == Direction.SOUTH) {
      newTile = currentTile.getSouth();
    }
    if (facing == Direction.EAST) {
      newTile = currentTile.getEast();
    }
    if (facing == Direction.WEST) {
      newTile = currentTile.getWest();
    }

    // if the tile is a door try to open it before moving through, otherwise move and then
    // interact with the tile
    if (newTile instanceof Door) {
      newTile.interact(this);
      if (newTile.isFree()) {
        currentTile.leave(this);
        currentTile = newTile;
      }
    } else {
      if (newTile.isFree()) {
        currentTile.leave(this);
        currentTile = newTile;
      }
      newTile.interact(this);
    }

    assert (currentTile.isFree()) : "Chap moved into wall";

  }

}
