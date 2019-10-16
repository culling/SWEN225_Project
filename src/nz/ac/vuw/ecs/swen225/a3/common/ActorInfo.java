package nz.ac.vuw.ecs.swen225.a3.common;

import java.awt.Image;
import java.awt.Point;
import java.util.Map;
import java.util.Set;

/**
 * Contains info on actors in the board. has a image and the name of the tile (name of class),
 * location, direction and a map of fields in the actor class. For example if the actor is Chap and
 * has an inventory field the map of fields will contain key="inventory" value=List&lt;ItemInfo&gt;.
 * 
 * @author straigfene
 *
 */
public interface ActorInfo {

  /**
   * Gets the name of the actor (e.g 'Chap', or 'Bug').
   * 
   * @return the name
   */
  public String getName();

  /**
   * Gets the image for this actor.
   * 
   * @return the image
   */
  public Image getImage();

  /**
   * Gets the location of this actor on the board.
   * 
   * @return the location
   */
  public Point getLocation();

  /**
   * Gets the direction the actor is facing.
   * 
   * @return the direction
   */
  public Direction getFacingDir();

  /**
   * Gets the value of a given field of this actor.
   * 
   * @param fieldName
   *          - the field to get the value of
   * @return the value of the field
   */
  public Object getField(String fieldName);

  /**
   * Checks if this actor has a given field.
   * 
   * @param fieldName
   *          -the name of the field to check for
   * @return whether or not it has the field
   */
  public boolean hasField(String fieldName);

  /**
   * Gets the names of all the fields needed for this actor (e.g for a Chap the will have a
   * 'chipsCollected' field).
   * 
   * @return a set of the field names
   */
  public Set<String> getFieldNames();
}
