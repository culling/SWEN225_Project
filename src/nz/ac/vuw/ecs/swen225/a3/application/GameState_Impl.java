package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.common.GameState;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.maze.NullMazeState;

/**
 * An Implementation of the GameState class
 *
 * @author Bryony
 *
 */
public class GameState_Impl implements GameState {
  private int currentLevel = 1;
  private int timeRemaining = 0;
  private int totalTime = 0;
  private MazeState mazeState = new NullMazeState();

  /**
   * Constructor for a GameState with given parameters
   *
   * @param currentLevel
   * @param timeRemaining
   * @param totalTime
   * @param mazeState
   */
  public GameState_Impl(int currentLevel, int timeRemaining, int totalTime, MazeState mazeState) {
    this.currentLevel = currentLevel;
    this.timeRemaining = timeRemaining;
    this.mazeState = mazeState;
    this.totalTime = totalTime;
  }

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

  @Override
  public void setTotalLevelTime(int time) {
    totalTime = time;
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
