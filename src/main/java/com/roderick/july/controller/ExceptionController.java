package com.roderick.july.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Roderick on 2016/8/21.
 */
@RestController
public class ExceptionController {

    @ExceptionHandler(MovieNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error movieNotFound(MovieNotFoundException e) {
        String movieName = e.getMovieName();
        return  new Error(movieName + " not found", e);
    }

}
