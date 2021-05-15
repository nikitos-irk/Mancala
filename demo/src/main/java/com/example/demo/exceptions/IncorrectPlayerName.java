package com.example.demo.exceptions;

public class IncorrectPlayerName extends Exception{
    public IncorrectPlayerName(String errorMessage){
        super(errorMessage);
    }
}
