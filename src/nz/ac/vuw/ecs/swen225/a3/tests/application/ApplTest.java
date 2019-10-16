package nz.ac.vuw.ecs.swen225.a3.tests.application;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import org.junit.Test;

import nz.ac.vuw.ecs.swen225.a3.application.GameState_Impl;
import nz.ac.vuw.ecs.swen225.a3.application.Keys;
import nz.ac.vuw.ecs.swen225.a3.application.NullGameState;
import nz.ac.vuw.ecs.swen225.a3.application.NullKeys;
import nz.ac.vuw.ecs.swen225.a3.application.ObservableAppl;
import nz.ac.vuw.ecs.swen225.a3.application.ObserverAppl;
import nz.ac.vuw.ecs.swen225.a3.common.GameState;
import nz.ac.vuw.ecs.swen225.a3.maze.NullMazeState;

/**
 * Tests for the Application Package
 *
 * @author Bryony
 *
 */
public class ApplTest {

  /**
   * Test if Chap moves to correct location when left key pushed
   */
  @Test
  public void TestLeftKey() {
    Keys key = new Keys();
    FakeAppl app = new FakeAppl(key);

    key.controlUp(KeyEvent.VK_LEFT);
    int[] location = { -1, 0 };
    assertTrue(app.checkChapPosition(location));
  }

  /**
   * Test if Chap moves to correct location when right key pushed
   */
  @Test
  public void TestRightKey() {
    Keys key = new Keys();
    FakeAppl app = new FakeAppl(key);

    key.controlUp(KeyEvent.VK_RIGHT);
    int[] location = { 1, 0 };
    assertTrue(app.checkChapPosition(location));
  }

  /**
   * Test if Chap moves to correct location when up key pushed
   */
  @Test
  public void TestUpKey() {
    Keys key = new Keys();
    FakeAppl app = new FakeAppl(key);

    key.controlUp(KeyEvent.VK_UP);
    int[] location = { 0, -1 };
    assertTrue(app.checkChapPosition(location));
  }

  /**
   * Test if Chap moves to correct location when down key pushed
   */
  @Test
  public void TestDownKey() {
    Keys key = new Keys();
    FakeAppl app = new FakeAppl(key);

    key.controlUp(KeyEvent.VK_DOWN);
    int[] location = { 0, 1 };
    assertTrue(app.checkChapPosition(location));
  }

  /**
   * Test if correct event happens when CTRL-X is pushed
   */
  @Test
  public void TestXCTRLKey() {
    Keys key = new Keys();
    FakeAppl app = new FakeAppl(key);

    key.controlDown(KeyEvent.VK_X);
    assertTrue(app.persistence.quitCheck());
  }

  /**
   * Test if correct event happens when CTRL-S is pushed
   */
  @Test
  public void TestSCTRLKey() {
    Keys key = new Keys();
    FakeAppl app = new FakeAppl(key);

    key.controlDown(KeyEvent.VK_S);
    assertTrue(app.persistence.saveCheck());
  }

  /**
   * Test if correct event happens when CTRL-R is pushed
   */
  @Test
  public void TestRCTRLKey() {
    Keys key = new Keys();
    FakeAppl app = new FakeAppl(key);

    key.controlDown(KeyEvent.VK_R);
    assertTrue(app.checkLoadCorrect(2));
  }

  /**
   * Test if correct event happens when CTRL-P is pushed
   */
  @Test
  public void TestPCTRLKey() {
    Keys key = new Keys();
    FakeAppl app = new FakeAppl(key);

    key.controlDown(KeyEvent.VK_P);
    assertTrue(app.checkLoadCorrect(1));
  }

  /**
   * Test if correct event happens when CTRL-1 is pushed
   */
  @Test
  public void Test1CTRLKey() {
    Keys key = new Keys();
    FakeAppl app = new FakeAppl(key);

    key.controlDown(KeyEvent.VK_1);
    assertTrue(app.checkLoadCorrect(0));
  }

  /**
   * Test if correct event happens when SPACE is pushed
   */
  @Test
  public void TestSpaceKey() {
    Keys key = new Keys();
    FakeAppl app = new FakeAppl(key);

    key.controlUp(KeyEvent.VK_SPACE);
    int a = app.getTime();
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
    }
    int b = app.getTime();
    assertTrue(a == b);
  }

  /**
   * Test if stopPause starts timing again
   */
  @Test
  public void TestStopPause() {
    Keys key = new Keys();
    FakeAppl app = new FakeAppl(key);

    key.controlUp(KeyEvent.VK_SPACE);
    app.stopPause();

    int a = app.getTime();
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
    }
    int b = app.getTime();
    assertTrue(a != b);
  }

  /**
   * Test if timeout restarts level
   */
  @Test
  public void TestTimeout() {
    Keys key = new Keys();
    FakeAppl app = new FakeAppl(key);

    app.setLevelPaused();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
    }
    assertTrue(app.checkLoadCorrect(1));
  }

  /**
   * Test if losing the level restarts level
   */
  @Test
  public void TestLose() {
    Keys key = new Keys();
    FakeAppl app = new FakeAppl(key);

    app.lost();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
    }
    assertTrue(app.checkLoadCorrect(1));
  }

  /**
   * Test if winning the level goes to next level
   */
  @Test
  public void TestWin() {
    Keys key = new Keys();
    FakeAppl app = new FakeAppl(key, true);

    app.stopTime();
    app.won();
    assertTrue(app.persistence.quitCheck());
  }

  /**
   * Test if setting level in GameState works correctly
   */
  @Test
  public void TestGameStateSetLevel() {
    GameState game = new GameState_Impl(1, 1, 1, new FakeMazeState(1));
    game.setCurrentLevel(2);
    assertTrue(game.getCurrentLevel() == 2);
  }

  /**
   * Test if setting time remaining in GameState works correctly
   */
  @Test
  public void TestGameStateSetTimeRemaining() {
    GameState game = new GameState_Impl(1, 1, 1, new FakeMazeState(1));
    game.setTimeRemaining(100);
    assertTrue(game.getTimeRemaining() == 100);
  }

  /**
   * Test if setting total time in GameState works correctly
   */
  @Test
  public void TestGameStateSetTotalTime() {
    GameState game = new GameState_Impl(1, 1, 1, new FakeMazeState(1));
    game.setTotalLevelTime(90);
    assertTrue(game.getTotalLevelTime() == 90);
  }

  /**
   * Test if setting MazeState in GameState works correctly
   */
  @Test
  public void TestGameStateSetMazeState() {
    GameState game = new GameState_Impl(1, 1, 1, new FakeMazeState(1));
    game.setMazeState(new FakeMazeState(5));
    assertTrue(game.getMazeState().getNumChips() == 5);
  }

  /**
   * Test if NullGameState can be changed
   */
  @Test
  public void TestNullGameState() {
    GameState game = new NullGameState();
    assertTrue(game instanceof NullGameState);
    // Check Return Values
    assertTrue(game.getCurrentLevel() == 0);
    assertTrue(game.getMazeState() instanceof NullMazeState);
    assertTrue(game.getTimeRemaining() == 0);
    assertTrue(game.getTotalLevelTime() == 0);
    // Check doesn't respond to Set
    game.setCurrentLevel(9);
    assertTrue(game.getCurrentLevel() != 9);
    assertTrue(game.getCurrentLevel() == 0);
    game.setTimeRemaining(9);
    assertTrue(game.getTimeRemaining() != 9);
    assertTrue(game.getTimeRemaining() == 0);
    game.setTotalLevelTime(9);
    assertTrue(game.getTotalLevelTime() != 9);
    assertTrue(game.getTotalLevelTime() == 0);
    game.setMazeState(new FakeMazeState(4));
    assertTrue(!(game.getMazeState() instanceof FakeMazeState));
    assertTrue(game.getMazeState() instanceof NullMazeState);
  }

  /**
   * Test if renderer and sideBar are being redrawn
   */
  @Test
  public void TestDrawing() {
    Keys key = new Keys();
    FakeAppl app = new FakeAppl(key, true);
    app.stopTime();

    int render = app.checkRedrawn();
    int sideBar = app.checkRedrawnSide();

    app.redraw();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
    }
    assertTrue(app.checkRedrawn() != render);
    assertTrue(app.checkRedrawnSide() != sideBar);
  }

  /**
   * Test if sideBar can draw all numbers and significant figures
   */
  @Test
  public void TestDrawingSide() {
    Keys key = new Keys();
    FakeAppl app = new FakeAppl(key, true);

    int side = app.checkRedrawnSide();
    app.stopTime();
    app.setTime(123);
    app.redraw();
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
    }
    assertTrue(app.checkRedrawnSide() != side);

    side = app.checkRedrawnSide();
    app.setTime(45);
    app.redraw();
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
    }
    assertTrue(app.checkRedrawnSide() != side);

    side = app.checkRedrawnSide();
    app.setTime(6);
    app.redraw();
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
    }
    assertTrue(app.checkRedrawnSide() != side);

    side = app.checkRedrawnSide();
    app.setTime(789);
    app.redraw();
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
    }
    assertTrue(app.checkRedrawnSide() != side);

    side = app.checkRedrawnSide();
    app.setTime(0);
    app.redraw();
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
    }
    assertTrue(app.checkRedrawnSide() != side);
  }

  /**
   * Test if Observer can tell App that its won
   */
  @Test
  public void TestObserverWin() {
    FakeObservable obs = new FakeObservable();
    FakeObserver os = new FakeObserver();
    FakeAppl app = new FakeAppl(obs, os);
    app.stopTime();

    obs.win();
    assertTrue(app.persistence.quitCheck());
  }

  /**
   * Test if Observer can tell App that its lost
   */
  @Test
  public void TestObserverLose() {
    FakeObservable obs = new FakeObservable();
    FakeObserver os = new FakeObserver();
    FakeAppl app = new FakeAppl(obs, os);
    app.stopTime();

    obs.lost();
    assertTrue(app.checkLoadCorrect(1));
  }

  /**
   * Test Observer can tell App that info needs passing on
   */
  @Test
  public void TestObserverInfo() {
    FakeObservable obs = new FakeObservable();
    FakeObserver os = new FakeObserver();
    FakeAppl app = new FakeAppl(obs, os);
    app.stopTime();

    obs.info();
    assertTrue(app.renderer.notice.equals("Lets all do this!"));
  }

  /**
   * Test if Observer can cope with fake String messages
   */
  @Test
  public void TestObserverStringFalse() {
    FakeObservable obs = new FakeObservable();
    FakeObserver os = new FakeObserver();
    FakeAppl app = new FakeAppl(obs, os);
    app.stopTime();

    assertThrows(RuntimeException.class, () -> obs.string());
    assertTrue(!app.checkLoadCorrect(1));
    assertTrue(!app.persistence.quitCheck());
  }

  /**
   * Test if Observer can cope with fake String[] messages
   */
  @Test
  public void TestObserverStringArrayFalse() {
    FakeObservable obs = new FakeObservable();
    FakeObserver os = new FakeObserver();
    FakeAppl app = new FakeAppl(obs, os);
    app.stopTime();

    assertThrows(RuntimeException.class, () -> obs.stringArray());
    assertTrue(!app.renderer.notice.equals("Lets all do this!"));
  }

  /**
   * Test if nextLevel moves to the next level if next level exists
   */
  @Test
  public void TestNextLevelJust1() {
    FakeAppl app = new FakeAppl(new NullKeys());
    int level = app.getLevel();
    app.setLevel(1);
    assertTrue(!app.checkLoadCorrect(level));
    level = app.getLevel();
    app.nextLevel();
    assertTrue(!app.checkLoadCorrect(level));
  }

  /**
   * Test that the help page is being called properly.
   */
  @Test
  public void TestHelp() {
    FakeDisplay display = new FakeDisplay();
    FakeAppl app = new FakeAppl(display);
    app.help();
    assertFalse(display.getPaused());
  }

  /**
   * Test that Negative Numbers won't be drawn.
   */
  @Test
  public void TestNegativeTime() {
    FakeAppl app = new FakeAppl();
    app.stopTime();
    assertThrows(RuntimeException.class, () -> app.setTime(-21));
  }

  /**
   * Test that 4+sf Number won't be drawn
   */
  @Test
  public void Test4SFTime() {
    FakeAppl app = new FakeAppl();
    app.stopTime();
    assertThrows(RuntimeException.class, () -> app.setTime(1952));
  }

  /**
   * Test NullKeys
   */
  @Test
  public void TestNullKeys() {
    NullKeys keys = new NullKeys();
    FakeAppl app = new FakeAppl(keys);

    // Left
    keys.controlUp(KeyEvent.VK_LEFT);
    int[] location = { 0, 0 };
    assertTrue(app.checkChapPosition(location));

    // Right
    keys.controlUp(KeyEvent.VK_RIGHT);
    assertTrue(app.checkChapPosition(location));

    // Up
    keys.controlUp(KeyEvent.VK_UP);
    assertTrue(app.checkChapPosition(location));

    // Down
    keys.controlUp(KeyEvent.VK_DOWN);
    assertTrue(app.checkChapPosition(location));

    // CTRL-X
    keys.controlDown(KeyEvent.VK_X);
    assertFalse(app.persistence.quitCheck());

    // CTRL-S
    keys.controlDown(KeyEvent.VK_S);
    assertFalse(app.persistence.saveCheck());

    // CTRL-R
    app.loadLevel();
    keys.controlDown(KeyEvent.VK_R);
    assertFalse(app.checkLoadCorrect(2));
    app.loadGame();

    // CTRL-P
    keys.controlDown(KeyEvent.VK_P);
    assertFalse(app.checkLoadCorrect(1));

    // CTRL-1
    keys.controlDown(KeyEvent.VK_1);
    assertFalse(app.checkLoadCorrect(0));

    // SPACE
    keys.controlUp(KeyEvent.VK_SPACE);
    int a = app.getTime();
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
    }
    int b = app.getTime();
    assertFalse(a == b);

    keys.addObserver(null);
    keys.keyPressed(null);
    keys.keyReleased(null);
    keys.keyTyped(null);
  }

  /**
   * Test if refuses negative and 4+sf Chips and Levels
   */
  @Test
  public void TestNeg4SFChipsLevels() {
    FakeDisplay display = new FakeDisplay();
    FakeAppl app = new FakeAppl(display);

    assertThrows(RuntimeException.class, () -> {
      app.setLevel(-1);
    });
    assertThrows(RuntimeException.class, () -> {
      app.setLevel(12345);
    });
    app.setLevel(0);
    assertThrows(RuntimeException.class, () -> {
      display.setChips(1000);
    });
    assertThrows(RuntimeException.class, () -> {
      display.setChips(-634);
    });
    display.setChips(999);
  }

  /**
   * Test that calling info with null creates error
   */
  @Test
  public void TestNullMessage() {
    FakeAppl app = new FakeAppl();
    assertThrows(RuntimeException.class, () -> {
      app.info(null);
    });
  }

  /**
   * Test that ObserverAppl can't take a null
   */
  @Test
  public void TestObserverApplNull() {
    assertThrows(RuntimeException.class, () -> {
      ObserverAppl obs = new ObserverAppl(null);
    });
    FakeObserver obs = new FakeObserver();
    FakeObservable os = new FakeObservable();
    FakeAppl app = new FakeAppl(os, obs);
    assertThrows(RuntimeException.class, () -> obs.update(os, null));
    Object[] m1 = { 0 };
    assertThrows(RuntimeException.class, () -> obs.update(os, m1));
    Object[] m2 = { 1, 2, 3 };
    assertThrows(RuntimeException.class, () -> obs.update(os, m2));

    Object[] m3 = { 1, 2 };
    assertThrows(RuntimeException.class, () -> obs.update(os, m3));
    Graphics g = app.getFrame().getGraphics();

    Object[] m4 = { g, 2 };
    assertThrows(RuntimeException.class, () -> obs.update(os, m4));
  }

  /**
   * Test SideBar Exceptions
   */
  @Test
  public void TestsideBarExceptions() {
    FakeSideBar bar = new FakeSideBar(true);
    FakeAppl app = new FakeAppl();
    Graphics g = app.getFrame().getGraphics();
    assertThrows(RuntimeException.class, () -> bar.redraw(g, -1, 0, 0, null));
    assertThrows(RuntimeException.class, () -> bar.redraw(g, 0, -1, 0, null));
    assertThrows(RuntimeException.class, () -> bar.redraw(g, 0, 0, -1, null));
    assertThrows(RuntimeException.class, () -> bar.redraw(g, 1000, 0, 0, null));
    assertThrows(RuntimeException.class, () -> bar.redraw(g, 0, 1000, 0, null));
    assertThrows(RuntimeException.class, () -> bar.redraw(g, 0, 0, 1000, null));
  }

  /**
   * Test Keys diff keys
   */
  @Test
  public void TestDiffKeys() {
    Keys k = new Keys();
    Observer os = new Observer() {
      @Override
      public void update(Observable o, Object arg) {
        throw new RuntimeException("No");
      }
    };
    k.addObserver(os);
    k.controlDown(KeyEvent.VK_5);
    k.controlUp(KeyEvent.VK_T);
  }

  /**
   * Test ObservableAppl with Null
   */
  @Test
  public void TestObsableNull() {
    ObservableAppl app = new ObservableAppl();
    assertThrows(RuntimeException.class, () -> app.update(null));
  }

  /**
   * Test ObservableAppl with Null
   */
  @Test
  public void TestDrawInventory() {
	  FakeMazeState fakeMazeState = new FakeMazeState(3);
	  FakeSideBar sideBar = new FakeSideBar(true);
	  FakeAppl app = new FakeAppl();
	  Graphics g = app.getFrame().getGraphics();

	  sideBar.redraw(g, 100, 3, 1, fakeMazeState.getInventory());

  }

}
