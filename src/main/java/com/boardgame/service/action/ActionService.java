package com.boardgame.service.action;

import com.boardgame.dto.ActionResponseDTO;
import com.boardgame.model.Game;

public interface ActionService {

    /**
     * Validate if requested action is valid
     * @param game
     * @param row
     * @param column
     * @return
     */
    boolean validAction(Game game, Integer row, Integer column);

    /**
     * Perform an action in the board square
     * @param game
     * @param row
     * @param column
     * @return
     */
    ActionResponseDTO executeAction(Game game, Integer row, Integer column);

}
