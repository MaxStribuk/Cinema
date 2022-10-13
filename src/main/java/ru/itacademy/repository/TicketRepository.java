package ru.itacademy.repository;

import ru.itacademy.model.Ticket;
import ru.itacademy.util.ConnectionManager;
import ru.itacademy.util.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class TicketRepository {

    public void createTickets(List<Ticket> tickets) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO ticket (row, place, cost, session_id) VALUES (?, ?, ?, ?)");
            for (Ticket ticket : tickets) {
                stmt.setInt(1, ticket.getRow());
                stmt.setInt(2, ticket.getPlace());
                stmt.setInt(3, ticket.getCost());
                stmt.setInt(4, ticket.getSessionID());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public boolean printTickets(int id, String column) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = getPreparedStatement(connection, column, id);
            return printTickets(stmt.executeQuery(),
                    column.equals("userID")
                            ? Constants.MISSING_USER_TICKETS
                            : Constants.MISSING_TICKETS);
        }
    }

    private PreparedStatement getPreparedStatement(Connection connection, String column, int id)
            throws SQLException {
        PreparedStatement stmt =
                switch (column) {
                    case "movieID" -> connection.prepareStatement(
                            "SELECT ticket.ticket_id, ticket.row, ticket.place, ticket.cost, " +
                                    "session.start_time, session.end_time, movie.title " +
                                    "FROM ticket, session, movie " +
                                    "WHERE ticket.session_id = session.session_id " +
                                    "AND session.movie_id = movie.movie_id " +
                                    "AND movie.movie_id = ? " +
                                    "AND ticket.user_id IS NULL");
                    case "sessionID" -> connection.prepareStatement(
                            "SELECT ticket.ticket_id, ticket.row, ticket.place, ticket.cost, " +
                                    "session.start_time, session.end_time, movie.title " +
                                    "FROM ticket, session, movie " +
                                    "WHERE ticket.session_id = session.session_id " +
                                    "AND session.movie_id = movie.movie_id " +
                                    "AND ticket.session_id = ? " +
                                    "AND ticket.user_id IS NULL");
                    default -> connection.prepareStatement(
                            "SELECT ticket.ticket_id, ticket.row, ticket.place, ticket.cost, " +
                                    "session.start_time, session.end_time, movie.title " +
                                    "FROM ticket, session, movie " +
                                    "WHERE ticket.session_id = session.session_id " +
                                    "AND session.movie_id = movie.movie_id " +
                                    "AND ticket.user_id = ?");
                };
        stmt.setInt(1, id);
        return stmt;
    }

    public boolean checkAvailabilityTicket(int ticketID) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM ticket WHERE ticket_id = ?");
            stmt.setInt(1, ticketID);
            return stmt.executeQuery().first();
        }
    }

    public void updateTicket(int ticketID, int userID, boolean isBuyTicket,
                             String successfulMessage, String failedMessage) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    isBuyTicket
                            ? "UPDATE ticket SET user_id = ? " +
                            "WHERE ticket_id = ? " +
                            "AND user_id IS NULL"
                            : "UPDATE ticket SET user_id = NULL " +
                            "WHERE user_id = ? " +
                            "AND ticket_id = ?"
            );
            stmt.setInt(1, userID);
            stmt.setInt(2, ticketID);
            if (!checkingSessionStart(connection, ticketID)
                    && !stmt.execute()
                    && stmt.getUpdateCount() == 1) {
                printMessage(connection, ticketID, successfulMessage);
            } else {
                System.out.println(failedMessage);
            }
        }
    }

    public void removeTickets(int sessionID) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM ticket " +
                            "WHERE session_id = ?");
            stmt.setInt(1, sessionID);
            stmt.execute();
        }
    }

    private void printMessage(Connection connection,
                              int ticketID, String message) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT cost FROM ticket WHERE ticket_id = ?");
        stmt.setInt(1, ticketID);
        ResultSet ticket = stmt.executeQuery();
        ticket.first();
        System.out.println(message +
                ticket.getInt("cost") +
                Constants.CURRENCY);
    }

    private boolean printTickets(ResultSet tickets, String message) throws SQLException {
        if (!tickets.first()) {
            System.out.println(message);
            return false;
        } else {
            tickets.beforeFirst();
            while (tickets.next()) {
                System.out.println(new Ticket(
                        tickets.getInt("ticket_id"),
                        tickets.getInt("row"),
                        tickets.getInt("place"),
                        tickets.getInt("cost"),
                        tickets.getTimestamp("start_time"),
                        tickets.getTimestamp("end_time"),
                        tickets.getString("title")
                ));
            }
            return true;
        }
    }

    private boolean checkingSessionStart(Connection connection, int ticketID) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT session.start_time " +
                        "FROM ticket, session " +
                        "WHERE ticket.session_id = session.session_id " +
                        "AND ticket.ticket_id = ?");
        stmt.setInt(1, ticketID);
        ResultSet tickets = stmt.executeQuery();
        tickets.first();
        return tickets.getTimestamp("start_time")
                .toLocalDateTime()
                .isBefore(LocalDateTime.now());
    }
}