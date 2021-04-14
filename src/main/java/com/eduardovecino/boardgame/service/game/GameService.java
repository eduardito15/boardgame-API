package com.eduardovecino.boardgame.service.game;

import com.eduardovecino.boardgame.dto.CreateGameRequestDTO;
import com.eduardovecino.boardgame.model.Board;
import com.eduardovecino.boardgame.model.Game;
import com.eduardovecino.boardgame.model.GameStatus;

public interface GameService {

    Game createNewGame(CreateGameRequestDTO createGameRequestDTO);

    default boolean validCreateGameRequest(CreateGameRequestDTO createGameRequestDTO) {
        return true;
    }

    Board fillEmptyBoard(Board board);

    GameStatus calculateStatus(Game game);

    Board prepareBoardToReloadGame(Game game);
}
