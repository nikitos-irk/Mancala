package com.example.demo.exceptions;

public class IncorrectPitIdException extends Exception {
    public IncorrectPitIdException(String errorMessage){
        super(errorMessage);
    }
}
