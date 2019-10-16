package nz.ac.vuw.ecs.swen225.a3.maze.items;

import java.awt.Image;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.json.JsonObject;

import nz.ac.vuw.ecs.swen225.a3.common.InvalidMazeElementException;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Util;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.a3.plugin.MazeElementRegistry;

/**
 * Implements the ItemInfo class and contains the meta data on an item (e.g key or a chip).
 *
 * @author straigfene
 *
 */
public class ItemInfo_Impl implements ItemInfo {
  private String name;
  private Image image;
  private Point location;
  private Map<String, Object> fields;

  /**
   * Constructor, takes all data required about the item. An exception will be thrown if the data
   * provided is invalid.
   *
   * @param name
   *          -the name/type of the item (e.g 'Chap' or 'Bug')
   * @param image
   *          -the image for the item
   * @param location
   *          -the location of the item on the board
   * @param fields
   *          -a map of any other fields in the item object. -the key = the name of the field as a
   *          string and the value = the value of the fields -e.g for Key this will contain:
   *          key='id', value=id(int)
   */
  public ItemInfo_Impl(String name, Image image, Point location, Map<String, Object> fields) {
    this.name = name;
    this.image = image;
    this.location = location;
    if (!CheckValid(fields)) {
      throw new InvalidMazeElementException("Item name and fields do not match");
    }
    this.fields = fields;
  }

  /**
   * Check that the data provided is valid. That is that all fields needed are given. e.g if the
   * name/type of item is Key it must be given the 'id' field.
   * 
   * @param fields
   *          -a map of the field names and values
   * @return return whether or not the data is valid
   */
  private boolean CheckValid(Map<String, Object> fields) {
    // TODO check this dynamically
    // if(name == null) { return false; }
    //
    // if(name.equals("Chip")) {
    // return true;
    //
    // } else if(name.equals("Key")) {
    // if(fields == null) { return false; }
    // if(!fields.containsKey("id")) { return false; }
    // if(!(fields.get("id") instanceof Integer)) { return false; }
    // return true;
    //
    // } else if(name.equals("Exit")) {
    // return true;
    //
    // } else if(name.equals("Info")) {
    // if(fields == null) { return false; }
    // if(!fields.containsKey("text")) { return false; }
    // if(!(fields.get("text") instanceof String)) { return false; }
    // return true;
    //
    // }else {
    // return false;
    // }

    MazeElementRegistry registry = MazeElementRegistry.getInstance();

    Set<Class<? extends Item>> itemTypes = registry.getRegisteredItems();

    // go through all registered actors and if the name matches this name check that all
    // fields of the registered actor are defined in the fields map
    boolean itemRegistered = false;
    for (Class<? extends Item> itemType : itemTypes) {
      if (itemType.getSimpleName().equals(name)) {
        itemRegistered = true;

        // if there is a getRequiredFeilds method then invoke it and make sure this Actor info
        // contains these
        // required fields otherwise continue.
        Map<String, Class<?>> requiredFields = null;
        try {
          requiredFields = (Map<String, Class<?>>) itemType
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
    if (!itemRegistered) {
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
    ItemInfo_Impl other = (ItemInfo_Impl) obj;
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
    if (location != null) {
      return "ItemInfo_Impl [name=" + name + ", location=(" + location.x + ", " + location.y + ")"
          + ", fields=" + fields + "]";
    }
    return "ItemInfo_Impl [name=" + name + ", location=(null)" + ", fields=" + fields + "]";
  }

}
