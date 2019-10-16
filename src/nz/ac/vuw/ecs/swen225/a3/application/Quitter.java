package nz.ac.vuw.ecs.swen225.a3.application;

/**
 * Small class to stop System.exit for tests
 * 
 * @author Bryony
 *
 */
public class Quitter {

  private boolean b;

  /**
   * Constructor with boolean b If b is true, system will exit
   * 
   * @param b
   */
  public Quitter(boolean b) {
    this.b = b;
  }

  /**
   * exit it b is true
   */
  public void quit() {
    if (b) {
      System.exit(0);
    }
  }

}
