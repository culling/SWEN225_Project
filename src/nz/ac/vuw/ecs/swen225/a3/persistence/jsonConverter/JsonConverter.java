package nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter;

import java.awt.Point;
import java.util.Map;
import java.util.Set;

import javax.json.JsonObject;
import javax.json.JsonValue;

import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.GameState;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Enemy;

/**
 * JSON Converter Interface. The JSON Converter Interface is designed to work with the game state of
 * the chap's challenge game. Used by the Persistence_Impl to convert to JSON before saving, or on
 * load.
 *
 * @author cullingene
 *
 */
public interface JsonConverter {

  /**
   * Get game state JSON.
   * 
   * @param gameState
   *          to turn into JSON
   * @return a JSON object with all the information about the game state
   */
  JsonObject getGameStateJson(GameState gameState);

  /**
   * Get game state from JSON.
   * 
   * @param gameStateJson
   *          is the JSON object for the game state
   * @return a game state from the JSON object
   */
  GameState getGameStateFromJson(JsonObject gameStateJson);

  /**
   * Get Maze State JSON.
   * 
   * @param mazeState
   *          to turn into JSON
   * @return a JSON Object representing the maze state
   */
  JsonObject getMazeStateJson(MazeState mazeState);

  /**
   * Get Maze State From JSON.
   * 
   * @param mazeStateJson
   *          is the JSON object for the maze state
   * @return maze state from the JSON object
   */
  MazeState getMazeStateFromJson(JsonObject mazeStateJson);

  /**
   * Get Point JSON.
   * 
   * @param location
   *          with X and Y values to be turned into a JSON value
   * @return a JSON Value representing the point/location
   */
  JsonValue getPointJson(Point location);

  /**
   * Get Point from JSON.
   * 
   * @param jsonObject
   *          to get the Point from
   * @return a point object from the JSON object
   */
  Point getPointFromJson(JsonObject jsonObject);

  /**
   * Get Actor Info JSON.
   * 
   * @param actorInfo
   *          to turn into JSON
   * @return a JSON Object representing the actor info
   */
  JsonObject getActorInfoJson(ActorInfo actorInfo);

  /**
   * Get Actor info Fields JSON.
   * 
   * @param actorInfo
   *          to get the Fields from and turn into JSON
   * @return a JSON value representing the fields for the actor info
   */
  JsonValue getActorInfoFieldsJson(ActorInfo actorInfo);

  /**
   * Get Actor Info from fields.
   * 
   * @param jsonObject
   *          representing the actor info
   * @return the Actor Info from the JSON object
   */
  ActorInfo getActorInfoFromJson(JsonObject jsonObject);

  /**
   * Get Board JSON.
   * 
   * @param board
   *          to get JSON for
   * @param height
   *          of the board
   * @param width
   *          of the board
   * @return a JSON Object representing the board
   */
  JsonObject getBoardJson(TileInfo[][] board, int height, int width);

  /**
   * Get Board from JSON.
   * 
   * @param boardJson
   *          is a JSON Object representing the board
   * @param height
   *          of the board
   * @param width
   *          of the board
   * @return a 2d array of Tile info representing the board
   */
  TileInfo[][] getBoardFromJson(JsonObject boardJson, int height, int width);

  /**
   * Get Tileset from JSON. The tileset is a map of Strings and tileInfo used for level design and
   * saves
   * 
   * @param tilesetJson
   *          is the JSON Object representing the Tile Set
   * @return a map of Strings(name of TileInfo) and TileInfo
   */
  Map<String, TileInfo> getTilesetFromJson(JsonObject tilesetJson);

  /**
   * Get a JSON Object for the Tile Info set. Used as the tileset for all maze tiles
   * 
   * @param tileSet
   *          is the set of TileInfo objects which can be used for the
   * @return a JSON object representing the tileset
   */
  JsonObject getTileInfoSetJson(Set<TileInfo> tileSet);

  /**
   * Get Tile Info JSON.
   * 
   * @param tileInfo
   *          is the TileInfo to be turned into JSON
   * @return a JSON object representing the tile info
   */
  JsonObject getTileInfoJson(TileInfo tileInfo);

  /**
   * Get Tile Info From JSON.
   * 
   * @param tileInfoJson
   *          is the JSON Object representing the TileInfo
   * @return the TileInfo represented by the JSON Object
   */
  TileInfo getTileInfoFromJson(JsonObject tileInfoJson);

  /**
   * Get ItemInfo JSON.
   * 
   * @param itemInfo
   *          to get JSON for
   * @param isInInventory
   *          is a boolean to say if the item in in the inventory
   * @return a JSON Object representing the ItemInfo
   */
  JsonObject getItemInfoJson(ItemInfo itemInfo, boolean isInInventory);

  /**
   * Get ItemInfo from JSON.
   * 
   * @param jsonObject
   *          represents the ItemInfo in JSON
   * @param isInInventory
   *          is a boolean to say if the item in in the inventory
   * @return an ItemInfo object represented by the JSON Object
   */
  ItemInfo getItemInfoFromJson(JsonObject jsonObject, boolean isInInventory);

  /**
   * Get Maze Items Set JSON.
   * 
   * @param items
   *          to turn into a JSON Value
   * @return a JSON Value representing the set of ItemInfo from the maze
   */
  JsonValue getMazeItemsSetJson(Set<ItemInfo> items);

  /**
   * Get Enemy JSON.
   * 
   * @param enemy
   *          to get JSON Object for
   * @return a JSON Object representing the enemy
   */
  JsonObject getEnemyJson(Enemy enemy);

  /**
   * Get Enemy from JSON.
   * 
   * @param jsonObject
   *          representing the Enemy
   * @param board
   *          on which the enemy is located
   * @return an Enemy object that was represented by the JSON Object
   */
  Enemy getEnemyFromJson(JsonObject jsonObject, TileInfo[][] board);

  /**
   * Get Enemies Set JSON.
   * 
   * @param enemies
   *          is a set of actor info representing the enemies
   * @return a JSON Value representing the set of Enemies in the maze
   */
  JsonValue getEnemiesSetJson(Set<ActorInfo> enemies);

}
