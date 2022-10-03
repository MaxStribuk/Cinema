package ru.itacademy.service;

import ru.itacademy.controller.Menu;
import ru.itacademy.repository.MovieRepository;
import ru.itacademy.util.Constants;

import java.sql.SQLException;
import java.sql.Time;
import java.time.DateTimeException;
import java.time.LocalTime;

public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository = new MovieRepository();

    @Override
    public void printAllMovies() {
        movieRepository.printAllMovies();
    }

    @Override
    public boolean createMovie(String title, LocalTime duration) {
        return movieRepository.createMovie(title, duration);
    }

    @Override
    public String inputTitle() {
        String title;
        while (true) {
            title = Menu.in.nextLine();
            if (title.equals("0")) {
                return "0";
            }
            if (title.length() > 0 && title.length() <= 100) {
                return title;
            } else {
                System.out.println(Constants.INVALID_MOVIE_TITLE);
            }
        }
    }

    @Override
    public LocalTime inputDuration() {
        while (true) {
            try {
                System.out.println(Constants.CREATING_MOVIE_DURATION_HOURS);
                int hours = Integer.parseInt(Menu.in.nextLine());
                System.out.println(Constants.CREATING_MOVIE_DURATION_MINUTES);
                int minutes = Integer.parseInt(Menu.in.nextLine());
                LocalTime duration = LocalTime.of(hours, minutes);
                if (!checkCorrectDuration(hours, minutes)) {
                    throw new DateTimeException("");
                }
                return duration;
            } catch (NumberFormatException | DateTimeException e) {
                System.out.println(Constants.INVALID_MOVIE_DURATION);
            }
        }
    }

    @Override
    public boolean checkMovieAvailability(int id) {
        return movieRepository.checkMovieAvailability(id);
    }

    @Override
    public Time getDuration(int movieID) throws SQLException {
        return movieRepository.getDuration(movieID);
    }

    private boolean checkCorrectDuration(int hours, int minutes) {
        int duration = hours * Constants.MINUTES_IN_HOUR + minutes;
        return duration >= Constants.MIN_MINUTES_IN_MOVIE
                && duration < Constants.MINUTES_IN_DAY;
    }
}