package ru.itacademy.util;

public final class Constants {

    public final static String FAILED_LOAD_DRIVER = "Не удалось загрузить jdbc драйвер.";
    public final static String FAILED_CONNECTION_DATABASE = "Не удалось установить соединение с базой данных.";
    public final static String FAILED_READ_FILE = "Не удалось прочитать файл application.properties.";
    public final static String FAILED_USER_CREATE = "Не удалось создать пользователя, пользователь с таким именем уже существует.";
    public final static String INCORRECT_INPUT = "Некорректный ввод.";
    public final static String GREETING = "Добро пожаловать в приложение Мой кинотеатр!!!";
    public final static String FAREWELL = "Спасибо, что воспользовались приложением, до свидания!!!";
    public final static String MAIN_MENU = """
            Пожалуйста, введите:
            1 - для авторизации в приложении
            2 - для регистрации нового пользователя
            0 - для выхода из приложения
            """;
    public final static String CREATING_LOGIN = "Пожалуйста, задайте логин или введите 0 для возврата в главное меню.";
    public final static String CREATING_PASSWORD = "Пожалуйста, задайте пароль или введите 0 для возврата в главное меню.";
    public final static String REGISTRATION_SUCCESSFUL = "Регистрация прошла успешно.";
    public final static String LOGIN_IS_BUSY = "Данный логин уже занят, попробуйте ввести другой или введите 0 для возврата в главное меню.";
    public final static String INCORRECT_LOGIN = """
            Введен недопустимый логин. Логин должен содержать только буквы латинского алфавита или цифры, не содержать пробелов, иных знаков,
            а также состоять минимум из 5 символов, но не превышать длину 32 символа.
            Пожалуйста, задайте корректный логин или введите 0 для возврата в главное меню.""";
    public static final String INCORRECT_PASSWORD = """
            Введен недопустимый пароль. Пароль должен содержать только буквы латинского алфавита или цифры, не содержать пробелов, иных знаков,
            а также состоять минимум из 5 символов, но не превышать длину 32 символа.
            Пожалуйста, задайте корректный пароль или введите 0 для возврата в главное меню.""";
    public final static String AUTHORIZATION_MENU = "";
    public static final String REGISTRATION_FAILED = "Во время регистрации возникла непредвиденная ошибка, пожалуйста, попробуйте позже.";

    private Constants() {
    }
}