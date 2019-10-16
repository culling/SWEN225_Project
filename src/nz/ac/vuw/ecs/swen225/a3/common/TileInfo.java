package nz.ac.vuw.ecs.swen225.a3.common;

import java.awt.Image;
import java.util.Set;

/**
 * Contains info on tiles in the board. has a image and the name of the tile (name of class) and a
 * map of fields in the tile class. For example if the Tile class has an id field the map of fields
 * will contain key="id" value=the value of the id.
 *
 * @author straigfene
 *
 */
public interface TileInfo {

  /**
   * Gets the name of the tile (e.g 'Free', or 'Wall').
   * 
   * @return the name
   */
  public String getName();

  /**
   * Gets the image for this tile.
   * 
   * @return the image
   */
  public Image getImage();

  /**
   * Gets the value of a given field of this tile.
   * 
   * @param fieldName
   *          - the field to get the value of
   * @return the value of the field
   */
  public Object getField(String fieldName);

  /**
   * Checks if this item has a given tile.
   * 
   * @param fieldName
   *          -the name of the field to check for
   * @return whether or not it has the field
   */
  public boolean hasField(String fieldName);

  /**
   * Gets the names of all the fields needed for this tile (e.g for a LockedDoor the will have 'id'
   * and 'isOpen' fields).
   * 
   * @return a set of the field names
   */
  public Set<String> getFieldNames();
}
