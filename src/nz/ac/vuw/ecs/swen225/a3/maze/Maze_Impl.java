package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.a3.common.ActorInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.Maze;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Bug;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Chap;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.Enemy;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Chip;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Exit;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Info;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Item;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Key;
import nz.ac.vuw.ecs.swen225.a3.maze.items.Pickupable;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.ExitDoor;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Free;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.LockedDoor;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.Wall;
import nz.ac.vuw.ecs.swen225.a3.plugin.MazeElementRegistry;

/**
 * Implements the Maze interface and is the point of interaction between the maze logic and the
 * application.
 *
 * @author straigfene 300373183
 *
 */
public class Maze_Impl extends Observable implements Maze{

	private int numChips;
	private Chap chap;
	private Set<Enemy> enemies = new HashSet<>();

	private Board board;

	private Set<Observer> observers = new HashSet<>();


	/**
	 * Constructor
	 */
	public Maze_Impl() {
		init();
	}

	/**
	 * Registers the basic maze elements (items/tiles/actors) created as part of the program (not plug-ins)
	 * that every level will need.
	 */
	public static void init() {
		MazeElementRegistry registry = MazeElementRegistry.getInstance();

		registry.RegisterTile(Wall.class);
		registry.RegisterTile(Free.class);
		registry.RegisterTile(ExitDoor.class);
		registry.RegisterTile(LockedDoor.class);

		registry.RegisterItem(Chip.class);
		registry.RegisterItem(Exit.class);
		registry.RegisterItem(Info.class);
		registry.RegisterItem(Key.class);

		registry.RegisterActor(Chap.class);
		registry.RegisterActor(Bug.class);

	}


	@Override
	public void addObserverAppl(Observer observer) {
		this.addObserver(observer);
		observers.add(observer);
		if(chap != null) { chap.addObserver(observer);}
	}

	@Override
	public void moveChap(Direction dir) {
		chap.move(dir, board);

		//if in same location as an enemy interact with it
		for(Enemy enemy : enemies) {
			if(chap.getLocation().equals(enemy.getLocation())) {
				enemy.interact(chap);
			}
		}
	}

	@Override
	public int getChipsLeft() {
		return numChips - chap.getChipsCollected();
	}
	
	@Override
	public List<Item> getInventory(){
		return chap.getInventory();
	}

	@Override
	public MazeState getState() {
		MazeState_Impl state = new MazeState_Impl(board.getWidth(), board.getHeight(), numChips);

		//add tiles and items
		board.addBoardInfoToSate(state);

		//add chap
		state.setChap(chap.getInfo());

		//add inventory
		for(Item item : chap.getInventory()) {
			state.addToInventory(item.getInfo(null));
		}

		//add enemies
		for(Enemy enemy : enemies) {
			state.addEnemy(enemy.getInfo());
		}

		return state;
	}

	@Override
	public void loadState(MazeState mazeState) {
		enemies.clear();
		
		//make board
		board = new Board(mazeState.getWidth(), mazeState.getHeight());
		board.load(mazeState);

		numChips = mazeState.getNumChips();

		//make chap
		chap = (Chap)Actor.makeActor(mazeState.getChap(), board.getTile(mazeState.getChap().getLocation().x, mazeState.getChap().getLocation().y));
		for(Observer observer : observers) {
			chap.addObserver(observer);
		}

		//make chaps inventory
		for(ItemInfo itemInfo : mazeState.getInventory()) {
			Item item = Item.makeItem(itemInfo);
			if(!(item instanceof Pickupable)) { throw new IllegalArgumentException("item is not pickupable"); }
			Pickupable pItem = (Pickupable)item;
			chap.addToInventry(pItem);
		}

		//add enemies
		for(ActorInfo actorInfo : mazeState.getEnemies()) {
			Enemy enemy = (Enemy)Actor.makeActor(actorInfo, board.getTile(actorInfo.getLocation().x, actorInfo.getLocation().y));
			enemies.add(enemy);
		}

	}

	@Override
	public void update() {
		for(Enemy enemy : enemies) {
			enemy.move(board);

			//check if same location as chap and if so interact with chap
			if(enemy.getLocation().equals(chap.getLocation())) {
				enemy.interact(chap);
			}
		}
	}

	/**
	 * Gets the board - for testing
	 * @return the baord
	 */
	public Board getBoard() {
		return board;
	}


	/**
	 * Gets Chap (the player character) - for testing
	 * @return chap
	 */
	public Chap getChap() {
		return chap;
	}

	/**
	 * Gets the total number of chips in the level - for testing
	 * @return the number of chips
	 */
	public int getNumChips() {
		return numChips;
	}

	/**
	 * Gets the enemies - for testing
	 * @return a set of all the enemies in the level
	 */
	public Set<Enemy> getEnemies(){
		return enemies;
	}
}
