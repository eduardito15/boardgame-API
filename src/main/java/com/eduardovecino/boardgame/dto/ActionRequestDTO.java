package com.eduardovecino.boardgame.dto;

import com.eduardovecino.boardgame.constants.ActionsEnum;
import io.swagger.annotations.ApiModelProperty;

public class ActionRequestDTO {

    private String gameId;
    @ApiModelProperty(value = "TURN|FLAG|QUESTION", name = "action", dataType = "ENUM", example = "TURN")
    private ActionsEnum action;
    private int row;
    private int column;
    private int timeMin;
    private int timeSec;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public ActionsEnum getAction() {
        return action;
    }

    public void setAction(ActionsEnum action) {
        this.action = action;
    }

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

    public int getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(int timeMin) {
        this.timeMin = timeMin;
    }

    public int getTimeSec() {
        return timeSec;
    }

    public void setTimeSec(int timeSec) {
        this.timeSec = timeSec;
    }
}
