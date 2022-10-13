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

public class SessionRepository {

    public boolean printSessions(boolean isAllSessions) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            ResultSet sessions = getSessions(connection, isAllSessions);
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
        }
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

    public boolean checkAvailabilitySession(
            Connection connection, int sessionID, Timestamp startTime,
            Timestamp endTime) throws SQLException {
        ResultSet sessions = getSessions(connection, true);
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

    public boolean checkAvailabilitySession(int sessionID) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM session WHERE session_id = ?");
            stmt.setInt(1, sessionID);
            return stmt.executeQuery().first();
        }
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

    public Session getSession(Timestamp startTime) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM session WHERE start_time = ?");
            stmt.setTimestamp(1, startTime);
            return getSession(stmt);
        }
    }

    public Session getSession(int sessionID) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * " +
                            "FROM session " +
                            "WHERE session_id = ?");
            stmt.setInt(1, sessionID);
            return getSession(stmt);
        }
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

    private Session getSession(PreparedStatement stmt) throws SQLException {
        ResultSet session = stmt.executeQuery();
        session.first();
        return new Session(
                session.getInt("session_id"),
                session.getTimestamp("start_time"),
                session.getTimestamp("end_time"),
                session.getInt("movie_id")
        );
    }

    private boolean checkAvailabilitySession(Timestamp startTime,
                                             Timestamp endTime, Timestamp currentSessionTime) {
        return !currentSessionTime.before(startTime)
                && !currentSessionTime.after(endTime);
    }

    /**
     * if the is All Sessions parameter is true,
     * the method will return all available sessions,
     * and if false,
     * the method will return only sessions whose start time has not yet come.
     */
    private ResultSet getSessions(Connection connection, boolean isAllSessions)
            throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(isAllSessions
                ? "SELECT session_id, start_time, end_time, title " +
                        "FROM session, movie " +
                        "WHERE session.movie_id = movie.movie_ID"
                : "SELECT session_id, start_time, end_time, title " +
                        "FROM session, movie " +
                        "WHERE session.movie_id = movie.movie_ID " +
                        "AND start_time > ?"
        );
        if (!isAllSessions) {
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
        }
        return stmt.executeQuery();
    }
}