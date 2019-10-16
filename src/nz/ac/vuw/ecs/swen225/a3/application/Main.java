package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.common.Application;

/**
 * Main Class for running a live version of the Game
 * 
 * @author Bryony
 *
 */
public class Main {

  /**
   * Starts the program with a Blank Application
   * 
   * @param args
   */
  public static void main(String[] args) {
    Application app = new Application_Impl();
    app.getTime();
  }

}
