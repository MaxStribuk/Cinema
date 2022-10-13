package ru.itacademy.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Session {

    private final int id;
    private final Timestamp startTime;
    private Timestamp endTime;
    private int movieID;
    private String title;

    public Session(int id, Timestamp startTime, Timestamp endTime, String title) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
    }

    public Session(int id, Timestamp startTime, Timestamp endTime, int movieID) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.movieID = movieID;
    }

    @Override
    public String toString() {
        return "ID сеанса - " + id +
                ",\n\tфильм - " + title +
                ",\n\tначало сеанса - " + startTime +
                ",\n\tокончание сеанса - " + endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return movieID == session.movieID && startTime.equals(session.startTime) && endTime.equals(session.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, movieID);
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public int getID() {
        return id;
    }

    public int getMovieID() {
        return movieID;
    }
}