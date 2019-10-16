package nz.ac.vuw.ecs.swen225.a3.maze.items;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Chap;

import javax.swing.*;

/**
 * An info item that when stood on by chap displayed information/instructions for the level.
 *
 * @author straigfene
 *
 */
public class Info implements Interactable, Item {
  String text;

  /**
   * Constructor.
   *
   * @param text
   *          -the information that will be displayed when stood on by chap
   */
  public Info(String text) {
    this.text = text;
  }

  /**
   * When stood on by chap notifies the observers of the maze (application) of this event and of the
   * text contained in this info item.
   */
  @Override
  public boolean interact(Actor actor) {
    String origText = text;

    if (actor == null) {
      throw new IllegalArgumentException("actor is null");
    }
    if (!(actor instanceof Chap)) {
      return false;
    }

    Chap chap = (Chap) actor;
    chap.change();
    String[] notification = { "Information", text };
    chap.notifyObservers(notification);

    assert (text.equals(origText)) : "text was changed";

    return true;
  }

  @Override
  public boolean leave(Actor actor) {
    String origText = text;

    if (actor == null) {
      throw new IllegalArgumentException("actor is null");
    }
    if (!(actor instanceof Chap)) {
      return false;
    }

    Chap chap = (Chap) actor;
    chap.change();
    chap.notifyObservers("LeaveInformation");

    assert (text.equals(origText)) : "text was changed";

    return true;
  }

  @Override
  public ItemInfo getInfo(Point location) {
    Map<String, Object> fields = new HashMap<>();
    fields.put("text", text);
    return new ItemInfo_Impl("Info", new ImageIcon("./images/info.png").getImage(), location,
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
  public static Info build(ItemInfo info) {
    return new Info((String) info.getField("text"));
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
    required.put("text", String.class);
    return required;
  }

}
