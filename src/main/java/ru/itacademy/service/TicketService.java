package ru.itacademy.service;

import java.sql.SQLException;
import java.sql.Timestamp;

public interface TicketService {

    boolean printTickets(int ID, String column) throws SQLException;

    void createTickets(Timestamp startTime) throws SQLException;

    boolean checkTicketAvailability(int ticketID) throws SQLException;

    void updateTicket(int ticketID, int userID, boolean isBuyTicket);

    void removeTickets(int sessionID) throws SQLException;
}