package com.eduardovecino.boardgame.service;

import com.eduardovecino.boardgame.dto.CreateGameRequestDTO;
import com.eduardovecino.boardgame.dto.GameResponseDTO;
import com.eduardovecino.boardgame.dto.GamesByUserRequestDTO;
import com.eduardovecino.boardgame.dto.GamesByUserResponseDTO;
import com.eduardovecino.boardgame.model.Game;

public interface GameFlow {

    /**
     * Create and save new game
     * @param createGameRequestDTO
     * @return
     */
    GameResponseDTO createNewGame(CreateGameRequestDTO createGameRequestDTO);

    /**
     * Obtain game by id
     * @param gameId
     * @return
     */
    Game getGameById(String gameId);

    /**
     * Save game on db
     * @param game
     */
    void saveGame(Game game);

    /**
     * Load game by id. Delegate to the implementation prepare the board when game is loading
     * @param gameId
     * @return
     */
    GameResponseDTO loadGame(String gameId);

    /**
     * Return games ids by user
     * @param gamesByUserRequestDTO
     * @return
     */
    GamesByUserResponseDTO getUserGamesIds(GamesByUserRequestDTO gamesByUserRequestDTO);
}
