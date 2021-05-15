package com.example.demo.exceptions;

public class WrongGameIdException extends Exception{
    public WrongGameIdException(String errorMessage){
        super(errorMessage);
    }
}
