package ru.itacademy.model;

import java.util.Objects;

public class User {

    private final int id;
    private final String login;
    private final String password;
    private final String role;
    private final String status;

    public User(int id, String login, String password, String role, String status) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Пользователь " + login +
                "\n\tid = " + id +
                ",\n\trole = " + role +
                ",\n\tstatus = " + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    public int getID() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public String getPassword() {
        return password;
    }
}