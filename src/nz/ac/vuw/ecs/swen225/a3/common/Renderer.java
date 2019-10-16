package nz.ac.vuw.ecs.swen225.a3.common;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * An interface for rendering the content of the game.
 *
 * @author achtenisaa 300437718
 */
public interface Renderer {
  /**
   * Sets the board layout to be rendered. A <code>null</code> state disables all rendering.
   *
   * @param maze
   *          the <code>MazeState</code> comprising the board
   * @return a <code>boolean</code> representing whether the map was successfully rendered
   */
  public boolean setMaze(MazeState maze);

  /**
   * Retrieves the current board layout that is being rendered.
   *
   * @return a <code>MazeState</code> comprising the currently rendered board.
   */
  public MazeState getMaze();

  /**
   * Changes the focus area (displayed region) of the board.
   *
   * Until <code>resetFocusArea()</code> is called, this also disables automatic adjustment of it.
   * The <code>Rectangle</code> comprising the new area must have a width and height of 9.
   *
   * @param area
   *          the new focus region
   * @return a <code>boolean</code> representing whether the focus region was changed
   */
  public boolean setFocusArea(Rectangle area);

  /**
   * Resets the focus area to its default value, enabling automatic adjustment of it.
   */
  public void resetFocusArea();

  /**
   * Retrieves the current focus area (displayed region) of the board.
   *
   * Should <code>setFocusArea()</code> not be called, this is computed by the last call of
   * <code>redraw()</code>.
   *
   * @return the current focus area as a <code>Rectangle</code>.
   */
  public Rectangle getFocusArea();

  /**
   * Gets the maximum coordinates of the map to draw, using the current focus area.
   *
   * @return a <code>Point</code> with the x and y coordinates
   */
  public Point getMaxPoint();

  /**
   * Gets the minimum coordinates of the map to draw, using the current focus area.
   *
   * @return a <code>Point</code> with the x and y coordinates
   */
  public Point getMinPoint();

  /**
   * Retrieves the cell size as rendered by the last call of <code>redraw()</code>. If
   * <code>redraw()</code> has not been called, <code>-1</code> is returned.
   *
   * @return the size of tiles drawn as an <code>int</code>.
   */
  public int getCellSize();

  /**
   * Returns a <code>HashSet</code> of item and actor <code>Object</code>s rendered by the last call
   * of <code>redraw()</code>.
   *
   * If <code>redraw()</code> has not been called, <code>-1</code> is returned.
   *
   * @return all rendered items and actors as an <code>HashMap</code> of <code>Objects</code>.
   */
  public HashSet<Object> getRenderedItems();

  /**
   * Returns a <code>HashSet</code> of <code>Points</code>s representing the locations of tiles
   * rendered by the last call of <code>redraw()</code>.
   *
   * If <code>redraw()</code> has not been called, <code>-1</code> is returned.
   *
   * @return all rendered tiles as an <code>HashMap</code> of <code>Objects</code>.
   */
  public HashSet<Point> getRenderedTiles();

  /**
   * Displays a <code>Notice</code> on the board.
   *
   * If <code>notice</code> is <code>null</code>, displays nothing.
   *
   * @param notice
   *          the <code>Notice</code> to display.
   */
  public void setNotice(Notice notice);

  /**
   * Retrieves the current <code>Notice</code> that is displayed by the board.
   *
   * @return the current <code>Notice</code>.
   */
  public Notice getNotice();

  /**
   * Hides any displayed <code>Notice</code>s.
   *
   * @return a <code>boolean</code> indicating whether a <code>Notice</code> was formerly displayed
   *         or not
   */
  public boolean hideNotice();

  /**
   * Redraws the game panel in accord with the specified layout.
   *
   * @param g
   *          the <code>Graphics</code> item to use
   * @param size
   *          the size of the canvas to draw upon
   */
  public void redraw(Graphics g, Dimension size);

}
