package nz.ac.vuw.ecs.swen225.a3.recnplay;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import nz.ac.vuw.ecs.swen225.a3.common.GameState;
import nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter.JsonConverter;
import nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter.JsonConverter_Impl;

/**
 * Replay Object.
 *
 * @author James
 *
 */
public class Replay {
  ArrayList<GameState> states;

  /**
   * Reads the given file.
   *
   * @param file
   */
  public Replay(File file) {
    read(file);
  }

  /**
   * Returns states.
   *
   * @return ArrayList<GameState> list of all states in the given file.
   */
  public ArrayList<GameState> getStates() {
    return states;
  }

  /**
   * Reads a file and converts the Json into an array of GameStates.
   *
   * @param file File to be read.
   */
  public void read(File file) {
    FileReader fr;
    states = new ArrayList<GameState>();
    try {
      JsonConverter json = new JsonConverter_Impl();
      fr = new FileReader(file);
      JsonReader reader = Json.createReader(fr);
      JsonArray arr = reader.readArray();
      reader.close();
      JsonObject temp;
      for (int i = 0; i < arr.size(); i++) {
        temp = arr.getJsonObject(i);
        states.add(json.getGameStateFromJson(temp));
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }

  }
}
