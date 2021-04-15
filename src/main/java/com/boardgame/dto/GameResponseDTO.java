package com.boardgame.dto;

import com.boardgame.model.Board;
import com.boardgame.model.GameStatus;

public class  GameResponseDTO extends CreateGameRequestDTO {

    private String gameId;
    private Board board;
    private GameStatus status;
    private int timeMin;
    private int timeSec;

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

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public int getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(int timeMin) {
        this.timeMin = timeMin;
    }

    public int getTimeSec() {
        return timeSec;
    }

    public void setTimeSec(int timeSec) {
        this.timeSec = timeSec;
    }
}
