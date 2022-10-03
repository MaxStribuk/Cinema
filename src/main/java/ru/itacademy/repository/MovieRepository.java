package ru.itacademy.repository;

import ru.itacademy.model.Movie;
import ru.itacademy.util.ConnectionManager;
import ru.itacademy.util.Constants;
import ru.itacademy.util.Exceptions.InvalidMovieException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

public class MovieRepository {
    public void printAllMovies() {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM movie");
            ResultSet movies = stmt.executeQuery();
            if (!movies.first()) {
                System.out.println(Constants.MISSING_MOVIES);
            } else {
                movies.beforeFirst();
                while (movies.next()) {
                    System.out.println(new Movie(
                            movies.getInt(1),
                            movies.getString(2),
                            movies.getTime(3).toLocalTime()
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
        }
    }

    public boolean createMovie(String title, LocalTime duration) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO movie (title, duration) VALUES (?, ?)");
            stmt.setString(1, title);
            stmt.setTime(2, Time.valueOf(duration));
            if (checkMovieAvailability(connection, title, duration)) {
                stmt.execute();
                return true;
            } else {
                throw new InvalidMovieException();
            }
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            return false;
        } catch (InvalidMovieException e) {
            System.out.println(Constants.MOVIE_IS_BUSY);
            return false;
        }
    }

    private boolean checkMovieAvailability(Connection connection,
                                           String title, LocalTime duration) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM movie WHERE title = ? AND duration = ?");
        stmt.setString(1, title);
        stmt.setTime(2, Time.valueOf(duration));
        return !stmt.executeQuery().first();
    }

    public boolean checkMovieAvailability(int movieID) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM movie WHERE movie_id = ?");
            stmt.setInt(1, movieID);
            return stmt.executeQuery().first();
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            return false;
        }
    }

    public Time getDuration(int movieID) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM movie WHERE movie_id = ?");
            stmt.setInt(1, movieID);
            ResultSet movie = stmt.executeQuery();
            movie.first();
            return movie.getTime(3);
        }
    }
}