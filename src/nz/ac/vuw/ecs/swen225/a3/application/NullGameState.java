package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.common.GameState;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.maze.NullMazeState;

/**
 * An Empty GameState that can't be changed
 * 
 * @author Bryony
 *
 */
public class NullGameState implements GameState {
  int currentLevel = 0;
  int timeRemaining = 0;
  MazeState mazeState = new NullMazeState();
  int totalTime = 0;

  @Override
  public int getCurrentLevel() {
    return currentLevel;
  }

  @Override
  public int getTimeRemaining() {
    return 0;
  }

  @Override
  public void setCurrentLevel(int currentLevel) {
  }

  @Override
  public void setTimeRemaining(int timeRemaining) {
  }

  @Override
  public MazeState getMazeState() {
    return new NullMazeState();
  }

  @Override
  public void setMazeState(MazeState mazeState) {
  }

  @Override
  public void setTotalLevelTime(int time) {
  }

  @Override
  public int getTotalLevelTime() {
    return totalTime;
  }

}
