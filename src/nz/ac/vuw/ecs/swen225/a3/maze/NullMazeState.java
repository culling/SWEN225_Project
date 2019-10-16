package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;

/**
 * Represents an empty maze description.
 * 
 * @author Gene
 *
 */
public class NullMazeState implements MazeState {
  int height = 0;
  int width = 0;

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getWidth() {
    return width;
  }

  // @Override
  // public void setHeight(int height) {
  // this.height = height;
  // }
  //
  // @Override
  // public void setWidth(int width) {
  // this.width = width;
  // }

  @Override
  public int getNumChips() {
    return 0;
  }

  @Override
  public void setNumChips(int num) {

  }

  @Override
  public TileInfo getTileAt(int x, int y) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<ItemInfo> getItems() {
    return new HashSet<ItemInfo>();
  }

  @Override
  public ActorInfo getChap() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<ActorInfo> getEnemies() {
    return new HashSet<ActorInfo>();
  }

  @Override
  public void setTileAt(TileInfo tile, int x, int y) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setChap(ActorInfo chap) {
    // TODO Auto-generated method stub

  }

  @Override
  public void additem(ItemInfo item) {
    // TODO Auto-generated method stub

  }

  @Override
  public void addEnemy(ActorInfo enemy) {
    // TODO Auto-generated method stub

  }

  @Override
  public List<ItemInfo> getInventory() {
    return new ArrayList<ItemInfo>();
  }

  @Override
  public void setInventory(List<ItemInfo> inventory) {
    // TODO Auto-generated method stub

  }

  @Override
  public TileInfo[][] getBoard() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void addToInventory(ItemInfo item) {
    // TODO Auto-generated method stub

  }

  @Override
  public String toString() {
    return "MazeState [height=" + height + ", width=" + width + "]";
  }

}
