package com.example.demo.api.controller;

import com.example.demo.core.Game;
import com.example.demo.domain.GameManagment;
import com.example.demo.domain.JsonGameData;
import com.example.demo.exceptions.*;
import com.example.demo.service.GameRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import java.util.HashMap;
import java.util.Set;


@RestController
public class UrlController {

    @Autowired
    GameManagment gameManagment;

    private static final Logger LOGGER= LoggerFactory.getLogger(UrlController.class);

    @RequestMapping(
            method = RequestMethod.POST,
            value = {"/games"},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String createGame() {
        Game game = new Game();
        gameManagment.addGame(game);
        return game.getId();
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = {"/games"},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Set<String> getGames() {
        return gameManagment.getGamesIds();
    }

    @ExceptionHandler(Exception.class)
    @RequestMapping(
            method = RequestMethod.POST,
            value = {"/games/{id}"},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void makeMove(
            @PathVariable("id") String id,
            @RequestParam("player") String player,
            @RequestParam("pit") Integer pitId)
            throws
            GameFinishedException,
            EmptyPitException,
            WrongPlayerException,
            IncorrectPitIdException,
            IncorrectPlayerName,
            WrongPairPlayerPitException {
        gameManagment.makeMove(id, player, pitId);
    }

    @ExceptionHandler(WrongGameIdException.class)
    @RequestMapping(
            method = RequestMethod.GET,
            value = {"/games/{id}"},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public JsonGameData getGameState(
            @PathVariable("id") String id) throws WrongGameIdException  {
                return gameManagment.getGameState(id);
    }
}
