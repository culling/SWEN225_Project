package nz.ac.vuw.ecs.swen225.a3.tests.application;

import javax.swing.JFrame;

import nz.ac.vuw.ecs.swen225.a3.application.Application_Impl;
import nz.ac.vuw.ecs.swen225.a3.application.Keys;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.GameState;

/**
 * A Fake version of Application_Impl which allows easy access
 *
 * @author Bryony
 *
 */
public class FakeAppl {

  private Application_Impl app;
  /**
   * The Maze
   */
  public FakeMaze maze;
  /**
   * The Renderer
   */
  public FakeRenderer renderer;
  /**
   * The Persistence
   */
  public FakePersistence persistence;
  /**
   * The SideBar
   */
  public FakeSideBar sideBar;

  /**
   * A Constructor that allows use of own keys
   *
   * @param key
   */
  public FakeAppl(Keys key) {
    maze = new FakeMaze();
    renderer = new FakeRenderer();
    persistence = new FakePersistence();
    sideBar = new FakeSideBar(false);
    app = new Application_Impl(maze, renderer, persistence, sideBar, key, null, null, false, null,
        false);
  }

  /**
   * A Constructor that uses own keys and doesn't allow sideBar to draw
   *
   * @param key
   * @param b
   */
  public FakeAppl(Keys key, boolean b) {
    maze = new FakeMaze();
    renderer = new FakeRenderer();
    persistence = new FakePersistence();
    sideBar = new FakeSideBar(b);
    app = new Application_Impl(maze, renderer, persistence, sideBar, key, null, null, false, null,
        false);
  }

  /**
   * A Constructor that uses own Observable and Observer
   *
   * @param obs
   * @param os
   */
  public FakeAppl(FakeObservable obs, FakeObserver os) {
    maze = new FakeMaze();
    renderer = new FakeRenderer();
    persistence = new FakePersistence();
    sideBar = new FakeSideBar(true);
    app = new Application_Impl(maze, renderer, persistence, sideBar, obs, os, false, null);
    os.addApp(app);
  }

  /**
   * A Constructor that uses own Display
   *
   * @param display
   */
  public FakeAppl(FakeDisplay display) {
    maze = new FakeMaze();
    renderer = new FakeRenderer();
    persistence = new FakePersistence();
    sideBar = new FakeSideBar(true);
    app = new Application_Impl(maze, renderer, persistence, sideBar, null, null, false, display);
  }

  /**
   * A Constructor with no Parameters
   */
  public FakeAppl() {
    maze = new FakeMaze();
    renderer = new FakeRenderer();
    persistence = new FakePersistence();
    sideBar = new FakeSideBar(true);
    app = new Application_Impl(maze, renderer, persistence, sideBar, null, null, false, null);
  }

  /**
   * Returns if Chap's position matches the one given
   *
   * @param location
   * @return returns a boolean
   */
  public boolean checkChapPosition(int[] location) {
    return maze.getLocation()[0] == location[0] && maze.getLocation()[1] == location[1];
  }

  /**
   * Returns current time
   *
   * @return returns an Integer
   */
  public int getTime() {
    return app.getTime();
  }

  /**
   * Returns the gamestate
   *
   * @return returns a gamestate
   */
  public GameState getGameState() {
    return app.getGameState();
  }

  /**
   * Saves current game and quits
   */
  public void save() {
    app.save();
  }

  /**
   * Move Chap in Direction d
   *
   * @param d
   */
  public void moveChap(Direction d) {
    app.moveChap(d);
  }

  /**
   * Saves current level and quits
   */
  public void quit() {
    app.quit();
  }

  /**
   * Loads an old game
   */
  public void loadGame() {
    app.loadGame();
  }

  /**
   * Loads an old level
   */
  public void loadLevel() {
    app.loadLevel();
  }

  /**
   * Loads level 1
   */
  public void newGame() {
    app.newGame();
  }

  /**
   * Pause game
   */
  public void pause() {
    app.pause();
  }

  /**
   * Continue game
   */
  public void stopPause() {
    app.stopPause();
  }

  /**
   * Won game
   */
  public void won() {
    app.nextLevel();
  }

  /**
   * Lost Game
   */
  public void lost() {
    app.lostLevel();
  }

  /**
   * Display a message
   *
   * @param message
   */
  public void info(String message) {
    app.info(message);
  }

  /**
   * Return if the Correct Level was loaded
   *
   * @param level
   * @return returns a boolean
   */
  public boolean checkLoadCorrect(int level) {
    return level == app.getLevel();
  }

  /**
   * Pretends timer has run out
   */
  public void setLevelPaused() {
    app.setLevelPaused();
  }

  /**
   * Returns the level
   *
   * @return returns an integer
   */
  public int getLevel() {
    return app.getLevel();
  }

  /**
   * redraw frame
   */
  public void redraw() {
    app.redraw();
  }

  /**
   * Returns how many times the Renderer has redrawn
   *
   * @return returns an integer
   */
  public int checkRedrawn() {
    return renderer.redrawn;
  }

  /**
   * Returns how many time the SideBar has redrawn
   *
   * @return returns an integer
   */
  public int checkRedrawnSide() {
    return sideBar.redrawn;
  }

  /**
   * Set time to given parameter
   *
   * @param i
   */
  public void setTime(int i) {
    app.setTime(i);
  }

  /**
   * Stop timer
   */
  public void stopTime() {
    app.stopTime();
  }

  /**
   * Start timer
   */
  public void startTime() {
    app.startTime();
  }

  /**
   * Set level to given parameter
   *
   * @param level
   */
  public void setLevel(int level) {
    app.setLevel(level);
  }

  /**
   * Goes to the nextLevel
   */
  public void nextLevel() {
    app.nextLevel();
  }

  /**
   * Displays a help screen
   */
  public void help() {
    app.help();
  }

  /**
   * Returns the Frame
   *
   * @return returns a JFrame
   */
  public JFrame getFrame() {
    return app.getFrame();
  }

}
