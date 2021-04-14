package com.eduardovecino.boardgame.controller;

import com.eduardovecino.boardgame.dto.*;
import com.eduardovecino.boardgame.service.ActionFlow;
import com.eduardovecino.boardgame.service.GameFlow;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value = "Board Game API")
public class GameController {

    private final GameFlow gameFlow;
    private final ActionFlow actionFlow;

    @Autowired
    public GameController(GameFlow gameFlow, ActionFlow actionFlow) {
        this.gameFlow = gameFlow;
        this.actionFlow = actionFlow;
    }

    @PostMapping("/game/new")
    @ApiOperation(value = "Create a new game", response = GameResponseDTO.class, produces = MediaType.APPLICATION_JSON_VALUE)
    public GameResponseDTO newGame(@RequestBody final CreateMinesweeperGameRequestDTO createGameRequestDTO) {
        return gameFlow.createNewGame(createGameRequestDTO);
    }

    @PostMapping("/game/action")
    @ApiOperation(value = "Apply action on game", response = ActionResponseDTO.class, produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResponseDTO action(@RequestBody final ActionRequestDTO actionRequestDTO) {
        return actionFlow.action(actionRequestDTO);
    }

    @GetMapping("/game/{id}/load")
    @ApiOperation(value = "Get game by id", response = GameResponseDTO.class, produces = MediaType.APPLICATION_JSON_VALUE)
    public GameResponseDTO getGame(@PathVariable String id) {
        return gameFlow.loadGame(id);
    }

    @PostMapping("/games/byUser")
    @ApiOperation(value = "Get game by id", response = GamesByUserResponseDTO.class, produces = MediaType.APPLICATION_JSON_VALUE)
    public GamesByUserResponseDTO getGame(@RequestBody final GamesByUserRequestDTO gamesByUserRequestDTO) {
        return gameFlow.getUserGamesIds(gamesByUserRequestDTO);
    }

}
