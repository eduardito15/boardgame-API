package com.eduardovecino.boardgame.service.game;

import com.eduardovecino.boardgame.annotation.GameImpl;
import com.eduardovecino.boardgame.constants.GameStatusEnum;
import com.eduardovecino.boardgame.dto.CreateGameRequestDTO;
import com.eduardovecino.boardgame.dto.CreateMinesweeperGameRequestDTO;
import com.eduardovecino.boardgame.model.*;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@GameImpl(gameName = "minesweeper")
public class MinesweeperGameImpl implements GameService {

    private Random random;

    @Autowired
    public MinesweeperGameImpl() {
        random = new Random();
    }

    @Override
    public Game createNewGame(CreateGameRequestDTO createGameRequestDTO) {
        Game game = new Game();

        MinesweeperGameParams gameParameters = new MinesweeperGameParams();
        gameParameters.setRows(createGameRequestDTO.getRows());
        gameParameters.setColumns(createGameRequestDTO.getColumns());
        gameParameters.setMines(((CreateMinesweeperGameRequestDTO) createGameRequestDTO).getMines());
        game.setGameParams(gameParameters);

        game.setBoard(new Board(createGameRequestDTO.getRows(), createGameRequestDTO.getColumns()));
        fillBoard(game);

        return game;
    }

    @Override
    public GameStatusEnum calculateStatus(Game game) {
        Set<Square> minesSquares = this.getMinesSquares(game.getBoard());

        if (minesSquares.stream().anyMatch(s -> ((MinesweeperSquare) s).isTurned())) {
            return GameStatusEnum.LOSE;
        }

        List<Square[]> listSquares = Arrays.asList(game.getBoard().getSquares());
        Set<Square> squares = listSquares.stream().flatMap(Arrays::stream).collect(Collectors.toSet());
        squares.removeAll(minesSquares);

        if (squares.stream().allMatch(s -> ((MinesweeperSquare) s).isTurned())) {
            return GameStatusEnum.WIN;
        }

        return GameStatusEnum.PLAYING;
    }

    private void fillBoard(Game game) {

        Set<Integer> minesPosition = randomMines(random, ((MinesweeperGameParams) game.getGameParams()).getMines(),
                game.getGameParams().getColumns() * game.getGameParams().getRows());

        IntStream.range(0, game.getGameParams().getRows()).forEach(row ->
                IntStream.range(0, game.getGameParams().getColumns()).forEach(column -> {
                    MinesweeperSquare square = new MinesweeperSquare();
                    square.setRow(row);
                    square.setColumn(column);
                    if (minesPosition.contains(game.getGameParams().getColumns() * row + column)) {
                        square.setMine(true);
                    } else {
                        getAdjacentSquares(game.getGameParams(), row, column).stream().forEach(p -> {
                            if (minesPosition.contains(game.getGameParams().getColumns() * p.getLeft() + p.getRight())) {
                                square.setAdjacentMines(square.getAdjacentMines() + 1);
                            }
                        });
                    }
                    game.getBoard().updateSquare(row, column, square);
                })
        );
    }

    public List<Pair<Integer, Integer>> getAdjacentSquares(GameParams gameParameters, Integer row, Integer column) {
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = column - 1; c <= column + 1; c++) {
                if (r >= 0 && c >= 0 && r < gameParameters.getRows() && c < gameParameters.getColumns()) {
                    result.add(Pair.of(r, c));
                }
            }
        }
        return result;
    }

    private Set<Integer> randomMines(Random random, Integer mines, Integer maxRandom) {
        Set<Integer> result = new HashSet<>();
        while (result.size() < mines) {
            result.add(random.nextInt(maxRandom));
        }
        return result;
    }

    public Set<Square> getMinesSquares(Board board) {
        List<Square[]> listSquares = Arrays.asList(board.getSquares());
        List<Square> squares = listSquares.stream().flatMap(Arrays::stream).collect(Collectors.toList());
        return squares.stream().filter(s -> ((MinesweeperSquare) s).isMine()).collect(Collectors.toSet());
    }

}
