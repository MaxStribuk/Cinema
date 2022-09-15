package ru.itacademy.repository;

import java.util.List;

public interface Repository<T> {

    boolean create(T t);

    boolean update(T tOld, T tNew);

    T read(String userName);

    boolean delete(T t);

    List<T> readAll();
}
