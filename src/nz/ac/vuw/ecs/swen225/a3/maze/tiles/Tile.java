package nz.ac.vuw.ecs.swen225.a3.maze.tiles;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Chap;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Item;
import nz.ac.vuw.ecs.swen225.a3.plugin.MazeElementRegistry;

/**
 * A tile of the board. Tiles can either be free, not free, or swap between the two. Tiles are
 * connected to their neighbors.
 *
 * In order to be able to make instances of implementations of this implementations must have either
 * a zero argument constructor or a public static build(TileInfo) method.
 *
 * @author straigfene 300373183
 *
 */
public abstract class Tile implements Interactable {
  protected String name;

  private Tile northTile;
  private Tile southTile;
  private Tile eastTile;
  private Tile westTile;

  protected Point location;

  /**
   * Constructor.
   * 
   * @param location
   *          -the location of this tile
   */
  public Tile(Point location) {
    this.location = location;
  }

  /**
   * Sets the location of this tile.
   * 
   * @param location
   *          the location to set to.
   */
  public void setLocation(Point location) {
    this.location = location;
  }

  /**
   * Checks if this tile is free for an actor to move into.
   * 
   * @return whether or not it is free
   */
  public abstract boolean isFree();

  /**
   * Gets data about the tile as an TileInfo object.
   * 
   * @return data about the item
   */
  public abstract TileInfo getInfo();

  /**
   * Gets the name (or type) of the tile (e.g 'Wall', 'Free')
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the location of this tile.
   * 
   * @return the location
   */
  public Point getLocation() {
    if (location == null) {
      return null;
    }
    return new Point(location.x, location.y);
  }

  /**
   * Gets the tile to the north of this tile.
   * 
   * @return the north tile
   */
  public Tile getNorth() {
    return northTile;
  }

  /**
   * Gets the south to the north of this tile.
   * 
   * @return the south tile
   */
  public Tile getSouth() {
    return southTile;
  }

  /**
   * Gets the east to the north of this tile.
   * 
   * @return the east tile
   */
  public Tile getEast() {
    return eastTile;
  }

  /**
   * Gets the west to the north of this tile.
   * 
   * @return the west tile
   */
  public Tile getWest() {
    return westTile;
  }

  /**
   * Gets the neighboring tile in a given direction.
   * 
   * @param dir
   *          - the direction of neighbor from this tile
   * @return the neighboring tile
   */
  public Tile getNeighborTile(Direction dir) {
    switch (dir) {
    case NORTH:
      return northTile;
    case SOUTH:
      return southTile;
    case EAST:
      return eastTile;
    case WEST:
      return westTile;
    default:
      return null;
    }
  }

  /**
   * Add a connection between this tile and the tile to the north.
   * 
   * @param north
   *          -the tile to the north
   * @return whether or not the connection was successful
   */
  public boolean connectNorth(Tile north) {
    if (north == null) {
      return false;
    }
    if (north.getLocation().x != location.x || north.getLocation().y != location.y - 1) {
      throw new IllegalArgumentException("north is not north of this tile");
    }

    northTile = north;
    if (north.getSouth() != this) {
      north.connectSouth(this);
    }
    return true;
  }

  /**
   * Add a connection between this tile and the tile to the south.
   * 
   * @param south
   *          -the tile to the north
   * @return whether or not the connection was successful
   */
  public boolean connectSouth(Tile south) {
    if (south == null) {
      return false;
    }
    if (south.getLocation().x != location.x || south.getLocation().y != location.y + 1) {
      throw new IllegalArgumentException("south is not south of this tile");
    }

    southTile = south;
    if (south.getNorth() != this) {
      south.connectNorth(this);
    }
    return true;
  }

  /**
   * Add a connection between this tile and the tile to the east.
   * 
   * @param east
   *          -the tile to the north
   * @return whether or not the connection was successful
   */
  public boolean connectEast(Tile east) {
    if (east == null) {
      return false;
    }
    if (east.getLocation().y != location.y || east.getLocation().x != location.x + 1) {
      throw new IllegalArgumentException("east is not east of this tile");
    }

    eastTile = east;
    if (east.getWest() != this) {
      east.connectWest(this);
    }
    return true;
  }

  /**
   * Add a connection between this tile and the tile to the west.
   * 
   * @param west
   *          -the tile to the north
   * @return whether or not the connection was successful
   */
  public boolean connectWest(Tile west) {
    if (west == null) {
      return false;
    }
    if (west.getLocation().y != location.y || west.getLocation().x != location.x - 1) {
      throw new IllegalArgumentException("west is not west of this tile");
    }

    westTile = west;
    if (west.getEast() != this) {
      west.connectEast(this);
    }
    return true;
  }

  /**
   * Makes a new tile from the data provided.
   * 
   * @param tileInfo
   *          -the data about the new tile
   * @param location
   *          -the location of the new tile (this is not contained in the TileInfo)
   * @return the new tile
   */
  public static Tile makeTile(TileInfo tileInfo, Point location) {

    MazeElementRegistry registry = MazeElementRegistry.getInstance();
    Tile tile = registry.getTileInstance(tileInfo);

    assert (tile != null) : "tile is null";

    tile.setLocation(location);
    return tile;

  }

}
