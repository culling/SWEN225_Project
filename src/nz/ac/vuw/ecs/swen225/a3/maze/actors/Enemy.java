package nz.ac.vuw.ecs.swen225.a3.maze.actors;

import java.awt.Point;

import javax.swing.ImageIcon;

import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.maze.Board;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Tile;

/**
 * An enemy that can move around the board and harms/kills chap when they come into contact.
 * 
 * @author straigfene
 *
 */
public abstract class Enemy implements Actor, Interactable {

  protected String name;
  protected String imagePath;

  protected Tile currentTile;
  protected Direction facing;

  /**
   * Constructor.
   * 
   * @param name
   *          - the name/type of the enemy (e.g 'Bug')
   * @param location
   *          -the tile the enemy is on (its location)
   * @param dir
   *          -the direction the enemy is facing
   * @param imagePath
   *          -the path of the image for this enemy
   */
  public Enemy(String name, Tile location, Direction dir, String imagePath) {
    this.name = name;
    currentTile = location;
    facing = dir;
    this.imagePath = imagePath;
  }

  @Override
  public Point getLocation() {
    return currentTile.getLocation();
  }

  @Override
  public Direction getDir() {
    return facing;
  }

  /**
   * When chap walks into the bug it kills him.
   */
  @Override
  public boolean interact(Actor actor) {
    if (actor == null) {
      throw new IllegalArgumentException("actor is null");
    }

    if (actor instanceof Chap) {
      Chap chap = (Chap) actor;
      chap.die();
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void turn(Direction dir) {
    facing = dir;
  }

  @Override
  public void move(Direction dir, Board board) {
    turn(dir);
    move(board);
  }

  @Override
  public boolean leave(Actor actor) {
    return false;
  }

  @Override
  public void setLocation(Tile location) {
    this.currentTile = location;

    assert (currentTile != null) : "location was not set or was set to null";
  }

  @Override
  public ActorInfo getInfo() {
    String thisImagePath = imagePath + getImageDirectionPostfix(facing);

    return new ActorInfo_Impl(name, new ImageIcon(thisImagePath).getImage(), getLocation(), facing,
        null);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((currentTile == null) ? 0 : currentTile.hashCode());
    result = prime * result + ((facing == null) ? 0 : facing.hashCode());
    result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    Enemy other = (Enemy) obj;
    if (currentTile == null) {
      if (other.currentTile != null)
        return false;
    } else if (!currentTile.equals(other.currentTile))
      return false;
    if (facing != other.facing)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (imagePath == null) {
      if (other.imagePath != null)
        return false;
    } else if (!imagePath.equals(other.imagePath))
      return false;

    return true;
  }

}
