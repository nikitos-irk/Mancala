package com.example.demo.exceptions;

public class GameFinishedException extends Exception{
    public GameFinishedException(String errorMessage){
        super(errorMessage);
    }
}
