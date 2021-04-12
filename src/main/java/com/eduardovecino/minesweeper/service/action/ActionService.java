package com.eduardovecino.minesweeper.service.action;

import com.eduardovecino.minesweeper.dto.ActionResponseDTO;
import com.eduardovecino.minesweeper.model.Game;

public interface ActionService {

    boolean validAction(Game game, Integer row, Integer column);
    ActionResponseDTO executeAction(Game game, Integer row, Integer column);

}
