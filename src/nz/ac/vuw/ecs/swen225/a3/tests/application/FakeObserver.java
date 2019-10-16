package nz.ac.vuw.ecs.swen225.a3.tests.application;

import java.util.Observable;

import nz.ac.vuw.ecs.swen225.a3.application.Application_Impl;
import nz.ac.vuw.ecs.swen225.a3.application.ObserverAppl;

/**
 * Fake Observer for Application Package Tests
 *
 * Allows creation before passing of Application which is needed for ObserverAppl
 *
 * @author Bryony
 *
 */
public class FakeObserver extends ObserverAppl {

  private Application_Impl app;
  private ObserverAppl obs;

  /**
   * Empty Contructor
   */
  public FakeObserver() {
  }

  /**
   * Add the Application_Impl
   * 
   * @param app
   */
  public void addApp(Application_Impl app) {
    this.app = app;
    obs = new ObserverAppl(app);
  }

  @Override
  public void update(Observable o, Object arg) {
    if (arg instanceof String) {
      String sArg = (String) arg;
      if (sArg.equals("Won")) {
        app.nextLevel();
        return;
      } else if (sArg.equals("Lost")) {
        app.lostLevel();
        return;
      }
    }
    obs.update(o, arg);
  }

}
