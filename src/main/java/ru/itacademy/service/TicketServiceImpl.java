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

    @Override
    public void createTickets(Timestamp startTime) throws SQLException {
        int sessionID = Service.sessionService.getSession(startTime).getID();
        List<Ticket> tickets = new ArrayList<>(createTickets(sessionID, 1,
                Constants.CHEAP_ROWS, Constants.COST_CHEAP_TICKET));
        tickets.addAll(createTickets(sessionID, Constants.CHEAP_ROWS + 1,
                Constants.MAX_ROW, Constants.COST_EXPENSIVE_TICKET));
        ticketRepository.createTickets(tickets);
    }

    @Override
    public boolean checkTicketAvailability(int ticketID) throws SQLException {
        if (ticketID < 0) {
            return false;
        } else {
            return ticketRepository.checkAvailabilityTicket(ticketID);
        }
    }

    @Override
    public boolean printTickets(int id, String column) throws SQLException {
        if (column.equals("userID")
                || column.equals("movieID")
                || column.equals("sessionID")) {
            return ticketRepository.printTickets(id, column);
        } else {
            throw new SQLException();
        }
    }

    @Override
    public void updateTicket(int ticketID, int userID, boolean isBuyTicket) {
        try {
            if (isBuyTicket) {
                ticketRepository.updateTicket(ticketID, userID, true,
                        Constants.SUCCESSFUL_BUY_TICKET, Constants.FAILED_BUY_TICKET);
            } else {
                ticketRepository.updateTicket(ticketID, userID, false,
                        Constants.SUCCESSFUL_RETURN_TICKET, Constants.FAILED_RETURN_TICKET);
            }
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
        }
    }

    @Override
    public void removeTickets(int sessionID) throws SQLException {
        ticketRepository.removeTickets(sessionID);
    }

    private ArrayList<Ticket> createTickets(int sessionID, int firstRow,
                                            int lastRow, int costTicket) {
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (int i = firstRow; i <= lastRow; i++) {
            for (int j = 1; j <= Constants.MAX_PLACE_IN_ROW; j++) {
                tickets.add(new Ticket(i, j, costTicket, sessionID));
            }
        }
        return tickets;
    }
}
