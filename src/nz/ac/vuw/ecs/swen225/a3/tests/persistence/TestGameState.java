package nz.ac.vuw.ecs.swen225.a3.tests.persistence;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.GameState;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.MazeState_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.ActorInfo_Impl;

/**
 * A class used for testing that implements the GameState interface
 * 
 * @author cullingene
 *
 */
public class TestGameState implements GameState {
  int timeRemaining = 0;
  int totalTime = 0;
  MazeState mazeState = makeSimpleMazeState();
  int currentLevel = 1;

  @Override
  public int getCurrentLevel() {
    return currentLevel;
  }

  @Override
  public int getTimeRemaining() {
    return timeRemaining;
  }

  @Override
  public void setCurrentLevel(int currentLevel) {
    this.currentLevel = currentLevel;
  }

  @Override
  public void setTimeRemaining(int timeRemaining) {
    this.timeRemaining = timeRemaining;
  }

  @Override
  public MazeState getMazeState() {
    return mazeState;
  }

  @Override
  public void setMazeState(MazeState mazeState) {
    this.mazeState = mazeState;
  }

  /**
   * Make a simple Maze State. Used for testing.
   * 
   * @return a mazeState_Impl used for testing
   */
  public MazeState makeSimpleMazeState() {
    MazeState state = new MazeState_Impl(4, 4, 1);

    // add Chap
    Map<String, Object> chapFields = new HashMap<String, Object>();
    chapFields.put("chipsCollected", 0);
    state.setChap(new ActorInfo_Impl("Chap", null, new Point(1, 1), Direction.NORTH, chapFields));

    // add inventory
    state.setInventory(new ArrayList<ItemInfo>());

    // add board/tiles
    // TileInfo wall = new TestTileInfo("Wall", null, new HashMap<String, Object>());
    TileInfo free = new TestTileInfo();

    state.setTileAt(free, 0, 0);
    state.setTileAt(free, 1, 0);
    state.setTileAt(free, 2, 0);
    state.setTileAt(free, 3, 0);
    state.setTileAt(free, 0, 3);
    state.setTileAt(free, 1, 3);
    state.setTileAt(free, 2, 3);
    state.setTileAt(free, 3, 3);
    state.setTileAt(free, 0, 1);
    state.setTileAt(free, 0, 2);
    state.setTileAt(free, 3, 1);
    state.setTileAt(free, 3, 2);

    state.setTileAt(free, 1, 1);
    state.setTileAt(free, 1, 2);
    state.setTileAt(free, 2, 1);
    state.setTileAt(free, 2, 2);

    return state;
  }

  /**
   * Equals method.
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if ((this instanceof GameState) != true) {
      return false;
    }
    GameState otherGameState = (GameState) other;
    if (otherGameState.getCurrentLevel() != this.getCurrentLevel()) {
      return false;
    }
    if (otherGameState.getTimeRemaining() != this.getTimeRemaining()) {
      return false;
    }
    if (!otherGameState.getMazeState().toString().equals(this.getMazeState().toString())) {
      return false;
    }
    return true;
  }

  /**
   * HashCode Method.
   */
  public int hashCode() {
    int hashCode = 0;
    hashCode += getCurrentLevel() * 1000000;
    hashCode += getTimeRemaining() * 1;
    hashCode += getMazeState().hashCode();

    return hashCode;
  }

  @Override
  public void setTotalLevelTime(int time) {
    // TODO Auto-generated method stub
    this.totalTime = time;
  }

  @Override
  public int getTotalLevelTime() {

    return totalTime;
  }

  @Override
  public String toString() {
    return "GameState_Impl [currentLevel=" + currentLevel + ", timeRemaining=" + timeRemaining
        + ", totalTime=" + totalTime + ", mazeState=" + mazeState + "]";
  }
}
