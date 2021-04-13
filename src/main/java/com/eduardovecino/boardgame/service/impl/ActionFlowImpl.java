package com.eduardovecino.boardgame.service.impl;

import com.eduardovecino.boardgame.constants.GameStatusEnum;
import com.eduardovecino.boardgame.dto.ActionRequestDTO;
import com.eduardovecino.boardgame.dto.ActionResponseDTO;
import com.eduardovecino.boardgame.exceptions.InvalidActionException;
import com.eduardovecino.boardgame.exceptions.NotFoundActionException;
import com.eduardovecino.boardgame.exceptions.NotFoundGameException;
import com.eduardovecino.boardgame.model.Game;
import com.eduardovecino.boardgame.service.ActionFlow;
import com.eduardovecino.boardgame.service.GameFlow;
import com.eduardovecino.boardgame.service.action.ActionFactory;
import com.eduardovecino.boardgame.service.action.ActionService;
import com.eduardovecino.boardgame.service.game.GameFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionFlowImpl implements ActionFlow {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionFlowImpl.class);

    private final ActionFactory actionFactory;
    private final GameFlow gameFlow;
    private final GameFactory gameFactory;

    @Autowired
    public ActionFlowImpl(ActionFactory actionFactory, GameFlow gameFlow, GameFactory gameFactory) {
        this.actionFactory = actionFactory;
        this.gameFlow = gameFlow;
        this.gameFactory = gameFactory;
    }

    @Override
    public ActionResponseDTO action(ActionRequestDTO actionRequestDTO) {
        Game gameById = this.gameFlow.getGameById(actionRequestDTO.getGameId());
        if (gameById == null) {
            LOGGER.warn("Not found game with id: {}", actionRequestDTO.getGameId());
            throw new NotFoundGameException("Not found game with id: " + actionRequestDTO.getGameId());
        }

        gameById.setTimeMin(actionRequestDTO.getTimeMin());
        gameById.setTimeSec(actionRequestDTO.getTimeSec());

        ActionService action = this.actionFactory.getInstance(gameById.getGameName(), actionRequestDTO.getAction());
        if (action == null) {
            LOGGER.warn("Not found action {} for game {}", actionRequestDTO.getAction(), gameById.getId());
            throw new NotFoundActionException(String.format("Not found action %s for game %s", actionRequestDTO.getAction(), gameById.getId()));
        }

        this.validateAction(gameById, action, actionRequestDTO);

        ActionResponseDTO actionResponseDTO = action.executeAction(gameById, actionRequestDTO.getRow(), actionRequestDTO.getColumn());
        actionResponseDTO.setGameId(gameById.getId());
        actionResponseDTO.setStatus(this.gameFactory.getInstance(gameById.getGameName()).calculateStatus(gameById));

        return actionResponseDTO;
    }

    private void validateAction(Game gameById, ActionService actionService, ActionRequestDTO actionRequestDTO) {
        if (!GameStatusEnum.PLAYING.equals(gameById.getStatus())) {
            LOGGER.warn("Invalid action for game {} Game is finished in status {}", gameById.getId(), gameById.getStatus());
            throw new InvalidActionException(String.format("Invalid action for game %s Game is finished in status %s", gameById.getId(), gameById.getStatus().name()));
        }

        if (actionRequestDTO.getRow() < 0 || actionRequestDTO.getColumn() < 0 || actionRequestDTO.getRow() >= gameById.getGameParams().getRows() ||
                actionRequestDTO.getColumn() >= gameById.getGameParams().getColumns() || !actionService.validAction(gameById, actionRequestDTO.getRow(), actionRequestDTO.getColumn())) {
            LOGGER.warn("Invalid action {} for game {} at row {} column {}", actionRequestDTO.getAction(), gameById.getId(), actionRequestDTO.getRow(), actionRequestDTO.getColumn());
            throw new InvalidActionException(String.format("Invalid action %s for game %s at row %s column %s", actionRequestDTO.getAction(), gameById.getId(), actionRequestDTO.getRow(), actionRequestDTO.getColumn()));
        }
    }
}
