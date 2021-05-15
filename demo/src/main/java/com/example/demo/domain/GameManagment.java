package com.example.demo.domain;

import com.example.demo.core.Game;
import com.example.demo.service.GameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GameManagment {
    @Autowired
    GameRepo gameRepo;

    public Game getGame(String gameId){
        return gameRepo.getGameStore().get(gameId);
    }

    public Set<String> getGamesIds(){
        return gameRepo.getGameStore().keySet();
    }

    public void addGame(Game game){
        gameRepo.getGameStore().put(game.getId(), game);
    }
}
