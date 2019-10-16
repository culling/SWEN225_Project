package nz.ac.vuw.ecs.swen225.a3.tests.application;

import java.awt.Graphics;
import java.util.List;

import nz.ac.vuw.ecs.swen225.a3.application.SideBar;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;

/**
 * Fake SideBar for Application Package Tests Has a boolean in the constructor that can stop
 * redrawing
 * 
 * @author Bryony
 *
 */
public class FakeSideBar extends SideBar {

  int redrawn = 0;
  SideBar sideBar;
  boolean b;

  /**
   * Constructor with boolean parameter if b is false, the real SideBar doesn't redraw
   * 
   * @param b
   */
  public FakeSideBar(boolean b) {
    sideBar = new SideBar();
    this.b = b;
  }

  @Override
  public void redraw(Graphics g, int time, int chips, int level, List<ItemInfo> inventory) {
    redrawn++;
    if (b) {
      sideBar.redraw(g, time, chips, level, inventory);
    }
  }

  /**
   * Returns how many times have redrawn
   * 
   * @return returns an integer
   */
  public int checkRedrawn() {
    return redrawn;
  }

}
