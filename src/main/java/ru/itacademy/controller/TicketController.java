package ru.itacademy.controller;

import ru.itacademy.service.Service;
import ru.itacademy.util.Constants;
import ru.itacademy.util.TernaryConsumer;

import java.sql.SQLException;
import java.util.function.Predicate;

public class TicketController {

    public void printTickets(String menu, Predicate<Integer> check, String column, String invalidMessage) {
        while (true) {
            try {
                System.out.println(menu);
                int id = Integer.parseInt(Menu.in.nextLine());
                if (id == 0) {
                    return;
                }
                if (check.test(id)) {
                    Service.ticketService.printTickets(id, column);
                    return;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(invalidMessage);
            } catch (SQLException e) {
                System.out.println(Constants.FAILED_CONNECTION_DATABASE);
                return;
            }
        }
    }

    public void printUserTickets(int userID) {
        try {
            Service.ticketService.printTickets(userID, "userID");
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
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
                    if (Service.ticketService.printTickets(userID, "userID")) {
                        updateTicket(userID, Constants.MENU_TICKET_RETURN,
                                Service.ticketService::updateTicket, false
                        );
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

    public void updateTicket(int userID, String message,
                             TernaryConsumer<Integer, Integer, Boolean> task, boolean isBuyTicket) {
        while (true) {
            System.out.println(message);
            try {
                int ticketID = Integer.parseInt(Menu.in.nextLine());
                if (ticketID == 0) {
                    return;
                }
                if (Service.ticketService.checkTicketAvailability(ticketID)) {
                    task.accept(ticketID, userID, isBuyTicket);
                    return;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_TICKET_ID);
            } catch (SQLException e) {
                System.out.println(Constants.FAILED_CONNECTION_DATABASE);
                return;
            }
        }
    }
}