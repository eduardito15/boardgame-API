package com.eduardovecino.minesweeper.service.game;

import com.eduardovecino.minesweeper.constants.GameStatusEnum;
import com.eduardovecino.minesweeper.dto.CreateGameRequestDTO;
import com.eduardovecino.minesweeper.model.Game;

public interface GameService {

    Game createNewGame(CreateGameRequestDTO createGameRequestDTO);

    GameStatusEnum calculateStatus(Game game);
}
