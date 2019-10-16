package nz.ac.vuw.ecs.swen225.a3.common;

import java.awt.Image;
import java.awt.Point;
import java.util.Map;
import java.util.Set;

/**
 * Contains info on items in the board. Has a image, the name of the item (name of class), the
 * location and a map of fields in the tile class. For example if the item ins a Key and has an id
 * field the map of fields will contain key="id" value=the value of the id.
 * 
 * @author straigfene
 *
 */
public interface ItemInfo {

  /**
   * Gets the name of the item (e.g 'Key', or 'Chip').
   * 
   * @return the name
   */
  public String getName();

  /**
   * Gets the image for this item.
   * 
   * @return the image
   */
  public Image getImage();

  /**
   * Gets the location of this item on the board. The location may be null if the item is not on the
   * board (e.g it is in the inventory).
   * 
   * @return the location
   */
  public Point getLocation();

  /**
   * Gets the value of a given field of this item.
   * 
   * @param fieldName
   *          - the feild to get the value of
   * @return the value of the field
   */
  public Object getField(String fieldName);

  /**
   * Checks if this item has a given field.
   * 
   * @param fieldName
   *          -the name of the field to check for
   * @return whether or not it has the field
   */
  public boolean hasField(String fieldName);

  /**
   * Gets the names of all the fields needed for this item (e.g for a Key the will have an 'id'
   * field).
   * 
   * @return a set of the field names
   */
  public Set<String> getFieldNames();

}
