package com.boardgame.service.action;

import com.boardgame.annotation.ActionImpl;
import com.boardgame.constants.ActionsEnum;
import com.boardgame.constants.GamesEnum;
import com.boardgame.dto.ActionResponseDTO;
import com.boardgame.model.Board;
import com.boardgame.model.Game;
import com.boardgame.model.MinesweeperSquare;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ActionImpl(gameName = GamesEnum.MINESWEEPER, action = ActionsEnum.FLAG)
public class MinesweeperActionFlagImpl implements ActionService {

    @Override
    public boolean validAction(Game game, Integer row, Integer column) {
        Board gameBoard = game.getBoard();
        MinesweeperSquare square = (MinesweeperSquare) gameBoard.getSquare(row, column);
        return !square.isTurned();
    }

    @Override
    public ActionResponseDTO executeAction(Game game, Integer row, Integer column) {
        Board gameBoard = game.getBoard();
        MinesweeperSquare square = (MinesweeperSquare) gameBoard.getSquare(row, column);
        square.setFlag(!square.isFlag());

        ActionResponseDTO actionResponseDTO = new ActionResponseDTO();
        actionResponseDTO.setBoardResult(Set.of(square));
        return actionResponseDTO;
    }

}
