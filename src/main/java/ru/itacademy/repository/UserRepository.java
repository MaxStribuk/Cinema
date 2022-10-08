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

    public boolean checkAvailabilityLogin(String login) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM user WHERE login = ?");
            stmt.setString(1, login);
            return !stmt.executeQuery().first();
        }
    }

    public boolean createUser(String login, String password) {
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
            ResultSet user = stmt.executeQuery();
            if (user.first()) {
                return new User(user.getInt("user_id"),
                        user.getString("login"),
                        user.getString("password"),
                        user.getString("role"),
                        user.getString("status"));
            } else {
                throw new InvalidUserException();
            }
        }
    }

    public boolean deleteUserAccount(User user) {
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

    public void printUsers() {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM user");
            ResultSet users = stmt.executeQuery();
            while (users.next()) {
                System.out.println(new User(
                        users.getInt("user_id"),
                        users.getString("login"),
                        users.getString("password"),
                        users.getString("role"),
                        users.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
        }
    }

    public boolean checkAvailabilityUser(int userID) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM user WHERE user_id = ?");
            stmt.setInt(1, userID);
            return stmt.executeQuery().first();
        }
    }

    public boolean updateRole(int userID, String role) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE user " +
                            "SET role = ? " +
                            "WHERE user_id = ?");
            stmt.setString(1, role);
            stmt.setInt(2, userID);
            stmt.execute();
            return true;
        }
    }

    public boolean updateStatus(int userID, String status) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE user " +
                            "SET status = ? " +
                            "WHERE user_id = ?");
            stmt.setString(1, status);
            stmt.setInt(2, userID);
            stmt.execute();
            return true;
        }
    }
}