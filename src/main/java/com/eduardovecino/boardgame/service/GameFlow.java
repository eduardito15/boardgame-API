package com.eduardovecino.boardgame.service;

import com.eduardovecino.boardgame.dto.CreateGameRequestDTO;
import com.eduardovecino.boardgame.dto.CreateGameResponseDTO;
import com.eduardovecino.boardgame.model.Board;
import com.eduardovecino.boardgame.model.Game;

public interface GameFlow {

    CreateGameResponseDTO createNewGame(CreateGameRequestDTO createGameRequestDTO);

    Game getGameById(String gameId);

    Board getGameBoardByGameId(String gameId);

}
