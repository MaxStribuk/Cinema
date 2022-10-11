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
    public int inputMovieID() {
        int id;
        System.out.println(Constants.CREATING_MOVIE_ID);
        while (true) {
            try {
                id = Integer.parseInt(Menu.in.nextLine());
                if (id == 0) {
                    return 0;
                }
                if (id > 0 && Service.movieService.checkMovieAvailability(id)) {
                    return id;
                } else {
                    throw new NumberFormatException();
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
    public boolean createSession(int movieID, Timestamp startTime) throws SQLException {
        Time duration;
        duration = Service.movieService.getDuration(movieID);
        Timestamp endTime = calculateEndTime(startTime, duration);
        return sessionRepository.createSession(movieID, startTime, endTime);
    }

    @Override
    public boolean checkSessionIDCorrectness(int sessionID){
        if (sessionID < 0) {
            return false;
        } else {
            try {
                return sessionRepository.checkAvailabilitySession(sessionID);
            } catch (SQLException e) {
                System.out.println(Constants.FAILED_CONNECTION_DATABASE);
                return false;
            }
        }
    }

    public boolean printSessions(boolean isAllSessions) {
        try {
            return sessionRepository.printSessions(isAllSessions);
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            return false;
        }
    }

    @Override
    public boolean updateSessions(int movieID, Time duration) throws SQLException {
        List<Session> sessions = sessionRepository.getSessions(movieID);
        for (Session session : sessions) {
            session.setEndTime(calculateEndTime(session.getStartTime(), duration));
            if (!sessionRepository.checkAvailabilitySession(ConnectionManager.open(),
                    session.getID(), session.getStartTime(), session.getEndTime())) {
                return false;
            }
        }
        for (Session session : sessions) {
            sessionRepository.updateSession(session.getID(), session.getStartTime(),
                    session.getEndTime(), session.getMovieID());
        }
        return true;
    }

    @Override
    public void removeSession(int sessionID) throws SQLException {
        sessionRepository.removeSession(sessionID);
    }

    @Override
    public Session getSession(Timestamp startTime) throws SQLException {
        return sessionRepository.getSession(startTime);
    }

    @Override
    public List<Session> getSessions(int movieID) throws SQLException {
        return sessionRepository.getSessions(movieID);
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

    @Override
    public void updateSession(int sessionID, boolean isUpdateMovieID) throws SQLException {
        Timestamp startTime = isUpdateMovieID
                ? sessionRepository.getSession(sessionID).getStartTime()
                : inputStartTime();
        int movieID = isUpdateMovieID
                ? inputMovieID()
                : sessionRepository.getSession(sessionID).getMovieID();
        Time duration = Service.movieService.getDuration(movieID);
        Timestamp endTime = calculateEndTime(startTime, duration);
        System.out.println(
                sessionRepository.updateSession(sessionID, startTime, endTime, movieID)
                        ? Constants.SUCCESSFUL_CREATE_SESSION
                        : Constants.SESSIONS_IS_BUSY
        );
    }
}