package nz.ac.vuw.ecs.swen225.a3.application;

import java.util.Observable;

/**
 * An Observable for Application Classes
 *
 * @author Bryony
 *
 */
public class ObservableAppl extends Observable {

  /**
   * Notifies Observer of Passed Parameter
   *
   * @param o
   */
  public void update(Object o) {
    if (o == null)
      throw new RuntimeException("Can't Update with null");
    setChanged();
    notifyObservers(o);
  }

}
