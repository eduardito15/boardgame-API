package com.boardgame.constants;

public enum GamesEnum {
    MINESWEEPER("minesweeper");

    private final String name;

    GamesEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
