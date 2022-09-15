package ru.itacademy.model;

import java.util.Objects;

public class User {

    private int ID;
    private String login;
    private String password;
    private String role = "user";
    private String status = "active";

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return ID == user.ID && login.equals(user.login) && password.equals(user.password) && role.equals(user.role) && status.equals(user.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, login, password, role, status);
    }
}
