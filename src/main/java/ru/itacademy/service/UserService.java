package ru.itacademy.service;

import ru.itacademy.model.User;

import java.sql.SQLException;

public interface UserService {

    boolean checkUserAvailability(String login, String password);

    int establishUserStatus(User user);

    User getUser();

    boolean deleteAccount(User user);

    String inputLogin();

    String inputPassword();

    boolean checkDataCorrectness(String data);

    boolean createUser(String login, String password);

    void printUsers();

    boolean checkUserAvailability(int userID) throws SQLException;
}