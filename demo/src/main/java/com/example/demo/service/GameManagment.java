package com.example.demo.service;

import com.example.demo.dbaccess.domain.Game;
import com.example.demo.core.PlayerSides;
import com.example.demo.dbaccess.repository.GameRepository;
import com.example.demo.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Set;

@Service
public class GameManagment {

    private final GameRepository gameRepository;
    @Autowired
    public GameManagment(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game getGame(String gameId) throws WrongGameIdException {
        Game game = gameRepository.findById(gameId);
        if (game == null){
            throw new WrongGameIdException("This game [" + gameId + "] does not exist.");
        }
        return game;
    }

    public Set<String> getGamesIds() {
        return gameRepository.getAllGameIDs();
    }

    public Game addGame(){
        Game game = new Game();
        gameRepository.save(game);
        return game;
    }

    public void makeMove(String gameId, String player, Integer pitId)
            throws IncorrectPlayerName,
            WrongGameIdException,
            GameFinishedException,
            EmptyPitException,
            WrongPlayerException,
            IncorrectPitIdException,
            WrongPairPlayerPitException {
        PlayerSides playerSide = null;
        for (PlayerSides ps : PlayerSides.values()) {
            if (player.equals(ps.toString())) {
                playerSide = ps;
                break;
            }
        }
        if (playerSide == null) {
            throw new IncorrectPlayerName("There is no such player as [" + player + "]");
        }

        Game game = getGame(gameId);
        game.getBoard().makeMove(pitId, PlayerSides.valueOf(player.toUpperCase()));
    }

    public Game getGameState(String gameId) throws WrongGameIdException {
        return getGame(gameId);
    }
}
