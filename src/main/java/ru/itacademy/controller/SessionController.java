package ru.itacademy.controller;

import ru.itacademy.service.Service;
import ru.itacademy.util.Constants;

import java.sql.SQLException;
import java.sql.Timestamp;

public class SessionController {

    TicketController ticketController = new TicketController();

    public void createSession() {
        int movieID = Service.sessionService.inputMovieID();
        if (movieID == 0) {
            return;
        }
        Timestamp startTime = Service.sessionService.inputStartTime();
        if (Service.sessionService.createSession(movieID, startTime)) {
            System.out.println(Constants.SUCCESSFUL_CREATE_SESSION);
            ticketController.createTicketsForSession(startTime);
        }
    }

        public boolean printFutureSessions () {
            return Service.sessionService.printFutureSessions();
        }

        public void updateSession () {
            while (true) {
                System.out.println(Constants.UPDATE_SESSION);
                try {
                    int session_ID = Integer.parseInt(Menu.in.nextLine());
                    if (session_ID == 0) {
                        return;
                    }
                    if (Service.sessionService.checkSessionIDCorrectness(session_ID)) {
                        updateSession(session_ID);
                        return;
                    } else {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    System.out.println(Constants.INVALID_SESSION_ID);
                } catch (SQLException e) {
                    System.out.println(Constants.FAILED_CONNECTION_DATABASE);
                    return;
                }
            }
        }

        private void updateSession ( int session_ID){
            while (true) {
                System.out.println(Constants.MENU_SESSION_UPDATE);
                try {
                    int input = Integer.parseInt(Menu.in.nextLine());
                    switch (input) {
                        case 0 -> {
                            return;
                        }
                        case 1 -> {
                            Service.sessionService.updateStartTime(session_ID);
                            return;
                        }
                        case 2 -> {
                            Service.sessionService.updateMovieID(session_ID);
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

        public void removeSession () {
            while (true) {
                System.out.println(Constants.REMOVE_SESSION);
                try {
                    int sessionID = Integer.parseInt(Menu.in.nextLine());
                    if (sessionID == 0) {
                        return;
                    }
                    if (Service.sessionService.checkSessionIDCorrectness(sessionID)) {
                        removeSession(sessionID);
                        return;
                    } else {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    System.out.println(Constants.INVALID_SESSION_ID);
                } catch (SQLException e) {
                    System.out.println(Constants.FAILED_CONNECTION_DATABASE);
                    return;
                }
            }
        }

        private void removeSession ( int sessionID){
            while (true) {
                System.out.println(Constants.MENU_SESSION_REMOVE);
                try {
                    int input = Integer.parseInt(Menu.in.nextLine());
                    switch (input) {
                        case 0 -> {
                            return;
                        }
                        case 1 -> {
                            Service.ticketService.removeTicketsForSession(sessionID);
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

        public void printAllSessions () {
            Service.sessionService.printAllSessions();
        }
    }