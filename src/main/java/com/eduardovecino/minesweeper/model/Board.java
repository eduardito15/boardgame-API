package com.eduardovecino.minesweeper.model;

public class Board {

    private Square[][] squares;

    public Board(int rows, int columns) {
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

}
