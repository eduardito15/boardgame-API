package com.eduardovecino.boardgame.model;

import com.eduardovecino.boardgame.constants.GameStatusEnum;

public class GameStatus {

    private GameStatusEnum status;
    private boolean ended;

    public GameStatus() {}

    public GameStatus(GameStatusEnum status, boolean ended) {
        this.setStatus(status);
        this.setEnded(ended);
    }

    public GameStatusEnum getStatus() {
        return status;
    }

    public void setStatus(GameStatusEnum status) {
        this.status = status;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }
}
