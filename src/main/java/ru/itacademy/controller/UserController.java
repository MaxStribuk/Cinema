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

    void logApp() {
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

    private void selectMenu() {
        int userStatus = Service.userService.establishUserStatus(Service.userService.getUser());
        switch (userStatus) {
            case 0 -> System.out.println(Constants.USER_BLOCKED);
            case 1 -> printUserMenu();
            case 2 -> printManagerMenu();
            case 3 -> printAdminMenu();
            default -> {
            }
        }
    }

    private void printAdminMenu() {
        while (true) {
            System.out.print(Constants.MENU_USER);
            System.out.print(Constants.MENU_MANAGER);
            System.out.println(Constants.MENU_ADMIN);
            String input = Menu.in.nextLine();
            switch (input) {
                case "1" -> {
                    if (sessionController.printFutureSessions()) {
                        ticketController.printTicketMenuWithSessionID();
                    }
                }
                case "2" -> {
                    if (movieController.printAllMovies()) {
                        ticketController.printTicketMenuWithMovieID();
                    }
                }
                case "3" -> ticketController.buyTicket(Service.userService.getUser().getID());
                case "4" -> ticketController.printUserTickets(Service.userService.getUser().getID());
                case "5" -> ticketController.returnTicket(Service.userService.getUser().getID());
                case "6" -> {
                    return;
                }
                case "7" -> {
                    if (Service.userService.deleteAccount(Service.userService.getUser())) {
                        System.out.println(Constants.USER_DELETED);
                        return;
                    }
                }
                case "8" -> movieController.updateMovie();
                case "9" -> sessionController.updateSession();
                case "10" -> Service.userService.printUsers();
                case "11" -> ticketController.buyUserTicket();
                case "12" -> registerUser();
                case "13" -> updateUser();
                case "14" -> movieController.createMovie();
                case "15" -> sessionController.createSession();
                case "16" -> sessionController.printAllSessions();
                case "17" -> sessionController.removeSession();
                case "18" -> movieController.removeMovie();
                case "0" -> {
                    System.out.println(Constants.FAREWELL);
                    System.exit(0);
                }
                default -> System.out.println(Constants.INVALID_INPUT);
            }
        }
    }

    private void updateUser() {
        System.out.println(Constants.UPDATE_USER);
        while (true) {
            try {
                int userID = Integer.parseInt(Menu.in.nextLine());
                if (userID == 0) {
                    return;
                }
                if (Service.userService.checkUserAvailability(userID)) {
                    if (Service.userService.getUser().getID() != userID) {
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
            } catch (InvalidUserException e) {
                System.out.println(Constants.INCORRECT_USER);
                return;
            }
        }
    }

    private void updateUser(int userID) {
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
            } catch (SQLException e) {
                System.out.println(Constants.FAILED_CONNECTION_DATABASE);
                return;
            }
        }
    }

    private void printManagerMenu() {
        while (true) {
            System.out.print(Constants.MENU_USER);
            System.out.println(Constants.MENU_MANAGER);
            String input = Menu.in.nextLine();
            switch (input) {
                case "1" -> {
                    if (sessionController.printFutureSessions()) {
                        ticketController.printTicketMenuWithSessionID();
                    }
                }
                case "2" -> {
                    if (movieController.printAllMovies()) {
                        ticketController.printTicketMenuWithMovieID();
                    }
                }
                case "3" -> ticketController.buyTicket(Service.userService.getUser().getID());
                case "4" -> ticketController.printUserTickets(Service.userService.getUser().getID());
                case "5" -> ticketController.returnTicket(Service.userService.getUser().getID());
                case "6" -> {
                    return;
                }
                case "7" -> {
                    if (Service.userService.deleteAccount(Service.userService.getUser())) {
                        System.out.println(Constants.USER_DELETED);
                        return;
                    }
                }
                case "8" -> movieController.updateMovie();
                case "9" -> sessionController.updateSession();
                case "10" -> Service.userService.printUsers();
                case "11" -> ticketController.buyUserTicket();
                case "0" -> {
                    System.out.println(Constants.FAREWELL);
                    System.exit(0);
                }
                default -> System.out.println(Constants.INVALID_INPUT);
            }
        }
    }

    private void printUserMenu() {
        while (true) {
            System.out.println(Constants.MENU_USER);
            String input = Menu.in.nextLine();
            switch (input) {
                case "1" -> {
                    if (sessionController.printFutureSessions()) {
                        ticketController.printTicketMenuWithSessionID();
                    }
                }
                case "2" -> {
                    if (movieController.printAllMovies()) {
                        ticketController.printTicketMenuWithMovieID();
                    }
                }
                case "3" -> ticketController.buyTicket(Service.userService.getUser().getID());
                case "4" -> ticketController.printUserTickets(Service.userService.getUser().getID());
                case "5" -> ticketController.returnTicket(Service.userService.getUser().getID());
                case "6" -> {
                    return;
                }
                case "7" -> {
                    if (Service.userService.deleteAccount(Service.userService.getUser())) {
                        System.out.println(Constants.USER_DELETED);
                        return;
                    }
                }
                case "0" -> {
                    System.out.println(Constants.FAREWELL);
                    System.exit(0);
                }
                default -> System.out.println(Constants.INVALID_INPUT);
            }
        }
    }

    public void registerUser() {
        System.out.println(Constants.CREATING_USER_LOGIN);
        String login = Service.userService.inputLogin();
        if (login.equals("0")) {
            return;
        }
        System.out.println(Constants.CREATING_USER_PASSWORD);
        String password = Service.userService.inputPassword();
        if (password.equals("0")) {
            return;
        }
        try {
            password = Service.userService.getHashGenerator().createSHAHash(password);
            System.out.println(Service.userService.createUser(login, password)
                    ? Constants.SUCCESSFUL_REGISTRATION_USER
                    : Constants.FAILED_REGISTRATION_USER);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(Constants.INVALID_HASH_ALGORITHM);
            System.out.println(Constants.FAILED_REGISTRATION_USER);
        }
    }
}