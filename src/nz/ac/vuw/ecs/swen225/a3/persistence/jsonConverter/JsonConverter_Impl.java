package nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import nz.ac.vuw.ecs.swen225.a3.application.GameState_Impl;
import nz.ac.vuw.ecs.swen225.a3.application.NullGameState;
import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.GameState;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.MazeState_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.NullMazeState;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.ActorInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Bug;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Enemy;
import nz.ac.vuw.ecs.swen225.a3.maze.items.ItemInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.TileInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.plugin.mazeElementLoader;

/**
 * JsonConverter_Impl implements the JsonConverter interface.
 *
 * @author cullingene
 */
public class JsonConverter_Impl implements JsonConverter {

  /*
   * Game State
   */
  @Override
  public JsonObject getGameStateJson(GameState gameState) {
    MazeState mazeState = gameState.getMazeState();
    JsonObject mazeStateJsonObject = getMazeStateJson(mazeState);

    JsonObject gameStateJsonObject = Json.createObjectBuilder()
        .add("level", gameState.getCurrentLevel())
        .add("timeRemaining", gameState.getTimeRemaining())
        .add("totalTime", gameState.getTotalLevelTime()).add("mazeState", mazeStateJsonObject)
        .build();
    return gameStateJsonObject;
  }

  @Override
  public GameState getGameStateFromJson(JsonObject gameStateJson) {
    assert (gameStateJson != null);

    GameState gameState = new NullGameState();
    int level = gameStateJson.getInt("level");
    int timeRemaining = gameStateJson.getInt("timeRemaining");
    int totalTime = gameStateJson.getInt("totalTime");
    JsonObject mazeStateJson = gameStateJson.getJsonObject("mazeState");
    MazeState mazeState = getMazeStateFromJson(mazeStateJson);

    gameState = new GameState_Impl(level, timeRemaining, totalTime, mazeState);
    return gameState;
  }

  /*
   * Maze State
   */
  @Override
  public JsonObject getMazeStateJson(MazeState mazeState) {

    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    jsonObjectBuilder.add("width", mazeState.getWidth()).add("height", mazeState.getHeight())
        .add("numChips", mazeState.getNumChips())
        .add("board",
            getBoardJson(mazeState.getBoard(), mazeState.getHeight(), mazeState.getWidth()))
        .add("chap", getActorInfoJson(mazeState.getChap()));

    jsonObjectBuilder.add("inventory", getInventoryJson(mazeState.getInventory()));
    jsonObjectBuilder.add("items", getMazeItemsSetJson(mazeState.getItems()));
    jsonObjectBuilder.add("enemies", getEnemiesSetJson(mazeState.getEnemies()));

    JsonObject jsonObject = jsonObjectBuilder.build();

    return jsonObject;
  }

  @Override
  public MazeState getMazeStateFromJson(JsonObject mazeStateJson) {
    assert (mazeStateJson != null);

    int width = mazeStateJson.getInt("width");
    int height = mazeStateJson.getInt("height");
    int numChips = mazeStateJson.getInt("numChips");
    MazeState mazeState = new MazeState_Impl(width, height, numChips);

    JsonObject chapJson = mazeStateJson.getJsonObject("chap");
    ActorInfo chap = getActorInfoFromJson(chapJson);
    mazeState.setChap(chap);

    JsonObject boardJson = mazeStateJson.getJsonObject("board");
    TileInfo[][] board = getBoardFromJson(boardJson, height, width);
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        mazeState.setTileAt(board[col][row], col, row);
      }
    }

    JsonArray inventoryJsonArray = mazeStateJson.getJsonArray("inventory");
    List<ItemInfo> inventory = getInventoryFromJson(inventoryJsonArray);
    for (ItemInfo inventoryItem : inventory) {
      mazeState.addToInventory(inventoryItem);
    }

    JsonArray mazeItemSetJsonArray = mazeStateJson.getJsonArray("items");
    Set<ItemInfo> items = getMazeItemsSetFromJson(mazeItemSetJsonArray);
    for (ItemInfo item : items) {
      mazeState.additem(item);
    }

    JsonArray mazeEnemiesSetJsonArray = mazeStateJson.getJsonArray("enemies");
    Set<ActorInfo> enemies = getMazeEnemiesSetFromJson(mazeEnemiesSetJsonArray);
    for (ActorInfo enemy : enemies) {
      mazeState.addEnemy(enemy);
    }

    return mazeState;
  }

  /*
   * Actor
   */
  @Override
  public JsonObject getActorInfoJson(ActorInfo actorInfo) {
    assert (actorInfo != null);

    JsonObject jsonObject = Json.createObjectBuilder().add("name", actorInfo.getName())
        .add("location", getPointJson(actorInfo.getLocation()))
        .add("direction", actorInfo.getFacingDir().toString())
        .add("fields", getActorInfoFieldsJson(actorInfo)).build();
    return jsonObject;
  }

  @Override
  public ActorInfo getActorInfoFromJson(JsonObject jsonObject) {
    mazeElementLoader loader = mazeElementLoader.getInstance();
    String name = jsonObject.getString("name");
    loader.loadActor(name);

    Map<String, Object> actorInfoFields = new HashMap<String, Object>();

    JsonObject fields = jsonObject.getJsonObject("fields");

    for (String key : fields.keySet()) {
      if (key.contains(".class")) {
        continue;
      }
      String fieldClassName = fields.getString(key + ".class");
      if (fieldClassName.contains("Integer")) {
        int value = Integer.parseInt(fields.getString(key));
        actorInfoFields.put(key, value);
      }
      if (fieldClassName.contains("String")) {
        String value = fields.getString(key);
        actorInfoFields.put(key, value);
      }
      if (fieldClassName.contains("Boolean")) {
        Boolean value = Boolean.parseBoolean(fields.getString(key));
        actorInfoFields.put(key, value);
      }
    }

    if (actorInfoFields.size() == 0) {
      actorInfoFields = null; // This was part of the original enemy info
    }

    Point location = getPointFromJson(jsonObject.getJsonObject("location"));
    Direction direction = Direction.valueOf(jsonObject.getString("direction"));

    ActorInfo actorInfo = new ActorInfo_Impl(name, null, location, direction, actorInfoFields);

    return actorInfo;
  }

  @Override
  public JsonValue getActorInfoFieldsJson(ActorInfo actorInfo) {
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    for (String fieldName : actorInfo.getFieldNames()) {
      jsonObjectBuilder.add(fieldName, actorInfo.getField(fieldName).toString());
      jsonObjectBuilder.add(fieldName + ".class",
          actorInfo.getField(fieldName).getClass().getName());
    }

    JsonObject jsonObject = jsonObjectBuilder.build();

    return jsonObject;
  }

  /*
   * Point
   */
  @Override
  public Point getPointFromJson(JsonObject jsonObject) {
    int x = jsonObject.getInt("x");
    int y = jsonObject.getInt("y");

    Point point = new Point(x, y);
    return point;
  }

  @Override
  public JsonValue getPointJson(Point location) {
    JsonObject jsonObject = Json.createObjectBuilder().add("x", location.getX())
        .add("y", location.getY()).build();
    return jsonObject;
  }

  /*
   * Board
   */
  @Override
  public TileInfo[][] getBoardFromJson(JsonObject jsonObject, int height, int width) {
    assert (jsonObject != null);

    TileInfo[][] board = new TileInfo[height][width];
    JsonObject tilesetJson = jsonObject.getJsonObject("tileset");
    JsonObject boardJson = jsonObject.getJsonObject("board");

    Map<String, TileInfo> tileset = getTilesetFromJson(tilesetJson);

    for (int row = 0; row < height; row++) {
      JsonArray rowJson = boardJson.getJsonArray("row " + row);
      for (int col = 0; col < width; col++) {

        String tileName = rowJson.getString(col);
        TileInfo tile = tileset.get(tileName);
        board[col][row] = tile;
      }
    }

    return board;
  }

  @Override
  public JsonObject getBoardJson(TileInfo[][] board, int height, int width) {
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    JsonObjectBuilder jsonObjectBuilderBoard = Json.createObjectBuilder();
    Set<TileInfo> tileSet = new HashSet<TileInfo>();

    for (int row = 0; row < height; row++) {
      JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
      for (int col = 0; col < width; col++) {

        TileInfo tileInfo = board[col][row];
        if (!tileSet.contains(tileInfo)) {
          tileSet.add(tileInfo);
        }

        String tileName = tileInfo.getName();

        if (tileInfo.hasField("id")) {
          tileName += tileInfo.getField("id");
        }

        arrayBuilder.add(tileName);
      }
      jsonObjectBuilderBoard.add("row " + row, arrayBuilder.build());
    }

    JsonObject tileSetJson = getTileInfoSetJson(tileSet);
    jsonObjectBuilder.add("tileset", tileSetJson);
    jsonObjectBuilder.add("board", jsonObjectBuilderBoard.build());
    JsonObject jsonObject = jsonObjectBuilder.build();

    return jsonObject;
  }

  /*
   * Tileset
   */
  @Override
  public JsonObject getTileInfoSetJson(Set<TileInfo> tileSet) {
    JsonObjectBuilder tilesetObjectBuilder = Json.createObjectBuilder();
    for (TileInfo tileInfo : tileSet) {
      JsonObject tileJson = getTileInfoJson(tileInfo);
      String tileName = tileInfo.getName();
      if (tileInfo.hasField("id")) {
        tileName += tileInfo.getField("id");
      }
      tilesetObjectBuilder.add(tileName, tileJson);

    }
    return tilesetObjectBuilder.build();
  }

  @Override
  public Map<String, TileInfo> getTilesetFromJson(JsonObject tilesetJson) {
    Map<String, TileInfo> tileset = new HashMap<String, TileInfo>();

    for (String tileName : tilesetJson.keySet()) {
      //System.out.println("Tile Name: " + tileName + " \n");

      JsonObject tileInfoJson = tilesetJson.getJsonObject(tileName);
      TileInfo tileInfo = getTileInfoFromJson(tileInfoJson);
      tileset.put(tileName, tileInfo);
    }

    return tileset;
  }

  /*
   * Tiles
   */
  @Override
  public JsonObject getTileInfoJson(TileInfo tileInfo) {
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    jsonObjectBuilder.add("name", tileInfo.getName());

    JsonObjectBuilder jsonObjectBuilderFields = Json.createObjectBuilder();
    for (String fieldName : tileInfo.getFieldNames()) {
      jsonObjectBuilderFields.add(fieldName, tileInfo.getField(fieldName).toString());
      jsonObjectBuilderFields.add(fieldName + ".class",
          tileInfo.getField(fieldName).getClass().getName());
    }

    jsonObjectBuilder.add("fields", jsonObjectBuilderFields.build());
    JsonObject jsonObject = jsonObjectBuilder.build();
    return jsonObject;
  }

  @Override
  public TileInfo getTileInfoFromJson(JsonObject tileInfoJson) {
    String name = tileInfoJson.getString("name");
    HashMap<String, Object> tileInfoFields = getFieldsFromJsonObject(tileInfoJson);

    mazeElementLoader loader = mazeElementLoader.getInstance();
    loader.loadTile(name);

    TileInfo tileInfo = new TileInfo_Impl(name, null, tileInfoFields);

    return tileInfo;
  }

  /*
   * Inventory
   */

  private JsonArray getInventoryJson(List<ItemInfo> inventory) {
    assert (inventory != null);

    boolean isInInventory = true;
    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

    for (ItemInfo itemInfo : inventory) {
      jsonArrayBuilder.add(getItemInfoJson(itemInfo, isInInventory));
    }
    JsonArray jsonArray = jsonArrayBuilder.build();

    return jsonArray;
  }

  private List<ItemInfo> getInventoryFromJson(JsonArray inventoryJsonArray) {

    List<ItemInfo> inventory = new ArrayList<ItemInfo>();
    boolean isInInventory = true;
    if (inventoryJsonArray == null) {
      return inventory;
    }
    if (inventoryJsonArray.size() == 0) {
      return inventory;
    }

    inventoryJsonArray.forEach(item -> {
      ItemInfo itemInfo = getItemInfoFromJson((JsonObject) item, isInInventory);
      inventory.add(itemInfo);
    });

    return inventory;
  }

  /*
   * Maze Items Set
   */

  @Override
  public JsonValue getMazeItemsSetJson(Set<ItemInfo> items) {
    boolean isInInventory = false;
    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

    for (ItemInfo itemInfo : items) {
      jsonArrayBuilder.add(getItemInfoJson(itemInfo, isInInventory));
    }

    return jsonArrayBuilder.build();
  }

  private Set<ItemInfo> getMazeItemsSetFromJson(JsonArray mazeItemsSetJsonArray) {
    Set<ItemInfo> items = new HashSet<ItemInfo>();
    boolean isInInventory = false;
    if (mazeItemsSetJsonArray == null) {
      return items;
    }

    mazeItemsSetJsonArray.forEach(item -> {
      ItemInfo itemInfo = getItemInfoFromJson((JsonObject) item, isInInventory);
      items.add(itemInfo);
    });
    return items;
  }

  @Override
  public JsonValue getEnemiesSetJson(Set<ActorInfo> enemies) {

    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

    for (ActorInfo actorInfo : enemies) {
      jsonArrayBuilder.add(getActorInfoJson(actorInfo));
    }

    return jsonArrayBuilder.build();
  }

  private Set<ActorInfo> getMazeEnemiesSetFromJson(JsonArray mazeEnemiesSetJsonArray) {

    Set<ActorInfo> enemies = new HashSet<ActorInfo>();

    if (mazeEnemiesSetJsonArray == null) {
      return enemies;
    }

    mazeEnemiesSetJsonArray.forEach(enemy -> {
      ActorInfo actorInfo = getActorInfoFromJson((JsonObject) enemy);
      enemies.add(actorInfo);
    });

    return enemies;
  }

  /*
   * ItemInfo
   */
  @Override
  public JsonObject getItemInfoJson(ItemInfo itemInfo, boolean isInInventory) {
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    jsonObjectBuilder.add("name", itemInfo.getName());

    if (isInInventory) {
      jsonObjectBuilder.add("location", "null");
    } else {
      jsonObjectBuilder.add("location", getPointJson(itemInfo.getLocation()));
    }

    JsonObjectBuilder jsonObjectBuilderFields = Json.createObjectBuilder();
    for (String fieldName : itemInfo.getFieldNames()) {
      jsonObjectBuilderFields.add(fieldName, itemInfo.getField(fieldName).toString());
      jsonObjectBuilderFields.add(fieldName + ".class",
          itemInfo.getField(fieldName).getClass().getName());
    }
    jsonObjectBuilder.add("fields", jsonObjectBuilderFields.build());

    JsonObject jsonObject = jsonObjectBuilder.build();
    return jsonObject;
  }

  @Override
  public ItemInfo getItemInfoFromJson(JsonObject jsonObject, boolean isInInventory) {
    mazeElementLoader loader = mazeElementLoader.getInstance();
    String name = jsonObject.getString("name");
    loader.loadItem(name);

    HashMap<String, Object> itemInfoFields = getFieldsFromJsonObject(jsonObject);
    Point location = null;
    if (!isInInventory) {
      location = getPointFromJson(jsonObject.getJsonObject("location"));
    }
    ItemInfo itemInfo = new ItemInfo_Impl(name, null, location, itemInfoFields);
    return itemInfo;

  }

  /**
   * Enemies
   */
  @Override
  public JsonObject getEnemyJson(Enemy enemy) {
    ActorInfo actorInfo = enemy.getInfo();
    getActorInfoJson(actorInfo);

    return getActorInfoJson(actorInfo);
  }

  @Override
  public Enemy getEnemyFromJson(JsonObject jsonObject, TileInfo[][] board) {
    String name = jsonObject.getString("name");
    JsonObject locationJson = jsonObject.getJsonObject("location");
    Point location = getPointFromJson(locationJson);
    Direction direction = Direction.valueOf(jsonObject.getString("direction"));

    if (name == null) {
      throw new IllegalArgumentException("Json for enemy must have a name");
    }
    if (location == null) {
      throw new IllegalArgumentException("Json for enemy location must not be null");
    }
    if (direction == null) {
      throw new IllegalArgumentException("Json for enemy direction must not be null");
    }

    Enemy enemy;

    switch (name) {
    case "Bug": {
      int row = (int) location.getX();
      int col = (int) location.getY();
      TileInfo tileInfo = board[row][col];
      Tile tile = Tile.makeTile(tileInfo, location);
      enemy = new Bug(tile, direction);
      break;
    }
    default: {
      throw new IllegalArgumentException("Json for enemy is malformed");
    }
    }

    return enemy;
  }

  /**
   * Helper Methods.
   */

  private HashMap<String, Object> getFieldsFromJsonObject(JsonObject jsonObject) {
    JsonObject fields = jsonObject.getJsonObject("fields");
    HashMap<String, Object> tileInfoFields = new HashMap<String, Object>();
    for (String key : fields.keySet()) {
      if (key.contains(".class")) {
        continue;
      }
      String fieldClassName = fields.getString(key + ".class");
      if (fieldClassName.contains("Integer")) {
        int value = Integer.parseInt(fields.getString(key));
        tileInfoFields.put(key, value);
      }
      if (fieldClassName.contains("String")) {
        String value = fields.getString(key);
        tileInfoFields.put(key, value);
      }
      if (fieldClassName.contains("Boolean")) {
        Boolean value = Boolean.parseBoolean(fields.getString(key));
        tileInfoFields.put(key, value);
      }
    }
    return tileInfoFields;
  }

}
