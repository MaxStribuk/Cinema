package ru.itacademy.service;

import ru.itacademy.model.User;
import ru.itacademy.util.HashGenerator;

import java.sql.SQLException;

public interface UserService {

    void printUsers();

    String inputLogin() throws SQLException;

    String inputPassword();

    boolean createUser(String login, String password) throws SQLException;

    User getCurrentUser();

    HashGenerator getHashGenerator();

    boolean checkUserAvailability(int userID) throws SQLException;

    boolean checkUserAvailability(String login, String password);

    boolean checkDataCorrectness(String data);

    void updateRole(int userID) throws SQLException;

    void updateStatus(int userID) throws SQLException;

    boolean removeUser(User user);
}