package com.boardgame.service.game;

import com.boardgame.dto.CreateGameRequestDTO;
import com.boardgame.model.Board;
import com.boardgame.model.Game;
import com.boardgame.model.GameStatus;

public interface GameService {

    /**
     * Create new game
     * @param createGameRequestDTO
     * @return
     */
    Game createNewGame(CreateGameRequestDTO createGameRequestDTO);

    /**
     * Validate if create game request is valid
     * @param createGameRequestDTO
     * @return
     */
    default boolean validCreateGameRequest(CreateGameRequestDTO createGameRequestDTO) {
        return true;
    }

    /**
     * Fill empty board, to new game
     * @param board
     * @return
     */
    Board fillEmptyBoard(Board board);

    /**
     * Calculate game status. Before action, we must calculate game status
     * @param game
     * @return
     */
    GameStatus calculateStatus(Game game);

    /**
     * Prepare de board when game is reload.
     * @param game
     * @return
     */
    Board prepareBoardToReloadGame(Game game);
}
