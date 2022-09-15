package ru.itacademy.controller;

import ru.itacademy.model.User;
import ru.itacademy.service.UserService;
import ru.itacademy.service.UserServiceImpl;
import ru.itacademy.util.Constants;
import ru.itacademy.util.InvalidUserException;

import java.sql.SQLException;
import java.util.Scanner;

public class UserController {

    private final UserService userService = new UserServiceImpl();
    private User user;
    private int userStatus;
    private final Scanner in = new Scanner(System.in);

    void logApp() {
        boolean userHasRegistered;
        while (true) {
            System.out.println(Constants.INPUT_LOGIN);
            String login = in.nextLine();
            if (login.equals("0")) {
                return;
            }
            System.out.println(Constants.INPUT_PASSWORD);
            String password = in.nextLine();
            if (password.equals("0")) {
                return;
            }
            userHasRegistered = checkUserAvailability(login, password);
            selectMenu(userHasRegistered);
        }
    }

    private void selectMenu(Boolean userHasRegistered) {
        if (userHasRegistered) {
            userStatus = userService.establishUserStatus(user);
            switch (userStatus) {
                case 0 -> System.out.println(Constants.USER_BLOCKED);
                case 1 -> printUserMenu();
                case 2 -> printManagerMenu();
                case 3 -> printAdminMenu();
                default -> {}
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
            String input = in.nextLine();
            switch (input) {
                case "1" -> System.out.println("для просмотра доступных сеансов");
                case "2" -> System.out.println("для просмотра репертуара фильмов");
                case "3" -> System.out.println("для просмотра приобретенных билетов");
                case "4" -> System.out.println("для возврата приобретенного билета");
                case "5" -> System.out.println("для отмены авторизации");
                case "6" -> System.out.println("для удаления аккаунта");
                case "0" -> {
                    System.out.println(Constants.FAREWELL);
                    System.exit(0);
                }
                default -> System.out.println(Constants.INCORRECT_INPUT);
            }
        }
    }

    private boolean checkUserAvailability(String login, String password) {
        try {
            if (userService.checkDataCorrectness(login)
                    && userService.checkDataCorrectness(password)
                    && !userService.checkLoginAvailability(login)) {
                user = userService.getUser(login, password);
                return true;
            } else {
                System.out.println(Constants.FAILED_AUTHORIZATION);
                return false;
            }
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            return false;
        } catch (InvalidUserException e) {
            System.out.println(Constants.INVALID_USER);
            return false;
        }
    }

    void registerUser() {
        System.out.println(Constants.CREATING_LOGIN);
        String login = inputLogin();
        if (login.equals("0")) {
            return;
        }
        System.out.println(Constants.CREATING_PASSWORD);
        String password = inputPassword();
        if (password.equals("0")) {
            return;
        }
        if (userService.createUser(login, password)) {
            System.out.println(Constants.REGISTRATION_SUCCESSFUL);
        } else {
            System.out.println(Constants.REGISTRATION_FAILED);
        }
    }

    private String inputLogin() {
        String login;
        while (true) {
            login = in.nextLine();
            if (login.equals("0")) {
                return "0";
            }
            try {
                if (!userService.checkDataCorrectness(login)) {
                    System.out.println(Constants.INCORRECT_LOGIN);
                } else if (!userService.checkLoginAvailability(login)) {
                    System.out.println(Constants.LOGIN_IS_BUSY);
                } else {
                    return login;
                }
            } catch (SQLException e) {
                System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            }
        }
    }

    private String inputPassword() {
        String password;
        while (true) {
            password = in.nextLine();
            if (password.equals("0")) {
                return "0";
            }
            if (!userService.checkDataCorrectness(password)) {
                System.out.println(Constants.INCORRECT_PASSWORD);
            } else {
                return password;
            }
        }
    }
}
