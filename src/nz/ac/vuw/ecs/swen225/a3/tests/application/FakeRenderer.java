package nz.ac.vuw.ecs.swen225.a3.tests.application;

import java.awt.*;
import java.util.HashSet;

import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.common.Notice;
import nz.ac.vuw.ecs.swen225.a3.common.Renderer;
import nz.ac.vuw.ecs.swen225.a3.maze.MazeState_Impl;

/**
 * Fake Renderer for Application Package Tests
 *
 * @author Bryony
 *
 */
public class FakeRenderer implements Renderer {

  int redrawn = 0;
  String notice = "";

  @Override
  public boolean setMaze(MazeState map) {
    return true;
  }

  @Override
  public MazeState getMaze() {
    return new MazeState_Impl(0, 0, 0);
  }

  @Override
  public boolean setFocusArea(Rectangle area) {
    return true;
  }

  @Override
  public void resetFocusArea() {
  }

  @Override
  public Rectangle getFocusArea() {
    return null;
  }

  @Override
  public Point getMaxPoint() {
    return null;
  }

  @Override
  public Point getMinPoint() {
    return null;
  }

  @Override
  public int getCellSize() {
    return 0;
  }

  @Override
  public HashSet<Object> getRenderedItems() {
    return new HashSet<>();
  }

  @Override
  public HashSet<Point> getRenderedTiles() {
    return new HashSet<>();
  }

  @Override
  public void setNotice(Notice notice) {
    this.notice = notice.getText();
  }

  @Override
  public Notice getNotice() {
    return null;
  }

  @Override
  public boolean hideNotice() {
    return true;
  }

  @Override
  public void redraw(Graphics g, Dimension size) {
    redrawn++;
  }

}
