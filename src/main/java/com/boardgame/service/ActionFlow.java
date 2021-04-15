package com.boardgame.service;

import com.boardgame.dto.ActionRequestDTO;
import com.boardgame.dto.ActionResponseDTO;

public interface ActionFlow {

    /**
     * Perform an action in the board square
     * @param actionRequestDTO
     * @return
     */
    ActionResponseDTO action(ActionRequestDTO actionRequestDTO);

}
