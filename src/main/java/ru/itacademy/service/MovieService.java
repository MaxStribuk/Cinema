package ru.itacademy.service;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

public interface MovieService {
    void printAllMovies();

    boolean createMovie(String title, LocalTime duration) throws SQLException;

    String inputTitle();

    LocalTime inputDuration();

    boolean checkMovieAvailability(int movieID);

    Time getDuration(int movieID) throws SQLException;

    void updateTitle(int movieID) throws SQLException;

    void updateDuration(int movieID) throws SQLException;
}