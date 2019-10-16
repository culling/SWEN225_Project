package nz.ac.vuw.ecs.swen225.a3.tests.application;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;

import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Chip;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Item;
import nz.ac.vuw.ecs.swen225.a3.maze.items.ItemInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Key;

/**
 * A Fake MazeState for Application Package Tests
 * 
 * @author Bryony
 *
 */
public class FakeMazeState implements MazeState {

  int chips = 0;

  /**
   * Constructor with chips number
   * 
   * @param chips
   */
  public FakeMazeState(int chips) {
    this.chips = chips;
  }

  @Override
  public int getNumChips() {
    return chips;
  }

  @Override
  public void setNumChips(int num) {
    chips = num;
  }

  @Override
  public int getHeight() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getWidth() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public TileInfo getTileAt(int x, int y) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public TileInfo[][] getBoard() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ActorInfo getChap() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<ItemInfo> getInventory() {
	  Maze_Impl.init();
	  List<ItemInfo> inv = new ArrayList<ItemInfo>();
	  
	  ItemInfo chip = new ItemInfo_Impl("Chip", new ImageIcon("./images/chip.png").getImage(), new Point(4, 1), null);
	  
	  //add key
	  Map<String, Object> keyFields = new HashMap<String, Object>();
	  keyFields.put("id", 0);
	  ItemInfo key = new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(), new Point(1, 2), keyFields);
	  inv.add(key);
	  
	  //add key2
	  keyFields.put("id", 1);
	  key = new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(), new Point(1, 2), keyFields);
	  inv.add(key);
	  
		inv.add(chip);
		inv.add(chip);
		
		inv.add(chip);
		
		keyFields.put("id", 2);
		key = new ItemInfo_Impl("Key", new ImageIcon("./images/key.png").getImage(), new Point(1, 2), keyFields);
		inv.add(key);
		return inv;
  }

  @Override
  public Set<ActorInfo> getEnemies() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<ItemInfo> getItems() {
    // TODO Auto-generated method stub
    return null;
  }

  // @Override
  // public void setHeight(int height) {
  // // TODO Auto-generated method stub
  //
  // }
  //
  // @Override
  // public void setWidth(int width) {
  // // TODO Auto-generated method stub
  //
  // }

  @Override
  public void setTileAt(TileInfo tile, int x, int y) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setChap(ActorInfo chap) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setInventory(List<ItemInfo> inventory) {
    // TODO Auto-generated method stub

  }

  @Override
  public void addToInventory(ItemInfo item) {
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

}
