package com.eduardovecino.minesweeper.service;

import com.eduardovecino.minesweeper.dto.ActionRequestDTO;
import com.eduardovecino.minesweeper.dto.ActionResponseDTO;

public interface ActionFlow {

    ActionResponseDTO action(ActionRequestDTO actionRequestDTO);

}
