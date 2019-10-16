package nz.ac.vuw.ecs.swen225.a3.application;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import nz.ac.vuw.ecs.swen225.a3.common.Application;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.GameState;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Maze;
import nz.ac.vuw.ecs.swen225.a3.common.Persistence;
import nz.ac.vuw.ecs.swen225.a3.common.Renderer;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Item;
import nz.ac.vuw.ecs.swen225.a3.persistence.Persistence_Impl;
import nz.ac.vuw.ecs.swen225.a3.recnplay.Record;
import nz.ac.vuw.ecs.swen225.a3.recnplay.Replay;
import nz.ac.vuw.ecs.swen225.a3.render.Notice_Impl;
import nz.ac.vuw.ecs.swen225.a3.render.Renderer_Impl;

/**
 * Implementation of the Application Interface
 *
 * @author Bryony
 *
 */
public class Application_Impl implements Application {

	private Maze		maze;
	private Renderer 	renderer;
	private Persistence persistence;
	private JFrame 		frame;
	private int 		totalTime;
	private boolean 	paused;
	private Quitter 	q;

	private ScheduledExecutorService executorService;
	private VisualDisplay display;

	private ArrayList<GameState> states;
	private boolean		recording;
	private int			count;
	private Record		record;
	private ScheduledExecutorService replayService;

	// --------------------------
	// CONSTUCTOR and MAIN
	// --------------------------

	/**
	 * Main Constructor for an Application_Impl using given parameters. The boolean decides where the
	 * GameState is loaded from
	 *
	 * @param maze
	 * @param renderer
	 * @param persistence
	 * @param sideBar
	 * @param key
	 * @param o
	 * @param os
	 * @param b
	 * @param display
	 * @param qB
	 */
	public Application_Impl(Maze maze, Renderer renderer, Persistence persistence, SideBar sideBar,
			Keys key, Observable o, ObserverAppl os, boolean b, VisualDisplay display, boolean qB) {
		this.maze = maze;
		this.renderer = renderer;
		this.persistence = persistence;
		frame = new JFrame("Chap's Challenge");
		if (display == null)
			display = new VisualDisplay();
		this.display = display;
		display.addElements(sideBar, frame);
		if (!b) {
			GameState game = this.persistence.loadLastGame();
			display.setLevel(game.getCurrentLevel());
			display.setTime(game.getTimeRemaining());
			display.setChips(game.getMazeState().getNumChips());
			totalTime = game.getTotalLevelTime();
			maze.loadState(game.getMazeState());
		} else {
			display.setLevel(1);
			display.setTime(100);
			totalTime = 100;
			//maze.loadState(MazeTests.makeMazeStateWithItems());
			GameState fallbackGameState = persistence.loadLevel(1);
			maze.loadState(fallbackGameState.getMazeState());
			display.setChips(maze.getChipsLeft());
		}
		ObserverAppl observer;
		if (os == null)
			observer = new ObserverAppl(this);
		else
			observer = os;
		if (key == null || key instanceof NullKeys) {
			key = new Keys();
		}
		recording = false;
		frame.addKeyListener(key);
		key.addObserver(observer);
		if (o == null)
			maze.addObserverAppl(observer);
		else
			o.addObserver(observer);
		display.addObserver(observer);
		q = new Quitter(qB);
		frame.setSize(800, 800);
		frame.pack();
		frame.setVisible(true);
		display.setLostLevel(false);
		startTime();
	}

	/**
	 * Constructor for an Application_Impl without a Keys Gives Main Constructor given parameters and
	 * a NullKeys
	 *
	 * @param maze
	 * @param renderer
	 * @param persistence
	 * @param bar
	 * @param obs
	 * @param os
	 * @param b
	 * @param display
	 */
	public Application_Impl(Maze maze, Renderer renderer, Persistence persistence, SideBar bar,
			Observable obs, ObserverAppl os, boolean b, VisualDisplay display) {
		this(maze, renderer, persistence, bar, new NullKeys(), obs, os, b, display, false);
	}

	/**
	 * Constructor for a new Application_Impl with no parameters Gives Main Constructor new everything
	 */
	public Application_Impl() {
		this(new Maze_Impl(), new Renderer_Impl(), new Persistence_Impl(), new SideBar(), new Keys(),
				null, null, false, null, true);
	}

	// --------------------------
	// Overridden Methods
	// --------------------------

	@Override
	public int getTime() {
		return display.getTime();
	}

	@Override
	public GameState getGameState() {
		GameState gameState = new GameState_Impl(display.getLevel(), display.getTime(), totalTime,
				maze.getState());
		return gameState;
	}

	@Override
	public void won() {
		int result = JOptionPane.showConfirmDialog(frame,
				"You Completed Level " + display.getLevel()
				+ "!\nWould you like to continue on to the next " + "level?",
				"Won Level", JOptionPane.OK_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			pause();
			nextLevel();
		}
	}

	@Override
	public void lost() {
		int result = JOptionPane.showConfirmDialog(frame,
				"You failed Level " + display.getLevel() + ".\nWould you like to try this level again?",
				"Lost Level", JOptionPane.OK_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			pause();
			lostLevel();
		}
	}

	@Override
	public void info(String message) {
		if (message == null)
			throw new RuntimeException("No Message");
		renderer.setNotice(new Notice_Impl(message));
		redraw();
	}

	@Override
	public void quit() {
		if(recording) {
			record.write();
		}
		persistence.quit(display.getLevel());
		q.quit();
	}

	@Override
	public void save() {
		if(recording) {
			record.write();
		}
		persistence.save(getGameState());
		q.quit();
	}

	@Override
	public void loadGame() {
		reset();
		GameState game = persistence.loadLastGame();
		maze.loadState(game.getMazeState());
		display.setLevel(game.getCurrentLevel());
		display.setTime(game.getTimeRemaining());
		display.setChips(maze.getChipsLeft());
		display.setInventory(game.getMazeState().getInventory());
		paused = false;
		display.setLostLevel(false);
		startTime();
	}

	@Override
	public void loadLevel() {
		reset();
		GameState game = persistence.loadLastLevel();
		maze.loadState(game.getMazeState());
		display.setLevel(game.getCurrentLevel());
		display.setTime(game.getTimeRemaining());
		display.setChips(maze.getChipsLeft());
		display.setInventory(game.getMazeState().getInventory());
		totalTime = game.getTotalLevelTime();
		paused = false;
		display.setLostLevel(false);
		startTime();
	}

	@Override
	public void newGame() {
	  if(recording) {
      record.write();
    }
		reset();
		GameState game = persistence.newGame();
		maze.loadState(game.getMazeState());
		display.setLevel(game.getCurrentLevel());
		display.setTime(game.getTimeRemaining());
		display.setChips(maze.getChipsLeft());
		display.setInventory(game.getMazeState().getInventory());
		paused = false;
		display.setLostLevel(false);
		startTime();
	}

	@Override
	public void moveChap(Direction d) {
		if (!paused && states == null) {
			maze.moveChap(d);
			display.setChips(maze.getChipsLeft());
			display.setInventory(this.getInventoryImageList(maze.getInventory()));
		}
		redraw();
	}

	@Override
	public void pause() {
		if(paused) {
			stopPause();
		}
		else {
			paused = true;
			display.paused(true);
		}
	}

	@Override
	public void stopPause() {
		paused = false;
		display.paused(false);
	}


	@Override
	public void help() {
		paused = true;
		display.paused(true);
		if (display.helpFrame(frame)) {
			paused = false;
			display.paused(false);
		}
	}

	// --------------------------
	// Other Methods
	// --------------------------

	/**
	 * Load the game.
	 * @param gamestate to load
	 */
	private void loadGame(GameState game) {
		maze.loadState(game.getMazeState());
		maze.update();
		display.setLevel(game.getCurrentLevel());
		display.setTime(game.getTimeRemaining());
		display.setChips(maze.getChipsLeft());
		display.setInventory(game.getMazeState().getInventory());
		display.setLostLevel(false);
		redraw();
	}

	/**
	 * Redraws the Frame
	 */
	public void redraw() {
		frame.repaint();
		if(recording) {
			record.add(this.getGameState());
		}
	}

	private void startTiming() {
		if (!paused) {
			display.setTime(display.getTime() - 1);
			maze.update();
			if (display.getTime() == 0) {
				timeOut();
			}
			redraw();
		}
	}

	private void timeOut() {
		if(recording) {
			record.write();
		}
		int result = JOptionPane.showConfirmDialog(frame,
				"You ran out of time.\n Would you like to try this level again?", "Timeout",
				JOptionPane.OK_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			display.setLostLevel(true);
			paused = true;
			redraw();
		}
	}

	/**
	 * Sets time to given parameter
	 *
	 * @param i
	 */
	public void setTime(int i) {
		display.setTime(i);
	}

	/**
	 * Shuts down the Timer
	 */
	public void stopTime() {
		executorService.shutdown();
	}

	/**
	 * Starts the Timer Doesn't change time
	 */
	public void startTime() {
		executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(() -> this.startTiming(), 1, 1, TimeUnit.SECONDS);
	}

	/**
	 * Called when level is lost
	 */
	public void lostLevel() {
		loadLevel();
	}

	/**
	 * Returns Level
	 *
	 * @return Returns an integer
	 */
	public int getLevel() {
		return display.getLevel();
	}

	/**
	 * A method for Testing which pretends that the timer has run out
	 */
	public void setLevelPaused() {
		display.setLostLevel(true);
		paused = true;
		redraw();
	}

	/**
	 * Moves on to next level
	 */
	public void nextLevel() {
		display.setLevel(display.getLevel() + 1);
		if (display.getLevel() > 2) {
			display.setLevel(1);
			quit();
		} else {
			GameState game = persistence.loadLevel(display.getLevel());
			maze.loadState(game.getMazeState());
			display.setLevel(game.getCurrentLevel());
			display.setTime(game.getTimeRemaining());
			display.setChips(maze.getChipsLeft());
			display.setInventory(game.getMazeState().getInventory());
			totalTime = game.getTotalLevelTime();
			paused = false;
			stopPause();
			display.setLostLevel(false);
		}
	}

	/**
	 * Tells the Renderer to redraw, passing necessary parameters
	 *
	 * @param g
	 * @param size
	 */
	public void rendererRedraw(Graphics g, Dimension size) {
		renderer.setMaze(maze.getState());
		renderer.redraw(g, size);
	}

	/**
	 * Set the Level to the given parameter
	 *
	 * @param level
	 */
	public void setLevel(int level) {
		display.setLevel(level);
		GameState game = persistence.loadLevel(level);
		maze.loadState(game.getMazeState());
		display.setLevel(game.getCurrentLevel());
		display.setTime(game.getTimeRemaining());
		display.setChips(maze.getChipsLeft());
		display.setInventory(game.getMazeState().getInventory());
		totalTime = game.getTotalLevelTime();
		paused = false;
		display.setLostLevel(false);
	}

	/**
	 * Returns the Frame
	 *
	 * @return returns a JFrame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Removes the Information Screen
	 */
	public void cancelMessage() {
		renderer.setNotice(null);
		renderer.hideNotice();
	}

  /**
   * Begins recording the game.
   */
  public void record() {
    recording = true;
    record = new Record();
    record.setName("1");
    record.add(this.getGameState());
  }

  /**
   * Begins recording the game to the given file.
   *
   * @param name name of the file to be written
   */
  public void record(String name) {
    recording = true;
    record = new Record();
    record.setName(name);
    record.add(this.getGameState());
  }

  /**
   * Begins the replay
   */
  public void replayGame() {
    //System.out.println("Start replay");
    if(recording) {
      record.write();
    }
    reset();
    JFileChooser chooser = new JFileChooser();
    File dir = new File(System.getProperty("user.dir"));
    chooser.setCurrentDirectory(dir);
    int returnVal = chooser.showOpenDialog(null);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      // System.out.println("You chose to open this file: " +
      // chooser.getSelectedFile().getName());
    }
    File file = chooser.getSelectedFile();
    Replay rep = new Replay(file);
    states = rep.getStates();
    startFrames();
  }

  /**
   * Displays the given frame.
   *
   * @param i the index of the frame to replay.
   */
  public void replayframe(int i) {
    if (i >= states.size()) {
      this.stopFrames();
      //System.out.println("replay done");
    } else {
      loadGame(states.get(i));
      i++;
      count = i;
    }
    redraw();
    // System.out.println("frame: "+i +", "+ time);
    // redraw();
  }

  /**
   * Displays the next frame.
   */
  public void next() {
    if (paused && states != null) {
      if (count < states.size() - 1) {
        replayframe(count);
      }
    }
  }

  /**
   * Displays the previous frame.
   */
  public void back() {
    if (paused && states != null) {
      if (count > 0) {
        this.count -= 2;
        replayframe(count);
      }
    }
  }

  /**
   * Starts displaying frames in the replay until either paused or finished
   */
  public void startFrames() {
    replayService = Executors.newSingleThreadScheduledExecutor();
    replayService.scheduleAtFixedRate(() -> {
      if (!paused) {
        this.replayframe(count);
      }
    }, 500, 1000, TimeUnit.MILLISECONDS);
  }

  /**
   * Stops the replay.
   */
  public void stopFrames() {
    replayService.shutdown();
  }

  /**
   * Shuts down both Executors and clears states. Stops time.
   */
  public void reset() {
    if (this.replayService != null)
      replayService.shutdown();
    if (this.executorService != null)
      executorService.shutdown();
    states = null;
    stopTime();
  }

  /**
   * Writes the record to a file.
   */
  public void write() {
    if (record != null)
      record.write();
  }

  /**
   * Get the inventory from the current maze state as a list of images.
   * @param mazeState
   *        -the current state of the maze
   * @return the inventory as list of images
   */
  private List<ItemInfo> getInventoryImageList(List<Item> inventory){
    List<ItemInfo> imageInventory = new ArrayList<>();

    for(Item item : inventory) {
      imageInventory.add(item.getInfo(null));
    }
    return imageInventory;
  }


}
