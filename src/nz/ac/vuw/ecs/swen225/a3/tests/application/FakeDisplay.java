package nz.ac.vuw.ecs.swen225.a3.tests.application;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observer;

import javax.swing.JFrame;

import nz.ac.vuw.ecs.swen225.a3.application.SideBar;
import nz.ac.vuw.ecs.swen225.a3.application.VisualDisplay;

/**
 * A Fake VisualDisplay for testing
 *
 * @author gatehobryo
 *
 */
public class FakeDisplay extends VisualDisplay {

  private VisualDisplay display;
  private boolean paused;

  /**
   * A Constructor which creates an internal VisualDisplay
   */
  public FakeDisplay() {
    display = new VisualDisplay();
    paused = false;
  }

  public void addElements(SideBar sideBar, JFrame frame) {
    display.addElements(sideBar, frame);
  }

  /**
   * Add given Observer to internal Observable
   * 
   * @param obs
   */
  public void addObserver(Observer obs) {
    display.addObserver(obs);
  }

  /**
   * Sets if have lost current level
   * 
   * @param lostLevel
   */
  public void setLostLevel(boolean lostLevel) {
    display.setLostLevel(lostLevel);
  }

  /**
   * Sets number of chips still not collected
   * 
   * @param chips
   */
  public void setChips(int chips) {
    display.setChips(chips);
  }

  /**
   * Returns current Time
   * 
   * @return returns an integer
   */
  public int getTime() {
    return display.getTime();
  }

  /**
   * Sets time to given parameter
   * 
   * @param time
   */
  public void setTime(int time) {
    display.setTime(time);
  }

  /**
   * Sets level to given parameter
   * 
   * @param level
   */
  public void setLevel(int level) {
    display.setLevel(level);
  }

  /**
   * Returns level
   * 
   * @return returns an integer
   */
  public int getLevel() {
    return display.getLevel();
  }

  /**
   * Opens the pop-up box which indicated game is paused
   * 
   * @param b
   */
  public void paused(boolean b) {
    paused = b;
  }

  /**
   * Redraws drawing panel Informs Application through Observer to get Renderer to redraw
   * 
   * @param g
   * @param size
   */
  public void redraw(Graphics g, Dimension size) {
    display.redraw(g, size);
  }

  /**
   * Redraws the SideBar
   * 
   * @param g
   */
  public void redrawBar(Graphics g) {
    display.redrawBar(g);
  }

  /**
   * Opens a Help pop-up box which displays rules
   * 
   * @param frame
   * @return returns true when box is closed
   */
  public boolean helpFrame(JFrame frame) {
    return true;
  }

  /**
   * Returns if paused
   * 
   * @return returns a boolean
   */
  public boolean getPaused() {
    return paused;
  }

}
