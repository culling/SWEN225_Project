package nz.ac.vuw.ecs.swen225.a3.common;

/**
 * Application Interface - Controls User Input
 * 
 * @author Bryony
 *
 */
public interface Application {

  /**
   * Returns the Current Time
   * 
   * @return Returns an int
   */
  public int getTime();

  /**
   * Returns the Current GameState
   * 
   * @return Returns a GameState
   */
  public GameState getGameState();

  /**
   * Call when Chap has won a level
   */
  public void won();

  /**
   * Call when Chap has lost a level
   */
  public void lost();

  /**
   * Call when Chap is on an information tile
   * 
   * @param message
   */
  public void info(String message);

  /**
   * Passes Current GameState to Persistence to save
   */
  public void save();

  /**
   * Passes Direction for Chap's movement to Maze
   * 
   * @param d
   */
  public void moveChap(Direction d);

  /**
   * Creates a GameState for the start of the level Passes GameState to Persistence to save
   */
  public void quit();

  /**
   * Gets a GameState from Persistence, sets as current GameState and passes MazeState to Maze.
   * Loads last full Game
   */
  public void loadGame();

  /**
   * Gets a GameState from Persistence, sets as current GameState and passes MazeState to Maze.
   * Loads last level
   */
  public void loadLevel();

  /**
   * Gets level 1 GameState from Persistence, sets as current GameState and passes MazeState to Maze
   */
  public void newGame();

  /**
   * Pauses Timer and stops Chap from moving
   */
  public void pause();

  /**
   * Starts Timer again
   */
  public void stopPause();

  /**
   * Shows rules in a pop-up box, stops timer until box closed
   */
  public void help();

}
