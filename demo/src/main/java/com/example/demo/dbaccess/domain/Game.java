package com.example.demo.dbaccess.domain;

import com.example.demo.core.Board;

import java.util.UUID;

public class Game {

    private final Board board;
    private final String id;

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
