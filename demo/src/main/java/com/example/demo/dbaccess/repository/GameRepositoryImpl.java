package com.example.demo.dbaccess.repository;

import com.example.demo.dbaccess.domain.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Set;

@Repository
public class GameRepositoryImpl implements GameRepository {
    private HashMap<String, Game> gameStore = new HashMap<>();

    public Game findById(String id) {
        return gameStore.get(id);
    }

    public Set<String> getAllGameIDs() {
        return gameStore.keySet();
    }

    public void save(Game game) {
        gameStore.put(game.getId(), game);
    }
}
