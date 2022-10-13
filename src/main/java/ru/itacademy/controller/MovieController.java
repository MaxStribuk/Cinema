package ru.itacademy.controller;

import ru.itacademy.model.Session;
import ru.itacademy.service.Service;
import ru.itacademy.util.Constants;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Consumer;

public class MovieController {

    public void createMovie() {
        String title = Service.movieService.inputTitle();
        if (title.equals("0")) {
            return;
        }
        LocalTime duration = Service.movieService.inputDuration();
        try {
            System.out.println(Service.movieService.createMovie(title, duration)
                    ? Constants.SUCCESSFUL_CREATE_MOVIE
                    : Constants.FAILED_CREATE_MOVIE);
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
        }
    }

    public boolean printMovies() {
        try {
            return Service.movieService.printMovies();
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            return false;
        }
    }

    public void changeMovie(String message, Consumer<Integer> task) {
        System.out.println(message);
        while (true) {
            try {
                int movieID = Integer.parseInt(Menu.in.nextLine());
                if (movieID == 0) {
                    return;
                }
                if (Service.movieService.checkMovieAvailability(movieID)) {
                    task.accept(movieID);
                    return;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_MOVIE_ID);
            }
        }
    }

    public void updateMovie(int movieID) {
        while (true) {
            System.out.println(Constants.MENU_MOVIE_UPDATE);
            try {
                int input = Integer.parseInt(Menu.in.nextLine());
                switch (input) {
                    case 0 -> {
                        return;
                    }
                    case 1 -> {
                        Service.movieService.updateTitle(movieID);
                        return;
                    }
                    case 2 -> {
                        Service.movieService.updateDuration(movieID);
                        return;
                    }
                    default -> throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_INPUT);
            } catch (SQLException e) {
                System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            }
        }
    }

    public void removeMovie(int movieID) {
        while (true) {
            System.out.println(Constants.MENU_MOVIE_REMOVE);
            try {
                int input = Integer.parseInt(Menu.in.nextLine());
                switch (input) {
                    case 0 -> {
                        return;
                    }
                    case 1 -> {
                        deleteMovie(movieID);
                        return;
                    }
                    default -> throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_INPUT);
            } catch (SQLException e) {
                System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            }
        }
    }

    private void deleteMovie(int movieID) throws SQLException {
        List<Session> sessions = Service.sessionService.getSessions(movieID);
        for (Session session: sessions) {
            Service.ticketService.removeTickets(session.getID());
        }
        for (Session session : sessions) {
            Service.sessionService.removeSession(session.getID());
        }
        Service.movieService.removeMovie(movieID);
        System.out.println(Constants.SUCCESSFUL_REMOVE_MOVIE);
    }
}