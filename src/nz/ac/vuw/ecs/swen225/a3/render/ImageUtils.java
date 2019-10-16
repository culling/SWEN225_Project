package nz.ac.vuw.ecs.swen225.a3.render;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * An utility class for adjustments to tile/item/actor images.
 *
 * @author achtenisaa 300437718
 */
public class ImageUtils {

  /**
   * Tints an <code>Image</code>, using a unique colour based on a number from 0 to 39.
   *
   * @param id
   *          the number to derive the tint colour from
   * @param image
   *          the image to tint
   * @return a tinted <code>BufferedImage</code>
   */
  public static Image tint(Image image, int id) {
    if (id > 39) {
      return image;
    }

    BufferedImage tintedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
        BufferedImage.TYPE_INT_ARGB);

    Color color = Color.gray; // ends with 0

    switch (id % 10) { // ones digit
    case 1:
      color = Color.CYAN;
      break;
    case 2:
      color = Color.PINK;
      break;
    case 3:
      color = Color.GREEN;
      break; // lime green
    case 4:
      color = Color.YELLOW;
      break;
    case 5:
      color = Color.BLUE;
      break;
    case 6:
      color = Color.RED;
      break;
    case 7:
      color = new Color(100, 30, 180);
      break; // purple
    case 8:
      color = Color.ORANGE;
      break;
    case 9:
      color = new Color(50, 110, 80);
      break; // forest green
    }

    switch ((id - (id % 10)) / 10) { // tens digit
    case 1:
      color = color.darker();
      break;
    case 2:
      color = color.brighter();
      break;
    case 3:
      color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
      break;
    }

    Graphics2D graphics = tintedImage.createGraphics();
    graphics.drawImage(image, 0, 0, null);
    graphics.setComposite(AlphaComposite.SrcAtop);
    graphics.setColor(color);
    graphics.fillRect(0, 0, tintedImage.getWidth(), tintedImage.getHeight());

    graphics.dispose();

    return tintedImage;
  }
}
