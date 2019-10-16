package nz.ac.vuw.ecs.swen225.a3.tests.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.ActorInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Bug;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Enemy;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Free;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.TileInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter.JsonConverter;
import nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter.JsonConverter_Impl;

class JsonObjectConversionForActorInfoTest {
  @BeforeEach
  void initalize() {
    Maze_Impl.init();
  }

  @Test
  void getActorInfoJson_shouldHaveName_1() {
    JsonConverter jsonConverter = new JsonConverter_Impl();
    Map<String, Object> actorInfoFields = new HashMap<String, Object>();
    actorInfoFields.put("chipsCollected", 0);
    ActorInfo actorInfo = new ActorInfo_Impl("Chap", null, new Point(1, 1), Direction.NORTH,
        actorInfoFields);

    JsonObject jsonObject = jsonConverter.getActorInfoJson(actorInfo);

    assertEquals("Chap", jsonObject.getString("name"));
  }

  @Test
  void getActorInfoJson_shouldHaveCorrectDirection_1() {
    JsonConverter jsonConverter = new JsonConverter_Impl();
    Map<String, Object> actorInfoFields = new HashMap<String, Object>();
    actorInfoFields.put("chipsCollected", 0);
    ActorInfo actorInfo = new ActorInfo_Impl("Chap", null, new Point(1, 1), Direction.NORTH,
        actorInfoFields);

    JsonObject jsonObject = jsonConverter.getActorInfoJson(actorInfo);

    assertEquals("NORTH", jsonObject.getString("direction"));
  }

  @Test
  void getActorInfoJson_shouldHaveCorrectLocation_1() {
    JsonConverter jsonConverter = new JsonConverter_Impl();
    Map<String, Object> actorInfoFields = new HashMap<String, Object>();
    actorInfoFields.put("chipsCollected", 0);
    ActorInfo actorInfo = new ActorInfo_Impl("Chap", null, new Point(1, 1), Direction.NORTH,
        actorInfoFields);

    JsonObject jsonObject = jsonConverter.getActorInfoJson(actorInfo);

    assertEquals("{\"x\":1.0,\"y\":1.0}", jsonObject.get("location").toString());
  }

  @Test
  void getActorInfoJson_shouldHaveCorrectFields_1() {
    JsonConverter jsonConverter = new JsonConverter_Impl();
    Map<String, Object> actorInfoFields = new HashMap<String, Object>();
    actorInfoFields.put("chipsCollected", 0);
    ActorInfo actorInfo = new ActorInfo_Impl("Chap", null, new Point(1, 1), Direction.NORTH,
        actorInfoFields);

    JsonObject jsonObject = jsonConverter.getActorInfoJson(actorInfo);

    JsonObject fields = jsonObject.getJsonObject("fields");
    assertEquals("0", fields.getString("chipsCollected"));
  }

  /// REHYDRATE ACTOR INFO
  @Test
  void getActorInfoFromJson_shouldHaveCorrectName_1() {
    JsonConverter jsonConverter = new JsonConverter_Impl();
    Map<String, Object> actorInfoFields = new HashMap<String, Object>();
    actorInfoFields.put("chipsCollected", 0);
    ActorInfo actorInfoOriginal = new ActorInfo_Impl("Chap", null, new Point(1, 1), Direction.NORTH,
        actorInfoFields);

    JsonObject jsonObject = jsonConverter.getActorInfoJson(actorInfoOriginal);

    ActorInfo actorInfo = jsonConverter.getActorInfoFromJson(jsonObject);

    assertEquals(actorInfoOriginal.getName(), actorInfo.getName());

  }

  @Test
  void getActorInfoFromJson_shouldBeEqualToOriginal_1() {
    JsonConverter jsonConverter = new JsonConverter_Impl();
    Map<String, Object> actorInfoFields = new HashMap<String, Object>();
    actorInfoFields.put("chipsCollected", 0);
    ActorInfo actorInfoOriginal = new ActorInfo_Impl("Chap", null, new Point(1, 2), Direction.NORTH,
        actorInfoFields);

    JsonObject jsonObject = jsonConverter.getActorInfoJson(actorInfoOriginal);

    ActorInfo actorInfo = jsonConverter.getActorInfoFromJson(jsonObject);

    assertEquals(actorInfoOriginal.toString(), actorInfo.toString());
  }

  @Test
  void getEnemyJSON_shouldHaveTheRightName_1() {
    JsonConverter jsonConverter = new JsonConverter_Impl();
    Point location = new Point(1, 1);
    Tile tile = new Free(location);
    Enemy enemy = new Bug(tile, Direction.EAST);

    JsonObject jsonObject = jsonConverter.getEnemyJson(enemy);
    assertEquals("Bug", jsonObject.getString("name"));
  }

  @Test
  void getEnemyJSON_shouldHavetheRightLocation_1() {
    JsonConverter jsonConverter = new JsonConverter_Impl();
    Point location = new Point(1, 1);
    Tile tile = new Free(location);
    Enemy enemy = new Bug(tile, Direction.EAST);

    JsonObject jsonObject = jsonConverter.getEnemyJson(enemy);
    JsonObject locationJson = jsonObject.getJsonObject("location");

    assertEquals(1, locationJson.getInt("x"));
    assertEquals(1, locationJson.getInt("y"));
  }

  @Test
  void getEnemyJSON_shouldHavetheRightLocation_2() {
    JsonConverter jsonConverter = new JsonConverter_Impl();
    Point originalLocation = new Point(1, 1);
    Tile tile = new Free(originalLocation);
    Enemy enemy = new Bug(tile, Direction.EAST);

    JsonObject jsonObject = jsonConverter.getEnemyJson(enemy);
    JsonObject locationJson = jsonObject.getJsonObject("location");

    Point location = jsonConverter.getPointFromJson(locationJson);
    assertEquals(originalLocation, location);
  }

  @Test
  void getEnemyFromJson_shouldBeEqualWhenRecreated_1() {
    JsonConverter jsonConverter = new JsonConverter_Impl();
    Point originalLocation = new Point(1, 1);
    int boardHeight = 2;
    int boardWidth = 2;
    TileInfo[][] board = new TileInfo[boardWidth][boardHeight];

    for (int row = 0; row < boardWidth; row++) {
      for (int col = 0; col < boardHeight; col++) {
        board[row][col] = new TestTileInfo();
      }
    }

    Tile tile = new Free(originalLocation);
    Enemy originalEnemy = new Bug(tile, Direction.EAST);
    JsonObject jsonObject = jsonConverter.getEnemyJson(originalEnemy);

    Enemy enemy = jsonConverter.getEnemyFromJson(jsonObject, board);

    assertEquals(originalEnemy, enemy);
  }
}
