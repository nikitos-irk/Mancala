package com.example.demo.core;

public class Game {

    private final Board board;
    public Game(){
        this.board = new Board();
    }

    Board getBoard(){
        return this.board;
    }
}
