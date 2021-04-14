package com.eduardovecino.boardgame.model;

import com.eduardovecino.boardgame.constants.GamesEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Game {

    @Id
    private String id;
    private GamesEnum gameName;
    private GameParams gameParams;
    private Board board;
    private GameStatus status;
    private String user;
    private int timeMin;
    private int timeSec;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GamesEnum getGameName() {
        return gameName;
    }

    public void setGameName(GamesEnum gameName) {
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

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
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
