package ru.itacademy.service;

import ru.itacademy.model.Session;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface TicketService {
    void createTicketsForSession(Timestamp startTime);

    void printTicketsWithSessionID(int sessionID);

    void printTicketsWithMovieID(int movieID);

    boolean checkTicketAvailability(int ticketID);

    void buyTicket(int ticketID, int userID);

    boolean printUserTickets(int userID);

    void returnTicket(int ticketID, int userID);

    void removeTicketsForSession(int sessionID) throws SQLException;

    void removeTicketsForSessions(List<Session> sessions) throws SQLException;
}
