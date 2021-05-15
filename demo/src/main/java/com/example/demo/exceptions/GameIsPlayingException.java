package com.example.demo.exceptions;

public class GameIsPlayingException extends Exception{
    public GameIsPlayingException(String errorMessage){
        super(errorMessage);
    }
}
