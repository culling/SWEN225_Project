package nz.ac.vuw.ecs.swen225.a3.common;

import java.awt.Point;

/**
 * Contains the 4 directions, north, south, east, west.
 * 
 * @author straigfene
 *
 */
public enum Direction {
  /** north/up */
  NORTH,
  /** south/down */
  SOUTH,
  /** east/right */
  EAST,
  /** west/left */
  WEST;

  /**
   * Gets the next direction going in a circle.
   * 
   * @param clockwise
   *          - whether to get the clockwise direction or not
   * @return the next direction
   */
  public Direction next(boolean clockwise) {
    switch (this) {
    case NORTH:
      return (clockwise) ? EAST : WEST;
    case SOUTH:
      return (clockwise) ? WEST : EAST;
    case EAST:
      return (clockwise) ? SOUTH : NORTH;
    case WEST:
      return (clockwise) ? NORTH : SOUTH;
    default:
      return this;
    }
  }

  /**
   * Turn in a circle.
   * 
   * @param amount
   *          - the number of turns to make
   * @param clockwise
   *          - whether to go clockwise or not
   * @return the new direction
   */
  public Direction turn(int amount, boolean clockwise) {
    Direction dir = this;
    for (int i = 0; i < amount; i++) {
      dir = dir.next(clockwise);
    }
    return dir;
  }

}
