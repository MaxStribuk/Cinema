package ru.itacademy.controller;

import ru.itacademy.service.SessionService;
import ru.itacademy.service.SessionServiceImpl;
import ru.itacademy.util.Constants;

import java.sql.Timestamp;

public class SessionController {

    SessionService sessionService = new SessionServiceImpl();
    TicketController ticketController = new TicketController();

    public void createSession() {
        System.out.println(Constants.CREATING_MOVIE_ID);
        int movieID = sessionService.inputID();
        if (movieID == 0) {
            return;
        }
        System.out.println(Constants.CREATING_MOVIE_START_TIME);
        Timestamp startTime = sessionService.inputStartTime();
        if (sessionService.createSession(movieID, startTime)) {
            System.out.println(Constants.SUCCESSFUL_CREATE_SESSION);
            ticketController.createTicketsForSession(startTime);
        } else {
            System.out.println(Constants.FAILED_CREATE_SESSION);
        }
    }

    public void printAllSessions() {
        sessionService.printAllSessions();
    }
}