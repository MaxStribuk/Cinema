package ru.itacademy.service;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

public interface SessionService {
    void printAllSessions();

    int inputMovieID();

    Timestamp inputStartTime();

    boolean createSession(int movieID, Timestamp startTime);

    int getSessionID(Timestamp startTime) throws SQLException;

    boolean checkSessionIDCorrectness(int sessionID) throws SQLException;

    void updateStartTime(int sessionID) throws SQLException;

    void updateMovieID(int sessionID) throws SQLException;

    boolean updateSessions(int movieID, Time duration) throws SQLException;
}