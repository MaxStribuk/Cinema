package ru.itacademy.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Ticket {

    private int id;
    private final int row;
    private final int place;
    private final int cost;
    private int sessionID;
    private Timestamp start_time;
    public Timestamp end_time;
    private String title;

    public Ticket(int id, int row, int place, int cost, Timestamp start_time, Timestamp end_time, String title) {
        this.id = id;
        this.row = row;
        this.place = place;
        this.cost = cost;
        this.start_time = start_time;
        this.end_time = end_time;
        this.title = title;
    }

    public Ticket(int row, int place, int cost, int sessionID) {
        this.row = row;
        this.place = place;
        this.cost = cost;
        this.sessionID = sessionID;
    }

    public int getRow() {
        return row;
    }

    public int getPlace() {
        return place;
    }

    public int getCost() {
        return cost;
    }

    public int getSessionID() {
        return sessionID;
    }

    @Override
    public String toString() {
        return "ID билета - " + id +
                ",\tряд - " + row +
                ",\tместо - " + place +
                ",\tстоимость - " + cost +
                ",\n\tназвание фильма - " + title +
                ",\n\tвремя начала сеанса - " + start_time +
                ",\n\tвремя окончания сеанса - " + end_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return row == ticket.row && place == ticket.place && cost == ticket.cost && sessionID == ticket.sessionID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, place, cost, sessionID);
    }
}