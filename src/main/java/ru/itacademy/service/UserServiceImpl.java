package ru.itacademy.service;

import ru.itacademy.controller.Menu;
import ru.itacademy.model.User;
import ru.itacademy.repository.UserRepository;
import ru.itacademy.util.Constants;
import ru.itacademy.util.Exceptions.InvalidUserException;
import ru.itacademy.util.HashGenerator;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository = new UserRepository();
    private User currentUser;
    private final HashGenerator hashGenerator = new HashGenerator();

    @Override
    public boolean checkDataCorrectness(String data) {
        return data.matches("[a-zA-Z0-9]{5,32}");
    }

    @Override
    public boolean checkUserAvailability(int userID) throws SQLException {
        return userRepository.checkAvailabilityUser(userID);
    }

    @Override
    public boolean createUser(String login, String password) throws SQLException {
        return userRepository.createUser(login, password);
    }

    @Override
    public void printUsers() {
        try {
            userRepository.printUsers();
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
        }
    }

    @Override
    public void updateRole(int userID) throws SQLException {
        String role = inputUserData(Constants.MENU_USER_UPDATE_ROLE,
                "user", "manager", "admin"
        );
        if (role.equals("0")) {
            return;
        }
        System.out.println(userRepository.updateRole(userID, role)
                ? Constants.SUCCESSFUL_UPDATE_USER
                : Constants.FAILED_UPDATE_USER);
    }

    @Override
    public void updateStatus(int userID) throws SQLException {
        String status = inputUserData(Constants.MENU_USER_UPDATE_STATUS,
                "active", "blocked", "deleted"
        );
        if (status.equals("0")) {
            return;
        }
        System.out.println(userRepository.updateStatus(userID, status)
                ? Constants.SUCCESSFUL_UPDATE_USER
                : Constants.FAILED_UPDATE_USER);
    }

    @Override
    public boolean removeUser(User user) {
        try {
            return userRepository.removeUser(user);
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            return false;
        }
    }

    @Override
    public boolean checkUserAvailability(String login, String password) {
        try {
            if (checkDataCorrectness(login)
                    && checkDataCorrectness(password)) {
                currentUser = userRepository.getUser(
                        login, hashGenerator.createSHAHash(password));
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
        } catch (NoSuchAlgorithmException e) {
            System.out.println(Constants.INVALID_HASH_ALGORITHM);
            return false;
        }
    }

    @Override
    public String inputLogin() throws SQLException {
        System.out.println(Constants.CREATING_USER_LOGIN);
        String login;
        while (true) {
            login = Menu.in.nextLine();
            if (login.equals("0")) {
                return "0";
            }
            if (!checkDataCorrectness(login)) {
                System.out.println(Constants.INVALID_USER_LOGIN);
            } else if (!userRepository.checkAvailabilityUser(login)) {
                System.out.println(Constants.LOGIN_IS_BUSY);
            } else {
                return login;
            }
        }
    }

    @Override
    public String inputPassword() {
        System.out.println(Constants.CREATING_USER_PASSWORD);
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
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public HashGenerator getHashGenerator() {
        return hashGenerator;
    }

    private String inputUserData(String menu, String firstUserData,
                                 String secondUserData, String thirdUserData) {
        int userData;
        while (true) {
            System.out.println(menu);
            try {
                userData = Integer.parseInt(Menu.in.nextLine());
                switch (userData) {
                    case 0 -> {
                        return "0";
                    }
                    case 1 -> {
                        return firstUserData;
                    }
                    case 2 -> {
                        return secondUserData;
                    }
                    case 3 -> {
                        return thirdUserData;
                    }
                    default -> throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_INPUT);
            }
        }
    }
}