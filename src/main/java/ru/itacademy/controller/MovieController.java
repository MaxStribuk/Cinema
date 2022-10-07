package ru.itacademy.controller;

import ru.itacademy.service.MovieService;
import ru.itacademy.service.MovieServiceImpl;
import ru.itacademy.util.Constants;

import java.sql.SQLException;
import java.time.LocalTime;

public class MovieController {

    MovieService movieService = new MovieServiceImpl();

    public void createMovie() {
        String title = movieService.inputTitle();
        if (title.equals("0")) {
            return;
        }
        System.out.println(Constants.CREATING_MOVIE_DURATION);
        LocalTime duration = movieService.inputDuration();
        try {
            if (movieService.createMovie(title, duration)) {
                System.out.println(Constants.SUCCESSFUL_CREATE_MOVIE);
            } else {
                System.out.println(Constants.FAILED_CREATE_MOVIE);
            }
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
        }
    }

    public void printAllMovies() {
        movieService.printAllMovies();
    }

    public void updateMovie() {
        System.out.println(Constants.UPDATE_MOVIE);
        while (true) {
            try {
                int movieID = Integer.parseInt(Menu.in.nextLine());
                if (movieID == 0) {
                    return;
                }
                if (movieService.checkMovieAvailability(movieID)) {
                    updateMovie(movieID);
                    return;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_MOVIE_ID);
            }
        }
    }

    private void updateMovie(int movieID) {
        while (true) {
            System.out.println(Constants.MENU_MOVIE_UPDATE);
            try {
                int input = Integer.parseInt(Menu.in.nextLine());
                switch (input) {
                    case 0 -> {
                        return;
                    }
                    case 1 -> {
                        movieService.updateTitle(movieID);
                        return;
                    }
                    case 2 -> {
                        movieService.updateDuration(movieID);
                        return;
                    }
                    default -> throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_INPUT);
            } catch (SQLException e) {
                System.out.println(Constants.FAILED_CONNECTION_DATABASE);
                return;
            }
        }
    }
}