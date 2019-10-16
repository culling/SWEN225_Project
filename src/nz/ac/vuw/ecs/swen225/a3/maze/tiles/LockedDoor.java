package nz.ac.vuw.ecs.swen225.a3.maze.tiles;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Chap;

import javax.swing.*;

/**
 * A door tile that can be opened if chap has the matching key.
 * 
 * @author straigfene 300373183
 *
 */
public class LockedDoor extends Door {

  /**
   * The id of this door. In order to open this door chap must have a key with a matching id
   */
  private int id;

  /**
   * Constructor.
   *
   * @param location
   *          -location of door
   * @param id
   *          -id of door
   */
  public LockedDoor(Point location, int id) {
    super(location);
    name = "LockedDoor";
    this.id = id;
  }

  /**
   * Alternate constructor that does not assume door is closed and takes an open argument;
   * 
   * @param location
   *          -location of door
   * @param id
   *          -id of door
   * @param open
   *          -whether or not the door is open
   */
  public LockedDoor(Point location, int id, boolean open) {
    super(location);
    name = "LockedDoor";
    isOpen = open;
    this.id = id;
  }

  /**
   * Opens this door if chap has the matching key (a key with the same id as the door).
   */
  @Override
  public boolean open(Chap chap) {
    int oldId = id;

    if (chap == null) {
      throw new IllegalArgumentException("chap is null");
    }

    if (chap.hasKey(id)) {
      isOpen = true;
      chap.removeKeyFromInventory(id);
      assert (id == oldId) : "LockedDoor id changed";
      return true;
    }
    assert (id == oldId) : "LockedDoor id changed";
    return false;
  }

  @Override
  public TileInfo getInfo() {
    Map<String, Object> fields = new HashMap<>();
    fields.put("id", id);
    fields.put("isOpen", isOpen);
    return new TileInfo_Impl(name,
        new ImageIcon(isOpen ? "./images/door_unlocked.png" : "./images/door.png").getImage(),
        fields);
  }

  /**
   * Gets a map of the names of all the fields and their types (as Class objects) of this class that
   * are required to have a value.
   *
   * Used for checking the validity of TileInfo (the description of an tile). The TileInfo object
   * MUST provide a value for these fields.
   * 
   * @return a map of field names to their types
   */
  public static Map<String, Class<?>> getRequiredFeilds() {
    Map<String, Class<?>> required = new HashMap<String, Class<?>>();
    required.put("id", int.class);
    required.put("isOpen", boolean.class);
    return required;
  }

  /**
   * Builds an instance of a this using the information in an ActorInfo. The instance that is
   * returned has no location.
   * 
   * @param info
   *          the description of the this
   * @return an instance of this
   */
  public static LockedDoor build(TileInfo info) {
    return new LockedDoor(null, (int) info.getField("id"), (boolean) info.getField("isOpen"));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (isOpen ? 1231 : 1237);
    result = prime * result + id;
    result = prime * result + ((location == null) ? 0 : location.hashCode());
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
    LockedDoor other = (LockedDoor) obj;
    if (isOpen != other.isOpen)
      return false;
    if (id != other.id)
      return false;
    if (location == null) {
      if (other.location != null)
        return false;
    } else if (!location.equals(other.location))
      return false;
    return true;
  }

}
