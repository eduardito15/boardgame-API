package com.eduardovecino.minesweeper.controller;

import com.eduardovecino.minesweeper.dto.*;
import com.eduardovecino.minesweeper.service.ActionFlow;
import com.eduardovecino.minesweeper.service.GameFlow;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/minesweeper")
@Api(value = "minesweeper Game API")
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

    @PostMapping("/game/turn")
    @ApiOperation(value = "Turn a Square", response = ActionResponseDTO.class, produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResponseDTO newGame(@RequestBody final ActionRequestDTO actionRequestDTO) {
        return actionFlow.action(actionRequestDTO);
    }

}
