package ru.itacademy.util;

public final class Constants {

    public final static String FAILED_LOAD_DRIVER = "Не удалось загрузить jdbc драйвер.";
    public final static String FAILED_CONNECTION_DATABASE = "Не удалось установить соединение с базой данных.";
    public final static String FAILED_READ_FILE = "Не удалось прочитать файл application.properties.";
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
    public static final String REGISTRATION_FAILED = "Во время регистрации возникла непредвиденная ошибка, пожалуйста, попробуйте позже.";
    public static final String INPUT_LOGIN = "Пожалуйста, введите свой логин или введите 0 для возврата в главное меню.";
    public static final String INPUT_PASSWORD = "Пожалуйста, введите свой пароль или введите 0 для возврата в главное меню.";
    public static final String FAILED_AUTHORIZATION = "Авторизация не удалась, введенные Вами данные были некорректны.";
    public static final String INVALID_USER = "Пользователь с введенными Вами данными не существует.";
    public static final String USER_MENU = """
            Пожалуйста, введите:
            1 - для просмотра доступных сеансов
            2 - для просмотра репертуара фильмов
            3 - для просмотра приобретенных билетов
            4 - для возврата приобретенного билета
            5 - для отмены авторизации
            6 - для удаления аккаунта
            0 - для выхода из приложения
            """;
    public static final String USER_BLOCKED = "Извините, Ваш аккаунт был заблокирован или удален.";

    private Constants() {
    }
}