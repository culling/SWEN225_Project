package nz.ac.vuw.ecs.swen225.a3.common;

import java.awt.*;

/**
 * An interface for notices to be displayed atop the board. Once created, their details cannot be
 * changed.
 *
 * @author achtenisaa 300437718
 */
public interface Notice {
  /**
   * Retrieves the text of the notice.
   *
   * @return a <code>String</code> containing the text of the notice.
   */
  public String getText();

  /**
   * Retrieves the <code>Color</code> of the notice.
   *
   * @return the <code>Color</code> of the notice's text.
   */
  public Color getColor();

  /**
   * Retrieves the icon of the notice as an <code>Image</code>.
   *
   * @return an <code>Image</code> containing the icon of the notice.
   */
  public Image getIcon();

  /**
   * Redraws the notice in accord with its details.
   *
   * @param graphics
   *          the <code>Graphics</code> item to use
   * @param size
   *          the size of the canvas to draw upon
   */
  public void redraw(Graphics2D graphics, Dimension size);
}
