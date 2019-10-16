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
 * An exit. When chap stands on this the level is won.
 *
 * @author straigfene
 *
 */
public class Exit implements Interactable, Item {

  /**
   * Zero argument constructor.
   */
  public Exit() {
  }

  /**
   * If the actor interacting with this is Chap that the level is won and the observers of the maze
   * (Application) are notified.
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

    chap.change();
    chap.notifyObservers("Won");
    return true;
  }

  @Override
  public boolean leave(Actor actor) {
    return false;
  }

  @Override
  public ItemInfo getInfo(Point location) {
    return new ItemInfo_Impl("Exit", new ImageIcon("./images/exit.png").getImage(), location, null);
  }

}
