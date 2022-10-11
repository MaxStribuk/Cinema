package ru.itacademy.service;

import ru.itacademy.model.Session;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface SessionService {

    boolean printSessions(boolean isAllSessions);

    int inputMovieID() throws SQLException;

    Timestamp inputStartTime();

    boolean createSession(int movieID, Timestamp startTime) throws SQLException;

    Session getSession(Timestamp startTime) throws SQLException;

    List<Session> getSessions(int movieID) throws SQLException;

    boolean checkSessionIDCorrectness(int sessionID);

    void updateSession(int sessionID, boolean isUpdateMovieID) throws SQLException;

    boolean updateSessions(int movieID, Time duration) throws SQLException;

    void removeSession(int sessionID) throws SQLException;
}