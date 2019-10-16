package nz.ac.vuw.ecs.swen225.a3.tests.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.MazeState_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.ActorInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Bug;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Enemy;
import nz.ac.vuw.ecs.swen225.a3.maze.items.ItemInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.TileInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter.JsonConverter;
import nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter.JsonConverter_Impl;

class JsonObjectConversionForMazeStateTest {
  @BeforeEach
  void initalize() {
    Maze_Impl.init();
  }

  @Test
  void getMazeStateJson_shouldHave1Chip_1() {
    MazeState mazeState = makeSimpleMazeState();
    JsonConverter jsonConverter = new JsonConverter_Impl();
    JsonObject jsonObject = jsonConverter.getMazeStateJson(mazeState);

    assertEquals(1, jsonObject.getInt("numChips"));
  }

  @Test
  void getMazeStateFromJson_shouldHave1Chip_1() {
    MazeState mazeStateOriginal = makeSimpleMazeState();
    JsonConverter jsonConverter = new JsonConverter_Impl();
    JsonObject jsonObject = jsonConverter.getMazeStateJson(mazeStateOriginal);

    MazeState mazeState = jsonConverter.getMazeStateFromJson(jsonObject);

    assertEquals(mazeStateOriginal.getNumChips(), mazeState.getNumChips());
  }

  @Test
  void getMazeStateJson_shouldHave1CorrectHeight_1() {
    MazeState mazeState = makeSimpleMazeState();
    JsonConverter jsonConverter = new JsonConverter_Impl();
    JsonObject jsonObject = jsonConverter.getMazeStateJson(mazeState);

    assertEquals(4, jsonObject.getInt("height"));
  }

  @Test
  void getMazeStateJson_shouldHave1CorrectWidth_1() {
    MazeState mazeState = makeSimpleMazeState();
    JsonConverter jsonConverter = new JsonConverter_Impl();
    JsonObject jsonObject = jsonConverter.getMazeStateJson(mazeState);

    assertEquals(4, jsonObject.getInt("width"));
  }

  @Test
  void getMazeStateJson_shouldHave1CorrectnumChips_1() {
    MazeState mazeState = makeSimpleMazeState();
    JsonConverter jsonConverter = new JsonConverter_Impl();
    JsonObject jsonObject = jsonConverter.getMazeStateJson(mazeState);

    assertEquals(1, jsonObject.getInt("numChips"));
  }

  /*
   * Inventory
   */

  @Test
  void getMazeStateJson_shouldHave1Inventory_1() {
    MazeState mazeState = makeMazeStateWithItems();
    JsonConverter jsonConverter = new JsonConverter_Impl();
    JsonObject jsonObject = jsonConverter.getMazeStateJson(mazeState);

    assertEquals(
        "[{\"name\":\"Key\",\"location\":\"null\",\"fields\":{\"id\":\"0\",\"id.class\":\"java.lang.Integer\"}}]",
        jsonObject.get("inventory").toString());
  }

  @Test
  void getMazeStateFromJson_shouldHave1Inventory_1() {
    MazeState mazeStateOriginal = makeMazeStateWithItems();
    mazeStateOriginal.getInventory();

    JsonConverter jsonConverter = new JsonConverter_Impl();
    JsonObject jsonObject = jsonConverter.getMazeStateJson(mazeStateOriginal);

    MazeState mazeState = jsonConverter.getMazeStateFromJson(jsonObject);

    assertEquals(mazeStateOriginal.getInventory(), mazeState.getInventory());
  }

  @Test
  void getMazeStateJson_shouldHave1Items_1() {
    MazeState mazeStateOriginal = makeMazeStateWithItems();
    mazeStateOriginal.getInventory();

    JsonConverter jsonConverter = new JsonConverter_Impl();
    JsonObject jsonObject = jsonConverter.getMazeStateJson(mazeStateOriginal);

    MazeState mazeState = jsonConverter.getMazeStateFromJson(jsonObject);

    assertEquals(mazeStateOriginal.getItems(), mazeState.getItems());
  }

  @Test
  void getMazeStateJson_shouldHave1Enemies_1() {
    MazeState mazeStateOriginal = makeMazeStateWithItems();
    Point location = new Point(1, 1);
    TileInfo tileInfo = mazeStateOriginal.getTileAt(location.x, location.y);

    Tile tile = Tile.makeTile(tileInfo, location);
    Direction direction = Direction.EAST;
    Enemy enemy = new Bug(tile, direction);

    mazeStateOriginal.addEnemy(enemy.getInfo());

    JsonConverter jsonConverter = new JsonConverter_Impl();
    JsonObject jsonObject = jsonConverter.getMazeStateJson(mazeStateOriginal);

    System.out.println("jsonObject: " + jsonObject);

    MazeState mazeState = jsonConverter.getMazeStateFromJson(jsonObject);
    System.out.println("mazeState: " + mazeState);

    assertEquals(mazeStateOriginal.getEnemies().toString(), mazeState.getEnemies().toString());
  }

  public MazeState makeSimpleMazeState() {
    MazeState state = new MazeState_Impl(4, 4, 1);

    // add Chap
    Map<String, Object> chapFields = new HashMap();
    chapFields.put("chipsCollected", 0);
    state.setChap(new ActorInfo_Impl("Chap", null, new Point(1, 1), Direction.NORTH, chapFields));

    // add inventory
    state.setInventory(new ArrayList<ItemInfo>());

    // add board/tiles
    TileInfo wall = new TileInfo_Impl("Wall", null, new HashMap<String, Object>());
    TileInfo free = new TileInfo_Impl("Free", null, new HashMap<String, Object>());

    state.setTileAt(wall, 0, 0);
    state.setTileAt(wall, 1, 0);
    state.setTileAt(wall, 2, 0);
    state.setTileAt(wall, 3, 0);
    state.setTileAt(wall, 0, 3);
    state.setTileAt(wall, 1, 3);
    state.setTileAt(wall, 2, 3);
    state.setTileAt(wall, 3, 3);
    state.setTileAt(wall, 0, 1);
    state.setTileAt(wall, 0, 2);
    state.setTileAt(wall, 3, 1);
    state.setTileAt(wall, 3, 2);

    state.setTileAt(free, 1, 1);
    state.setTileAt(free, 1, 2);
    state.setTileAt(free, 2, 1);
    state.setTileAt(free, 2, 2);

    return state;
  }

  public static MazeState makeMazeStateWithItems() {
    MazeState state = new MazeState_Impl(4, 4, 3);

    // add Chap
    Map<String, Object> chapFields = new HashMap();
    chapFields.put("chipsCollected", 2);
    state.setChap(new ActorInfo_Impl("Chap", null, new Point(1, 1), Direction.NORTH, chapFields));

    // add inventory
    List<ItemInfo> inventory = new ArrayList<ItemInfo>();
    Map<String, Object> key1Fields = new HashMap();
    key1Fields.put("id", 0);
    inventory.add(new ItemInfo_Impl("Key", null, null, key1Fields));
    state.setInventory(inventory);

    // add items
    Map<String, Object> key2Fields = new HashMap();
    key2Fields.put("id", 1);
    state.additem(new ItemInfo_Impl("Key", null, new Point(1, 2), key2Fields));
    state.additem(new ItemInfo_Impl("Chip", null, new Point(2, 1), new HashMap<String, Object>()));

    // add board/tiles
    TileInfo wall = new TileInfo_Impl("Wall", null, new HashMap<String, Object>());
    TileInfo free = new TileInfo_Impl("Free", null, new HashMap<String, Object>());

    Map<String, Object> doorfields = new HashMap<String, Object>();
    doorfields.put("id", 1);
    doorfields.put("isOpen", false);
    TileInfo door = new TileInfo_Impl("LockedDoor", null, doorfields);

    state.setTileAt(wall, 0, 0);
    state.setTileAt(wall, 1, 0);
    state.setTileAt(wall, 2, 0);
    state.setTileAt(wall, 3, 0);
    state.setTileAt(wall, 0, 3);
    state.setTileAt(wall, 1, 3);
    state.setTileAt(wall, 2, 3);
    state.setTileAt(wall, 3, 3);
    state.setTileAt(wall, 0, 1);
    state.setTileAt(wall, 0, 2);
    state.setTileAt(wall, 3, 1);
    state.setTileAt(wall, 3, 2);

    state.setTileAt(free, 1, 1);
    state.setTileAt(free, 1, 2);
    state.setTileAt(free, 2, 1);
    state.setTileAt(door, 2, 2);

    return state;
  }

}
