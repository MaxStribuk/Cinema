package ru.itacademy.service;

import java.sql.Timestamp;

public interface TicketService {
    void createTicketsForSession(Timestamp startTime);

    void printTicketsWithSessionID(int sessionID);

    void printTicketsWithMovieID(int movieID);

    boolean checkTicketAvailability(int ticketID);

    void buyTicket(int ticketID, int userID);

    boolean printUserTickets(int userID);

    void returnTicket(int ticketID, int userID);
}
