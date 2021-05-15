package com.example.demo.domain;

import com.example.demo.core.Game;
import com.example.demo.core.Player;
import com.example.demo.core.PlayerSides;
import com.example.demo.exceptions.*;
import com.example.demo.service.GameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

@Service
public class GameManagment {
    @Autowired
    GameRepo gameRepo;

    HashMap<String, PlayerSides> pm;

    public GameManagment(){
        this.pm = new HashMap<>();
        this.pm.put("first", PlayerSides.FIRST);
        this.pm.put("second", PlayerSides.SECOND);
    }

    public Game getGame(String gameId) throws WrongGameIdException {
        if (!gameRepo.getGameStore().containsKey(gameId)){
            throw new WrongGameIdException("This game [" + gameId + "] does not exist.");
        }
        return gameRepo.getGameStore().get(gameId);
    }

    public Set<String> getGamesIds(){
        return gameRepo.getGameStore().keySet();
    }

    public void addGame(Game game){
        gameRepo.getGameStore().put(game.getId(), game);
    }

    public void makeMove(String gameId, String player, Integer pitId) throws IncorrectPlayerName, GameFinishedException, EmptyPitException, WrongPlayerException, IncorrectPitIdException, WrongPairPlayerPitException {
        if (!this.pm.containsKey(player.toLowerCase())) {
            throw new IncorrectPlayerName("There is no such player as [" + player + "]");
        }
        gameRepo.getGameStore().get(gameId).getBoard().makeMove(pitId, this.pm.get(player.toLowerCase()));
    }

    public JsonGameData getGameState(String gameId) throws WrongGameIdException {
        Game game = this.getGame(gameId);
        return new JsonGameData(game);
    }
}
