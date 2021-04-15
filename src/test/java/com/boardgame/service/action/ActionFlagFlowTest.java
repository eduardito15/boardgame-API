package com.boardgame.service.action;

import com.boardgame.constants.ActionsEnum;
import com.boardgame.constants.GameStatusEnum;
import com.boardgame.constants.GamesEnum;
import com.boardgame.dto.ActionRequestDTO;
import com.boardgame.dto.ActionResponseDTO;
import com.boardgame.model.*;
import com.boardgame.service.GameFlow;
import com.boardgame.service.game.GameFactory;
import com.boardgame.service.game.MinesweeperGameImpl;
import com.boardgame.service.impl.ActionFlowImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActionFlagFlowTest {

    private ActionFlowImpl actionFlow;

    @MockBean
    private ActionFactory actionFactory;

    @MockBean
    private GameFlow gameFlow;

    @MockBean
    private GameFactory gameFactory;

    @Before
    public void setUp() {
        actionFlow = new ActionFlowImpl(actionFactory, gameFlow, gameFactory);

        Mockito.when(gameFlow.getGameById(any()))
                .thenReturn(createTestMockGame());

        Mockito.when(gameFactory.getInstance(any()))
                .thenReturn(new MinesweeperGameImpl());

        Mockito.when(actionFactory.getInstance(any(), any()))
                .thenReturn(new MinesweeperActionFlagImpl());
    }


    @Test
    public void testFlagNoFlagSquare() {
        ActionRequestDTO actionRequestDTO = new ActionRequestDTO();
        actionRequestDTO.setAction(ActionsEnum.FLAG);
        actionRequestDTO.setGameId("GID-TEST");
        actionRequestDTO.setRow(0);
        actionRequestDTO.setColumn(2);
        actionRequestDTO.setTimeMin(0);
        actionRequestDTO.setTimeSec(1);

        ActionResponseDTO action = actionFlow.action(actionRequestDTO);

        Assert.assertEquals("Not expected status", "PLAYING", action.getStatus().getStatus().name());
        Assert.assertEquals("Not expected board result count", true, action.getBoardResult().size() == 1);
        Assert.assertEquals("Not expected board result", new HashSet<>(Arrays.asList(createMinesweeperSquare(0,2,
                true, false, 1, false, false))), action.getBoardResult());
    }

    @Test
    public void testFalgFlagSquare() {
        ActionRequestDTO actionRequestDTO = new ActionRequestDTO();
        actionRequestDTO.setAction(ActionsEnum.FLAG);
        actionRequestDTO.setGameId("GID-TEST");
        actionRequestDTO.setRow(0);
        actionRequestDTO.setColumn(0);
        actionRequestDTO.setTimeMin(0);
        actionRequestDTO.setTimeSec(2);

        ActionResponseDTO action = actionFlow.action(actionRequestDTO);

        Assert.assertEquals("Not expected status", "PLAYING", action.getStatus().getStatus().name());
        Assert.assertEquals("Not expected board result count", true, action.getBoardResult().size() == 1);
        Assert.assertEquals("Not expected board result", new HashSet<>(Arrays.asList(
                createMinesweeperSquare(0,0, false, false, 0, false, false))), action.getBoardResult());
    }

    private Game createTestMockGame(){
        Game g = new Game();
        g.setGameName(GamesEnum.MINESWEEPER);
        g.setId("GID-TEST");
        g.setStatus(new GameStatus(GameStatusEnum.PLAYING, false));
        g.setTimeSec(0);
        g.setTimeMin(0);

        MinesweeperGameParams gameParams = new MinesweeperGameParams();
        gameParams.setRows(8);
        gameParams.setColumns(8);
        gameParams.setMines(10);
        g.setGameParams(gameParams);
        g.setUser("test");
        g.setBoard(getGameTestBoard());
        return g;
    }

    private Board getGameTestBoard() {
        Board board = new Board(8,8);

        board.updateSquare(0,0, createMinesweeperSquare(0,0, true, false, 0, false, false));
        board.updateSquare(0,1, createMinesweeperSquare(0,1, false, false, 1, true, false));
        board.updateSquare(0,2, createMinesweeperSquare(0,2, false, false, 1, false, false));
        board.updateSquare(0,3, createMinesweeperSquare(0,3, false, false, 3, false, false));
        board.updateSquare(0,4, createMinesweeperSquare(0,4, false, true, 0, false, false));
        board.updateSquare(0,5, createMinesweeperSquare(0,5, false, false, 2, false, false));
        board.updateSquare(0,6, createMinesweeperSquare(0,6, false, false, 0, false, false));
        board.updateSquare(0,7, createMinesweeperSquare(0,7, false, false, 0, false, false));

        board.updateSquare(1,0, createMinesweeperSquare(1,0, false, false, 1, false, false));
        board.updateSquare(1,1, createMinesweeperSquare(1,1, false, false, 3, false, false));
        board.updateSquare(1,2, createMinesweeperSquare(1,2, false, true, 0, false, false));
        board.updateSquare(1,3, createMinesweeperSquare(1,3, true, false, 5, false, false));
        board.updateSquare(1,4, createMinesweeperSquare(1,4, false, true, 0, false, false));
        board.updateSquare(1,5, createMinesweeperSquare(1,5, false, false, 3, false, false));
        board.updateSquare(1,6, createMinesweeperSquare(1,6, false, false, 0, false, false));
        board.updateSquare(1,7, createMinesweeperSquare(1,7, false, false, 0, false, false));

        board.updateSquare(2,0, createMinesweeperSquare(2,0, false, true, 0, false, false));
        board.updateSquare(2,1, createMinesweeperSquare(2,1, false, false, 3, false, false));
        board.updateSquare(2,2, createMinesweeperSquare(2,2, false, true, 0, false, false));
        board.updateSquare(2,3, createMinesweeperSquare(2,3, false, false, 4, false, false));
        board.updateSquare(2,4, createMinesweeperSquare(2,4, false, true, 0, false, false));
        board.updateSquare(2,5, createMinesweeperSquare(2,5, true, false, 2, false, false));
        board.updateSquare(2,6, createMinesweeperSquare(2,6, false, false, 0, false, false));
        board.updateSquare(2,7, createMinesweeperSquare(2,7, false, false, 0, false, false));

        board.updateSquare(3,0, createMinesweeperSquare(3,0, false, false, 1, false, false));
        board.updateSquare(3,1, createMinesweeperSquare(3,1, false, false, 3, false, false));
        board.updateSquare(3,2, createMinesweeperSquare(3,2, false, false, 2, false, false));
        board.updateSquare(3,3, createMinesweeperSquare(3,3, false, false, 4, false, false));
        board.updateSquare(3,4, createMinesweeperSquare(3,4, false, false, 2, false, false));
        board.updateSquare(3,5, createMinesweeperSquare(3,5, false, false, 2, false, false));
        board.updateSquare(3,6, createMinesweeperSquare(3,6, false, false, 0, false, false));
        board.updateSquare(3,7, createMinesweeperSquare(3,7, false, false, 0, false, false));

        board.updateSquare(4,0, createMinesweeperSquare(4,0, false, false, 0, false, false));
        board.updateSquare(4,1, createMinesweeperSquare(4,1, false, false, 1, false, false));
        board.updateSquare(4,2, createMinesweeperSquare(4,2, false, true, 0, false, false));
        board.updateSquare(4,3, createMinesweeperSquare(4,3, false, false, 2, false, false));
        board.updateSquare(4,4, createMinesweeperSquare(4,4, false, true, 0, false, false));
        board.updateSquare(4,5, createMinesweeperSquare(4,5, false, false, 2, false, false));
        board.updateSquare(4,6, createMinesweeperSquare(4,6, false, false, 1, false, false));
        board.updateSquare(4,7, createMinesweeperSquare(4,7, false, false, 0, false, false));

        board.updateSquare(5,0, createMinesweeperSquare(5,0, false, false, 0, false, false));
        board.updateSquare(5,1, createMinesweeperSquare(5,1, false, false, 1, false, false));
        board.updateSquare(5,2, createMinesweeperSquare(5,2, false, false, 2, false, false));
        board.updateSquare(5,3, createMinesweeperSquare(5,3, false, false, 3, false, false));
        board.updateSquare(5,4, createMinesweeperSquare(5,4, false, false, 3, false, false));
        board.updateSquare(5,5, createMinesweeperSquare(5,5, false, true, 0, false, false));
        board.updateSquare(5,6, createMinesweeperSquare(5,6, false, false, 1, false, false));
        board.updateSquare(5,7, createMinesweeperSquare(5,7, false, false, 0, false, false));

        board.updateSquare(6,0, createMinesweeperSquare(6,0, false, false, 0, false, false));
        board.updateSquare(6,1, createMinesweeperSquare(6,1, false, false, 0, false, false));
        board.updateSquare(6,2, createMinesweeperSquare(6,2, false, false, 1, false, false));
        board.updateSquare(6,3, createMinesweeperSquare(6,3, false, true, 0, false, false));
        board.updateSquare(6,4, createMinesweeperSquare(6,4, false, false, 2, false, false));
        board.updateSquare(6,5, createMinesweeperSquare(6,5, false, false, 1, false, false));
        board.updateSquare(6,6, createMinesweeperSquare(6,6, false, false, 1, false, false));
        board.updateSquare(6,7, createMinesweeperSquare(6,7, false, false, 0, false, false));

        board.updateSquare(7,0, createMinesweeperSquare(7,0, false, false, 0, false, false));
        board.updateSquare(7,1, createMinesweeperSquare(7,1, false, false, 0, false, false));
        board.updateSquare(7,2, createMinesweeperSquare(7,2, false, false, 1, false, false));
        board.updateSquare(7,3, createMinesweeperSquare(7,3, false, false, 1, false, false));
        board.updateSquare(7,4, createMinesweeperSquare(7,4, false, false, 1, false, false));
        board.updateSquare(7,5, createMinesweeperSquare(7,5, false, false, 0, false, false));
        board.updateSquare(7,6, createMinesweeperSquare(7,6, false, false, 0, false, false));
        board.updateSquare(7,7, createMinesweeperSquare(7,7, false, false, 0, false, false));

        return board;
    }

    private MinesweeperSquare createMinesweeperSquare(int row, int column, boolean flag, boolean mine, int adjacentMines, boolean turned, boolean question) {
        MinesweeperSquare minesweeperSquare = new MinesweeperSquare(row, column);
        minesweeperSquare.setFlag(flag);
        minesweeperSquare.setMine(mine);
        minesweeperSquare.setAdjacentMines(adjacentMines);
        minesweeperSquare.setTurned(turned);
        minesweeperSquare.setQuestion(question);
        return minesweeperSquare;
    }
}
