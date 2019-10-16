package nz.ac.vuw.ecs.swen225.a3.maze.tiles;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Chap;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Key;

import javax.swing.*;

/**
 * A door tile that can only be opened if chap has collected a minimum number of chips (this will
 * usually be all the chips in the level.) It blocks the way to the exit (is the exit lock).
 *
 * @author straigfene 300373183
 *
 */
public class ExitDoor extends Door {

  /**
   * The number of chips Chap needs to collect to open the door. This will usually be the total
   * number of chips in the level.
   */
  int numChipsNeeded;

  /**
   * Constructor.
   *
   * @param location
   *          -the location of the door
   * @param numChipsNeeded
   *          -the number of chips needed to open this door
   */
  public ExitDoor(Point location, int numChipsNeeded) {
    super(location);
    name = "ExitDoor";
    this.numChipsNeeded = numChipsNeeded;
  }

  /**
   * Alternative constructor that takes an open argument.
   * 
   * @param location
   *          -the location of the door
   * @param numChipsNeeded
   *          -the number of chips needed to open this door
   * @param open
   *          -whether or not the door is open
   */
  public ExitDoor(Point location, int numChipsNeeded, boolean open) {
    super(location);
    name = "ExitDoor";
    isOpen = open;
    this.numChipsNeeded = numChipsNeeded;
  }

  @Override
  public TileInfo getInfo() {
    Map<String, Object> fields = new HashMap<>();
    fields.put("numChipsNeeded", numChipsNeeded);
    fields.put("isOpen", isOpen);
    return new TileInfo_Impl(name,
        new ImageIcon(isOpen ? "./images/exitdoor_unlocked.png" : "./images/exitdoor.png")
            .getImage(),
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
  public static ExitDoor build(TileInfo info) {
    return new ExitDoor(null, (int) info.getField("numChipsNeeded"),
        (boolean) info.getField("isOpen"));
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
    required.put("isOpen", boolean.class);
    required.put("numChipsNeeded", int.class);
    return required;
  }

  /**
   * Opens this door if chap has collected a min number of chips (usually all chips are needed).
   */
  @Override
  public boolean open(Chap chap) {
    boolean wasOpen = isOpen;

    if (chap == null) {
      throw new IllegalArgumentException("chap is null");
    }
    if (chap.getChipsCollected() >= numChipsNeeded) {
      isOpen = true;

      assert (!(wasOpen && !isOpen)) : "door was closed";
      return true;
    }
    assert (!(wasOpen && !isOpen)) : "door was closed";
    return false;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (isOpen ? 1231 : 1237);
    result = prime * result + numChipsNeeded;
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
    ExitDoor other = (ExitDoor) obj;
    if (isOpen != other.isOpen)
      return false;
    if (numChipsNeeded != other.numChipsNeeded)
      return false;
    if (location == null) {
      if (other.location != null)
        return false;
    } else if (!location.equals(other.location))
      return false;
    return true;
  }

}
