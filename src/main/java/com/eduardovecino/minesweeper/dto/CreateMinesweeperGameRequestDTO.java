package com.eduardovecino.minesweeper.dto;

public class CreateMinesweeperGameRequestDTO extends CreateGameRequestDTO {

    private int mines;

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }
}
