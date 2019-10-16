package nz.ac.vuw.ecs.swen225.a3.render;

import java.awt.*;
import java.util.HashSet;

import nz.ac.vuw.ecs.swen225.a3.common.*;
import nz.ac.vuw.ecs.swen225.a3.common.Renderer;

/**
 * Renders the game's content.
 *
 * @author achtenisaa 300437718
 */
public class Renderer_Impl implements Renderer {
  private static final Color BACKDROP_COLOR = new Color(0, 0, 0, 0);
  private static final Color FOCUS_AREA_BACKDROP_COLOR = new Color(210, 210, 210);
  private static final Color FREE_COLOR = new Color(40, 40, 40);
  private static final Color WALL_COLOR = new Color(78, 78, 78);
  private static final Color PLACEHOLDER_COLOR = new Color(170, 10, 200);

  /**
   * The size when the board, rather than being displayed completely, is only shown when overlapping
   * with the focus area. This also determines the size of said focus area.
   */
  private static final int FOCUS_AREA_SIZE = 9;

  /**
   * A 'magic number' used to compensate for properties of default Java rounding.
   */
  private static final int ROUNDING_COMPENSATION = 3; // in px

  private int cellSize = -1;
  private boolean customFocusArea;

  private Rectangle focusArea;
  private MazeState data = null;
  private HashSet<Point> renderedTiles;
  private HashSet<Object> renderedItems;
  private Notice notice;

  /**
   * Creates a new <code>Renderer_Impl</code>.
   */
  public Renderer_Impl() {
    resetFocusArea();
    renderedItems = new HashSet<>();
    renderedTiles = new HashSet<>();
  }

  /**
   * Draws an rectangle of the specified colour in the stated location.
   *
   * @param graphics
   *          the <code>Graphics</code> item to draw with
   * @param location
   *          the location of the item to draw
   * @param offset
   *          the amount to modify the location by, based on the sizing of the board
   * @param color
   *          the colour to draw the square in
   * @param cellSize
   *          the size of a cell; should be the same as others
   * @param scale
   *          how many cells the square should encompass
   */
  private void drawSquare(Graphics graphics, Point location, Point offset, Color color,
      int cellSize, int scale) {
    graphics.setColor(color);
    graphics.fillRect(((location.x - focusArea.x) * cellSize) + offset.x,
        ((location.y - focusArea.y) * cellSize) + offset.y, cellSize * scale, cellSize * scale);
  }

  /**
   * Draws an individual item or tile, represented using the specified image in the stated location.
   *
   * @param graphics
   *          the <code>Graphics</code> item to draw with
   * @param location
   *          the location of the item to draw
   * @param offset
   *          the amount to modify the location by, based on the sizing of the board
   * @param image
   *          the image to draw
   * @param cellSize
   *          the size of a cell; should be the same as others
   * @param scale
   *          how many cells the image should encompass
   * @return <code>false</code> if the image pixels are still changing; <code>true</code> otherwise
   */
  private boolean drawIcon(Graphics graphics, Point location, Point offset, Image image,
      int cellSize, int scale) {
    return graphics.drawImage(image, ((location.x - focusArea.x) * cellSize) + offset.x,
        ((location.y - focusArea.y) * cellSize) + offset.y, cellSize * scale, cellSize * scale,
        null);
  }

  /**
   * Determines whether an object is within some bounds, specified by a minimum and maximum point.
   *
   * @param location
   *          the location of the object
   * @param min
   *          a <code>Point</code> containing the minimum <code>x</code> and <code>y</code> values
   * @param max
   *          a <code>Point</code> containing the maximum <code>x</code> and <code>y</code> * values
   * @return a <code>boolean</code> representing the object's location relative to the specified
   *         bounds
   */
  private boolean inBounds(Point location, Point min, Point max) {
    return !(max.x <= location.x || max.y <= location.y || min.y > location.y
        || min.x > location.x);
  }

  @Override
  public boolean setMaze(MazeState maze) {
    data = maze;
    return data != null;
  }

  @Override
  public MazeState getMaze() {
    return data;
  }

  @Override
  public boolean setFocusArea(Rectangle area) {
    if (area.width != FOCUS_AREA_SIZE || area.height != FOCUS_AREA_SIZE) {
      return false;
    }

    customFocusArea = true;
    focusArea = area;
    return true;
  }

  @Override
  public void resetFocusArea() {
    customFocusArea = false;
    focusArea = new Rectangle(0, 0, FOCUS_AREA_SIZE, FOCUS_AREA_SIZE);
  }

  @Override
  public Rectangle getFocusArea() {
    return focusArea;
  }

  @Override
  public Point getMaxPoint() {
    return new Point(Math.min(data.getWidth(), focusArea.x + focusArea.width),
        Math.min(data.getHeight(), focusArea.y + focusArea.height));
  }

  @Override
  public Point getMinPoint() {
    return new Point(Math.max(0, focusArea.x), Math.max(0, focusArea.y));
  }

  @Override
  public int getCellSize() {
    return cellSize;
  }

  @Override
  public HashSet<Object> getRenderedItems() {
    return renderedItems;
  }

  @Override
  public HashSet<Point> getRenderedTiles() {
    return renderedTiles;
  }

  @Override
  public void setNotice(Notice notice) {
    this.notice = notice;
  }

  @Override
  public Notice getNotice() {
    return notice;
  }

  @Override
  public boolean hideNotice() {
    if (notice == null) {
      return true;
    }

    notice = null;
    return false;
  }

  @Override
  public void redraw(Graphics g, Dimension size) {
    Graphics2D graphics = (Graphics2D) g;

    graphics.setColor(BACKDROP_COLOR);
    graphics.fillRect(0, 0, size.width, size.height);

    renderedTiles.clear();
    renderedItems.clear();

    if (data == null) {
      return;
    }

    ActorInfo chap = data.getChap();
    Point offset = new Point(
        size.width > size.height ? (size.width - size.height) / 2 : ROUNDING_COMPENSATION,
        size.height > size.width ? (size.height - size.width) / 2 : ROUNDING_COMPENSATION);

    if (Math.min(data.getHeight(), data.getWidth()) <= FOCUS_AREA_SIZE && !customFocusArea) {
      resetFocusArea();
      cellSize = (Math.min(size.height, size.width) / Math.max(data.getWidth(), data.getHeight()));
    } else {
      if (!customFocusArea) {
        focusArea = new Rectangle(chap.getLocation().x - FOCUS_AREA_SIZE / 2,
            chap.getLocation().y - FOCUS_AREA_SIZE / 2, FOCUS_AREA_SIZE, FOCUS_AREA_SIZE);
      }
      cellSize = (Math.min(size.height, size.width) / FOCUS_AREA_SIZE);

      // draw focus area to distinguish from backdrop
      drawSquare(graphics, focusArea.getLocation(), offset, FOCUS_AREA_BACKDROP_COLOR, cellSize,
          FOCUS_AREA_SIZE);
    }

    Point max = getMaxPoint();
    Point min = getMinPoint();

    // Draw tiles
    for (int y = min.y; y < max.y; y++) {
      for (int x = min.x; x < max.x; x++) {
        TileInfo tile = data.getTileAt(x, y);
        Point location = new Point(x, y);

        if (tile.getName().equals("LockedDoor") || tile.getName().equals("ExitDoor")) {
          drawSquare(graphics, location, offset, FREE_COLOR, cellSize, 1);

          if (tile.getName().equals("LockedDoor")) {
            // check made here for optimisation
            int id = (int) tile.getField("id");
            drawIcon(graphics, location, offset, ImageUtils.tint(tile.getImage(), id), cellSize, 1);

            renderedTiles.add(location);
            continue;
          }
        } else if (tile.getImage() == null) {
          if (tile.getName().equals("Free")) {
            drawSquare(graphics, location, offset, FREE_COLOR, cellSize, 1);
          } else if (tile.getName().equals("Wall")) {
            drawSquare(graphics, location, offset, WALL_COLOR, cellSize, 1);
          } else {
            // image missing; purple square
            drawSquare(graphics, location, offset, PLACEHOLDER_COLOR, cellSize, 1);
          }

          renderedTiles.add(location);
          continue;
        }

        renderedTiles.add(location);
        drawIcon(graphics, location, offset, tile.getImage(), cellSize, 1);
      }
    }

    // Draw items
    for (ItemInfo item : data.getItems()) {
      Point location = item.getLocation();

      if (!inBounds(location, min, max)) {
        continue;
      }

      if (item.getName().equals("Key")) {
        int id = (int) item.getField("id");
        renderedItems.add(item);
        drawIcon(graphics, item.getLocation(), offset, ImageUtils.tint(item.getImage(), id),
            cellSize, 1);
      } else {
        renderedItems.add(item);
        drawIcon(graphics, item.getLocation(), offset, item.getImage(), cellSize, 1);
      }
    }

    // Draw Enemies
    for (ActorInfo enemy : data.getEnemies()) {
      Point location = enemy.getLocation();

      if (!inBounds(location, min, max)) {
        continue;
      }

      renderedItems.add(enemy);
      drawIcon(graphics, enemy.getLocation(), offset, enemy.getImage(), cellSize, 1);
    }

    Point location = chap.getLocation();

    // Draw Chap
    if (inBounds(location, min, max)) {
      renderedItems.add(chap);
      drawIcon(graphics, chap.getLocation(), offset, chap.getImage(), cellSize, 1);
    }

    // Draw Notice
    if (notice != null) {
      notice.redraw(graphics, size);
    }
  }
}
