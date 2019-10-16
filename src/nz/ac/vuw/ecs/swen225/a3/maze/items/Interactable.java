package nz.ac.vuw.ecs.swen225.a3.maze.items;

import nz.ac.vuw.ecs.swen225.a3.maze.actors.Actor;

/**
 * Can be interacted with by an actor.
 * 
 * @author straigfene
 *
 */
public interface Interactable {
  /**
   * Interacts with the actor.
   * 
   * @param actor
   *          -the actor to interact with
   * @return whether an interaction occurred or not.
   */
  boolean interact(Actor actor);

  /**
   * Interacts with an actor, called when the actor leaves (or steps off) the tile or item.
   * 
   * @param actor
   *          - the actor that has left the tile
   * @return whether or not there was an interaction.
   */
  boolean leave(Actor actor);

}
