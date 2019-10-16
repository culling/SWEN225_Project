package nz.ac.vuw.ecs.swen225.a3.application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;

/**
 * Organizes all visual aspects needed for the Application Class
 *
 * @author Bryony
 *
 */
public class VisualDisplay {

  private boolean lostLevel;
  private SideBar sideBar;
  private int time;
  private int level;
  private int chips;
  private List<ItemInfo> inventory;
  private JDialog pausedFrame;
  private ObservableAppl os;

  /**
   * Constructor. Sets up internal Observable
   */
  public VisualDisplay() {
    os = new ObservableAppl();
  }

  /**
   * Add the external elements needed
   *
   * @param sideBar
   * @param frame
   */
  public void addElements(SideBar sideBar, JFrame frame) {
    this.sideBar = sideBar;
    createDisplay(frame);
    createMenu(frame);
    createPausedScreen(frame);
  }

  /**
   * Add given Observer to internal Observable
   *
   * @param obs
   */
  public void addObserver(Observer obs) {
    os.addObserver(obs);
  }

  /**
   * Sets if have lost current level
   *
   * @param lostLevel
   */
  public void setLostLevel(boolean lostLevel) {
    this.lostLevel = lostLevel;
  }

  /**
   * Sets number of chips still not collected
   *
   * @param chips
   */
  public void setChips(int chips) {
    if (chips < 0)
      throw new RuntimeException("Chips can't be negative");
    if (chips > 999)
      throw new RuntimeException("Chips can't be 4+sf");
    this.chips = chips;
  }

  /**
   * Set the current inventory (list of images of items in inventory).
   * @param inventory
   * 			- the inventory
   */
  public void setInventory(List<ItemInfo> inventory) {
	  //System.out.println("\n inventory = " + inventory);
	  this.inventory = inventory;
  }

  /**
   * Returns current Time
   *
   * @return returns an integer
   */
  public int getTime() {
    return time;
  }

  /**
   * Sets time to given parameter
   *
   * @param time
   */
  public void setTime(int time) {
    if (time < 0)
      throw new RuntimeException("Time can't be negative");
    if (time > 999)
      throw new RuntimeException("Time can't be 4+sf");
    this.time = time;
  }

  /**
   * Sets level to given parameter
   *
   * @param level
   */
  public void setLevel(int level) {
    if (level < 0)
      throw new RuntimeException("Level can't be negative");
    if (level > 999)
      throw new RuntimeException("Level can't be 4+sf");
    this.level = level;
  }

  /**
   * Returns level
   *
   * @return returns an integer
   */
  public int getLevel() {
    return level;
  }

  /**
   * Opens the pop-up box which indicated game is paused
   *
   * @param b
   */
  public void paused(boolean b) {
    pausedFrame.setVisible(b);
  }

  /**
   * Redraws drawing panel Informs Application through Observer to get Renderer to redraw
   *
   * @param g
   * @param size
   */
  public void redraw(Graphics g, Dimension size) {
    if (lostLevel) {
      lostLevel = false;
      os.update("lostLevel");
    }
    Object[] arg = { g, size };
    os.update(arg);
  }

  /**
   * Redraws the SideBar
   *
   * @param g
   */
  public void redrawBar(Graphics g) {
    sideBar.redraw(g, time, chips, level, inventory);
  }

  @SuppressWarnings("serial")
  private void createDisplay(JFrame frame) {
    JComponent drawing = new JComponent() {
      protected void paintComponent(Graphics g) {
        redraw(g, this.getSize());
      }
    };
    drawing.setPreferredSize(new Dimension(600, 600));
    drawing.setVisible(true);
    JComponent sideBar = new JComponent() {
      protected void paintComponent(Graphics g) {
        redrawBar(g);
      }
    };
    sideBar.setPreferredSize(new Dimension(200, 600));
    sideBar.setVisible(true);

    JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, drawing, sideBar);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.add(split, BorderLayout.CENTER);
  }

  private void createMenu(JFrame frame) {
    JMenuBar bar = new JMenuBar();
    frame.setJMenuBar(bar);
    JMenu game = new JMenu("Game");
    bar.add(game);
    JMenuItem newGame = new JMenuItem("New Game");
    newGame.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        os.update("newGame");
      }
    });
    game.add(newGame);
    JMenuItem help = new JMenuItem("Help");
    help.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        os.update("help");
      }
    });
    game.add(help);
    JMenuItem restartLevel = new JMenuItem("Restart Level");
    restartLevel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        os.update("loadLevel");
      }
    });
    game.add(restartLevel);
    JMenuItem resume = new JMenuItem("Resume Saved Game");
    resume.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        os.update("loadGame");
      }
    });
    game.add(resume);
    JMenuItem save = new JMenuItem("Save Game");
    save.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        os.update("save");
      }
    });
    game.add(save);
    JMenuItem record = new JMenuItem("Record");
    record.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        os.update("record");
      }
    });
    game.add(record);
    JMenuItem replay = new JMenuItem("Replay");
    replay.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        os.update("replay");
      }
    });
    game.add(replay);
    JMenuItem quit = new JMenuItem("Quit");
    quit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        os.update("quit");
      }
    });
    game.add(quit);
  }

  private void createPausedScreen(JFrame frame) {
    pausedFrame = new JDialog(frame);
    JLabel label = new JLabel("The Game is Paused");
    JPanel panel = new JPanel();
    panel.add(label);
    pausedFrame.add(panel);
    pausedFrame.setSize(200, 100);
    pausedFrame.setLocation(300, 300);
    pausedFrame.setVisible(false);
    pausedFrame.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
      }

      @Override
      public void keyPressed(KeyEvent e) {
      }

      @Override
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
          os.update("stopPaused");
        }
      }
    });
  }

  /**
   * Opens a Help pop-up box which displays rules
   *
   * @param frame
   * @return returns true when box is closed
   */
  public boolean helpFrame(JFrame frame) {
    String mes = "Use the arrow keys to move Chap.\n" + "Collect chips and keys. Use same\n"
        + "colored keys to open locked doors.\n" + "Avoid danger. Try and win within\n"
        + "the timelimit.";
    JOptionPane.showMessageDialog(frame, mes);
    return true;
  }

}
