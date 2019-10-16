package nz.ac.vuw.ecs.swen225.a3.common;

import javax.json.JsonObject;

/**
 * Persistence Interface. The persistence interface is used by the application to + load games +
 * save games
 *
 * JSON Object is also included for testing and debugging purposes
 * 
 * @author cullingene
 *
 */
public interface Persistence {
  /**
   * Load the game from the file at filename.
   * 
   * @param fileName
   *          to load the game state from
   * @return GameState stored in the file
   */
  public GameState load(String fileName);

  /**
   * Load last level.
   *
   * @return GameState for the last level
   */
  public GameState loadLastLevel();

  /**
   * Load Last Game.
   *
   * @return the game state for the last game
   */
  public GameState loadLastGame();

  /**
   * Load the Game for level with levelNumber.
   *
   * @param levelNumber
   *          to load
   * @return a game state from the level to load
   */
  public GameState loadLevel(int levelNumber);

  /**
   * Saves the Current State of Game then quits.
   * 
   * @param gameState
   *          to save
   */
  public void save(GameState gameState);

  /**
   * Save the gameState to the file name.
   * 
   * @param gameState
   *          to save to disk
   * @param fileName
   *          of the file to save the game state in
   */
  public void save(GameState gameState, String fileName);

  /**
   * Get the JSON object for a Game State object.
   *
   * @param gameState
   *          to turn into JSON
   * @return JSON object for the game state
   */
  public JsonObject getGameStateJson(GameState gameState);

  /**
   * Saves which level is last unfinished then quits.
   * 
   * @param levelNumber
   *          is the current level
   */
  public void quit(int levelNumber);

  /**
   * Loads a new game from level 1.
   * 
   * @return a game state from level 1.
   */
  public GameState newGame();
}
