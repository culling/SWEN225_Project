package nz.ac.vuw.ecs.swen225.a3.tests.persistence;

import nz.ac.vuw.ecs.swen225.a3.common.Maze;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Item;

import java.util.List;
import java.util.Observer;

import nz.ac.vuw.ecs.swen225.a3.common.Direction;

/**
 * A class used for testing that implements the Maze interface
 * 
 * @author cullingene
 *
 */
public class TestMaze implements Maze {

  @Override
  public void moveChap(Direction dir) {
    // TODO Auto-generated method stub

  }

  @Override
  public int getChipsLeft() {
    // TODO Auto-generated method stub
    return 0;
  }

  /*
   * @Override public String[][] getBoard() { // TODO Auto-generated method stub return null; }
   */

  @Override
  public void loadState(MazeState mazeState) {
    // TODO Auto-generated method stub

  }

  @Override
  public MazeState getState() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub

  }

  @Override
  public void addObserverAppl(Observer observer) {
    // TODO Auto-generated method stub

  }

@Override
public List<Item> getInventory() {
	// TODO Auto-generated method stub
	return null;
}

}
