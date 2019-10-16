package nz.ac.vuw.ecs.swen225.a3.maze.actors;

import java.awt.Image;
import java.awt.Point;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.InvalidMazeElementException;
import nz.ac.vuw.ecs.swen225.a3.common.Util;
import nz.ac.vuw.ecs.swen225.a3.plugin.MazeElementRegistry;

/**
 * Implements the ActorInfo class and contains the meta data on an actor (e.g Chap or a Bug).
 *
 * @author straigfene
 *
 */
public class ActorInfo_Impl implements ActorInfo {
  private String name;
  private Image image;
  private Point location;
  private Direction dir;
  private Map<String, Object> fields;

  /**
   * Constructor, takes all data required about the actor. An exception will be thrown if the data
   * provided is invalid.
   *
   * @param name
   *          -the name/type of the actor (e.g 'Chap' or 'Bug')
   * @param image
   *          -the image for the actor
   * @param location
   *          -the location of the actor on the board
   * @param dir
   *          -the direction the actor is facing
   * @param fields
   *          -a map of any other fields in the actor object. -the key = the name of the field as a
   *          string and the value = the value of the fields -e.g for Chap this will contain:
   *          key='chipsCollected', value=num chips collected (int)
   */
  public ActorInfo_Impl(String name, Image image, Point location, Direction dir,
      Map<String, Object> fields) {
    this.name = name;
    this.image = image;
    this.location = location;
    this.dir = dir;
    if (!checkValid(fields)) {
      throw new InvalidMazeElementException("Actor name and fields do not match");
    }
    this.fields = fields;
  }

  /**
   * Check that the data provided is valid. That is that all fields needed are given. e.g if the
   * name/type of actor is Chap it must be given the 'chipsCollected' field.
   * 
   * @param fields
   *          -a map of the field names and values
   * @return return whether or not the data is valid
   */
  private boolean checkValid(Map<String, Object> fields) {
    // TODO check this dynamically
    if (name == null) {
      return false;
    }

    MazeElementRegistry registry = MazeElementRegistry.getInstance();

    Set<Class<? extends Actor>> actorTypes = registry.getRegisteredActors();

    // go through all registered actors and if the name matches this name check that all
    // fields of the registered actor are defined in the fields map
    boolean actorRegistered = false;
    for (Class<? extends Actor> actorType : actorTypes) {
      if (actorType.getSimpleName().equals(name)) {
        actorRegistered = true;

        // if there is a getRequiredFeilds method then invoke it and make sure this Actor info
        // contains these
        // required fields otherwise continue.
        Map<String, Class<?>> requiredFields = null;
        try {
          requiredFields = (Map<String, Class<?>>) actorType
              .getMethod("getRequiredFeilds", new Class<?>[] {}).invoke(null, new Object[] {});
        } catch (IllegalAccessException e) {
          break;
        } catch (IllegalArgumentException e) {
          break;
        } catch (InvocationTargetException e) {
          break;
        } catch (NoSuchMethodException e) {
          break;
        } catch (SecurityException e) {
          break;
        }

        // fields being null is only invalid if the actorType has required fields
        if (requiredFields.size() > 0 && fields == null) {
          return false;
        }

        // go through all required fields of actor at check that they are all defined in the fields
        // map of this ActorInfo
        for (String requiredField : requiredFields.keySet()) {
          if (!(fields.containsKey(requiredField))) { // check field exists
            return false;
          }

          // check value of field is of the correct type
          Class<?> valueClass = Util.toWrapperClass(fields.get(requiredField).getClass());
          Class<?> type = Util.toWrapperClass(requiredFields.get(requiredField));
          if (!(type.isAssignableFrom(valueClass))) {
            return false;
          }
        }

        break;

      }
    }

    // if the actor described by this ActorInfo is not registered (does not exist) then this
    // ActorInfo is not valid
    if (!actorRegistered) {
      return false;
    }

    return true;
  }

  @Override
  public Image getImage() {
    return image;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Point getLocation() {
    return location;
  }

  @Override
  public Direction getFacingDir() {
    return dir;
  }

  @Override
  public Object getField(String fieldName) {
    if (fields == null) {
      return null;
    } // TODO throw error
    return fields.get(fieldName);
  }

  @Override
  public boolean hasField(String fieldName) {
    if (fields == null) {
      return false;
    }
    return fields.containsKey(fieldName);
  }

  @Override
  public Set<String> getFieldNames() {
    if (fields == null) {
      return new HashSet<String>();
    }
    return fields.keySet();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((dir == null) ? 0 : dir.hashCode());
    result = prime * result + ((fields == null || fields.isEmpty()) ? 0 : fields.hashCode());
    result = prime * result + ((image == null) ? 0 : image.hashCode());
    result = prime * result + ((location == null) ? 0 : location.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    ActorInfo_Impl other = (ActorInfo_Impl) obj;
    if (dir != other.dir)
      return false;
    if (fields == null || fields.isEmpty()) {
      if (other.fields != null && !other.fields.isEmpty())
        return false;
    } else if (!fields.equals(other.fields))
      return false;
    if (image == null) {
      if (other.image != null)
        return false;
    } else if (!image.equals(other.image))
      return false;
    if (location == null) {
      if (other.location != null)
        return false;
    } else if (!location.equals(other.location))
      return false;
    if (!name.equals(other.name))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "ActorInfo_Impl [name=" + name + ", location=(" + location.x + ", " + location.y + ")"
        + ", dir=" + dir + ", fields=" + fields + "]";
  }

}
