package com.example.demo.dbaccess.repository;

import com.example.demo.dbaccess.domain.Game;

import java.util.Set;

public interface GameRepository {

    Game findById(String id);

    Set<String> getAllGameIDs();

    void save(Game game);

}
