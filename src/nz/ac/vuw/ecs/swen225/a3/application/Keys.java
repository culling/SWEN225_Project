package nz.ac.vuw.ecs.swen225.a3.application;

import java.awt.event.KeyEvent;

import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import java.awt.event.KeyListener;
import java.util.Observer;

/**
 * A KeyListener designed for Application_Impl
 *
 * @author Bryony
 *
 */
public class Keys implements KeyListener {

  ObservableAppl os;

  /**
   * Constructor
   */
  public Keys() {
    os = new ObservableAppl();
  }

  /**
   * Adds the Observer to the internal Observable
   * 
   * @param obs
   */
  public void addObserver(Observer obs) {
    os.addObserver(obs);
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.isControlDown()) {
      controlDown(e.getKeyCode());
    } else {
      controlUp(e.getKeyCode());
    }
  }

  /**
   * Updates Observer based on given keyCode assuming that the Control Key is down
   * 
   * @param keyCode
   */
  public void controlDown(int keyCode) {
    switch (keyCode) {
    case KeyEvent.VK_X:
      os.update("quit");
      break;
    case KeyEvent.VK_S:
      os.update("save");
      break;
    case KeyEvent.VK_R:
      os.update("loadGame");
      break;
    case KeyEvent.VK_P:
      os.update("loadLevel");
      break;
    case KeyEvent.VK_1:
      os.update("newGame");
      break;
    }
  }

  /**
   * Updates the Observer based on given KeyCode assuming that Control Key is up
   * 
   * @param keyCode
   */
  public void controlUp(int keyCode) {
    switch (keyCode) {
    case KeyEvent.VK_LEFT:
      os.update(Direction.WEST);
      os.update("back");
      break;
    case KeyEvent.VK_RIGHT:
      os.update(Direction.EAST);
      os.update("next");
      break;
    case KeyEvent.VK_UP:
      os.update(Direction.NORTH);
      break;
    case KeyEvent.VK_DOWN:
      os.update(Direction.SOUTH);
      break;
    case KeyEvent.VK_SPACE:
      os.update("pause");
      break;
    }
  }

}
