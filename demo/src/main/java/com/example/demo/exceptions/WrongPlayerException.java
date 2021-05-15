package com.example.demo.exceptions;

public class WrongPlayerException extends Exception{
    public WrongPlayerException(String errorMessage){
        super(errorMessage);
    }
}
