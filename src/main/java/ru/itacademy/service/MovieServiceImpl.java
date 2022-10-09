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
    public boolean printAllMovies() {
        return movieRepository.printMovies();
    }

    @Override
    public boolean createMovie(String title, LocalTime duration) throws SQLException {
        return movieRepository.createMovie(title, Time.valueOf(duration));
    }

    @Override
    public String inputTitle() {
        String title;
        System.out.println(Constants.CREATING_MOVIE_TITLE);
        while (true) {
            title = Menu.in.nextLine().trim();
            if (title.equals("0")) {
                return "0";
            }
            if (title.length() > 0
                    && title.length() <= 100
                    && checkCorrectTitle(title)) {
                return title;
            } else {
                System.out.println(Constants.INVALID_MOVIE_TITLE);
            }
        }
    }

    private boolean checkCorrectTitle(String title) {
        return title.matches("[a-zA-Z0-9 !?,-]{1,100}")
                && !title.matches("[ !?,-]{" + title.length() + "}");
    }

    @Override
    public LocalTime inputDuration() {
        System.out.println(Constants.CREATING_MOVIE_DURATION);
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
        return movieRepository.checkAvailabilityMovie(id);
    }

    @Override
    public Time getDuration(int movieID) throws SQLException {
        return movieRepository.getDuration(movieID);
    }

    @Override
    public void updateTitle(int movieID) throws SQLException {
        String title = inputTitle();
        if (title.equals("0")) {
            return;
        }
        Time duration = movieRepository.getDuration(movieID);
        System.out.println(movieRepository.updateMovie(movieID, title, duration)
                ? Constants.SUCCESSFUL_CREATE_MOVIE
                : Constants.FAILED_CREATE_MOVIE);
    }

    @Override
    public void updateDuration(int movieID) throws SQLException {
        Time duration = Time.valueOf(inputDuration());
        String title = movieRepository.getTitle(movieID);
        if (movieRepository.checkAvailabilityMovie(title, duration)
                && Service.sessionService.updateSessions(movieID, duration)) {
            movieRepository.updateMovie(movieID, title, duration);
            System.out.println(Constants.SUCCESSFUL_CREATE_MOVIE);
        } else {
            System.out.println(Constants.FAILED_CREATE_MOVIE);
            System.out.println(Constants.SESSIONS_IS_BUSY);
        }
    }

    @Override
    public void removeMovie(int movieID) throws SQLException {
        movieRepository.removeMovie(movieID);
    }

    private boolean checkCorrectDuration(int hours, int minutes) {
        int duration = hours * Constants.MINUTES_IN_HOUR + minutes;
        return duration >= Constants.MIN_MINUTES_IN_MOVIE
                && duration < Constants.MINUTES_IN_DAY;
    }
}