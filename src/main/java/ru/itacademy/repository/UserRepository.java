package ru.itacademy.repository;

import ru.itacademy.model.User;
import ru.itacademy.util.ConnectionManager;
import ru.itacademy.util.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserRepository implements Repository<User> {

    @Override
    public boolean create(User user) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO user (login, password, role, status) VALUES (?, ?, ?, ?)");
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getStatus());
            checkLoginAvailability(stmt);
            return true;
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            return false;
        } catch (RuntimeException e) {
            System.out.println(Constants.FAILED_USER_CREATE);
            return false;
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

    private void checkLoginAvailability(PreparedStatement stmt) {
        try {
            stmt.executeQuery().first();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public User read(String login) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM user WHERE login =?");
            stmt.setString(1, login);
            ResultSet set = stmt.executeQuery();
            set.next();
            return new User(set.getInt("user_id"),
                    set.getString("login"),
                    set.getString("password"),
                    set.getString("role"),
                    set.getString("status"));
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            return new User();
        }
    }

    public boolean checkLoginAvailability(String login) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM user WHERE login =?");
            stmt.setString(1, login);
            return !stmt.executeQuery().first();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    @Override
    public boolean update(User tOld, User tNew) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public List<User> readAll() {
        return null;
    }
}

//            PreparedStatement stmt = conn.prepareStatement("UPDATE users SET login=?, password=? WHERE id=?");
//            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
//            PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE login=? AND password=?");
//                stmt.setInt(3, 3);
//            String name;
//            while (rs.next()) {
//                name = rs.getString("password");
//                System.out.println("-----------------");
//                System.out.println(name + "\n");
//            }
