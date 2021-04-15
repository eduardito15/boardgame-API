package com.eduardovecino.boardgame.service;

import com.eduardovecino.boardgame.dto.ActionRequestDTO;
import com.eduardovecino.boardgame.dto.ActionResponseDTO;

public interface ActionFlow {

    /**
     * Perform an action in the board square
     * @param actionRequestDTO
     * @return
     */
    ActionResponseDTO action(ActionRequestDTO actionRequestDTO);

}
