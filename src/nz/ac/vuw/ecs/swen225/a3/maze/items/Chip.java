package nz.ac.vuw.ecs.swen225.a3.maze.items;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Chap;

import javax.swing.*;

/**
 * A chip (or treasure) that can be picked up.
 *
 * @author straigfene
 *
 */
public class Chip implements Pickupable, Item {

  /**
   * Zero argument constructor.
   */
  public Chip() {
  }

  /**
   * When this is picked up is does not go into the inventory and is just counted (added to the
   * number of chips collected)
   */
  @Override
  public boolean interact(Actor actor) {
    if (actor == null) {
      throw new IllegalArgumentException("actor is null");
    }

    if (!(actor instanceof Chap)) {
      return false;
    }
    Chap chap = (Chap) actor;

    return chap.collectChip();
  }

  @Override
  public ItemInfo getInfo(Point location) {
    return new ItemInfo_Impl("Chip", new ImageIcon("./images/chip.png").getImage(), location, null);
  }

}
