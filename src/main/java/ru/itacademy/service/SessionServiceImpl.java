package ru.itacademy.service;

import ru.itacademy.controller.Menu;
import ru.itacademy.repository.SessionRepository;
import ru.itacademy.util.Constants;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository = new SessionRepository();
    private final MovieService movieService = new MovieServiceImpl();

    @Override
    public void printAllSessions() {
        sessionRepository.printAllSessions();
    }

    @Override
    public int inputID() {
        int id;
        while (true) {
            try {
                id = Integer.parseInt(Menu.in.nextLine());
                if (id < 0) {
                    throw new NumberFormatException();
                }
                if (id == 0) {
                    return 0;
                }
                if (checkMovieIDAvailability(id)) {
                    return id;
                } else {
                    System.out.println(Constants.INVALID_MOVIE_ID);
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_MOVIE_ID);
            }
        }
    }

    @Override
    public Timestamp inputStartTime() {
        LocalDateTime startTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH mm");
        while (true) {
            try {
                startTime = LocalDateTime.parse(Menu.in.nextLine(), formatter);
                if (checkSessionStartTimeCorrectness(startTime)) {
                    return Timestamp.valueOf(startTime);
                } else {
                    throw new DateTimeException("");
                }
            } catch (DateTimeParseException e) {
                System.out.println(Constants.INVALID_SESSION_START_TIME);
                System.out.println(Constants.CREATING_MOVIE_START_TIME);
            }
        }
    }

    @Override
    public boolean createSession(int movieID, Timestamp startTime) {
        Time duration;
        try {
            duration = movieService.getDuration(movieID);
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            return false;
        }
        Timestamp endTime = calculateEndTime(startTime, duration);
        return sessionRepository.createSession(movieID, startTime, endTime);
    }

    @Override
    public int getSessionID(Timestamp startTime) throws SQLException {
        return sessionRepository.getSessionID(startTime);
    }

    @Override
    public boolean checkSessionIDCorrectness(int sessionID) throws SQLException {
        if (sessionID < 0) {
            return false;
        } else {
            return sessionRepository.checkSessionIDCorrectness(sessionID);
        }
    }

    private Timestamp calculateEndTime(Timestamp startTime, Time duration) {
        LocalDateTime startMovie = startTime.toLocalDateTime();
        LocalTime durationMovie = duration.toLocalTime();
        return Timestamp.valueOf(startMovie
                .plus(durationMovie.getHour(), ChronoUnit.HOURS)
                .plus(durationMovie.getMinute(), ChronoUnit.MINUTES));
    }

    private boolean checkSessionStartTimeCorrectness(LocalDateTime startTime) {
        return LocalDateTime.now().isBefore(startTime);
    }

    private boolean checkMovieIDAvailability(int id) {
        return movieService.checkMovieAvailability(id);
    }
}