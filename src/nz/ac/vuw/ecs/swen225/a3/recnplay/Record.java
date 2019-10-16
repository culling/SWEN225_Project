package nz.ac.vuw.ecs.swen225.a3.recnplay;

import java.io.FileWriter;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import nz.ac.vuw.ecs.swen225.a3.common.GameState;
import nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter.JsonConverter;
import nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter.JsonConverter_Impl;


/**
 * Allows a game to be saved second by second as a JSON.
 * 
 * @author James
 *
 */
public class Record {
  private JsonArrayBuilder games;
  private String name;

  /**
   * Record default constructor.
   */
  public Record() {
    name = "1";
    games = Json.createArrayBuilder();
  }

  /**
   * Changes the name to the given input.
   * 
   * @param name name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Adds a new GameState to the record.
   * 
   * @param game GameState to be added
   */
  public void add(GameState game) {
    JsonConverter json = new JsonConverter_Impl();
    JsonObject gameJson = json.getGameStateJson(game);
    games.add(gameJson);
  }

  /**
   * Writes the record to the JSON file sharing the same name.
   */
  public void write() {
    try {
      FileWriter file = new FileWriter("records/" + name + ".json");
      JsonWriter writer = Json.createWriter(file);
      writer.write(games.build());
      file.close();
      writer.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
