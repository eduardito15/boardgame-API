package com.eduardovecino.boardgame.service.game;

import com.eduardovecino.boardgame.constants.GameStatusEnum;
import com.eduardovecino.boardgame.dto.CreateGameRequestDTO;
import com.eduardovecino.boardgame.model.Game;

public interface GameService {

    Game createNewGame(CreateGameRequestDTO createGameRequestDTO);

    GameStatusEnum calculateStatus(Game game);
}
