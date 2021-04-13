package com.eduardovecino.boardgame.model;

import java.util.Objects;

public class Square {

    private int row;
    private int column;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return row == square.row &&
                column == square.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
