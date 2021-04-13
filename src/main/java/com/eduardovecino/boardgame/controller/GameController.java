package com.eduardovecino.boardgame.controller;

import com.eduardovecino.boardgame.dto.*;
import com.eduardovecino.boardgame.service.ActionFlow;
import com.eduardovecino.boardgame.service.GameFlow;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiOperation(value = "Create a new game", response = CreateGameResponseDTO.class, produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateGameResponseDTO newGame(@RequestBody final CreateMinesweeperGameRequestDTO createGameRequestDTO) {
        return gameFlow.createNewGame(createGameRequestDTO);
    }

    @PostMapping("/game/action")
    @ApiOperation(value = "Apply action on game", response = ActionResponseDTO.class, produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResponseDTO action(@RequestBody final ActionRequestDTO actionRequestDTO) {
        return actionFlow.action(actionRequestDTO);
    }

}
