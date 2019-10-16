package nz.ac.vuw.ecs.swen225.a3.application;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;

import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.render.ImageUtils;

/**
 * Draws the SideBar of the Game
 *
 * @author Bryony
 *
 */
public class SideBar {

	private double inventoryWidth = 180;
	private double inventoryHeight = 100;
	private double numItemsPerInvRow = 4;

  /**
   * Empty Constructor
   */
  public SideBar() {

  }

  /**
   * Redraws sidebar using given parameters
   *
   * @param g
   * @param time
   * @param chips
   * @param level
   * @param inventory
   * 			- A list of images of items in the inventory
   */
  public void redraw(Graphics g, int time, int chips, int level, List<ItemInfo> inventory) {
    if (time > 999 || chips > 999 || level > 999)
      throw new RuntimeException("Can't do 4+sf Numbers");
    if (time < 0 || chips < 0 || level < 0)
      throw new RuntimeException("Can't deal with Negative Numbers");
    int y = 40;

    g.setFont(new Font("Arial", Font.BOLD, 30));
    g.setColor(Color.black);
    centerText("LEVEL", (Graphics2D) g, 0, y, 200, 40);
    g.fillRect(50, y + 40, 98, 52);
    drawNumbers(level, 50, y + 40, g);

    y += 120;

    g.setColor(Color.black);
    centerText("TIME", (Graphics2D) g, 0, y, 200, 40);
    g.fillRect(50, y + 40, 98, 52);
    drawNumbers(time, 50, y + 40, g);

    y += 120;

    g.setColor(Color.black);
    centerText("CHIPS", (Graphics2D) g, 0, y, 200, 40);
    y += 30;
    centerText("LEFT", (Graphics2D) g, 0, y, 200, 40);
    g.fillRect(50, y + 40, 98, 52);
    drawNumbers(chips, 50, y + 40, g);

    y += 120;

    g.setColor(Color.gray);
    g.fillRect(10, y, 180, 100);

    drawInventory((Graphics2D)g, inventory, 10, y);
    // g.drawImage(loadImage("images/Key.png"), 12, y+2, 40, 40, null);
  }

  private void drawNumbers(int number, int x, int y, Graphics g) {
    String num = Integer.toString(number);
    char[] numsplit = num.toCharArray();
    int[] nums = new int[3];
    if (numsplit.length == 3) {
      nums[0] = Integer.parseInt(String.valueOf(numsplit[0]));
      nums[1] = Integer.parseInt(String.valueOf(numsplit[1]));
      nums[2] = Integer.parseInt(String.valueOf(numsplit[2]));
    } else if (numsplit.length == 2) {
      nums[0] = -1;
      nums[1] = Integer.parseInt(String.valueOf(numsplit[0]));
      nums[2] = Integer.parseInt(String.valueOf(numsplit[1]));
    } else {
      nums[0] = -1;
      nums[1] = -1;
      nums[2] = Integer.parseInt(String.valueOf(numsplit[0]));
    }
    int size = 26;
    x = x + 5;
    y = y + 5;
    for (int i = 0; i < nums.length; i++) {
      drawNumber(nums[i], i, x + (size + 5) * i, y, g);
    }
  }

  private void drawNumber(int num, int posi, int x, int y, Graphics g) {
    switch (num) {
    case -1:
      Sticks.empty(g, x, y);
      break;
    case 0:
      Sticks.num0(g, x, y);
      break;
    case 1:
      Sticks.num1(g, x, y);
      break;
    case 2:
      Sticks.num2(g, x, y);
      break;
    case 3:
      Sticks.num3(g, x, y);
      break;
    case 4:
      Sticks.num4(g, x, y);
      break;
    case 5:
      Sticks.num5(g, x, y);
      break;
    case 6:
      Sticks.num6(g, x, y);
      break;
    case 7:
      Sticks.num7(g, x, y);
      break;
    case 8:
      Sticks.num8(g, x, y);
      break;
    case 9:
      Sticks.num9(g, x, y);
      break;
    }
  }

  private void centerText(String text, Graphics2D g, int x, int y, int width, int height) {
    FontMetrics fm = g.getFontMetrics(g.getFont());
    java.awt.geom.Rectangle2D rect = fm.getStringBounds(text, g);
    int textHeight = (int) (rect.getHeight());
    int textWidth = (int) (rect.getWidth());

    int textX = x + (width - textWidth) / 2;
    int textY = y + (height - textHeight) / 2 + fm.getAscent();
    g.drawString(text, textX, textY);
  }

  private void drawInventory(Graphics2D g, List<ItemInfo> inventory, int x, int y) {
	  if(inventory == null) { return; }
	  if(inventory.isEmpty()) {return;}

	  double imgSize = this.inventoryWidth/this.numItemsPerInvRow;

	  int itemImgIndex = 0;

	  for(int row=0; row<2; row++) {
		  for(int col=0; col<this.numItemsPerInvRow; col++) {
			  int imgX = (int)(x + col*imgSize);
			  int imgY = (int)(y + row*imgSize);

			  ItemInfo item = inventory.get(itemImgIndex);
			  if(item.getImage() == null) {continue;}
			  if(item.hasField("id")) {
				  g.drawImage(ImageUtils.tint(item.getImage(), (int)item.getField("id")), imgX, imgY, (int)imgSize, (int)imgSize, null);
			  }else {
				  g.drawImage(item.getImage(), imgX, imgY, (int)imgSize, (int)imgSize, null);
			  }

			  itemImgIndex++;
			  if(itemImgIndex>=inventory.size()) { break; }
		  }
		  if(itemImgIndex>=inventory.size()) { break; }
	  }
  }

}
