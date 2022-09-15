package ru.itacademy.service;

import ru.itacademy.model.User;
import ru.itacademy.util.InvalidUserException;

import java.sql.SQLException;

public interface UserService {

    //User getUser(String login);

    boolean checkLoginAvailability(String login) throws SQLException;

    boolean checkDataCorrectness(String data);

    boolean createUser(String login, String password);

    User getUser(String login, String password) throws SQLException, InvalidUserException;

    int establishUserStatus(User user);
}
