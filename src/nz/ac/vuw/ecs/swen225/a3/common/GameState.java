package nz.ac.vuw.ecs.swen225.a3.common;

/**
 * Contains the Level, Time, and MazeState
 * 
 * @author Bryony
 *
 */
public interface GameState {
  /**
   * Returns Level
   * 
   * @return returns an integer
   */
  int getCurrentLevel();

  /**
   * Returns Time Remaining
   * 
   * @return returns an integer
   */
  int getTimeRemaining();

  /**
   * Sets Level to given parameter
   * 
   * @param currentLevel
   */
  void setCurrentLevel(int currentLevel);

  /**
   * Sets Remaining Time to given parameter
   * 
   * @param timeRemaining
   */
  void setTimeRemaining(int timeRemaining);

  /**
   * Returns MazeState
   * 
   * @return returns a MazeState
   */
  MazeState getMazeState();

  /**
   * Sets MazeState as given parameter
   * 
   * @param mazeState
   */
  void setMazeState(MazeState mazeState);

  /**
   * Sets Total time as given parameter
   * 
   * @param time
   */
  void setTotalLevelTime(int time);

  /**
   * Returns total time allowed for that level
   * 
   * @return returns an integer
   */
  int getTotalLevelTime();
}
