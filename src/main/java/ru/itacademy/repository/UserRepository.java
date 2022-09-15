package ru.itacademy.repository;

import ru.itacademy.model.User;
import ru.itacademy.util.ConnectionManager;
import ru.itacademy.util.Constants;
import ru.itacademy.util.InvalidUserException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository implements Repository<User> {

//    @Override
//    public boolean create(User user) {
//        try (Connection connection = ConnectionManager.open()) {
//            PreparedStatement stmt = connection.prepareStatement(
//                    "INSERT INTO user (login, password, role, status) VALUES (?, ?, ?, ?)");
//            stmt.setString(1, user.getLogin());
//            stmt.setString(2, user.getPassword());
//            stmt.setString(3, user.getRole());
//            stmt.setString(4, user.getStatus());
//            stmt.execute();
//            return true;
//        } catch (SQLException e) {
//            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
//            return false;
//        }
//    }

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

    @Override
    public User getUser(String login, String password) throws SQLException, InvalidUserException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM user WHERE login =? AND password =?");
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

//    @Override
//    public boolean update(User tOld, User tNew) {
//        return false;
//    }
//
//    @Override
//    public boolean delete(User user) {
//        return false;
//    }
//
//    @Override
//    public List<User> readAll() {
//        return null;
//    }
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
