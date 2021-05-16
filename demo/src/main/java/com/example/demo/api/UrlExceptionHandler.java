package com.example.demo.api;

import com.example.demo.api.dto.JsonRequestError;
import com.example.demo.api.dto.JsonResult;
import com.example.demo.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class UrlExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            GameFinishedException.class,
            EmptyPitException.class,
            WrongPlayerException.class,
            IncorrectPitIdException.class,
            IncorrectPlayerName.class,
            WrongPairPlayerPitException.class,
            WrongGameIdException.class})
    public ResponseEntity<Object> handleBaseException(HttpServletRequest req, Exception e) throws Exception {
        return new ResponseEntity(new JsonResult<>(new JsonRequestError(e, req.getRequestURL().toString())), HttpStatus.BAD_REQUEST);
    }
}
