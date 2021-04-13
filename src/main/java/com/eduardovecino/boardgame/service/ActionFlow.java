package com.eduardovecino.boardgame.service;

import com.eduardovecino.boardgame.dto.ActionRequestDTO;
import com.eduardovecino.boardgame.dto.ActionResponseDTO;

public interface ActionFlow {

    ActionResponseDTO action(ActionRequestDTO actionRequestDTO);

}
