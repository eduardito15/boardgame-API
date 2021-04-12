package com.eduardovecino.minesweeper.service;

import com.eduardovecino.minesweeper.dto.CreateGameRequestDTO;
import com.eduardovecino.minesweeper.dto.CreateGameResponseDTO;
import com.eduardovecino.minesweeper.model.Board;
import com.eduardovecino.minesweeper.model.Game;

public interface GameFlow {

    CreateGameResponseDTO createNewGame(CreateGameRequestDTO createGameRequestDTO);

    Game getGameById(String gameId);

    Board getGameBoardByGameId(String gameId);

}
