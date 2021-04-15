package com.eduardovecino.boardgame.service.impl;

import com.eduardovecino.boardgame.constants.ExceptionsMessages;
import com.eduardovecino.boardgame.constants.LogsMessages;
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
            LOGGER.warn(LogsMessages.NOT_FOUND_GAME_WITH_ID, actionRequestDTO.getGameId());
            throw new NotFoundGameException(ExceptionsMessages.NOT_FOUND_GAME_WITH_ID + actionRequestDTO.getGameId());
        }

        gameById.setTimeMin(actionRequestDTO.getTimeMin());
        gameById.setTimeSec(actionRequestDTO.getTimeSec());

        ActionService action = this.actionFactory.getInstance(gameById.getGameName(), actionRequestDTO.getAction());
        if (action == null) {
            LOGGER.warn(LogsMessages.NOT_FOUND_ACTION_FOR_GAME, actionRequestDTO.getAction(), gameById.getId());
            throw new NotFoundActionException(String.format(ExceptionsMessages.NOT_FOUND_ACTION_S_FOR_GAME, actionRequestDTO.getAction(), gameById.getId()));
        }

        this.validateAction(gameById, action, actionRequestDTO);

        ActionResponseDTO actionResponseDTO = action.executeAction(gameById, actionRequestDTO.getRow(), actionRequestDTO.getColumn());
        gameById.setStatus(this.gameFactory.getInstance(gameById.getGameName()).calculateStatus(gameById));
        actionResponseDTO.setGameId(gameById.getId());
        actionResponseDTO.setStatus(gameById.getStatus());
        gameFlow.saveGame(gameById);

        return actionResponseDTO;
    }

    private void validateAction(Game gameById, ActionService actionService, ActionRequestDTO actionRequestDTO) {
        if (gameById.getStatus().isEnded()) {
            LOGGER.warn(LogsMessages.INVALID_ACTION_FOR_GAME_GAME_IS_FINISHED_IN_STATUS, gameById.getId(), gameById.getStatus());
            throw new InvalidActionException(String.format(ExceptionsMessages.INVALID_ACTION_FOR_GAME_S_GAME_IS_FINISHED_IN_STATUS, gameById.getId(), gameById.getStatus().getStatus().name()));
        }

        if (actionRequestDTO.getRow() < 0 || actionRequestDTO.getColumn() < 0 || actionRequestDTO.getRow() >= gameById.getGameParams().getRows() ||
                actionRequestDTO.getColumn() >= gameById.getGameParams().getColumns() || !actionService.validAction(gameById, actionRequestDTO.getRow(), actionRequestDTO.getColumn())) {
            LOGGER.warn(LogsMessages.INVALID_ACTION_FOR_GAME_AT_ROW_COLUMN, actionRequestDTO.getAction(), gameById.getId(), actionRequestDTO.getRow(), actionRequestDTO.getColumn());
            throw new InvalidActionException(String.format(ExceptionsMessages.INVALID_ACTION_S_FOR_GAME_S_AT_ROW_S_COLUMN, actionRequestDTO.getAction(), gameById.getId(), actionRequestDTO.getRow(), actionRequestDTO.getColumn()));
        }
    }
}
