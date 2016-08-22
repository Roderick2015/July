package com.roderick.july.controller;

/**
 * Created by Roderick on 2016/8/18.
 */
public class MovieNotFoundException extends RuntimeException {
    private String movieName;

    public MovieNotFoundException(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieName() {
        return movieName;
    }

}
