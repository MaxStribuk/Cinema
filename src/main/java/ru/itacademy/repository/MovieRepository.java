package ru.itacademy.repository;

import ru.itacademy.model.Movie;
import ru.itacademy.util.ConnectionManager;
import ru.itacademy.util.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class MovieRepository {

    public boolean createMovie(String title, Time duration) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO movie (title, duration) VALUES (?, ?)");
            return createMovie(title, duration, connection, stmt);
        }
    }

    public boolean updateMovie(int movieID, String title, Time duration) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE movie " +
                            "SET title = ?, " +
                            "duration = ? " +
                            "WHERE movie_id = ?");
            stmt.setInt(3, movieID);
            return createMovie(title, duration, connection, stmt);
        }
    }

    public boolean printMovies() throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM movie");
            ResultSet movies = stmt.executeQuery();
            if (!movies.first()) {
                System.out.println(Constants.MISSING_MOVIES);
                return false;
            } else {
                movies.beforeFirst();
                while (movies.next()) {
                    System.out.println(new Movie(
                            movies.getInt("movie_id"),
                            movies.getString("title"),
                            movies.getTime("duration").toLocalTime()
                    ));
                }
                return true;
            }
        }
    }

    public boolean checkAvailabilityMovie(int movieID) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            ResultSet movie = getMovie(movieID, connection);
            return movie.first();
        }
    }

    public boolean checkAvailabilityMovie(Connection connection,
                                          String title, Time duration)
            throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM movie WHERE title = ? AND duration = ?");
        stmt.setString(1, title);
        stmt.setTime(2, duration);
        return !stmt.executeQuery().first();
    }

    public void removeMovie(int movieID) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM movie " +
                            "WHERE movie_id = ?");
            stmt.setInt(1, movieID);
            stmt.execute();
        }
    }

    public Time getDuration(int movieID) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            ResultSet movie = getMovie(movieID, connection);
            movie.first();
            return movie.getTime("duration");
        }
    }

    public String getTitle(int movieID) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            ResultSet movie = getMovie(movieID, connection);
            movie.first();
            return movie.getString("title");
        }
    }

    private ResultSet getMovie(int movieID, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM movie WHERE movie_id = ?");
        stmt.setInt(1, movieID);
        return stmt.executeQuery();
    }

    private boolean createMovie(String title, Time duration, Connection connection,
                                PreparedStatement stmt) throws SQLException {
        stmt.setString(1, title);
        stmt.setTime(2, duration);
        if (checkAvailabilityMovie(connection, title, duration)) {
            stmt.execute();
            return true;
        } else {
            System.out.println(Constants.MOVIE_IS_BUSY);
            return false;
        }
    }
}