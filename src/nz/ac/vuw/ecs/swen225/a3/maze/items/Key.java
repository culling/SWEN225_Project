package nz.ac.vuw.ecs.swen225.a3.maze.items;

import java.awt.Point;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Chap;

import javax.swing.*;

/**
 * A key that can unlock LockedDoors with the same id and can be picked up by chap.
 *
 * @author straigfene
 *
 */
public class Key implements Pickupable {
  private static final int HashMap = 0;
  private int id;

  /**
   * Constructor.
   *
   * @param id
   *          -the id of this key
   */
  public Key(int id) {
    this.id = id;
  }

  /**
   * Gets the id of this key.
   *
   * @return the id
   */
  public int getId() {
    return id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
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
    Key other = (Key) obj;
    if (id != other.id)
      return false;
    return true;
  }

  @Override
  public ItemInfo getInfo(Point location) {
    Map<String, Object> fields = new HashMap<>();
    fields.put("id", id);
    return new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(), location, fields);
  }

  /**
   * Builds an instance of a this using the information in an ActorInfo. The instance that is
   * returned has no location.
   * 
   * @param info
   *          the description of the this
   * @return an instance of this
   */
  public static Key build(ItemInfo info) {
    return new Key((int) info.getField("id"));
  }

  /**
   * Gets a map of the names of all the fields and their types (as Class objects) of this class that
   * are required to have a value.
   *
   * Used for checking the validity of ItemInfo (the description of an item). The ItemInfo object
   * MUST provide a value for these fields.
   * 
   * @return a map of field names to their types
   */
  public static Map<String, Class<?>> getRequiredFeilds() {
    Map<String, Class<?>> required = new HashMap<String, Class<?>>();
    required.put("id", int.class);
    return required;
  }

}
