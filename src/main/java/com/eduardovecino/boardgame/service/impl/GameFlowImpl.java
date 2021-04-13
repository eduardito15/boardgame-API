package com.eduardovecino.boardgame.service.impl;

import com.eduardovecino.boardgame.constants.GameStatusEnum;
import com.eduardovecino.boardgame.dto.CreateGameRequestDTO;
import com.eduardovecino.boardgame.dto.CreateGameResponseDTO;
import com.eduardovecino.boardgame.exceptions.NotFoundGameException;
import com.eduardovecino.boardgame.model.Board;
import com.eduardovecino.boardgame.model.Game;
import com.eduardovecino.boardgame.model.Square;
import com.eduardovecino.boardgame.service.GameFlow;
import com.eduardovecino.boardgame.service.game.GameFactory;
import com.eduardovecino.boardgame.service.game.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

@Service
public class GameFlowImpl implements GameFlow {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameFlowImpl.class);

    private final GameFactory gameFactory;
    private Map<String, Game> games;

    @Autowired
    public GameFlowImpl(GameFactory gameFactory) {
        this.gameFactory = gameFactory;
        games = new ConcurrentHashMap<>();
    }

    @Override
    public CreateGameResponseDTO createNewGame(CreateGameRequestDTO createGameRequestDTO) {
        GameService gameService = this.gameFactory.getInstance(createGameRequestDTO.getGameName());

        if (gameService == null) {
            LOGGER.warn("Game {} not found implementation", createGameRequestDTO.getGameName());
            throw new NotFoundGameException("Game not found");
        }

        Game newGame = gameService.createNewGame(createGameRequestDTO);
        newGame.setId(UUID.randomUUID().toString());
        newGame.setStatus(GameStatusEnum.PLAYING);
        newGame.setGameName(createGameRequestDTO.getGameName());
        newGame.setUser(createGameRequestDTO.getUser());
        games.putIfAbsent(newGame.getId(), newGame);
        return getCreateGameResponseFromGame(newGame);
    }

    @Override
    public Game getGameById(String gameId) {
        return games.get(gameId);
    }

    @Override
    public Board getGameBoardByGameId(String gameId) {
        return games.get(gameId).getBoard();
    }

    private CreateGameResponseDTO getCreateGameResponseFromGame(Game game) {
        CreateGameResponseDTO responseDTO = new CreateGameResponseDTO();

        responseDTO.setGameName(game.getGameName());
        responseDTO.setGameId(game.getId());
        responseDTO.setRows(game.getGameParams().getRows());
        responseDTO.setColumns(game.getGameParams().getColumns());
        responseDTO.setUser(game.getUser());
        responseDTO.setBoard(fillEmptyBoard(new Board(game.getGameParams().getRows(), game.getGameParams().getColumns()), game.getGameParams().getRows(), game.getGameParams().getColumns()));
        responseDTO.setStatus(game.getStatus());
        responseDTO.setUser(game.getUser());

        return responseDTO;
    }

    private Board fillEmptyBoard(Board board, Integer rows, Integer columns) {

        IntStream.range(0, rows).forEach(row ->
                IntStream.range(0, columns).forEach(column -> {
                    Square square = new Square();
                    square.setRow(row);
                    square.setColumn(column);
                    board.updateSquare(row, column, square);
                })
        );

        return board;
    }
}
