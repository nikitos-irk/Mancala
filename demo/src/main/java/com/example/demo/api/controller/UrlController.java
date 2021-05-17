package com.example.demo.api.controller;

import com.example.demo.api.converter.JsonGameConverter;
import com.example.demo.api.dto.JsonMove;
import com.example.demo.api.dto.JsonResult;
import com.example.demo.service.GameManagment;
import com.example.demo.api.dto.JsonGameData;
import com.example.demo.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.Set;


@RestController
public class UrlController {

    private final GameManagment gameManagment;
    private final JsonGameConverter gameConverter;

    private static final Logger LOGGER= LoggerFactory.getLogger(UrlController.class);


    @Autowired
    public UrlController(GameManagment gameManagment, JsonGameConverter gameConverter) {
        this.gameManagment = gameManagment;
        this.gameConverter = gameConverter;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = {"/games"},
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String createGame() {
        return gameManagment.addGame().getId();
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = {"/games"},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Set<String> getGames() {
        return gameManagment.getGamesIds();
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = {"/games/{id}"},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void makeMove(
            @PathVariable("id") String id,
            @RequestBody JsonMove jsonMove)
            throws
            GameFinishedException,
            EmptyPitException,
            WrongPlayerException,
            IncorrectPitIdException,
            IncorrectPlayerName,
            WrongGameIdException,
            WrongPairPlayerPitException{
        gameManagment.makeMove(id, jsonMove.getPlayer(), jsonMove.getPitId());
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = {"/games/{id}"},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public JsonResult<JsonGameData> getGameState(@PathVariable("id") String id) throws WrongGameIdException  {
        return gameConverter.toJsonResult(gameManagment.getGameState(id));
    }
}
