package com.example.demo.core;

import java.util.UUID;

public class Game {

    private final Board board;
    final private String id;
    public Game(){
        this.board = new Board();
        this.id = UUID.randomUUID().toString();
    }

    public Board getBoard(){
        return this.board;
    }

    public String getId(){
        return this.id;
    }
}
