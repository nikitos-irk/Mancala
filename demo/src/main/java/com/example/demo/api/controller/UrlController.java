package com.example.demo.api.controller;

import com.example.demo.core.Game;
import com.example.demo.domain.GameManagment;
import com.example.demo.service.GameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType;
import java.util.HashMap;
import java.util.Set;


@RestController
public class UrlController {

    @Autowired
    GameManagment gameManagment;

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
}
