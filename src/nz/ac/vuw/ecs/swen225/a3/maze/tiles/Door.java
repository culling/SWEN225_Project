package nz.ac.vuw.ecs.swen225.a3.maze.tiles;

import java.awt.Point;

import nz.ac.vuw.ecs.swen225.a3.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Chap;

/**
 * A door tile that can either be open (free) or closed (not free).
 * 
 * @author straigfene
 *
 */
public abstract class Door extends Tile {
  protected boolean isOpen;

  /**
   * Constructor.
   * 
   * @param location
   *          -the location of this tile
   */
  public Door(Point location) {
    super(location);
    isOpen = false;
  }

  /**
   * Checks the conditions needed to open this door and if they are meet opens it.
   * 
   * @param chap
   *          -the player character chap (only the player can open doors)
   * @return whether or not the door was opened.
   */
  public abstract boolean open(Chap chap);

  /**
   * Checks if this door is open.
   * 
   * @return whether or not it is open
   */
  public boolean isOpen() {
    return isOpen;
  }

  @Override
  public boolean isFree() {
    return isOpen();
  }

  /**
   * Attempts to open the door. It may not be opened if the conditions to open it are not meet. e.g.
   * if chap does not have the correct key.
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
    return open(chap);
  }

  @Override
  public boolean leave(Actor actor) {
    return false;
  }

}
