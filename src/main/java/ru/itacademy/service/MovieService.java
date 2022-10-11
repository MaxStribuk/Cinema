package ru.itacademy.service;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

public interface MovieService {

    boolean printMovies() throws SQLException;

    String inputTitle();

    LocalTime inputDuration();

    boolean createMovie(String title, LocalTime duration) throws SQLException;

    Time getDuration(int movieID) throws SQLException;

    boolean checkMovieAvailability(int movieID);

    void updateTitle(int movieID) throws SQLException;

    void updateDuration(int movieID) throws SQLException;

    void removeMovie(int movieID) throws SQLException;
}