package nz.ac.vuw.ecs.swen225.a3.tests.application;

import javax.json.JsonObject;

import nz.ac.vuw.ecs.swen225.a3.application.GameState_Impl;
import nz.ac.vuw.ecs.swen225.a3.common.GameState;
import nz.ac.vuw.ecs.swen225.a3.common.Persistence;

/**
 * Fake Persistence for Application Package Tests
 *
 * @author Bryony
 *
 */
public class FakePersistence implements Persistence {

  private boolean saved = false;
  private boolean quit = false;

  @Override
  public GameState loadLastLevel() {
    return new GameState_Impl(1, 10, 100, new FakeMazeState(1));
  }

  @Override
  public GameState loadLastGame() {
    return new GameState_Impl(2, 10, 100, new FakeMazeState(2));
  }

  @Override
  public GameState loadLevel(int levelNumber) {
    return new GameState_Impl(levelNumber, 10, 100, new FakeMazeState(levelNumber));
  }

  @Override
  public void save(GameState gameState) {
    saved = true;
  }

  @Override
  public JsonObject getGameStateJson(GameState gameState) {
    return null;
  }

  @Override
  public void quit(int levelNumber) {
    quit = true;
  }

  @Override
  public GameState newGame() {
    return new GameState_Impl(0, 10, 100, new FakeMazeState(0));
  }

  /**
   * Returns if quit was executed
   * 
   * @return returns a boolean
   */
  public boolean quitCheck() {
    return quit;
  }

  /**
   * Returns if save was executed
   * 
   * @return returns a boolean
   */
  public boolean saveCheck() {
    return saved;
  }

  @Override
  public GameState load(String fileName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void save(GameState gameState, String fileName) {
    saved = true;

  }

}
