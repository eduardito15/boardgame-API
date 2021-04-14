package com.eduardovecino.boardgame.model;

public class Board {

    private Square[][] squares;
    private int rows;
    private int columns;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        squares = new Square[rows][columns];
    }

    public Square[][] getSquares() {
        return squares;
    }

    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }

    public Square updateSquare(Integer row, Integer column, Square square) {
        squares[row][column] = square;
        return square;
    }

    public Square getSquare(Integer row, Integer column) {
        return squares[row][column];
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
}
