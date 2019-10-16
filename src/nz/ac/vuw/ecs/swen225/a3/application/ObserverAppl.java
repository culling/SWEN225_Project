package nz.ac.vuw.ecs.swen225.a3.application;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import nz.ac.vuw.ecs.swen225.a3.common.Direction;

/**
 * An Observer which relays information back to the Application Class
 *
 * @author Bryony
 *
 */
public class ObserverAppl implements Observer {

  private Application_Impl app;

  /**
   * Empty constructor
   */
  public ObserverAppl() {
  }

  /**
   * Constructor which needs the Application_Impl so information can be passed
   *
   * @param app
   */
  public ObserverAppl(Application_Impl app) {
    if (app == null)
      throw new RuntimeException("Has to be given an Application");
    this.app = app;
  }

  @Override
  public void update(Observable o, Object arg) {
    if (arg == null)
      throw new RuntimeException("Can't deal with null");
    if (arg instanceof String) {
      String message = (String) arg;
      if (message.equals("Won")) {
        app.won();
        return;
      } else if (message.equals("Lost")) {
        app.lost();
        return;
      } else if (message.equals("newGame")) {
        app.newGame();
        return;
      } else if (message.equals("loadLevel")) {
        app.loadLevel();
        return;
      } else if (message.equals("loadGame")) {
        app.loadGame();
        return;
      } else if (message.equals("save")) {
        app.save();
        return;
      } else if (message.equals("quit")) {
        app.quit();
        return;
      } else if (message.equals("lostLevel")) {
        app.stopPause();
        app.loadLevel();
        app.stopTime();
        app.startTime();
        app.redraw();
        return;
      } else if (message.equals("stopPaused")) {
        app.stopPause();
        return;
      } else if (message.equals("pause")) {
        app.pause();
        return;
      } else if (message.equals("help")) {
        app.help();
        return;
      } else if (message.equals("next")) {
        app.next();
        return;
      } else if (message.equals("back")) {
        app.back();
        return;
      } else if (message.equals("record")) {
        app.record();
        return;
      } else if (message.equals("replay")) {
        app.replayGame();
        return;
      } else if (message.equals("LeaveInformation")) {
        app.cancelMessage();
        return;
      }
    } else if (arg instanceof Object[]) {
      Object[] message = (Object[]) arg;
      if (message.length != 2)
        throw new RuntimeException("Array Doesn't have correct size");
      if (arg instanceof String[]) {
        String[] messageS = (String[]) arg;
        if (messageS[0].equals("Information")) {
          app.info(messageS[1]);
          return;
        }
      } else if (message[0] instanceof Graphics) {
        if (message[1] instanceof Dimension) {
          app.rendererRedraw((Graphics) message[0], (Dimension) message[1]);
          return;
        }
      }
    } else if (arg instanceof Direction) {
      Direction message = (Direction) arg;
      app.moveChap(message);
      return;
    }
    throw new RuntimeException("Can't deal with given Arg");
  }
}
