package nz.ac.vuw.ecs.swen225.a3.maze.tiles;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Chap;

import javax.swing.*;

/**
 * A wall tile that is not free and can never be walked onto or interacted with.
 *
 * @author straigfene
 *
 */
public class Wall extends Tile {

  /**
   * constructor.
   * 
   * @param location
   *          -location of the wall
   */
  public Wall(Point location) {
    super(location);
    name = "Wall";
  }

  /**
   * Zero argument constructor.
   */
  public Wall() {
    super(null);
    name = "Wall";
  }

  /**
   * Does not interact with actors.
   */
  @Override
  public boolean interact(Actor actor) {
    return false;
  }

  @Override
  public boolean leave(Actor actor) {
    return false;
  }

  @Override
  public boolean isFree() {
    return false;
  }

  @Override
  public TileInfo getInfo() {
    return new TileInfo_Impl(name, null, null);
  }

}
