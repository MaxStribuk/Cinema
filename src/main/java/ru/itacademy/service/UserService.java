package ru.itacademy.service;

import java.sql.SQLException;

public interface UserService {

    //User getUser(String login);

    boolean checkLoginAvailability(String login) throws SQLException;

    boolean checkDataCorrectness(String data);

    boolean createUser(String login, String password);
}
