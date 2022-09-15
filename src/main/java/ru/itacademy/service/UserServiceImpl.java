package ru.itacademy.service;

import ru.itacademy.repository.UserRepository;

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
        return !data.matches("[a-zA-Z0-9]{5,32}");
    }

    @Override
    public boolean createUser(String login, String password) {
        return userRepository.create(login, password);
    }
}
