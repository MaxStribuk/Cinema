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
    public void createTicketsForSession(List<Ticket> tickets) {
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
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
        }
    }

    public void printTicketsWithSessionID(int sessionID) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT ticket.ticket_id, ticket.row, ticket.place, ticket.cost, " +
                            "session.start_time, session.end_time, movie.title " +
                            "FROM ticket, session, movie " +
                            "WHERE ticket.session_id = session.session_id " +
                            "AND session.movie_id = movie.movie_id " +
                            "AND ticket.session_id = ? " +
                            "AND ticket.user_id IS NULL");
            stmt.setInt(1, sessionID);
            ResultSet tickets = stmt.executeQuery();
            if (!tickets.first()) {
                System.out.println(Constants.TICKETS_MISSING);
            } else {
                tickets.beforeFirst();
                while (tickets.next()) {
                    System.out.println(new Ticket(
                            tickets.getInt(1),
                            tickets.getInt(2),
                            tickets.getInt(3),
                            tickets.getInt(4),
                            tickets.getTimestamp(5),
                            tickets.getTimestamp(6),
                            tickets.getString(7)
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
        }
    }

    public void printTicketsWithMovieID(int movieID) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT ticket.ticket_id, ticket.row, ticket.place, ticket.cost, " +
                            "session.start_time, session.end_time, movie.title " +
                            "FROM ticket, session, movie " +
                            "WHERE ticket.session_id = session.session_id " +
                            "AND session.movie_id = movie.movie_id " +
                            "AND movie.movie_id = ? " +
                            "AND ticket.user_id IS NULL");
            stmt.setInt(1, movieID);
            ResultSet tickets = stmt.executeQuery();
            if (!tickets.first()) {
                System.out.println(Constants.TICKETS_MISSING);
            } else {
                tickets.beforeFirst();
                while (tickets.next()) {
                    System.out.println(new Ticket(
                            tickets.getInt(1),
                            tickets.getInt(2),
                            tickets.getInt(3),
                            tickets.getInt(4),
                            tickets.getTimestamp(5),
                            tickets.getTimestamp(6),
                            tickets.getString(7)
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
        }
    }

    public boolean checkTicketAvailability(int ticketID) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM ticket WHERE ticket_id = ?");
            stmt.setInt(1, ticketID);
            return stmt.executeQuery().first();
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            return false;
        }
    }

    public void buyTicket(int ticketID, int userID) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE ticket SET user_id = ? WHERE ticket_id = ?");
            stmt.setInt(1, userID);
            stmt.setInt(2, ticketID);
            stmt.execute();
            stmt = connection.prepareStatement(
                    "SELECT cost FROM ticket WHERE ticket_id = ?");
            stmt.setInt(1, ticketID);
            ResultSet ticket = stmt.executeQuery();
            ticket.first();
            System.out.println(Constants.BUY_TICKET_SUCCESS +
                    ticket.getInt(1) +
                    " рублей.");
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
        }
    }

    public void printUserTickets(int userID) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT ticket.ticket_id, ticket.row, ticket.place, ticket.cost, " +
                            "session.start_time, session.end_time, movie.title " +
                            "FROM ticket, session, movie " +
                            "WHERE ticket.session_id = session.session_id " +
                            "AND session.movie_id = movie.movie_id " +
                            "AND ticket.user_id = ?");
            stmt.setInt(1, userID);
            ResultSet tickets = stmt.executeQuery();
            if (!tickets.first()) {
                System.out.println(Constants.TICKET_MISSING);
            } else {
                tickets.beforeFirst();
                while (tickets.next()) {
                    System.out.println(new Ticket(
                            tickets.getInt(1),
                            tickets.getInt(2),
                            tickets.getInt(3),
                            tickets.getInt(4),
                            tickets.getTimestamp(5),
                            tickets.getTimestamp(6),
                            tickets.getString(7)
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
        }
    }

    public void returnTicket(int ticketID, int userID) {
        try (Connection connection = ConnectionManager.open()) {
            if (!checkingSessionStart(connection, ticketID)) {
                PreparedStatement stmt = connection.prepareStatement(
                        "UPDATE ticket SET user_id = NULL " +
                                "WHERE user_id = ? " +
                                "AND ticket_id = ?");
                stmt.setInt(1, userID);
                stmt.setInt(2, ticketID);
                if (!stmt.execute() && stmt.getUpdateCount() == 1) {
                    stmt = connection.prepareStatement(
                            "SELECT cost FROM ticket WHERE ticket_id = ?");
                    stmt.setInt(1, ticketID);
                    ResultSet ticket = stmt.executeQuery();
                    ticket.first();
                    System.out.println(Constants.RETURN_TICKET_SUCCESS +
                            ticket.getInt(1) +
                            " рублей.");
                } else {
                    System.out.println(Constants.RETURN_TICKET_FAILED);
                }
            }
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
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
        return tickets.getTimestamp(1)
                .toLocalDateTime()
                .isBefore(LocalDateTime.now());
    }
}