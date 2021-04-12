package com.eduardovecino.minesweeper.service.action;

import com.eduardovecino.minesweeper.annotation.ActionImpl;
import com.eduardovecino.minesweeper.constants.ActionsEnum;
import com.eduardovecino.minesweeper.dto.ActionResponseDTO;
import com.eduardovecino.minesweeper.model.Board;
import com.eduardovecino.minesweeper.model.Game;
import com.eduardovecino.minesweeper.model.MinesweeperSquare;
import com.eduardovecino.minesweeper.model.Square;
import com.eduardovecino.minesweeper.service.game.MinesweeperGameImpl;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@ActionImpl(gameName = "minesweeper", action = ActionsEnum.TURN)
public class MinesweeperActionTurnImpl implements ActionService {

    private final MinesweeperGameImpl minesweeperGame;

    @Autowired
    public MinesweeperActionTurnImpl(MinesweeperGameImpl minesweeperGame) {
        this.minesweeperGame = minesweeperGame;
    }

    @Override
    public boolean validAction(Game game, Integer row, Integer column) {
        Board gameBoard = game.getBoard();
        MinesweeperSquare square = (MinesweeperSquare) gameBoard.getSquare(row, column);
        return !square.isFlag();
    }

    @Override
    public ActionResponseDTO executeAction(Game game, Integer row, Integer column) {
        Board gameBoard = game.getBoard();
        MinesweeperSquare square = (MinesweeperSquare) gameBoard.getSquare(row, column);
        if (square.isMine()) {
            square.setTurned(true);
            ActionResponseDTO response = new ActionResponseDTO();
            response.setAffectedSquares(this.minesweeperGame.getMinesSquares(gameBoard));
            return response;
        }

        Set<Square> turnedSquares = new HashSet<>();
        turnedSquares = this.turnSquare(turnedSquares, game, square);

        ActionResponseDTO responseDTO = new ActionResponseDTO();
        responseDTO.setAffectedSquares(turnedSquares);

        return responseDTO;

    }

    public Set<Square> turnSquare(Set<Square> result, Game game, MinesweeperSquare square) {

        if (!square.isMine() && !square.isFlag()) {
            if (square.getAdjacentMines() > 0) {
                square.setTurned(true);
                result.add(square);
                return result;
            }

            List<Pair<Integer, Integer>> adjacentSquares = this.minesweeperGame.getAdjacentSquares(game.getGameParams(), square.getRow(), square.getColumn());
            adjacentSquares.forEach(as -> {
                MinesweeperSquare adjacentSquare = (MinesweeperSquare) game.getBoard().getSquare(as.getLeft(), as.getRight());
                if (!adjacentSquare.isTurned() && !adjacentSquare.isFlag()) {
                    adjacentSquare.setTurned(true);
                    result.add(adjacentSquare);
                    if (adjacentSquare.getAdjacentMines() == 0) {
                        result.addAll(turnSquare(result, game, adjacentSquare));
                    }
                }
            });
        }

        return result;
    }
}
