package nz.ac.vuw.ecs.swen225.a3.maze.actors;

import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Tile;

/**
 * An enemy that moves forwards until it hits a wall then turns around.
 *
 * @author straigfene 300373183
 *
 */
public class Bug extends Enemy {

  /**
   * Constructor.
   *
   * @param location
   *          -the tile the bug is on (its location)
   * @param dir
   *          -the direction the bug is facing
   */
  public Bug(Tile location, Direction dir) {
    super("Bug", location, dir, "./images/bug");
  }

  /**
   * Set the name of this bug. For testing only
   * 
   * @param name
   *          the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Set the image path of this bug. For testing only
   * 
   * @param path
   *          the new image path
   */
  public void setImgPath(String path) {
    this.imagePath = path;
  }

  /**
   * Moves forwards until it hits a wall and then turn around and continue moving.
   */
  @Override
  public void move(Board board) {
    if (currentTile == null) {
      throw new IllegalStateException("tile is null");
    }

    Tile newTile = currentTile;
    if (facing == Direction.NORTH) {
      newTile = currentTile.getNorth();
    }
    if (facing == Direction.SOUTH) {
      newTile = currentTile.getSouth();
    }
    if (facing == Direction.EAST) {
      newTile = currentTile.getEast();
    }
    if (facing == Direction.WEST) {
      newTile = currentTile.getWest();
    }

    // if the tile is free move to it otherwise turn around
    if (newTile.isFree()) {
      currentTile = newTile;
      newTile.interact(this);
    } else {
      facing = facing.turn(2, true);
    }

    assert (currentTile.isFree()) : "bug is in wall";

  }

  /**
   * Builds an instance of a this using the information in an ActorInfo. The instance returned has
   * no location.
   * 
   * @param info
   *          the description of the this
   * @return an instance of this
   */
  public static Bug build(ActorInfo info) {
    return new Bug(null, info.getFacingDir());
  }

}
