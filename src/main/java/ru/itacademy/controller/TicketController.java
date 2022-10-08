package ru.itacademy.controller;

import ru.itacademy.service.Service;
import ru.itacademy.service.TicketService;
import ru.itacademy.service.TicketServiceImpl;
import ru.itacademy.util.Constants;

import java.sql.SQLException;
import java.sql.Timestamp;

public class TicketController {

    TicketService ticketService = new TicketServiceImpl();

    public void createTicketsForSession(Timestamp startTime) {
        ticketService.createTicketsForSession(startTime);
    }

    public void printTicketMenuWithSessionID() {
        while (true) {
            try {
                System.out.println(Constants.MENU_SESSION);
                int sessionID = Integer.parseInt(Menu.in.nextLine());
                if (sessionID == 0) {
                    return;
                }
                if (Service.sessionService.checkSessionIDCorrectness(sessionID)) {
                    ticketService.printTicketsWithSessionID(sessionID);
                    return;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_SESSION_ID);
            } catch (SQLException e) {
                System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            }
        }
    }

    public void printTicketMenuWithMovieID() {
        System.out.println(Constants.MENU_MOVIE);
        while (true) {
            try {
                int movieID = Integer.parseInt(Menu.in.nextLine());
                if (movieID == 0) {
                    return;
                }
                if (Service.movieService.checkMovieAvailability(movieID)) {
                    Service.ticketService.printTicketsWithMovieID(movieID);
                    return;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_MOVIE_ID);
            }
        }
    }

    public void buyTicket(int userID) {
        while (true) {
            System.out.println(Constants.MENU_TICKET_BUY);
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
                System.out.println(Constants.INVALID_TICKET_ID);
            }
        }
    }

    public void printUserTickets(int userID) {
        ticketService.printUserTickets(userID);
    }

    public void returnTicket(int userID) {
        while (true) {
            System.out.println(Constants.MENU_TICKET_RETURN);
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
                System.out.println(Constants.INVALID_TICKET_ID);
            }
        }
    }

    public void buyUserTicket() {
        while (true) {
            System.out.println(Constants.MENU_TICKET_USER_BUY);
            try {
                int userID = Integer.parseInt(Menu.in.nextLine());
                if (userID == 0) {
                    return;
                }
                if (Service.userService.checkUserAvailability(userID)) {
                    if (ticketService.printUserTickets(userID)) {
                        returnTicket(userID);
                    }
                    return;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_USER_ID);
            } catch (SQLException e) {
                System.out.println(Constants.FAILED_CONNECTION_DATABASE);
                return;
            }
        }
    }
}