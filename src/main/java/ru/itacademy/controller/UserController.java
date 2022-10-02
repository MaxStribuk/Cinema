package ru.itacademy.controller;

import ru.itacademy.service.UserService;
import ru.itacademy.service.UserServiceImpl;
import ru.itacademy.util.Constants;

public class UserController {

    private final UserService userService = new UserServiceImpl();
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
            if (userService.checkUserAvailability(login, password)) {
                selectMenu();
            }
        }
    }

    private void selectMenu() {
        int userStatus = userService.establishUserStatus(userService.getUser());
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
    }

    private void printManagerMenu() {
    }

    private void printUserMenu() {
        while (true) {
            System.out.println(Constants.USER_MENU);
            String input = Menu.in.nextLine();
            switch (input) {
                case "1" -> {
                    sessionController.printAllSessions();
                    ticketController.printTicketMenuWithSessionID();
                }
                case "2" -> {
                    movieController.printAllMovies();
                    ticketController.printTicketMenuWithMovieID();
                }
                case "3" -> ticketController.buyTicket(userService.getUser().getID());
                case "4" -> ticketController.printUserTickets(userService.getUser().getID());
                case "5" -> ticketController.returnTicket(userService.getUser().getID());
                case "6" -> {
                    return;
                }
                case "7" -> {
                    if (userService.deleteAccount(userService.getUser())) {
                        System.out.println(Constants.USER_DELETED);
                        return;
                    }
                }
                case "0" -> {
                    System.out.println(Constants.FAREWELL);
                    System.exit(0);
                }
                default -> System.out.println(Constants.INCORRECT_INPUT);
            }
        }
    }

    public void registerUser() {
        System.out.println(Constants.CREATING_LOGIN);
        String login = userService.inputLogin();
        if (login.equals("0")) {
            return;
        }
        System.out.println(Constants.CREATING_PASSWORD);
        String password = userService.inputPassword();
        if (password.equals("0")) {
            return;
        }
        if (userService.createUser(login, password)) {
            System.out.println(Constants.REGISTRATION_SUCCESSFUL);
        } else {
            System.out.println(Constants.REGISTRATION_FAILED);
        }
    }
}