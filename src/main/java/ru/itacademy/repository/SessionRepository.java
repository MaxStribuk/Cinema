package ru.itacademy.repository;

import ru.itacademy.model.Session;
import ru.itacademy.util.ConnectionManager;
import ru.itacademy.util.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SessionRepository {

    public boolean printFutureSessions() {
        try (Connection connection = ConnectionManager.open()) {
            ResultSet sessions = getFutureSessions(connection);
            if (!sessions.first()) {
                System.out.println(Constants.MISSING_SESSIONS);
                return false;
            } else {
                sessions.beforeFirst();
                while (sessions.next()) {
                    System.out.println(new Session(
                            sessions.getInt("session_id"),
                            sessions.getTimestamp("start_time"),
                            sessions.getTimestamp("end_time"),
                            sessions.getString("title")
                    ));
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            return false;
        }
    }

    public void printAllSessions() {
        try (Connection connection = ConnectionManager.open()) {
            ResultSet sessions = getAllSessions(connection);
            if (!sessions.first()) {
                System.out.println(Constants.MISSING_SESSIONS);
            } else {
                sessions.beforeFirst();
                while (sessions.next()) {
                    System.out.println(new Session(
                            sessions.getInt("session_id"),
                            sessions.getTimestamp("start_time"),
                            sessions.getTimestamp("end_time"),
                            sessions.getString("title")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
        }
    }

    private ResultSet getAllSessions(Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT session_id, start_time, end_time, title " +
                        "FROM session, movie " +
                        "WHERE session.movie_id = movie.movie_ID");
        return stmt.executeQuery();
    }

    private static ResultSet getFutureSessions(Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT session_id, start_time, end_time, title " +
                        "FROM session, movie " +
                        "WHERE session.movie_id = movie.movie_ID " +
                        "AND start_time > ?");
        stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
        return stmt.executeQuery();
    }

    public boolean createSession(int movieID, Timestamp startTime, Timestamp endTime) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            if (checkAvailabilitySession(connection, 0, startTime, endTime)) {
                PreparedStatement stmt = connection.prepareStatement(
                        "INSERT INTO session (start_time, end_time, movie_id) VALUES (?, ?, ?)");
                stmt.setTimestamp(1, startTime);
                stmt.setTimestamp(2, endTime);
                stmt.setInt(3, movieID);
                stmt.execute();
                return true;
            } else {
                System.out.println(Constants.SESSIONS_IS_BUSY);
                return false;
            }
        }
    }

    public boolean checkAvailabilitySession(
            Connection connection, int sessionID, Timestamp startTime,
            Timestamp endTime) throws SQLException {
        ResultSet sessions = getAllSessions(connection);
        while (sessions.next()) {
            if (sessions.getInt("session_id") != sessionID) {
                Timestamp currentSessionStartTime = sessions.getTimestamp("start_time");
                Timestamp currentSessionEndTime = sessions.getTimestamp("end_time");
                if (checkAvailabilitySession(startTime, endTime, currentSessionStartTime)
                        || checkAvailabilitySession(startTime, endTime, currentSessionEndTime)) {
                    return false;
                }
            }
        }
        return true;

    }

    private boolean checkAvailabilitySession(Timestamp startTime,
                                             Timestamp endTime, Timestamp currentSessionTime) {
        return !currentSessionTime.before(startTime)
                && !currentSessionTime.after(endTime);
    }

    public int getSessionID(Timestamp startTime) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM session WHERE start_time = ?");
            stmt.setTimestamp(1, startTime);
            ResultSet session = stmt.executeQuery();
            session.first();
            return session.getInt("session_id");
        }
    }

    public boolean checkAvailabilitySession(int sessionID) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM session WHERE session_id = ?");
            stmt.setInt(1, sessionID);
            return stmt.executeQuery().first();
        }
    }

    public int getMovieID(int sessionID) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            ResultSet session = getSession(sessionID, connection);
            return session.getInt("movie_id");
        }
    }

    public boolean updateSession(int sessionID, Timestamp startTime,
                                 Timestamp endTime, int movieID) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            if (checkAvailabilitySession(connection, sessionID, startTime, endTime)) {
                PreparedStatement stmt = connection.prepareStatement(
                        "UPDATE session " +
                                "SET start_time = ?, " +
                                "end_time = ?, " +
                                "movie_id = ? " +
                                "WHERE session_id = ?");
                stmt.setTimestamp(1, startTime);
                stmt.setTimestamp(2, endTime);
                stmt.setInt(3, movieID);
                stmt.setInt(4, sessionID);
                stmt.execute();
                return true;
            } else {
                return false;
            }
        }
    }

    public Timestamp getStartTime(int sessionID) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            ResultSet session = getSession(sessionID, connection);
            return session.getTimestamp("start_time");
        }
    }

    private static ResultSet getSession(int sessionID, Connection connection) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT * " +
                        "FROM session " +
                        "WHERE session_id = ?");
        stmt.setInt(1, sessionID);
        ResultSet session = stmt.executeQuery();
        session.first();
        return session;
    }

    public ArrayList<Session> getSessions(int movieID) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM session " +
                            "WHERE movie_id = ?");
            stmt.setInt(1, movieID);
            ResultSet sessionsSet = stmt.executeQuery();
            ArrayList<Session> sessions = new ArrayList<>();
            while (sessionsSet.next()) {
                sessions.add(new Session(
                        sessionsSet.getInt("session_id"),
                        sessionsSet.getTimestamp("start_time"),
                        sessionsSet.getTimestamp("end_time"),
                        sessionsSet.getInt("movie_id")
                ));
            }
            return sessions;
        }
    }

    public boolean updateSessions(List<Session> sessions) throws SQLException {
        for (Session session : sessions) {
            if (!updateSession(session.getID(), session.getStartTime(),
                    session.getEndTime(), session.getMovieID())) {
                return false;
            }
        }
        return true;
    }

    public void removeSession(int sessionID) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM session " +
                            "WHERE session_id = ?");
            stmt.setInt(1, sessionID);
            stmt.execute();
        }
    }
}