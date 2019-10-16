package nz.ac.vuw.ecs.swen225.a3.tests.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.common.Direction;
import nz.ac.vuw.ecs.swen225.a3.common.ItemInfo;
import nz.ac.vuw.ecs.swen225.a3.common.MazeState;
import nz.ac.vuw.ecs.swen225.a3.common.TileInfo;
import nz.ac.vuw.ecs.swen225.a3.maze.MazeState_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.actors.ActorInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.maze.tiles.TileInfo_Impl;
import nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter.JsonConverter;
import nz.ac.vuw.ecs.swen225.a3.persistence.jsonConverter.JsonConverter_Impl;

class JsonObjectConversionForBoardTest {
  @BeforeEach
  void initalize() {
    Maze_Impl.init();
  }

  @Test
  void test_SimpleMazeState_1() {
    JsonConverter jsonConverter = new JsonConverter_Impl();

    MazeState mazeState = makeSimpleMazeState();
    TileInfo[][] boardOriginal = mazeState.getBoard();

    JsonObject jsonObject = jsonConverter.getBoardJson(boardOriginal, mazeState.getHeight(),
        mazeState.getWidth());

    TileInfo[][] board = jsonConverter.getBoardFromJson(jsonObject, mazeState.getHeight(),
        mazeState.getWidth());

    assertEquals(boardOriginal[0][0], board[0][0]);
    assertEquals(boardOriginal[0][1], board[0][1]);
  }

  public MazeState makeSimpleMazeState() {
    MazeState state = new MazeState_Impl(4, 4, 1);

    // add Chap
    Map<String, Object> chapFields = new HashMap();
    chapFields.put("chipsCollected", 0);
    state.setChap(new ActorInfo_Impl("Chap", null, new Point(1, 1), Direction.NORTH, chapFields));

    // add inventory
    state.setInventory(new ArrayList<ItemInfo>());

    // add board/tiles
    TileInfo wall = new TileInfo_Impl("Wall", null, new HashMap<String, Object>());
    TileInfo free = new TileInfo_Impl("Free", null, new HashMap<String, Object>());

    state.setTileAt(wall, 0, 0);
    state.setTileAt(wall, 1, 0);
    state.setTileAt(wall, 2, 0);
    state.setTileAt(wall, 3, 0);
    state.setTileAt(wall, 0, 3);
    state.setTileAt(wall, 1, 3);
    state.setTileAt(wall, 2, 3);
    state.setTileAt(wall, 3, 3);
    state.setTileAt(wall, 0, 1);
    state.setTileAt(wall, 0, 2);
    state.setTileAt(wall, 3, 1);
    state.setTileAt(wall, 3, 2);

    state.setTileAt(free, 1, 1);
    state.setTileAt(free, 1, 2);
    state.setTileAt(free, 2, 1);
    state.setTileAt(free, 2, 2);

    return state;
  }

}
