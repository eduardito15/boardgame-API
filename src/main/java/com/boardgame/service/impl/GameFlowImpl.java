package com.boardgame.service.impl;

import com.boardgame.constants.GamesEnum;
import com.boardgame.dto.GamesByUserResponseDTO;
import com.boardgame.exceptions.InvalidActionException;
import com.boardgame.exceptions.NotFoundGameException;
import com.boardgame.service.game.GameFactory;
import com.boardgame.service.game.GameService;
import com.boardgame.constants.ExceptionsMessages;
import com.boardgame.constants.LogsMessages;
import com.boardgame.dto.CreateGameRequestDTO;
import com.boardgame.dto.GameResponseDTO;
import com.boardgame.dto.GamesByUserRequestDTO;
import com.boardgame.model.Board;
import com.boardgame.model.Game;
import com.boardgame.repository.GameRepository;
import com.boardgame.service.GameFlow;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameFlowImpl implements GameFlow {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameFlowImpl.class);

    private final GameFactory gameFactory;
    private final GameRepository gameRepository;

    @Autowired
    public GameFlowImpl(GameFactory gameFactory, GameRepository gameRepository) {
        this.gameFactory = gameFactory;
        this.gameRepository = gameRepository;
    }

    @Override
    public GameResponseDTO createNewGame(CreateGameRequestDTO createGameRequestDTO) {
        GameService gameService = this.gameFactory.getInstance(GamesEnum.valueOf(createGameRequestDTO.getGameName().toUpperCase()));

        if (gameService == null) {
            LOGGER.warn(LogsMessages.NOT_FOUND_IMPLEMENTATION_FOR_GAME, createGameRequestDTO.getGameName());
            throw new NotFoundGameException(ExceptionsMessages.GAME_NOT_FOUND);
        }

        this.validateCreateGameRequest(gameService, createGameRequestDTO);

        Game newGame = gameService.createNewGame(createGameRequestDTO);
        newGame.setGameName(GamesEnum.valueOf(createGameRequestDTO.getGameName().toUpperCase()));
        newGame.setUser(createGameRequestDTO.getUser());
        newGame = gameRepository.save(newGame);
        return getGameResponseFromGame(gameService, newGame, true);
    }

    @Override
    public Game getGameById(String gameId) {
        return gameRepository.findById(gameId).orElseThrow(() -> new NotFoundGameException(String.format(ExceptionsMessages.NOT_FOUND_GAME_WITH_ID_S, gameId)));
    }

    private GameResponseDTO getGameResponseFromGame(GameService gameService,Game game, boolean emptyBoard) {
        GameResponseDTO responseDTO = new GameResponseDTO();

        responseDTO.setGameName(game.getGameName().getName());
        responseDTO.setGameId(game.getId());
        responseDTO.setRows(game.getGameParams().getRows());
        responseDTO.setColumns(game.getGameParams().getColumns());
        responseDTO.setUser(game.getUser());
        if (emptyBoard) {
            responseDTO.setBoard(gameService.fillEmptyBoard(new Board(game.getGameParams().getRows(), game.getGameParams().getColumns())));
        } else {
            responseDTO.setBoard(game.getBoard());
        }
        responseDTO.setStatus(game.getStatus());
        responseDTO.setUser(game.getUser());
        responseDTO.setTimeMin(game.getTimeMin());
        responseDTO.setTimeSec(game.getTimeSec());

        return responseDTO;
    }

    @Override
    public void saveGame(Game game) {

        if (game.getStatus().isEnded()) {
            //Delete game
            this.gameRepository.deleteById(game.getId());
        } else {
            this.gameRepository.save(game);
        }
    }

    @Override
    public GameResponseDTO loadGame(String gameId) {
        Game gameById = this.getGameById(gameId);
        if (gameById == null) {
            LOGGER.warn(LogsMessages.NOT_FOUND_GAME_WITH_ID, gameId);
            throw new NotFoundGameException(ExceptionsMessages.NOT_FOUND_GAME_WITH_ID + gameId);
        }

        GameService gameService = this.gameFactory.getInstance(gameById.getGameName());
        gameById.setBoard(gameService.prepareBoardToReloadGame(gameById));

        return this.getGameResponseFromGame(gameService, gameById, false);
    }

    @Override
    public GamesByUserResponseDTO getUserGamesIds(GamesByUserRequestDTO gamesByUserRequestDTO) {
        if (StringUtils.isBlank(gamesByUserRequestDTO.getUserName())) {
            throw new InvalidActionException(ExceptionsMessages.USER_NAME_IS_MANDATORY_TO_FIND_USER_GAMES);
        }

        List<Game> byUserName = gameRepository.findByUserName(gamesByUserRequestDTO.getUserName());
        return new GamesByUserResponseDTO(byUserName.stream().map(Game::getId).collect(Collectors.toList()));
    }

    private void validateCreateGameRequest(GameService gameService, CreateGameRequestDTO createGameRequestDTO) {
        if (createGameRequestDTO.getRows() <= 0 || createGameRequestDTO.getColumns() <=0) {
            throw new InvalidActionException(ExceptionsMessages.ROWS_AND_COLUMNS_MUST_BE_GRATER_THAN_0);
        }

        if (StringUtils.isBlank(createGameRequestDTO.getUser())) {
            throw new InvalidActionException(ExceptionsMessages.USER_NAME_IS_MANDATORY);
        }

        if (!gameService.validCreateGameRequest(createGameRequestDTO)) {
            throw new InvalidActionException(ExceptionsMessages.INVALID_CREATE_GAME_REQUEST);
        }
    }


}
