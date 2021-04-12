package com.eduardovecino.minesweeper.dto;

import com.eduardovecino.minesweeper.constants.GameStatusEnum;
import com.eduardovecino.minesweeper.model.Square;

import java.util.Set;

public class ActionResponseDTO {

    private Set<Square> affectedSquares;
    private GameStatusEnum status;

    public Set<Square> getAffectedSquares() {
        return affectedSquares;
    }

    public void setAffectedSquares(Set<Square> affectedSquares) {
        this.affectedSquares = affectedSquares;
    }

    public GameStatusEnum getStatus() {
        return status;
    }

    public void setStatus(GameStatusEnum status) {
        this.status = status;
    }
}
