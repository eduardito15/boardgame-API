package com.boardgame.dto;

import java.util.List;

public class GamesByUserResponseDTO {

    private List<String> gamesIds;

    public GamesByUserResponseDTO(List<String> gamesIds) {
        this.gamesIds = gamesIds;
    }

    public List<String> getGamesIds() {
        return gamesIds;
    }

    public void setGamesIds(List<String> gamesIds) {
        this.gamesIds = gamesIds;
    }
}
