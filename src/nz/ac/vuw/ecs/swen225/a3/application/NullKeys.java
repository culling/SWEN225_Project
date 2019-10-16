package nz.ac.vuw.ecs.swen225.a3.application;

import java.awt.event.KeyEvent;
import java.util.Observer;

/**
 * An empty Keys
 *
 * @author Bryony
 *
 */
public class NullKeys extends Keys {

  /**
   * Constructor
   */
  public NullKeys() {
  }

  /**
   * Adds the Observer to the internal Observable
   * 
   * @param obs
   */
  public void addObserver(Observer obs) {
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  /**
   * Updates Observer based on given keyCode assuming that the Control Key is down
   * 
   * @param keyCode
   */
  public void controlDown(int keyCode) {
  }

  /**
   * Updates the Observer based on given KeyCode assuming that Control Key is up
   * 
   * @param keyCode
   */
  public void controlUp(int keyCode) {
  }

}
