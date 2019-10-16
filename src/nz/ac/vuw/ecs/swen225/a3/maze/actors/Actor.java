package nz.ac.vuw.ecs.swen225.a3.maze.actors;

import java.awt.Point;

import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.a3.plugin.MazeElementRegistry;

/**
 * An Actor that can move around on the board and interact with items an tiles.
 *
 * In order to be able to make instances of implementations of this implementations must have either
 * a zero argument constructor or a public static build(ActorInfo) method.
 *
 * @author straigfene
 *
 */
public interface Actor {

  /**
   * Sets the location of this actor (the tile that it is on).
   * 
   * @param location
   *          the tile
   */
  void setLocation(Tile location);

  /**
   * Gets the location of this actor on the board.
   * 
   * @return the location
   */
  Point getLocation();

  /**
   * Gets the direction this actor is currently facing.
   * 
   * @return the direction
   */
  Direction getDir();

  /**
   * Turns the actor to face the given direction.
   * 
   * @param dir
   *          -the direction to turn towards
   */
  void turn(Direction dir);

  /**
   * Move the actor one step in the given direction
   * 
   * @param dir
   * @param board
   */
  void move(Direction dir, Board board);

  /**
   * Moves the actor one step using a set of rules defined by the actor implementation. e.g for a
   * bug move forward and turn when hit a wall or for chap move forward in current direction.
   * 
   * @param board
   */
  void move(Board board);

  /**
   * Gets the postfix of the image name of this actor depending on which direction it is facing. e.g
   * _back.png, or _left.png.
   * 
   * @param facing
   *          the direction the actor is currently facing.
   * @return the image path postfix as a string.
   */
  default String getImageDirectionPostfix(Direction facing) {
    if (facing == null) {
      throw new IllegalArgumentException("facing is null");
    }

    switch (facing) {
    case NORTH:
      return "_back.png";
    case WEST:
      return "_left.png";
    case EAST:
      return "_right.png";
    case SOUTH:
      return ".png";
    }
    return null;
  }

  /**
   * Get the data about this actor
   * 
   * @return the data as ActorInfo object
   */
  ActorInfo getInfo();

  /**
   * Construct a new actor from the data given in an ActorInfo object and from its location.
   * 
   * @param info
   *          -the data about the actor
   * @param location
   *          -the tile the actor is on (its location)
   * @return the new actor
   */
  static Actor makeActor(ActorInfo info, Tile location) {
    if (location == null) {
      throw new IllegalArgumentException("location is null");
    }
    if (info == null) {
      throw new IllegalArgumentException("info is null");
    }

    MazeElementRegistry registry = MazeElementRegistry.getInstance();
    Actor actor = registry.getActorInstance(info);

    assert (actor != null) : "actor is null";

    actor.setLocation(location);

    return actor;
  }

}
