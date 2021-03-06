package com.boardgame.dto;

import com.boardgame.model.GameStatus;
import com.boardgame.model.Square;

import java.util.Set;

public class ActionResponseDTO {

    private String gameId;
    private Set<Square> boardResult;
    private GameStatus status;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Set<Square> getBoardResult() {
        return boardResult;
    }

    public void setBoardResult(Set<Square> boardResult) {
        this.boardResult = boardResult;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
