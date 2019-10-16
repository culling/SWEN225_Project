package nz.ac.vuw.ecs.swen225.a3.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import nz.ac.vuw.ecs.swen225.a3.application.NullGameState;
import nz.ac.vuw.ecs.swen225.a3.common.GameState;
import nz.ac.vuw.ecs.swen225.a3.common.Persistence;
import nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter.JsonConverter;
import nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter.JsonConverter_Impl;
import nz.ac.vuw.ecs.swen225.a3.plugin.mazeElementLoader;

/**
 * Implements the Persistence interface. Saves to disk in a JSON format, reads from disk files saved
 * in JSON format
 *
 * @author cullingene
 */
public class Persistence_Impl implements Persistence {
  static final String SAVE_GAME_FOLDER = "saves";
  static final String LEVELS_FOLDER = "levels";
  static final String SAVE_GAME_FILE = SAVE_GAME_FOLDER + "/save.json";

  @Override
  public void save(GameState gameState) {
    save(gameState, SAVE_GAME_FILE);
  }

  @Override
  public void save(GameState gameState, String fileName) {
    JsonObject gameStateJsonObject = getGameStateJson(gameState);
    writeToDisk(gameStateJsonObject, fileName);
  }

  @Override
  public GameState load(String fileName) {
    File file = new File(fileName);
    //System.out.println("SAVE_GAME_FILE: " + fileName + "}");
    GameState gameState = getGameStateFromFile(file);
    //System.out.println("load: GameState{ " + gameState + "}");
    return gameState;
  }

  @Override
  public GameState loadLastLevel() {
    GameState gameState = loadLastGame();
    int lastLevel = gameState.getCurrentLevel();
    gameState = loadLevel(lastLevel);

    return gameState;
  }

  @Override
  public GameState loadLastGame() {
    File file = new File(SAVE_GAME_FILE);
    if (file.exists()) {
      //System.out.println("SAVE_GAME_FILE: " + SAVE_GAME_FILE + "}");
      GameState gameState = getGameStateFromFile(file);
      //System.out.println("loadLastGame: GameState{ " + gameState + "}");
      return gameState;
    }
    return newGame();
  }

  @Override
  public GameState loadLevel(int levelNumber) {
    GameState gameState = new NullGameState();

    String levelFileName = LEVELS_FOLDER + "/" + "level-" + levelNumber + "/" + "level-"
        + levelNumber + ".json";

    File levelFile = new File(levelFileName);
    gameState = getGameStateFromFile(levelFile);
    return gameState;
  }

  @Override
  public void quit(int levelNumber) {
    GameState gameState = loadLevel(levelNumber);
    save(gameState);
  }

  @Override
  public GameState newGame() {
    GameState gameState = loadLevel(1);
    return gameState;
  }

  @Override
  public JsonObject getGameStateJson(GameState gameState) {
    JsonConverter jsonConverter = new JsonConverter_Impl();
    return jsonConverter.getGameStateJson(gameState);
  }

  // ########################### Private Methods

  /**
   * Get the game state from file.
   *
   * @param file
   *          to get game state from
   * @return the game state of the file
   */
  public GameState getGameStateFromFile(File file) {
    JsonConverter jsonConverter = new JsonConverter_Impl();

    JsonObject gameStateJson = getJsonFromFile(file);
    int levelNumber = getLevelNumberFromJson(gameStateJson);
    mazeElementLoader loader = mazeElementLoader.getInstance();
    loader.SetLevelToLoadFrom(levelNumber);

    GameState gameState = jsonConverter.getGameStateFromJson(gameStateJson);

    return gameState;
  }

  private int getLevelNumberFromJson(JsonObject gameStateJson) {
    int level = gameStateJson.getInt("level");
    return level;
  }

  /**
   * Helper method to get JsonObject from file.
   *
   * @param file
   *          to get the JSON Object from
   * @return the JSON Object from the file
   */
  private JsonObject getJsonFromFile(File file) {

    List<String> lines = getLinesFromFile(file);
    String contents = new String();
    for (String line : lines) {
      contents += line;
    }

    JsonReader reader = Json.createReader(new StringReader(contents));
    JsonObject jsonObject = reader.readObject();

    return jsonObject;
  }

  /**
   * Write to Disk.
   *
   * @param gameStateJsonObject
   *          is the jsonObject for game state to write to disk
   * @param fileName
   *          to write the JSON Object to
   */
  private void writeToDisk(JsonObject gameStateJsonObject, String fileName) {
    File file = new File(fileName);

    try {
      FileWriter fileWriter = new FileWriter(file);
      fileWriter.write(gameStateJsonObject.toString());
      fileWriter.close();
    } catch (IOException e) {
      System.err.println("Error writing to file");
      System.err.println(e.getMessage());
    }
  }

  /**
   * Get the lines from the file.
   *
   * @param file
   *          to get lines from
   * @return a list of the lines of the file
   */
  private List<String> getLinesFromFile(File file) {
    List<String> lines = new ArrayList<String>();
    try {
      FileReader fileReader = new FileReader(file);
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      String line = bufferedReader.readLine();

      while (line != null) {
        lines.add(line);
        line = bufferedReader.readLine();
      }

      bufferedReader.close();
    } catch (Exception e) {
      //System.out.println("Fail to get lines from file: " + file.getAbsolutePath());
      //System.out.println(e);
    }
    return lines;
  }

}
