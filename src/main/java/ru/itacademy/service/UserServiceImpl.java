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
        return userRepository.checkAvailabilityLogin(login);
    }

    @Override
    public boolean checkDataCorrectness(String data) {
        return data.matches("[a-zA-Z0-9]{5,32}");
    }

    @Override
    public boolean createUser(String login, String password) {
        return userRepository.createUser(login, password);
    }

    @Override
    public void printUsers() {
        userRepository.printUsers();
    }

    @Override
    public boolean checkUserAvailability(int userID) throws SQLException {
        return userRepository.checkAvailabilityUser(userID);
    }

    @Override
    public void updateRole(int userID) throws SQLException {
        String role = inputRole();
        if (role.equals("0")) {
            return;
        }
        System.out.println(userRepository.updateRole(userID, role)
                ? Constants.SUCCESSFUL_UPDATE_USER
                : Constants.FAILED_UPDATE_USER);
    }

    @Override
    public void updateStatus(int userID) throws SQLException {
        String status = inputStatus();
        if (status.equals("0")) {
            return;
        }
        System.out.println(userRepository.updateStatus(userID, status)
                ? Constants.SUCCESSFUL_UPDATE_USER
                : Constants.FAILED_UPDATE_USER);
    }

    private String inputStatus() {
        int status;
        while (true) {
            System.out.println(Constants.MENU_USER_UPDATE_STATUS);
            try {
                status = Integer.parseInt(Menu.in.nextLine());
                switch (status) {
                    case 0 -> {
                        return "0";
                    }
                    case 1 -> {
                        return "active";
                    }
                    case 2 -> {
                        return "blocked";
                    }
                    case 3 -> {
                        return "deleted";
                    }
                    default -> throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_INPUT);
            }
        }
    }

    private String inputRole() {
        int role;
        while (true) {
            System.out.println(Constants.MENU_USER_UPDATE_ROLE);
            try {
                role = Integer.parseInt(Menu.in.nextLine());
                switch (role) {
                    case 0 -> {
                        return "0";
                    }
                    case 1 -> {
                        return "user";
                    }
                    case 2 -> {
                        return "manager";
                    }
                    case 3 -> {
                        return "admin";
                    }
                    default -> throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_INPUT);
            }
        }
    }

        private User getUser (String login, String password) throws SQLException, InvalidUserException {
            return userRepository.getUser(login, password);
        }

        @Override
        public int establishUserStatus (User user){
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
        public boolean deleteAccount (User user){
            return userRepository.deleteUserAccount(user);
        }

        @Override
        public boolean checkUserAvailability (String login, String password){
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
        public String inputLogin () {
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
        public String inputPassword () {
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
        public User getUser () {
            return user;
        }
    }