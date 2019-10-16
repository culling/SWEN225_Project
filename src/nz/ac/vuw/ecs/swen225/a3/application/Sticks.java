package nz.ac.vuw.ecs.swen225.a3.application;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Draws the classic Calculator look numbers for the SideBar
 * 
 * @author Bryony
 *
 */
public class Sticks {

  private static Color empty = new Color(0, 150, 0);
  private static Color filled = new Color(0, 255, 0);

  private static void topLeft(Graphics g, int x, int y) {
    g.setColor(empty);
    g.fillRect(x, y + 2, 2, 2);
    g.fillRect(x, y + 6, 2, 2);
    g.fillRect(x, y + 10, 2, 2);
    g.fillRect(x, y + 14, 2, 2);
    g.fillRect(x, y + 18, 2, 2);

    g.fillRect(x + 2, y + 4, 2, 2);
    g.fillRect(x + 2, y + 8, 2, 2);
    g.fillRect(x + 2, y + 12, 2, 2);
    g.fillRect(x + 2, y + 16, 2, 2);

    g.fillRect(x + 4, y + 6, 2, 2);
    g.fillRect(x + 4, y + 10, 2, 2);
    g.fillRect(x + 4, y + 14, 2, 2);
  }

  private static void topLeftFilled(Graphics g, int x, int y) {
    g.setColor(filled);
    for (int i = 2; i < 19; i++) {
      g.fillRect(x, y + i, 2, 2);
    }
    for (int i = 4; i < 17; i++) {
      g.fillRect(x + 2, y + i, 2, 2);
    }
    for (int i = 6; i < 15; i++) {
      g.fillRect(x + 4, y + i, 2, 2);
    }
  }

  private static void botLeft(Graphics g, int x, int y) {
    g.setColor(empty);
    y = y + 20;

    g.fillRect(x, y + 2, 2, 2);
    g.fillRect(x, y + 6, 2, 2);
    g.fillRect(x, y + 10, 2, 2);
    g.fillRect(x, y + 14, 2, 2);
    g.fillRect(x, y + 18, 2, 2);

    g.fillRect(x + 2, y + 4, 2, 2);
    g.fillRect(x + 2, y + 8, 2, 2);
    g.fillRect(x + 2, y + 12, 2, 2);
    g.fillRect(x + 2, y + 16, 2, 2);

    g.fillRect(x + 4, y + 6, 2, 2);
    g.fillRect(x + 4, y + 10, 2, 2);
    g.fillRect(x + 4, y + 14, 2, 2);
  }

  private static void botLeftFilled(Graphics g, int x, int y) {
    g.setColor(filled);
    y = y + 20;
    for (int i = 2; i < 19; i++) {
      g.fillRect(x, y + i, 2, 2);
    }
    for (int i = 4; i < 17; i++) {
      g.fillRect(x + 2, y + i, 2, 2);
    }
    for (int i = 6; i < 15; i++) {
      g.fillRect(x + 4, y + i, 2, 2);
    }
  }

  private static void bot(Graphics g, int x, int y) {
    g.setColor(empty);
    g.fillRect(x + 4, y + 40, 2, 2);
    g.fillRect(x + 8, y + 40, 2, 2);
    g.fillRect(x + 12, y + 40, 2, 2);
    g.fillRect(x + 16, y + 40, 2, 2);
    g.fillRect(x + 20, y + 40, 2, 2);

    g.fillRect(x + 6, y + 38, 2, 2);
    g.fillRect(x + 10, y + 38, 2, 2);
    g.fillRect(x + 14, y + 38, 2, 2);
    g.fillRect(x + 18, y + 38, 2, 2);

    g.fillRect(x + 8, y + 36, 2, 2);
    g.fillRect(x + 12, y + 36, 2, 2);
    g.fillRect(x + 16, y + 36, 2, 2);
  }

  private static void botFilled(Graphics g, int x, int y) {
    g.setColor(filled);
    for (int i = 4; i < 21; i++) {
      g.fillRect(x + i, y + 40, 2, 2);
    }
    for (int i = 6; i < 19; i++) {
      g.fillRect(x + i, y + 38, 2, 2);
    }
    for (int i = 8; i < 17; i++) {
      g.fillRect(x + i, y + 36, 2, 2);
    }
  }

  private static void botRight(Graphics g, int x, int y) {
    g.setColor(empty);
    y = y + 20;
    x = x + 24;

    g.fillRect(x, y + 2, 2, 2);
    g.fillRect(x, y + 6, 2, 2);
    g.fillRect(x, y + 10, 2, 2);
    g.fillRect(x, y + 14, 2, 2);
    g.fillRect(x, y + 18, 2, 2);

    g.fillRect(x - 2, y + 4, 2, 2);
    g.fillRect(x - 2, y + 8, 2, 2);
    g.fillRect(x - 2, y + 12, 2, 2);
    g.fillRect(x - 2, y + 16, 2, 2);

    g.fillRect(x - 4, y + 6, 2, 2);
    g.fillRect(x - 4, y + 10, 2, 2);
    g.fillRect(x - 4, y + 14, 2, 2);
  }

  private static void botRightFilled(Graphics g, int x, int y) {
    g.setColor(filled);
    y = y + 20;
    x = x + 24;
    for (int i = 2; i < 19; i++) {
      g.fillRect(x, y + i, 2, 2);
    }
    for (int i = 4; i < 17; i++) {
      g.fillRect(x - 2, y + i, 2, 2);
    }
    for (int i = 6; i < 15; i++) {
      g.fillRect(x - 4, y + i, 2, 2);
    }
  }

  private static void topRight(Graphics g, int x, int y) {
    g.setColor(empty);
    x = x + 24;

    g.fillRect(x, y + 2, 2, 2);
    g.fillRect(x, y + 6, 2, 2);
    g.fillRect(x, y + 10, 2, 2);
    g.fillRect(x, y + 14, 2, 2);
    g.fillRect(x, y + 18, 2, 2);

    g.fillRect(x - 2, y + 4, 2, 2);
    g.fillRect(x - 2, y + 8, 2, 2);
    g.fillRect(x - 2, y + 12, 2, 2);
    g.fillRect(x - 2, y + 16, 2, 2);

    g.fillRect(x - 4, y + 6, 2, 2);
    g.fillRect(x - 4, y + 10, 2, 2);
    g.fillRect(x - 4, y + 14, 2, 2);
  }

  private static void topRightFilled(Graphics g, int x, int y) {
    g.setColor(filled);
    x = x + 24;
    for (int i = 2; i < 19; i++) {
      g.fillRect(x, y + i, 2, 2);
    }
    for (int i = 4; i < 17; i++) {
      g.fillRect(x - 2, y + i, 2, 2);
    }
    for (int i = 6; i < 15; i++) {
      g.fillRect(x - 4, y + i, 2, 2);
    }
  }

  private static void top(Graphics g, int x, int y) {
    g.setColor(empty);
    g.fillRect(x + 4, y, 2, 2);
    g.fillRect(x + 8, y, 2, 2);
    g.fillRect(x + 12, y, 2, 2);
    g.fillRect(x + 16, y, 2, 2);
    g.fillRect(x + 20, y, 2, 2);

    g.fillRect(x + 6, y + 2, 2, 2);
    g.fillRect(x + 10, y + 2, 2, 2);
    g.fillRect(x + 14, y + 2, 2, 2);
    g.fillRect(x + 18, y + 2, 2, 2);

    g.fillRect(x + 8, y + 4, 2, 2);
    g.fillRect(x + 12, y + 4, 2, 2);
    g.fillRect(x + 16, y + 4, 2, 2);
  }

  private static void topFilled(Graphics g, int x, int y) {
    g.setColor(filled);
    for (int i = 4; i < 21; i++) {
      g.fillRect(x + i, y, 2, 2);
    }
    for (int i = 6; i < 19; i++) {
      g.fillRect(x + i, y + 2, 2, 2);
    }
    for (int i = 8; i < 17; i++) {
      g.fillRect(x + i, y + 4, 2, 2);
    }
  }

  private static void mid(Graphics g, int x, int y) {
    g.setColor(empty);
    y = y + 18;

    g.fillRect(x + 6, y, 2, 2);
    g.fillRect(x + 10, y, 2, 2);
    g.fillRect(x + 14, y, 2, 2);
    g.fillRect(x + 18, y, 2, 2);

    g.fillRect(x + 4, y + 2, 2, 2);
    g.fillRect(x + 8, y + 2, 2, 2);
    g.fillRect(x + 12, y + 2, 2, 2);
    g.fillRect(x + 16, y + 2, 2, 2);
    g.fillRect(x + 20, y + 2, 2, 2);

    g.fillRect(x + 6, y + 4, 2, 2);
    g.fillRect(x + 10, y + 4, 2, 2);
    g.fillRect(x + 14, y + 4, 2, 2);
    g.fillRect(x + 18, y + 4, 2, 2);
  }

  private static void midFilled(Graphics g, int x, int y) {
    g.setColor(filled);
    y = y + 18;
    for (int i = 6; i < 19; i++) {
      g.fillRect(x + i, y, 2, 2);
    }
    for (int i = 4; i < 21; i++) {
      g.fillRect(x + i, y + 2, 2, 2);
    }
    for (int i = 6; i < 19; i++) {
      g.fillRect(x + i, y + 4, 2, 2);
    }
  }

  private static void draw(Graphics g, int x, int y, boolean tl, boolean bl, boolean b, boolean br,
      boolean tr, boolean t, boolean m) {
    if (tl)
      topLeftFilled(g, x, y);
    else
      topLeft(g, x, y);
    if (bl)
      botLeftFilled(g, x, y);
    else
      botLeft(g, x, y);
    if (b)
      botFilled(g, x, y);
    else
      bot(g, x, y);
    if (br)
      botRightFilled(g, x, y);
    else
      botRight(g, x, y);
    if (tr)
      topRightFilled(g, x, y);
    else
      topRight(g, x, y);
    if (t)
      topFilled(g, x, y);
    else
      top(g, x, y);
    if (m)
      midFilled(g, x, y);
    else
      mid(g, x, y);
  }

  /**
   * Draws an empty space (all sticks draw in darker dots)
   * 
   * @param g
   * @param x
   * @param y
   */
  public static void empty(Graphics g, int x, int y) {
    draw(g, x, y, false, false, false, false, false, false, false);
  }

  /**
   * Draws the number 1
   * 
   * @param g
   * @param x
   * @param y
   */
  public static void num1(Graphics g, int x, int y) {
    draw(g, x, y, false, false, false, true, true, false, false);
  }

  /**
   * Draws the number 2
   * 
   * @param g
   * @param x
   * @param y
   */
  public static void num2(Graphics g, int x, int y) {
    draw(g, x, y, false, true, true, false, true, true, true);
  }

  /**
   * Draws the number 3
   * 
   * @param g
   * @param x
   * @param y
   */
  public static void num3(Graphics g, int x, int y) {
    draw(g, x, y, false, false, true, true, true, true, true);
  }

  /**
   * Draws the number 4
   * 
   * @param g
   * @param x
   * @param y
   */
  public static void num4(Graphics g, int x, int y) {
    draw(g, x, y, true, false, false, true, true, false, true);
  }

  /**
   * Draws the number 5
   * 
   * @param g
   * @param x
   * @param y
   */
  public static void num5(Graphics g, int x, int y) {
    draw(g, x, y, true, false, true, true, false, true, true);
  }

  /**
   * Draws the number 6
   * 
   * @param g
   * @param x
   * @param y
   */
  public static void num6(Graphics g, int x, int y) {
    draw(g, x, y, true, true, true, true, false, true, true);
  }

  /**
   * Draws the number 7
   * 
   * @param g
   * @param x
   * @param y
   */
  public static void num7(Graphics g, int x, int y) {
    draw(g, x, y, false, false, false, true, true, true, false);
  }

  /**
   * Draws the number 8
   * 
   * @param g
   * @param x
   * @param y
   */
  public static void num8(Graphics g, int x, int y) {
    draw(g, x, y, true, true, true, true, true, true, true);
  }

  /**
   * Draws the number 9
   * 
   * @param g
   * @param x
   * @param y
   */
  public static void num9(Graphics g, int x, int y) {
    draw(g, x, y, true, false, true, true, true, true, true);
  }

  /**
   * Draws the number 0
   * 
   * @param g
   * @param x
   * @param y
   */
  public static void num0(Graphics g, int x, int y) {
    draw(g, x, y, true, true, true, true, true, true, false);
  }
}
