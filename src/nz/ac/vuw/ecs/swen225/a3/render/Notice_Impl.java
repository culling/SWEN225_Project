package nz.ac.vuw.ecs.swen225.a3.render;

import nz.ac.vuw.ecs.swen225.a3.common.Notice;

import javax.swing.*;
import java.awt.*;

/**
 * Displays a notice atop the board. Once created, its details cannot be changed.
 *
 * @author achtenisaa 300437718
 */
public class Notice_Impl implements Notice {
  private String notice;
  private Color color;
  private Image icon;

  /**
   * Creates a new <code>Notice_Impl</code>.
   *
   * @param notice
   *          the text to display
   * @param textColor
   *          the colour of the displayed text
   * @param icon
   *          the path of the icon associated with the notice
   */
  public Notice_Impl(String notice, Color textColor, String icon) {
    this.notice = notice;
    this.color = textColor;
    this.icon = icon == null ? null : new ImageIcon("./images" + icon).getImage();
  }

  /**
   * Creates a new <code>Notice_Impl</code> with white text and no icon.
   *
   * @param notice
   *          the text to display
   */
  public Notice_Impl(String notice) {
    this(notice, Color.white, null);
  }

  @Override
  public String getText() {
    return notice;
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public Image getIcon() {
    return icon;
  }

  public void redraw(Graphics2D graphics, Dimension size) {
    int lineWidth = (size.width - 70) / 7;
    graphics.setFont(new Font("System", Font.PLAIN, 12));

    StringBuilder noticeBuilder = new StringBuilder(notice);
    int pos = 0; // start of the line
    int end = 0; // end of the line
    int num = 0; // line number

    while (end < noticeBuilder.length() - 1 && num < 100) {
      if (pos + lineWidth >= noticeBuilder.length()) {
        end = noticeBuilder.length() - 1;
      } // last line
      else {
        end = noticeBuilder.lastIndexOf(" ", pos + lineWidth);
      }
      if (end < pos) {
        end = pos + lineWidth - 1;
      } // word is bigger than lineWidth

      String line = noticeBuilder.substring(pos, pos = end + 1);

      graphics.setColor(Color.BLACK);
      graphics.fillRect(0, (++num * 25) - (num == 1 ? 25 : 20), size.width, 45);

      graphics.setColor(color);
      graphics.drawString(line, 60, num * 25);
    }

    if (icon != null) {
      graphics.drawImage(icon, 20, 12, 20, 20, null);
    }
  }
}
