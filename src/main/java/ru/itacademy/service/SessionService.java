package ru.itacademy.service;

import ru.itacademy.model.Session;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface SessionService {
    boolean printFutureSessions();

    int inputMovieID();

    Timestamp inputStartTime();

    boolean createSession(int movieID, Timestamp startTime);

    int getSessionID(Timestamp startTime) throws SQLException;

    boolean checkSessionIDCorrectness(int sessionID) throws SQLException;

    void updateStartTime(int sessionID) throws SQLException;

    void updateMovieID(int sessionID) throws SQLException;

    boolean updateSessions(int movieID, Time duration) throws SQLException;

    void printAllSessions();

    void removeSession(int sessionID) throws SQLException;

    List<Session> getSessions(int movieID) throws SQLException;
}