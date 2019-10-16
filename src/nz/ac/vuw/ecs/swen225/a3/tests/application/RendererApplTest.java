package nz.ac.vuw.ecs.swen225.a3.tests.application;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A Fake Renderer which fills the background with the given color Used with VisualTestGUI for
 * manual visual testing
 * 
 * @author Bryony
 *
 */
public class RendererApplTest {

  /**
   * Empty Constructor
   */
  public RendererApplTest() {

  }

  /**
   * Colors background given color
   * 
   * @param g
   * @param background
   */
  public void redraw(Graphics g, Color background) {
    g.setColor(background);
    g.fillRect(0, 0, 600, 600);
  }

}
