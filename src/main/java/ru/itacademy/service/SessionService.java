package ru.itacademy.service;

import java.sql.SQLException;
import java.sql.Timestamp;

public interface SessionService {
    void printAllSessions();

    int inputID();

    Timestamp inputStartTime();

    boolean createSession(int movieID, Timestamp startTime);

    int getSessionID(Timestamp startTime) throws SQLException;

    boolean checkSessionIDCorrectness(int sessionID) throws SQLException;
}