package com.eduardovecino.boardgame.dto;

import com.eduardovecino.boardgame.constants.GameStatusEnum;
import com.eduardovecino.boardgame.model.Board;

public class CreateGameResponseDTO extends CreateGameRequestDTO {

    private String gameId;
    private Board board;
    private GameStatusEnum status;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public GameStatusEnum getStatus() {
        return status;
    }

    public void setStatus(GameStatusEnum status) {
        this.status = status;
    }
}
