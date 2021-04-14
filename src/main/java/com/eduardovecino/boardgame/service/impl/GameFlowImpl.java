package com.eduardovecino.boardgame.service.impl;

import com.eduardovecino.boardgame.constants.GamesEnum;
import com.eduardovecino.boardgame.dto.CreateGameRequestDTO;
import com.eduardovecino.boardgame.dto.GameResponseDTO;
import com.eduardovecino.boardgame.dto.GamesByUserRequestDTO;
import com.eduardovecino.boardgame.dto.GamesByUserResponseDTO;
import com.eduardovecino.boardgame.exceptions.InvalidActionException;
import com.eduardovecino.boardgame.exceptions.NotFoundGameException;
import com.eduardovecino.boardgame.model.Board;
import com.eduardovecino.boardgame.model.Game;
import com.eduardovecino.boardgame.repository.GameRepository;
import com.eduardovecino.boardgame.service.GameFlow;
import com.eduardovecino.boardgame.service.game.GameFactory;
import com.eduardovecino.boardgame.service.game.GameService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
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
            LOGGER.warn("Game {} not found implementation", createGameRequestDTO.getGameName());
            throw new NotFoundGameException("Game not found");
        }

        this.validateCreateGameRequest(gameService, createGameRequestDTO);

        Game newGame = gameService.createNewGame(createGameRequestDTO);
        newGame.setId(UUID.randomUUID().toString());
        newGame.setGameName(GamesEnum.valueOf(createGameRequestDTO.getGameName().toUpperCase()));
        newGame.setUser(createGameRequestDTO.getUser());
        gameRepository.save(newGame);
        return getGameResponseFromGame(gameService, newGame, true);
    }

    @Override
    public Game getGameById(String gameId) {
        return gameRepository.findById(gameId).orElseThrow(() -> new NotFoundGameException(String.format("Not found game with id: %s", gameId)));
    }

    @Override
    public Board getGameBoardByGameId(String gameId) {
        return this.getGameById(gameId).getBoard();
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
            LOGGER.warn("Not found game with id: {}", gameId);
            throw new NotFoundGameException("Not found game with id: " + gameId);
        }

        GameService gameService = this.gameFactory.getInstance(gameById.getGameName());
        gameById.setBoard(gameService.prepareBoardToReloadGame(gameById));

        return this.getGameResponseFromGame(gameService, gameById, false);
    }

    @Override
    public GamesByUserResponseDTO getUserGamesIds(GamesByUserRequestDTO gamesByUserRequestDTO) {
        if (StringUtils.isBlank(gamesByUserRequestDTO.getUserName())) {
            if (StringUtils.isBlank(gamesByUserRequestDTO.getUserName())) {
                throw new InvalidActionException("User name is mandatory to find user games");
            }
        }

        List<Game> byUserName = gameRepository.findByUserName(gamesByUserRequestDTO.getUserName());
        return new GamesByUserResponseDTO(byUserName.stream().map(g -> g.getId()).collect(Collectors.toList()));
    }

    private void validateCreateGameRequest(GameService gameService, CreateGameRequestDTO createGameRequestDTO) {
        if (createGameRequestDTO.getRows() <= 0 || createGameRequestDTO.getColumns() <=0) {
            throw new InvalidActionException("Rows and columns must be grater than 0");
        }

        if (StringUtils.isBlank(createGameRequestDTO.getUser())) {
            throw new InvalidActionException("User name is mandatory");
        }

        if (!gameService.validCreateGameRequest(createGameRequestDTO)) {
            throw new InvalidActionException("Invalid create game request");
        }
    }


}
