package ru.itacademy.controller;

import ru.itacademy.service.Service;
import ru.itacademy.util.Constants;
import ru.itacademy.util.Exceptions.InvalidUserException;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserController {

    private final SessionController sessionController = new SessionController();
    private final MovieController movieController = new MovieController();
    private final TicketController ticketController = new TicketController();

    public void registerUser() {
        try {
            String login = Service.userService.inputLogin();
            if (login.equals("0")) {
                return;
            }
            String password = Service.userService.inputPassword();
            if (password.equals("0")) {
                return;
            }
            password = Service.userService.getHashGenerator().createSHAHash(password);
            System.out.println(Service.userService.createUser(login, password)
                    ? Constants.SUCCESSFUL_REGISTRATION_USER
                    : Constants.FAILED_REGISTRATION_USER);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(Constants.INVALID_HASH_ALGORITHM);
            System.out.println(Constants.FAILED_REGISTRATION_USER);
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
        }
    }

    public void logApp() {
        while (true) {
            System.out.println(Constants.INPUT_LOGIN);
            String login = Menu.in.nextLine();
            if (login.equals("0")) {
                return;
            }
            System.out.println(Constants.INPUT_PASSWORD);
            String password = Menu.in.nextLine();
            if (password.equals("0")) {
                return;
            }
            if (Service.userService.checkUserAvailability(login, password)) {
                selectMenu();
            }
        }
    }

    private void printUserMenu() {
        while (true) {
            System.out.println(Constants.MENU_USER);
            String input = Menu.in.nextLine();
            switch (input) {
                case "0", "1", "2", "3", "4", "5" -> selectOperation(input);
                case "6" -> {
                    return;
                }
                case "7" -> {
                    if (Service.userService.removeUser(Service.userService.getCurrentUser())) {
                        System.out.println(Constants.USER_DELETED);
                        return;
                    }
                }
                default -> System.out.println(Constants.INVALID_INPUT);
            }
        }
    }

    private void selectMenu() {
        if (Service.userService.getCurrentUser().getStatus().equals("active")) {
            switch (Service.userService.getCurrentUser().getRole()) {
                case "user" -> printUserMenu();
                case "manager" -> printManagerMenu();
                case "admin" -> printAdminMenu();
                default -> {
                }
            }
        } else {
            System.out.println(Constants.USER_BLOCKED);
        }
    }

    private void selectOperation(String input) {
        switch (input) {
            case "1" -> {
                if (sessionController.printSessions(
                        Service.sessionService::printSessions, false)) {
                    ticketController.printTickets(Constants.MENU_SESSION,
                            Service.sessionService::checkSessionIDCorrectness,
                            "sessionID", Constants.INVALID_SESSION_ID
                    );
                }
            }
            case "2" -> {
                if (movieController.printMovies()) {
                    ticketController.printTickets(Constants.MENU_MOVIE,
                            Service.movieService::checkMovieAvailability,
                            "movieID", Constants.INVALID_MOVIE_ID
                    );
                }
            }
            case "3" -> ticketController.updateTicket(
                    Service.userService.getCurrentUser().getID(),
                    Constants.MENU_TICKET_BUY,
                    Service.ticketService::updateTicket, true
            );
            case "4" -> ticketController.printUserTickets(Service.userService.getCurrentUser().getID());
            case "5" -> ticketController.updateTicket(
                    Service.userService.getCurrentUser().getID(),
                    Constants.MENU_TICKET_RETURN,
                    Service.ticketService::updateTicket, false
            );
            case "0" -> {
                System.out.println(Constants.FAREWELL);
                System.exit(0);
            }
        }
    }

    private void printManagerMenu() {
        while (true) {
            System.out.print(Constants.MENU_USER);
            System.out.println(Constants.MENU_MANAGER);
            String input = Menu.in.nextLine();
            switch (input) {
                case "0", "1", "2", "3", "4", "5" -> selectOperation(input);
                case "6" -> {
                    return;
                }
                case "7" -> {
                    if (Service.userService.removeUser(Service.userService.getCurrentUser())) {
                        System.out.println(Constants.USER_DELETED);
                        return;
                    }
                }
                case "8", "9", "10", "11" -> selectSpecialOperation(input);
                default -> System.out.println(Constants.INVALID_INPUT);
            }
        }
    }

    private void selectSpecialOperation(String input) {
        switch (input) {
            case "8" -> movieController.changeMovie(Constants.UPDATE_MOVIE,
                    movieController::updateMovie
            );
            case "9" -> sessionController.changeSession(
                    Constants.UPDATE_SESSION,
                    sessionController::updateSession
            );
            case "10" -> Service.userService.printUsers();
            case "11" -> ticketController.buyUserTicket();
        }
    }

    private void printAdminMenu() {
        while (true) {
            System.out.print(Constants.MENU_USER);
            System.out.print(Constants.MENU_MANAGER);
            System.out.println(Constants.MENU_ADMIN);
            String input = Menu.in.nextLine();
            switch (input) {
                case "0", "1", "2", "3", "4", "5" -> selectOperation(input);
                case "6" -> {
                    return;
                }
                case "7" -> {
                    if (Service.userService.removeUser(Service.userService.getCurrentUser())) {
                        System.out.println(Constants.USER_DELETED);
                        return;
                    }
                }
                case "8", "9", "10", "11" -> selectSpecialOperation(input);
                case "12" -> registerUser();
                case "13" -> updateUser();
                case "14" -> movieController.createMovie();
                case "15" -> sessionController.createSession();
                case "16" -> sessionController.printSessions(
                        Service.sessionService::printSessions, true
                );
                case "17" -> sessionController.changeSession(
                        Constants.REMOVE_SESSION,
                        sessionController::removeSession
                );
                case "18" -> movieController.changeMovie(Constants.REMOVE_MOVIE,
                        movieController::removeMovie
                );
                default -> System.out.println(Constants.INVALID_INPUT);
            }
        }
    }

    private void updateUser() {
        while (true) {
            System.out.println(Constants.UPDATE_USER);
            try {
                int userID = Integer.parseInt(Menu.in.nextLine());
                if (userID == 0) {
                    return;
                }
                if (Service.userService.checkUserAvailability(userID)) {
                    if (Service.userService.getCurrentUser().getID() != userID) {
                        updateUser(userID);
                        return;
                    } else {
                        throw new InvalidUserException();
                    }
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_USER_ID);
            } catch (SQLException e) {
                System.out.println(Constants.FAILED_CONNECTION_DATABASE);
                return;
            } catch (InvalidUserException e) {
                System.out.println(Constants.INCORRECT_USER);
            }
        }
    }

    private void updateUser(int userID) throws SQLException {
        while (true) {
            System.out.println(Constants.MENU_USER_UPDATE);
            try {
                int input = Integer.parseInt(Menu.in.nextLine());
                switch (input) {
                    case 0 -> {
                        return;
                    }
                    case 1 -> {
                        Service.userService.updateRole(userID);
                        return;
                    }
                    case 2 -> {
                        Service.userService.updateStatus(userID);
                        return;
                    }
                    default -> throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_INPUT);
            }
        }
    }
}