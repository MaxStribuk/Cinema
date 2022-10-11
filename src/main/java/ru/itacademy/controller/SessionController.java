package ru.itacademy.controller;

import ru.itacademy.service.Service;
import ru.itacademy.util.Constants;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SessionController {

    public void createSession() {
        while (true) {
            try {
                int movieID = Service.sessionService.inputMovieID();
                if (movieID == 0) {
                    return;
                }
                Timestamp startTime = Service.sessionService.inputStartTime();
                if (Service.sessionService.createSession(movieID, startTime)) {
                    Service.ticketService.createTickets(startTime);
                    System.out.println(Constants.SUCCESSFUL_CREATE_SESSION);
                    return;
                }
            } catch (SQLException e) {
                System.out.println(Constants.FAILED_CONNECTION_DATABASE);
                return;
            }
        }
    }

    public boolean printSessions(Predicate<Boolean> task, boolean isAllSessions) {
        return task.test(isAllSessions);
    }

    public void changeSession(String message, Consumer<Integer> task) {
        while (true) {
            System.out.println(message);
            try {
                int session_ID = Integer.parseInt(Menu.in.nextLine());
                if (session_ID == 0) {
                    return;
                }
                if (Service.sessionService.checkSessionIDCorrectness(session_ID)) {
                    task.accept(session_ID);
                    return;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_SESSION_ID);
            }
        }
    }

    public void updateSession(int session_ID) {
        while (true) {
            System.out.println(Constants.MENU_SESSION_UPDATE);
            try {
                int input = Integer.parseInt(Menu.in.nextLine());
                switch (input) {
                    case 0 -> {
                        return;
                    }
                    case 1 -> {
                        Service.sessionService.updateSession(session_ID, false);
                        return;
                    }
                    case 2 -> {
                        Service.sessionService.updateSession(session_ID, true);
                        return;
                    }
                    default -> throw new NumberFormatException();

                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_INPUT);
            } catch (SQLException e) {
                System.out.println(Constants.FAILED_CONNECTION_DATABASE);
                return;
            }
        }
    }

    public void removeSession(int sessionID) {
        while (true) {
            System.out.println(Constants.MENU_SESSION_REMOVE);
            try {
                int input = Integer.parseInt(Menu.in.nextLine());
                switch (input) {
                    case 0 -> {
                        return;
                    }
                    case 1 -> {
                        Service.ticketService.removeTickets(sessionID);
                        Service.sessionService.removeSession(sessionID);
                        System.out.println(Constants.SUCCESSFUL_REMOVE_SESSION);
                        return;
                    }
                    default -> throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_INPUT);
            } catch (SQLException e) {
                System.out.println(Constants.FAILED_CONNECTION_DATABASE);
                return;
            }
        }
    }
}