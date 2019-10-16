package nz.ac.vuw.ecs.swen225.a3.tests.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.items.ItemInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter.JsonConverter;
import nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter.JsonConverter_Impl;

class JsonObjectConversionForItemInfoTest {
  @BeforeEach
  void initalize() {
    Maze_Impl.init();
  }

  @Test
  void getItemInfoJson_shouldHaveName_1() {
    Map<String, Object> key1Fields = new HashMap<String, Object>();
    key1Fields.put("id", 0);
    ItemInfo itemInfo = new ItemInfo_Impl("Key", null, null, key1Fields);

    JsonConverter jsonConverter = new JsonConverter_Impl();
    boolean isInInventory = true;
    JsonObject jsonObject = jsonConverter.getItemInfoJson(itemInfo, isInInventory);

    System.out.println("jsonObject.toString(): " + jsonObject.toString());

    assertEquals("Key", jsonObject.getString("name"));
  }

}
