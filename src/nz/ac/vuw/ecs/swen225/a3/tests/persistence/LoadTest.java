package nz.ac.vuw.ecs.swen225.a3.tests.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.application.GameState_Impl;
import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.GameState;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.common.Persistence;
import nz.ac.vuw.ecs.swen225.a3.maze.MazeState_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.ActorInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.persistence.Persistence_Impl;

class LoadTest {
  @BeforeEach
  void initalize() {
    Maze_Impl.init();
  }

  @Test
  void loadTest1ShouldPass() {
    String fileName = "saves/tests/load-test-1.json";

    Persistence persistence = new Persistence_Impl();
    GameState gameState = new TestGameState();
    assertTrue(persistence != null);
    gameState.setCurrentLevel(1);

    persistence.save(gameState, fileName);
    GameState loadedGameState = persistence.load(fileName);

    assertTrue(gameState.equals(loadedGameState));
  }

  @Test
  void loadTest2ShouldPass() {
    String fileName = "saves/tests/load-test-2.json";
    Persistence persistence = new Persistence_Impl();
    MazeState mazeState = new TestMazeState();

    mazeState.setChap(getChap());
    GameState gameState = new TestGameState();
    assertTrue(persistence != null);
    gameState.setCurrentLevel(1);

    persistence.save(gameState, fileName);
    GameState loadedGameState = persistence.load(fileName);

    assertEquals(gameState.toString(), loadedGameState.toString());
  }

  @Test
  void newGame_1() {
    Persistence persistence = new Persistence_Impl();
    GameState gameState = persistence.newGame();
    assertTrue(gameState != null);
    assertEquals(1, gameState.getCurrentLevel());
  }

  ActorInfo getChap() {
    Point point = new Point(1, 1);
    Direction direction = Direction.EAST;
    Map<String, Object> fields = new HashMap<String, Object>();
    fields.put("chipsCollected", 0);

    ActorInfo actorInfo = new ActorInfo_Impl("Chap", null, point, direction, fields);
    return actorInfo;
  }

}
