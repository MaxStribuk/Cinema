package ru.itacademy.service;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

public interface MovieService {
    void printAllMovies();

    boolean createMovie(String title, LocalTime duration);

    String inputTitle();

    LocalTime inputDuration();

    boolean checkMovieAvailability(int movieID);

    Time getDuration(int movieID) throws SQLException;
}