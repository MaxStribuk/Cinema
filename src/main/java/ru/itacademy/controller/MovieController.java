package ru.itacademy.controller;

import ru.itacademy.service.MovieService;
import ru.itacademy.service.MovieServiceImpl;
import ru.itacademy.util.Constants;

import java.time.LocalTime;

public class MovieController {

    MovieService movieService = new MovieServiceImpl();

    public void createMovie() {
        System.out.println(Constants.CREATING_MOVIE_TITLE);
        String title = movieService.inputTitle();
        if (title.equals("0")) {
            return;
        }
        System.out.println(Constants.CREATING_MOVIE_DURATION);
        LocalTime duration = movieService.inputDuration();
        if (movieService.createMovie(title, duration)) {
            System.out.println(Constants.SUCCESSFUL_CREATE_MOVIE);
        } else {
            System.out.println(Constants.FAILED_CREATE_MOVIE);
        }
    }

    public void printAllMovies() {
        movieService.printAllMovies();
    }
}