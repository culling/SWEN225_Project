package nz.ac.vuw.ecs.swen225.a3.tests.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.Maze;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Chip;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Item;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Key;

/**
 * A Fake Maze for Application Package Tests
 *
 * @author Bryony
 *
 */
public class FakeMaze implements Maze {

  int[] location = { 0, 0 };
  MazeState mazeState;

  @Override
  public void moveChap(Direction dir) {
    switch (dir) {
    case EAST:
      location[0]++;
      break;
    case NORTH:
      location[1]--;
      break;
    case WEST:
      location[0]--;
      break;
    case SOUTH:
      location[1]++;
      break;
    }
  }

  @Override
  public void loadState(MazeState mazeState) {
    this.mazeState = mazeState;
  }

  @Override
  public int getChipsLeft() {
    return mazeState.getNumChips();
  }

  @Override
  public MazeState getState() {
    return mazeState;
  }

  @Override
  public void update() {
  }

  /**
   * Returns the current location
   *
   * @return returns an integer array
   */
  public int[] getLocation() {
    return location;
  }

  @Override
  public void addObserverAppl(Observer observer) {
  }

@Override
public List<Item> getInventory() {
	List<Item> inv = new ArrayList<Item>();
	return inv;
}

}
