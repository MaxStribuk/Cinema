package ru.itacademy.repository;

import ru.itacademy.util.InvalidUserException;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {

//    boolean create(T t);

//    boolean update(T tOld, T tNew);
//
    T getUser(String login, String password) throws SQLException, InvalidUserException;
//
//    boolean delete(T t);
//
//    List<T> readAll();
}
