package nz.ac.vuw.ecs.swen225.a3.tests.persistence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.common.GameState;
import nz.ac.vuw.ecs.swen225.a3.common.Persistence;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze_Impl;
import nz.ac.vuw.ecs.swen225.a3.persistence.Persistence_Impl;
import nz.ac.vuw.ecs.swen225.a3.tests.maze.MazeTests;

class SaveTest {

  @BeforeEach
  void initalize() {
    Maze_Impl.init();
  }

  @Test
  void canary() {
    String fileName = "saves/tests/canary.json";
    Persistence persistence = new Persistence_Impl();
    GameState gameState = new TestGameState();
    gameState.setMazeState(new TestMazeState());

    assertTrue(persistence != null);

    persistence.save(gameState, fileName);
  }

  @Test
  void saveTest1ShouldPass() {
    String fileName = "saves/tests/saveTest1ShouldPass.json";
    Persistence persistence = new Persistence_Impl();
    GameState gameState = new TestGameState();
    assertTrue(persistence != null);
    gameState.setCurrentLevel(1);

    persistence.save(gameState, fileName);
    GameState loadedGameState = persistence.load(fileName);

    assertTrue(gameState.equals(loadedGameState));
  }

  @Test
  void saveShouldOutputSameJsonAsRead() {
    String fileName = "saves/tests/saveShouldOutputSameJsonAsRead.json";
    Persistence persistence = new Persistence_Impl();
    GameState gameState = new TestGameState();
    assertTrue(persistence != null);
    gameState.setCurrentLevel(1);

    persistence.save(gameState, fileName);
    GameState loadedGameState = persistence.load(fileName);

    assertTrue(gameState.equals(loadedGameState));
  }

  @Test
  void saveShouldHaveSameMazeState() {
    String fileName = "saves/tests/saveShouldOutputSameJsonAsRead.json";
    Persistence persistence = new Persistence_Impl();
    GameState gameState = new TestGameState();
    assertTrue(persistence != null);
    gameState.setCurrentLevel(1);

    persistence.save(gameState, fileName);
    GameState loadedGameState = persistence.load(fileName);

    assertEquals(gameState.getMazeState().toString(), loadedGameState.getMazeState().toString());
  }

  @Test
  void saveShouldHaveSameMazeStateInventory() {
    String fileName = "saves/tests/saveShouldHaveSameMazeStateInventory.json";
    Persistence persistence = new Persistence_Impl();
    GameState gameState = new TestGameState();
    assertTrue(persistence != null);
    gameState.setCurrentLevel(1);

    persistence.save(gameState, fileName);
    GameState loadedGameState = persistence.load(fileName);

    assertEquals(gameState.getMazeState().getInventory(),
        loadedGameState.getMazeState().getInventory());
  }

  @Test
  void saveAndLoadShouldBeTheSame_1() {
    String fileName = "saves/tests/saveAndLoadShouldBeTheSame_1.json";

    Persistence persistence = new Persistence_Impl();

    GameState gameState = persistence.loadLevel(1);
    persistence.save(gameState, fileName);

    GameState loadedGameState = persistence.load(fileName);
    System.out.println("loadedGameState: " + loadedGameState.toString());
    assertEquals(gameState.toString(), loadedGameState.toString());
  }

}
