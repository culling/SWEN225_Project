package nz.ac.vuw.ecs.swen225.a3.maze.items;

import nz.ac.vuw.ecs.swen225.a3.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Chap;

/**
 * Can be picked up by an actor (Chap).
 * 
 * @author straigfene
 *
 */
public interface Pickupable extends Interactable, Item {
  /**
   * Adds this to the inventory of the actor.
   */
  @Override
  default boolean interact(Actor actor) {
    if (actor == null) {
      throw new IllegalArgumentException("actor is null");
    }

    if (!(actor instanceof Chap)) {
      return false;
    }
    Chap chap = (Chap) actor;

    return chap.addToInventry(this);
  }

  @Override
  default boolean leave(Actor actor) {
    return false;
  }
}
