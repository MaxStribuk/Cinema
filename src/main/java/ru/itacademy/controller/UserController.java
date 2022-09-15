package ru.itacademy.controller;

import ru.itacademy.model.User;
import ru.itacademy.service.UserService;
import ru.itacademy.service.UserServiceImpl;
import ru.itacademy.util.Constants;

import java.sql.SQLException;
import java.util.Scanner;

public class UserController {

    private final UserService userService = new UserServiceImpl();
    private User user;
    private final Scanner in = new Scanner(System.in);

    void logApp() {

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
                if (userService.checkDataCorrectness(login)) {
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
            if (userService.checkDataCorrectness(password)) {
                System.out.println(Constants.INCORRECT_PASSWORD);
            } else {
                return password;
            }
        }
    }
}
