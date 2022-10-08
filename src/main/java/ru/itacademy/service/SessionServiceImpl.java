package ru.itacademy.service;

import ru.itacademy.controller.Menu;
import ru.itacademy.model.Session;
import ru.itacademy.repository.SessionRepository;
import ru.itacademy.util.ConnectionManager;
import ru.itacademy.util.Constants;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository = new SessionRepository();

    @Override
    public void printAllSessions() {
        sessionRepository.printSessions();
    }

    @Override
    public int inputMovieID() {
        int id;
        System.out.println(Constants.CREATING_MOVIE_ID);
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
            System.out.println(Constants.CREATING_SESSION_START_TIME);
            try {
                startTime = LocalDateTime.parse(Menu.in.nextLine(), formatter);
                if (checkSessionStartTimeCorrectness(startTime)) {
                    return Timestamp.valueOf(startTime);
                } else {
                    throw new DateTimeException("");
                }
            } catch (DateTimeException e) {
                System.out.println(Constants.INVALID_SESSION_START_TIME);
            }
        }
    }

    @Override
    public boolean createSession(int movieID, Timestamp startTime) {
        Time duration;
        try {
            duration = Service.movieService.getDuration(movieID);
            Timestamp endTime = calculateEndTime(startTime, duration);
            return sessionRepository.createSession(movieID, startTime, endTime);
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            return false;
        }
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
            return sessionRepository.checkAvailabilitySession(sessionID);
        }
    }

    @Override
    public void updateStartTime(int sessionID) throws SQLException {
        System.out.println(Constants.CREATING_SESSION_START_TIME);
        Timestamp startTime = inputStartTime();
        int movieID = sessionRepository.getMovieID(sessionID);
        updateSession(sessionID, startTime, movieID);
    }

    @Override
    public void updateMovieID(int sessionID) throws SQLException {
        Timestamp startTime = sessionRepository.getStartTime(sessionID);
        int movieID = inputMovieID();
        updateSession(sessionID, startTime, movieID);
    }

    @Override
    public boolean updateSessions(int movieID, Time duration) throws SQLException {
        List <Session> sessions = sessionRepository.getSessions(movieID);
        for (Session session : sessions) {
            Timestamp endTime = calculateEndTime(session.getStartTime(), duration);
            if (endTime.after(session.getEndTime())) {
                if (sessionRepository.checkAvailabilitySession(ConnectionManager.open(),
                        session.getID(), session.getStartTime(), endTime)) {
                    session.setEndTime(endTime);
                } else {
                    return false;
                }
            } else {
                session.setEndTime(endTime);
            }
        }
        return sessionRepository.updateSessions(sessions);
    }

    private void updateSession(int sessionID, Timestamp startTime, int movieID) throws SQLException {
        Time duration = Service.movieService.getDuration(movieID);
        Timestamp endTime = calculateEndTime(startTime, duration);
        if (sessionRepository.updateSession(sessionID, startTime, endTime, movieID)) {
            System.out.println(Constants.SUCCESSFUL_CREATE_SESSION);
        } else {
            System.out.println(Constants.SESSIONS_IS_BUSY);
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
        return Service.movieService.checkMovieAvailability(id);
    }
}