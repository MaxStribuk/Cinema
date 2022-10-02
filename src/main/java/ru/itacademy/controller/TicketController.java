package ru.itacademy.controller;

import ru.itacademy.service.MovieService;
import ru.itacademy.service.MovieServiceImpl;
import ru.itacademy.service.SessionService;
import ru.itacademy.service.SessionServiceImpl;
import ru.itacademy.service.TicketService;
import ru.itacademy.service.TicketServiceImpl;
import ru.itacademy.util.Constants;

import java.sql.SQLException;
import java.sql.Timestamp;

public class TicketController {

    TicketService ticketService = new TicketServiceImpl();
    SessionService sessionService = new SessionServiceImpl();
    MovieService movieService = new MovieServiceImpl();

    public void createTicketsForSession(Timestamp startTime) {
        ticketService.createTicketsForSession(startTime);
    }

    public void printTicketMenuWithSessionID() {
        while (true) {
            try {
                System.out.println(Constants.SESSION_MENU);
                int sessionID = Integer.parseInt(Menu.in.nextLine());
                if (sessionID == 0) {
                    return;
                }
                if (sessionService.checkSessionIDCorrectness(sessionID)) {
                    ticketService.printTicketsWithSessionID(sessionID);
                    return;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INCORRECT_SESSION_ID);
            } catch (SQLException e) {
                System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            }
        }
    }

    public void printTicketMenuWithMovieID() {
        System.out.println(Constants.MOVIE_MENU);
        while (true) {
            try {
                int movieID = Integer.parseInt(Menu.in.nextLine());
                if (movieID == 0) {
                    return;
                }
                if (movieService.checkMovieAvailability(movieID)) {
                    ticketService.printTicketsWithMovieID(movieID);
                    return;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INCORRECT_MOVIE_ID);
            }
        }
    }

    public void buyTicket(int userID) {
        System.out.println(Constants.TICKET_MENU_BUY);
        while (true) {
            try {
                int ticketID = Integer.parseInt(Menu.in.nextLine());
                if (ticketID == 0) {
                    return;
                }
                if (ticketService.checkTicketAvailability(ticketID)) {
                    ticketService.buyTicket(ticketID, userID);
                    return;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INCORRECT_TICKET_ID);
            }
        }
    }

    public void printUserTickets(int userID) {
        ticketService.printUserTickets(userID);
    }

    public void returnTicket(int userID) {
        while (true) {
            System.out.println(Constants.TICKET_MENU_RETURN);
            try {
                int ticketID = Integer.parseInt(Menu.in.nextLine());
                if (ticketID == 0) {
                    return;
                }
                if (ticketService.checkTicketAvailability(ticketID)) {
                    ticketService.returnTicket(ticketID, userID);
                    return;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INCORRECT_TICKET_ID);
            }
        }
    }
}