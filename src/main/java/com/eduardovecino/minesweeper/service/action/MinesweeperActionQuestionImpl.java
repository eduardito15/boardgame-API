package com.eduardovecino.minesweeper.service.action;

import com.eduardovecino.minesweeper.annotation.ActionImpl;
import com.eduardovecino.minesweeper.constants.ActionsEnum;
import com.eduardovecino.minesweeper.dto.ActionResponseDTO;
import com.eduardovecino.minesweeper.model.Board;
import com.eduardovecino.minesweeper.model.Game;
import com.eduardovecino.minesweeper.model.MinesweeperSquare;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ActionImpl(gameName = "minesweeper", action = ActionsEnum.QUESTION)
public class MinesweeperActionQuestionImpl implements ActionService {

    @Override
    public boolean validAction(Game game, Integer row, Integer column) {
        Board gameBoard = game.getBoard();
        MinesweeperSquare square = (MinesweeperSquare) gameBoard.getSquare(row, column);
        return !(square.isTurned() || square.isFlag());
    }

    @Override
    public ActionResponseDTO executeAction(Game game, Integer row, Integer column) {
        Board gameBoard = game.getBoard();
        MinesweeperSquare square = (MinesweeperSquare) gameBoard.getSquare(row, column);
        square.setQuestion(!square.isQuestion());

        ActionResponseDTO actionResponseDTO = new ActionResponseDTO();
        actionResponseDTO.setAffectedSquares(Set.of(square));
        return actionResponseDTO;
    }
}
