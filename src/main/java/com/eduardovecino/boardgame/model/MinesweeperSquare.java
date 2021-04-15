package com.eduardovecino.boardgame.model;

import java.util.Objects;

public class MinesweeperSquare extends Square {

    public MinesweeperSquare(Integer row, Integer column) {
        super(row, column);
    }

    private boolean flag;
    private boolean mine;
    private int adjacentMines;
    private boolean turned;
    private boolean question;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    public boolean isTurned() {
        return turned;
    }

    public void setTurned(boolean turned) {
        this.turned = turned;
    }

    public boolean isQuestion() {
        return question;
    }

    public void setQuestion(boolean question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MinesweeperSquare that = (MinesweeperSquare) o;
        return flag == that.flag &&
                mine == that.mine &&
                adjacentMines == that.adjacentMines &&
                turned == that.turned &&
                question == that.question;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), flag, mine, adjacentMines, turned, question);
    }

    @Override
    public String toString() {
        return "MinesweeperSquare{" +
                "flag=" + flag +
                ", mine=" + mine +
                ", adjacentMines=" + adjacentMines +
                ", turned=" + turned +
                ", question=" + question +
                '}';
    }
}
