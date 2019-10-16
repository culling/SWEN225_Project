package nz.ac.vuw.ecs.swen225.a3.tests.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

import nz.ac.vuw.ecs.swen225.a3.common.Application;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.GameState;
import nz.ac.vuw.ecs.swen225.a3.application.SideBar;

/**
 * A fake Application for
 *
 * @author Bryony
 *
 */
public class VisualTestGUI implements Application {

  private JFrame frame;
  private int time;
  private SideBar sideBar;
  private RendererApplTest drawing;
  private Color background;
  private boolean paused;
  private JDialog pausedFrame;
  private boolean lostLevel;
  private ScheduledExecutorService executorService;

  @SuppressWarnings("serial")
  private void createDisplay() {
    JComponent drawing = new JComponent() {
      protected void paintComponent(Graphics g) {
        redraw(g);
      }
    };
    drawing.setPreferredSize(new Dimension(600, 600));
    drawing.setVisible(true);
    this.drawing = new RendererApplTest();
    JComponent sideBar = new JComponent() {
      protected void paintComponent(Graphics g) {
        redrawBar(g);
      }
    };
    sideBar.setPreferredSize(new Dimension(200, 600));
    sideBar.setVisible(true);
    this.sideBar = new SideBar();

    JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, drawing, sideBar);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.add(split, BorderLayout.CENTER);
  }

  private void createMenu() {
    JMenuBar bar = new JMenuBar();
    frame.setJMenuBar(bar);
    JMenu game = new JMenu("Game");
    bar.add(game);
    JMenuItem newGame = new JMenuItem("New Game");
    newGame.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newGame();
      }
    });
    game.add(newGame);
    JMenuItem restartLevel = new JMenuItem("Restart Level");
    restartLevel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        loadLevel();
      }
    });
    game.add(restartLevel);
    JMenuItem resume = new JMenuItem("Resume Saved Game");
    resume.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        loadGame();
      }
    });
    game.add(resume);
    JMenuItem save = new JMenuItem("Save Game");
    save.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        save();
      }
    });
    game.add(save);
    JMenuItem quit = new JMenuItem("Quit");
    quit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        quit();
      }
    });
    game.add(quit);
  }

  private void createKeys() {
    // Keys key = new Keys();
    // frame.addKeyListener(key);
  }

  /**
   * main method creates a new VisualTestGUI
   *
   * @param args
   */
  public static void main(String[] args) {
    VisualTestGUI app = new VisualTestGUI();
    app.getTime();
  }

  /**
   * Constructor
   */
  public VisualTestGUI() {
    frame = new JFrame("Chap's Challenge");
    createDisplay();
    createMenu();
    createKeys();
    frame.setSize(800, 800);
    frame.pack();
    frame.setVisible(true);
    time = 20;
    background = Color.blue;
    paused = false;
    lostLevel = false;
    createPausedScreen();
    executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.scheduleAtFixedRate(() -> this.startTiming(), 1, 1, TimeUnit.SECONDS);
  }

  private void startTiming() {
    if (!paused) {
      time--;
      redraw();
      if (time == 0) {
        timeOut();
      }
    }
  }

  /**
   * Redraws frame
   */
  public void redraw() {
    frame.repaint();
  }

  private void redraw(Graphics g) {
    if (lostLevel) {
      lostLevel = false;
      background = Color.yellow;
      time = 2;
      executorService.shutdown();
      executorService = Executors.newSingleThreadScheduledExecutor();
      executorService.scheduleAtFixedRate(() -> this.startTiming(), 1, 1, TimeUnit.SECONDS);
      paused = false;
      redraw();
    }
    drawing.redraw(g, background);
  }

  private void redrawBar(Graphics g) {
    sideBar.redraw(g, time, 0, 1, null);
  }

  public void save() {
    background = Color.cyan;
    redraw();
  }

  public void moveChap(Direction d) {
    background = Color.green;
    redraw();
  }

  public void quit() {
    background = Color.magenta;
    redraw();
  }

  public void loadGame() {
    background = Color.orange;
    redraw();
  }

  public void loadLevel() {
    background = Color.pink;
    redraw();
  }

  public void newGame() {
    background = Color.red;
    redraw();
  }

  public void pause() {
    paused = true;
    pausedFrame.setVisible(true);
  }

  public void stopPause() {
    paused = false;
    pausedFrame.setVisible(false);
  }

  private void createPausedScreen() {
    pausedFrame = new JDialog(frame);
    JLabel label = new JLabel("The Game is Paused");
    JPanel panel = new JPanel();
    panel.add(label);
    pausedFrame.add(panel);
    pausedFrame.setSize(200, 50);
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
          stopPause();
        }
      }

    });
  }

  private void timeOut() {
    int result = JOptionPane.showConfirmDialog(frame,
        "You've run out of time.\n" + "Would you like to try this level again?", "Timeout",
        JOptionPane.DEFAULT_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      lostLevel = true;
      paused = true;
      redraw();
    }
  }

  @Override
  public int getTime() {
    return 0;
  }

  @Override
  public GameState getGameState() {
    return null;
  }

  @Override
  public void won() {
  }

  @Override
  public void lost() {
  }

  @Override
  public void info(String message) {
  }

  @Override
  public void help() {
  }
}
