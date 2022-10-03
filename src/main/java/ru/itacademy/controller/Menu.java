package ru.itacademy.controller;

import ru.itacademy.util.Constants;

import java.util.Scanner;

public class Menu {

    public static final Scanner in = new Scanner(System.in);
    private final UserController userController = new UserController();

    public void start() {
        System.out.println(Constants.GREETING);
        printMenu();
    }

    private void printMenu() {
        while (true) {
            System.out.println(Constants.MENU_MAIN);
            String input = in.nextLine();
            switch (input) {
                case "1" -> userController.logApp();
                case "2" -> userController.registerUser();
                case "0" -> {
                    System.out.println(Constants.FAREWELL);
                    return;
                }
                default -> System.out.println(Constants.INVALID_INPUT);
            }
        }
    }
}
