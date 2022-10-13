package ru.itacademy.service;

public class Service {

    public static final MovieService movieService = new MovieServiceImpl();
    public static final TicketService ticketService = new TicketServiceImpl();
    public static final UserService userService = new UserServiceImpl();
    public static final SessionService sessionService = new SessionServiceImpl();
}
