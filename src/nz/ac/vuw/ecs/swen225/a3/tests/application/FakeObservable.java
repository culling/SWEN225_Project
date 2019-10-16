package nz.ac.vuw.ecs.swen225.a3.tests.application;

import java.util.Observable;

/**
 * Fake Observer for Application Package Tests
 * 
 * @author Bryony
 *
 */
public class FakeObservable extends Observable {

  void win() {
    setChanged();
    notifyObservers("Won");
  }

  void lost() {
    setChanged();
    notifyObservers("Lost");
  }

  void info() {
    setChanged();
    String[] args = { "Information", "Lets all do this!" };
    notifyObservers(args);
  }

  void string() {
    setChanged();
    notifyObservers("Hello");
  }

  void stringArray() {
    setChanged();
    String[] args = { "Hello", "Lets all do this!" };
    notifyObservers(args);
  }

}
