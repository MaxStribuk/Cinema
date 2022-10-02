package ru.itacademy.repository;

import ru.itacademy.model.User;
import ru.itacademy.util.ConnectionManager;
import ru.itacademy.util.Constants;
import ru.itacademy.util.Exceptions.InvalidUserException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public boolean checkLoginAvailability(String login) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM user WHERE login = ?");
            stmt.setString(1, login);
            return !stmt.executeQuery().first();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    public boolean create(String login, String password) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO user (login, password) VALUES (?, ?)");
            stmt.setString(1, login);
            stmt.setString(2, password);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            return false;
        }
    }

    public User getUser(String login, String password) throws SQLException, InvalidUserException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM user WHERE login = ? AND password = ?");
            stmt.setString(1, login);
            stmt.setString(2, password);
            ResultSet set = stmt.executeQuery();
            if (set.first()) {
                return new User(set.getInt("user_id"),
                        set.getString("login"),
                        set.getString("password"),
                        set.getString("role"),
                        set.getString("status"));
            } else {
                throw new InvalidUserException();
            }
        }
    }

    public boolean deleteAccount(User user) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE user SET status = \"deleted\" WHERE user_id = ?");
            stmt.setInt(1, user.getID());
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            return false;
        }
    }
}