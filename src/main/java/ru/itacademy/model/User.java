package ru.itacademy.model;

import java.util.Objects;

public class User {

    private final int ID;
    private final String login;
    private final String password;
    private final String role;
    private final String status;

    public User(int ID, String login, String password, String role, String status) {
        this.ID = ID;
        this.login = login;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Пользователь:" +
                "\n\tID = " + ID +
                ",\n\tlogin = " + login +
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
}