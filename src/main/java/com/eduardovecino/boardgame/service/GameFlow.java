package com.eduardovecino.boardgame.service;

import com.eduardovecino.boardgame.dto.CreateGameRequestDTO;
import com.eduardovecino.boardgame.dto.GameResponseDTO;
import com.eduardovecino.boardgame.dto.GamesByUserRequestDTO;
import com.eduardovecino.boardgame.dto.GamesByUserResponseDTO;
import com.eduardovecino.boardgame.model.Board;
import com.eduardovecino.boardgame.model.Game;

import java.util.List;

public interface GameFlow {



    GameResponseDTO createNewGame(CreateGameRequestDTO createGameRequestDTO);

    Game getGameById(String gameId);

    Board getGameBoardByGameId(String gameId);

    void saveGame(Game game);

    GameResponseDTO loadGame(String gameId);

    GamesByUserResponseDTO getUserGamesIds(GamesByUserRequestDTO gamesByUserRequestDTO);
}
