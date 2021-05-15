package com.example.demo.dbaccess.mongo;

import org.springframework.data.annotation.Id;


public class Game {

    @Id
    public String id;
    public Game() {}

    @Override
    public String toString() {
        return String.format("Game [id=%s]", id);
    }
}
