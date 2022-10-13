package ru.itacademy.model;

import java.time.LocalTime;
import java.util.Objects;

public class Movie {

    private final int id;
    private final String title;
    private final LocalTime duration;

    public Movie(int id, String title, LocalTime duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "ID фильма - " + id +
                ",\n\t название - " + title +
                ",\n\t продолжительность - " + duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return title.equals(movie.title) && duration.equals(movie.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, duration);
    }
}