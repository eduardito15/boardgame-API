package com.eduardovecino.boardgame.service.action;

import com.eduardovecino.boardgame.dto.ActionResponseDTO;
import com.eduardovecino.boardgame.model.Game;

public interface ActionService {

    boolean validAction(Game game, Integer row, Integer column);
    ActionResponseDTO executeAction(Game game, Integer row, Integer column);

}
