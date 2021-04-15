package com.boardgame.dto;

import io.swagger.annotations.ApiModelProperty;

public class CreateGameRequestDTO {

    @ApiModelProperty(
            value = "minesweeper",
            name = "gameName",
            dataType = "ENUM",
            example = "minesweeper")
    private String gameName;
    private String user;
    private int rows;
    private int columns;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
