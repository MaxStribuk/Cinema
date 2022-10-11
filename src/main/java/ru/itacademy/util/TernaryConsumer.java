package ru.itacademy.util;

@FunctionalInterface
public interface TernaryConsumer<F, U, T> {

    void accept(F f, U u, T t);
}