package com.example.demo.service;

import com.example.demo.core.Game;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class GameRepo {
    HashMap<String, Game> gameStore = new HashMap<>();

    public HashMap<String, Game> getGameStore(){
        return gameStore;
    }
}
