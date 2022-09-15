package ru.itacademy.service;

import ru.itacademy.model.User;
import ru.itacademy.repository.UserRepository;
import ru.itacademy.util.InvalidUserException;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository = new UserRepository();

//    @Override
//    public User getUser(String login) {
//        return new User();
//    }

    @Override
    public boolean checkLoginAvailability(String login) throws SQLException {
        return userRepository.checkLoginAvailability(login);
    }

    @Override
    public boolean checkDataCorrectness(String data) {
        return data.matches("[a-zA-Z0-9]{5,32}");
    }

    @Override
    public boolean createUser(String login, String password) {
        return userRepository.create(login, password);
    }

    @Override
    public User getUser(String login, String password) throws SQLException, InvalidUserException {
        return userRepository.getUser(login, password);
    }

    @Override
    public int establishUserStatus(User user) {
        if (!user.getStatus().equals("active")) {
            return 0;
        } else
            switch (user.getRole()) {
                case "user" -> {return 1;}
                case "manager" -> {return 2;}
                case "admin" -> {return 3;}
                default -> {return 0;}
            }
    }
}
