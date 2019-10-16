package nz.ac.vuw.ecs.swen225.a3.tests.persistence;

import static org.junit.jupiter.api.Assertions.*;

import javax.json.JsonObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.common.GameState;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze_Impl;
import nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter.JsonConverter;
import nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter.JsonConverter_Impl;

class JsonObjectConversionForGameStateTest {
  @BeforeEach
  void initalize() {
    Maze_Impl.init();
  }

  @Test
  void testGetGameStateJson() {
    JsonConverter jsonConverter = new JsonConverter_Impl();
    GameState originalGameState = new TestGameState();
    assertTrue(jsonConverter != null);
    JsonObject gameStateJson = jsonConverter.getGameStateJson(originalGameState);
    GameState gameState = jsonConverter.getGameStateFromJson(gameStateJson);
    assertEquals(originalGameState, gameState);
  }

  @Test
  void testGetGameStateFromJson() {
    JsonConverter jsonConverter = new JsonConverter_Impl();
    GameState originalGameState = new TestGameState();
    assertTrue(jsonConverter != null);
    JsonObject originalGameStateJson = jsonConverter.getGameStateJson(originalGameState);
    GameState gameState = jsonConverter.getGameStateFromJson(originalGameStateJson);
    JsonObject gameStateJson = jsonConverter.getGameStateJson(originalGameState);
    assertEquals(originalGameStateJson, gameStateJson);
  }

}
