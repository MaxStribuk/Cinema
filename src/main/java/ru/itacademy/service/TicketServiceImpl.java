package ru.itacademy.service;

import ru.itacademy.model.Ticket;
import ru.itacademy.repository.TicketRepository;
import ru.itacademy.util.Constants;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TicketServiceImpl implements TicketService {

    TicketRepository ticketRepository = new TicketRepository();
    SessionService sessionService = new SessionServiceImpl();

    @Override
    public void createTicketsForSession(Timestamp startTime) {
        int sessionID;
        try {
            sessionID = sessionService.getSessionID(startTime);
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            return;
        }
        List<Ticket> tickets = createTicketsForSession(sessionID);
        ticketRepository.createTicketsForSession(tickets);
    }

    @Override
    public void printTicketsWithSessionID(int sessionID) {
        ticketRepository.printTicketsWithSessionID(sessionID);
    }

    @Override
    public void printTicketsWithMovieID(int movieID) {
        ticketRepository.printTicketsWithMovieID(movieID);
    }

    @Override
    public boolean checkTicketAvailability(int ticketID) {
        if (ticketID < 0) {
            return false;
        } else {
            return ticketRepository.checkAvailabilityTicket(ticketID);
        }
    }

    @Override
    public void buyTicket(int ticketID, int userID) {
        ticketRepository.buyTicket(ticketID, userID);
    }

    @Override
    public boolean printUserTickets(int userID) {
        return ticketRepository.printUserTickets(userID);
    }

    @Override
    public void returnTicket(int ticketID, int userID) {
        ticketRepository.returnTicket(ticketID, userID);
    }

    private ArrayList<Ticket> createTicketsForSession(int sessionID) {
        ArrayList<Ticket> tickets = new ArrayList<>();
        tickets.addAll(createCheapTickets(sessionID));
        tickets.addAll(createExpensiveTickets(sessionID));
        return tickets;
    }

    private ArrayList<Ticket> createExpensiveTickets(int sessionID) {
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (int i = Constants.CHEAP_ROWS + 1; i <= Constants.MAX_ROW; i++) {
            for (int j = 1; j <= Constants.MAX_PLACE_IN_ROW; j++) {
                tickets.add(new Ticket(
                        i, j, Constants.COST_EXPENSIVE_TICKET, sessionID));
            }
        }
        return tickets;
    }

    private ArrayList<Ticket> createCheapTickets(int sessionID) {
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (int i = 1; i <= Constants.CHEAP_ROWS; i++) {
            for (int j = 1; j <= Constants.MAX_PLACE_IN_ROW; j++) {
                tickets.add(new Ticket(
                        i, j, Constants.COST_CHEAP_TICKET, sessionID));
            }
        }
        return tickets;
    }
}
