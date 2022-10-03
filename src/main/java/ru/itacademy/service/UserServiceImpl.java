package ru.itacademy.service;

import ru.itacademy.controller.Menu;
import ru.itacademy.model.User;
import ru.itacademy.repository.UserRepository;
import ru.itacademy.util.Constants;
import ru.itacademy.util.Exceptions.InvalidUserException;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository = new UserRepository();
    private User user;

    private boolean checkLoginAvailability(String login) throws SQLException {
        return userRepository.checkLoginAvailability(login);
    }

    @Override
    public boolean checkDataCorrectness(String data) {
        return data.matches("[a-zA-Z0-9]{5,32}");
    }

    @Override
    public boolean createUser(String login, String password) {
        return userRepository.create(login, password);
    }

    private User getUser(String login, String password) throws SQLException, InvalidUserException {
        return userRepository.getUser(login, password);
    }

    @Override
    public int establishUserStatus(User user) {
        if (!user.getStatus().equals("active")) {
            return 0;
        } else
            switch (user.getRole()) {
                case "user" -> {
                    return 1;
                }
                case "manager" -> {
                    return 2;
                }
                case "admin" -> {
                    return 3;
                }
                default -> {
                    return 0;
                }
            }
    }

    @Override
    public boolean deleteAccount(User user) {
        return userRepository.deleteAccount(user);
    }

    @Override
    public boolean checkUserAvailability(String login, String password) {
        try {
            if (checkDataCorrectness(login)
                    && checkDataCorrectness(password)) {
                user = getUser(login, password);
                return true;
            } else {
                System.out.println(Constants.FAILED_AUTHORIZATION_USER);
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

    @Override
    public String inputLogin() {
        String login;
        while (true) {
            login = Menu.in.nextLine();
            if (login.equals("0")) {
                return "0";
            }
            try {
                if (!checkDataCorrectness(login)) {
                    System.out.println(Constants.INVALID_USER_LOGIN);
                } else if (!checkLoginAvailability(login)) {
                    System.out.println(Constants.LOGIN_IS_BUSY);
                } else {
                    return login;
                }
            } catch (SQLException e) {
                System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            }
        }
    }

    @Override
    public String inputPassword() {
        String password;
        while (true) {
            password = Menu.in.nextLine();
            if (password.equals("0")) {
                return "0";
            }
            if (!checkDataCorrectness(password)) {
                System.out.println(Constants.INVALID_USER_PASSWORD);
            } else {
                return password;
            }
        }
    }

    @Override
    public User getUser() {
        return user;
    }
}