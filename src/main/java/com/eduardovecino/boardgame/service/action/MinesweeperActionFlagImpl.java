package com.eduardovecino.boardgame.service.action;

import com.eduardovecino.boardgame.annotation.ActionImpl;
import com.eduardovecino.boardgame.constants.ActionsEnum;
import com.eduardovecino.boardgame.dto.ActionResponseDTO;
import com.eduardovecino.boardgame.model.Board;
import com.eduardovecino.boardgame.model.Game;
import com.eduardovecino.boardgame.model.MinesweeperSquare;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ActionImpl(gameName = "minesweeper", action = ActionsEnum.FLAG)
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
