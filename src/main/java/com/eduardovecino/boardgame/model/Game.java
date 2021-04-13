package com.eduardovecino.boardgame.model;

import com.eduardovecino.boardgame.constants.GameStatusEnum;

public class Game {

    private String id;
    private String gameName;
    private GameParams gameParams;
    private Board board;
    private GameStatusEnum status;
    private String user;
    private int timeMin;
    private int timeSec;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public GameParams getGameParams() {
        return gameParams;
    }

    public void setGameParams(GameParams gameParams) {
        this.gameParams = gameParams;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public GameStatusEnum getStatus() {
        return status;
    }

    public void setStatus(GameStatusEnum status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
