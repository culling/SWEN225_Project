package nz.ac.vuw.ecs.swen225.a3.tests.render;

import nz.ac.vuw.ecs.swen225.a3.common.Renderer;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Key;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.LockedDoor;
import nz.ac.vuw.ecs.swen225.a3.render.ImageUtils;
import nz.ac.vuw.ecs.swen225.a3.render.Notice_Impl;
import nz.ac.vuw.ecs.swen225.a3.render.Renderer_Impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * A few very basic tests relating to the <code>render</code> portion of the program.
 *
 * @author achtenisaa 300437718
 */
public class RendererTests {
  final static int MAX_TINT = 40;

  /**
   * Tests that the renderer starts up without error.
   */
  @Test
  public void test_renderPass() {
    Renderer renderer = new Renderer_Impl();
    JComponent panel = new JComponent() {
      protected void paintComponent(Graphics g) {
        renderer.redraw(g, this.getSize());
      }
    };

    BufferedImage image = new BufferedImage(600, 600, BufferedImage.TYPE_BYTE_GRAY);
    panel.setSize(new Dimension(600, 600));
    panel.paint(image.getGraphics());
  }

  /**
   * Tests the different exposed components of the notice functionality
   */
  @Test
  public void test_notice() {
    // Test that the notice actually... you know, renders
    Renderer renderer = new Renderer_Impl();
    Notice_Impl notice = new Notice_Impl("The quick brown dog jumps over the lazy dog.");
    renderer.setNotice(notice);
    JComponent panel = new JComponent() {
      protected void paintComponent(Graphics g) {
        renderer.redraw(g, this.getSize());
        renderer.getNotice().redraw((Graphics2D) g, this.getSize());
      }
    };

    BufferedImage image = new BufferedImage(600, 600, BufferedImage.TYPE_BYTE_GRAY);
    panel.setSize(new Dimension(600, 600));
    panel.paint(image.getGraphics());

    // Now do some more pedestrian stuff
    Assertions.assertNotNull(renderer.getNotice());
    Assertions.assertEquals(notice, renderer.getNotice());
    Assertions.assertFalse(renderer.hideNotice());
    Assertions.assertNotEquals(notice, renderer.getNotice());
  }

  /**
   * Tests that all possible <code>LockedDoor</code>s and <code>Key</code>s are coloured uniquely.
   */
  @Test
  public void test_colourTiles() {
    ArrayList<Image> doorList = new ArrayList<>();
    ArrayList<Image> keyList = new ArrayList<>();

    Image door = new LockedDoor(new Point(0, 0), 0).getInfo().getImage();
    Image key = new Key(0).getInfo(new Point(0, 0)).getImage();

    for (int i = 0; i < MAX_TINT; i++) {
      doorList.add(ImageUtils.tint(door, i));
      keyList.add(ImageUtils.tint(key, i));
    }

    for (int i = 0; i < MAX_TINT; i++) {
      for (int j = i + 1; j < MAX_TINT; j++) {
        Assertions.assertNotEquals(doorList.get(i), doorList.get(j));
        Assertions.assertNotEquals(keyList.get(i), keyList.get(j));
      }
    }

    Assertions.assertEquals(ImageUtils.tint(door, MAX_TINT + 1),
        ImageUtils.tint(door, MAX_TINT + 2));
    Assertions.assertEquals(ImageUtils.tint(key, MAX_TINT + 1), ImageUtils.tint(key, MAX_TINT + 2));
  }

}
