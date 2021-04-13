package com.eduardovecino.boardgame.dto;

import com.eduardovecino.boardgame.constants.GameStatusEnum;
import com.eduardovecino.boardgame.model.Square;

import java.util.Set;

public class ActionResponseDTO {

    private String gameId;
    private Set<Square> boardResult;
    private GameStatusEnum status;

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

    public GameStatusEnum getStatus() {
        return status;
    }

    public void setStatus(GameStatusEnum status) {
        this.status = status;
    }
}
