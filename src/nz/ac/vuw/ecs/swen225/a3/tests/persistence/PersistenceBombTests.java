package nz.ac.vuw.ecs.swen225.a3.tests.persistence;

import static org.junit.jupiter.api.Assertions.*;

import javax.json.JsonObject;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.application.GameState_Impl;
import nz.ac.vuw.ecs.swen225.a3.application.NullGameState;
import nz.ac.vuw.ecs.swen225.a3.common.GameState;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.common.Persistence;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze_Impl;
import nz.ac.vuw.ecs.swen225.a3.persistence.Persistence_Impl;
import nz.ac.vuw.ecs.swen225.a3.tests.maze.MazeTests;

class PersistenceBombTests {

  @BeforeEach
  void initalize() {
    Maze_Impl.init();
  }

  /*
   * CTRL-X - exit the game, the current game state will be lost, the next time the game is started,
   * it will resume from the last unfinished level CTRL-S - exit the game, saves the game state,
   * game will resume next time the application will be started CTRL-R - resume a saved game CTRL-P
   * - start a new game at the last unfinished level CTRL-1 - start a new game at level 1
   * 
   */
  @Test
  void exitCanary_1_shouldNotFail() {
    Persistence persistence = new Persistence_Impl();
    persistence.quit(1);
  }

  @Test
  void saveCanary_1_shouldNotFail() {
    Persistence persistence = new Persistence_Impl();
    GameState gameState = new TestGameState();
    assertTrue(persistence != null);
    gameState.setCurrentLevel(1);

    persistence.save(gameState);
  }

  /**
   * Resume an in progress game.
   */
  @Test
  void resumeCanary_1_shouldNotFail() {
    Persistence persistence = new Persistence_Impl();
    assertTrue(persistence != null);
    GameState gameState = persistence.loadLastGame();
    assertTrue(gameState != null);
  }

  /**
   * Start a new game at the last unfinished level.
   */
  @Test
  void restartLevelCanary_1_shouldNotFail() {

    Persistence persistence = new Persistence_Impl();
    assertTrue(persistence != null);
    GameState gameState = persistence.loadLastGame();
    assertTrue(gameState != null);
  }

  /**
   * Start a new game at level 1.
   */
  @Test
  void newGameCanary_1_shouldNotFail() {
    Persistence persistence = new Persistence_Impl();
    assertTrue(persistence != null);
    GameState gameState = persistence.loadLevel(1);
    assertFalse(gameState.equals(new NullGameState()));
    assertEquals(1, gameState.getCurrentLevel());
  }

  @Test
  void jsonShouldBeTheSame() {
    Persistence persistence = new Persistence_Impl();
    GameState testGameState = new TestGameState();
    MazeState mazeState = new TestMazeState();
    testGameState.setMazeState(mazeState);

    GameState gameState = new GameState_Impl(1, 0, 0, new TestMazeState());

    JsonObject testGameStateJson = persistence.getGameStateJson(testGameState);
    JsonObject gameStateJson = persistence.getGameStateJson(gameState);

    assertEquals(gameStateJson.toString(), testGameStateJson.toString());
  }

}
