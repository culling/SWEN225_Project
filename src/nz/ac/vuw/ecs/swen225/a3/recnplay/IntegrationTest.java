package nz.ac.vuw.ecs.swen225.a3.recnplay;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import nz.ac.vuw.ecs.swen225.a3.application.Application_Impl;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.GameState;
import org.junit.Test;

/**
 * Tests for Record and Replay.
 * 
 * @author whita
 *
 */
public class IntegrationTest {

  /**
   * Tests whether record will create a file in the given location.
   */
  @Test
  public void testRecord() {
    Application_Impl app = new Application_Impl();
    File file = new File("records/test.json");
    if (file.exists())
      file.delete();
    app.record("test");
    app.stopPause();
    app.moveChap(Direction.SOUTH);
    app.write();
    boolean exists = file.exists();
    file.delete();
    assertTrue(exists);
  }

  /**
   * Tests whether the states are the same before and after being written to a
   * Json.
   */
  @Test
  public void testMove() {
    Application_Impl app = new Application_Impl();
    app.record("test");
    app.stopPause();
    ArrayList<GameState> state0 = new ArrayList<GameState>();
    ArrayList<GameState> state1 = new ArrayList<GameState>();
    state0.add(app.getGameState());
    app.moveChap(Direction.SOUTH);
    state0.add(app.getGameState());
    app.moveChap(Direction.SOUTH);
    state0.add(app.getGameState());
    app.write();
    File file = new File("records/test.json");
    boolean exists = file.exists();
    assertTrue(exists);
    if (exists) {
      Replay r = new Replay(file);
      state1 = r.getStates();
      System.out.println(state0.size() + ", " + state1.size());
      assertTrue(state0.size() == state1.size());
      for (int i = 0; i < state1.size(); i++) {
        // Compares values in the states.
        assertTrue(state0.get(i).getTimeRemaining() == state1.get(i).getTimeRemaining());
        assertTrue(state0.get(i).getCurrentLevel() == state1.get(i).getCurrentLevel());
        assertTrue(state0.get(i).getTotalLevelTime() == state1.get(i).getTotalLevelTime());
        assertTrue(
            state0.get(i).getMazeState().getChap().getName().equals(state1.get(i).getMazeState().getChap().getName()));
        assertTrue(
            state0.get(i).getMazeState().getEnemies().size() == state1.get(i).getMazeState().getEnemies().size());
        assertTrue(state0.get(i).getMazeState().getItems().size() == state1.get(i).getMazeState().getItems().size());

      }
      file.delete();
    }

  }

  /**
   * Tests that if Replay is called with a non-existent file, nothing will be
   * loaded and no errors will be thrown.
   */
  @Test
  public void loadNull() {
    File file = new File("records/t2.json");
    file.delete();
    boolean exists = file.exists();
    assertFalse(exists);
    Replay rep = new Replay(file);
    assertTrue(rep.getStates().isEmpty());
  }

}
