package nz.ac.vuw.ecs.swen225.a3.tests.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.TileInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter.JsonConverter;
import nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter.JsonConverter_Impl;

class JsonObjectConversionForTileInfoTest {
  @BeforeEach
  void initalize() {
    Maze_Impl.init();
  }

  @Test
  void getTileInfoJson_shouldHaveName_1() {
    JsonConverter jsonConverter = new JsonConverter_Impl();
    Map<String, Object> tileInfoFields = new HashMap();

    TileInfo tileInfo = new TileInfo_Impl("Wall", null, tileInfoFields);

    JsonObject jsonObject = jsonConverter.getTileInfoJson(tileInfo);

    assertEquals("Wall", jsonObject.getString("name"));
  }

  @Test
  void getTileInfoJson_shouldBeEqual_Wall_1() {
    JsonConverter jsonConverter = new JsonConverter_Impl();
    Map<String, Object> tileInfoFields = new HashMap();

    TileInfo tileInfo = new TileInfo_Impl("Wall", null, tileInfoFields);

    JsonObject jsonObject = jsonConverter.getTileInfoJson(tileInfo);

    assertEquals("{\"name\":\"Wall\",\"fields\":{}}", jsonObject.toString());
  }

  @Test
  void getTileInfoFromJson_Wall_1() {
    JsonConverter jsonConverter = new JsonConverter_Impl();
    Map<String, Object> tileInfoFields = new HashMap<String, Object>();

    TileInfo tileInfoOriginal = new TileInfo_Impl("Wall", null, tileInfoFields);

    JsonObject jsonObject = jsonConverter.getTileInfoJson(tileInfoOriginal);
    TileInfo tileInfo = jsonConverter.getTileInfoFromJson(jsonObject);
    assertEquals(tileInfoOriginal, tileInfo);
  }

  @Test
  void getTileInfoFromJson_LockedDoor_1() {

    // "name":"LockedDoor","fields":{"isOpen":"false","isOpen.class":"java.lang.Boolean","id":"1","id.class":"java.lang.Integer"}
    JsonConverter jsonConverter = new JsonConverter_Impl();
    Map<String, Object> tileInfoFields = new HashMap<String, Object>();
    tileInfoFields.put("isOpen", false);
    tileInfoFields.put("id", 1);

    TileInfo tileInfoOriginal = new TileInfo_Impl("LockedDoor", null, tileInfoFields);

    JsonObject jsonObject = jsonConverter.getTileInfoJson(tileInfoOriginal);
    TileInfo tileInfo = jsonConverter.getTileInfoFromJson(jsonObject);
    assertEquals(tileInfoOriginal, tileInfo);
  }

  @Test
  void TestTileInfo_ShouldBeValid() {
    JsonConverter jsonConverter = new JsonConverter_Impl();
    TileInfo tileInfoOriginal = new TestTileInfo();

    JsonObject jsonObject = jsonConverter.getTileInfoJson(tileInfoOriginal);
    TileInfo tileInfo = jsonConverter.getTileInfoFromJson(jsonObject);
    assertEquals(tileInfoOriginal.toString(), tileInfo.toString());
  }

}
