package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.List;
import java.util.Observer;

import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.Maze;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Item;

/**
 * Represents and empty maze.
 * 
 * @author Gene
 *
 */
public class NullMaze implements Maze {

  @Override
  public void moveChap(Direction dir) {
    // TODO Auto-generated method stub

  }

  @Override
  public int getChipsLeft() {
    // TODO Auto-generated method stub
    return 0;
  }

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
