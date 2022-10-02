package ru.itacademy.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Session {

    private final int ID;
    private final Timestamp start_time;
    private final Timestamp end_time;
    private final String title;

    public Session(int ID, Timestamp start_time, Timestamp end_time, String title) {
        this.ID = ID;
        this.start_time = start_time;
        this.end_time = end_time;
        this.title = title;
    }

    @Override
    public String toString() {
        return "ID сеанса - " + ID +
                ",\n\tфильм - " + title +
                ",\n\tначало сеанса - " + start_time +
                ",\n\tокончание сеанса - " + end_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return start_time.equals(session.start_time) && end_time.equals(session.end_time) && title.equals(session.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start_time, end_time, title);
    }
}