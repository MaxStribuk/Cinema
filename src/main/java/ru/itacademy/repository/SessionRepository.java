package ru.itacademy.repository;

import ru.itacademy.model.Session;
import ru.itacademy.util.ConnectionManager;
import ru.itacademy.util.Constants;
import ru.itacademy.util.Exceptions.InvalidSessionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SessionRepository {
    public void printAllSessions() {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT session_id, start_time, end_time, title " +
                            "FROM session, movie " +
                            "WHERE start_time > ? " +
                            "AND session.movie_id = movie.movie_ID");
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ResultSet sessions = stmt.executeQuery();
            if (!sessions.first()) {
                System.out.println(Constants.MISSING_SESSIONS);
            } else {
                sessions.beforeFirst();
                while (sessions.next()) {
                    System.out.println(new Session(
                            sessions.getInt(1),
                            sessions.getTimestamp(2),
                            sessions.getTimestamp(3),
                            sessions.getString(4)
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
        }
    }

    public boolean createSession(int movieID, Timestamp startTime, Timestamp endTime) {
        try (Connection connection = ConnectionManager.open()) {
            if (checkSessionAvailability(connection, startTime, endTime)) {
                PreparedStatement stmt = connection.prepareStatement(
                        "INSERT INTO session (start_time, end_time, movie_id) VALUES (?, ?, ?)");
                stmt.setTimestamp(1, startTime);
                stmt.setTimestamp(2, endTime);
                stmt.setInt(3, movieID);
                stmt.execute();
                return true;
            } else {
                throw new InvalidSessionException();
            }
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            return false;
        } catch (InvalidSessionException e) {
            System.out.println(Constants.SESSIONS_IS_BUSY);
            return false;
        }
    }

    private boolean checkSessionAvailability(Connection connection, Timestamp startTime,
                                             Timestamp endTime) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM session WHERE start_time > ?");
        stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
        ResultSet sessions = stmt.executeQuery();
        while (sessions.next()) {
            Timestamp currentSessionStartTime = sessions.getTimestamp(2);
            Timestamp currentSessionEndTime = sessions.getTimestamp(3);
            if (!checkSessionCorrectness(startTime, endTime, currentSessionStartTime)
                    || !checkSessionCorrectness(startTime, endTime, currentSessionEndTime)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSessionCorrectness(Timestamp startTime, Timestamp endTime, Timestamp currentSessionTime) {
        return !((currentSessionTime.after(startTime)
                && currentSessionTime.before(endTime))
                || currentSessionTime.equals(startTime)
                || currentSessionTime.equals(endTime));
    }

    public int getSessionID(Timestamp startTime) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM session WHERE start_time = ?");
            stmt.setTimestamp(1, startTime);
            ResultSet session = stmt.executeQuery();
            session.first();
            return session.getInt(1);
        }
    }

    public boolean checkSessionIDCorrectness(int sessionID) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM session WHERE session_id = ?");
            stmt.setInt(1, sessionID);
            ResultSet session = stmt.executeQuery();
            return session.first();
        }
    }
}