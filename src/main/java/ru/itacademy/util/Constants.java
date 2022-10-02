package ru.itacademy.util;

public final class Constants {

    public final static int MINUTES_IN_HOUR = 60;
    public final static int MINUTES_IN_DAY = 1440;
    public final static int MIN_MINUTES_IN_MOVIE = 10;

    public final static int MAX_ROW = 10;
    public final static int CHEAP_ROWS = 8;
    public final static int MAX_PLACE_IN_ROW = 10;
    public final static int COST_CHEAP_TICKET = 10;
    public final static int COST_EXPENSIVE_TICKET = 15;
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
    public static final String INPUT_LOGIN = "Пожалуйста, введите свой логин или 0 для возврата в главное меню.";
    public static final String INPUT_PASSWORD = "Пожалуйста, введите свой пароль или 0 для возврата в главное меню.";
    public static final String FAILED_AUTHORIZATION = "Авторизация не удалась, введенные Вами данные были некорректны.";
    public static final String INVALID_USER = "Пользователь с введенными Вами данными не существует.";
    public static final String USER_MENU = """
            Пожалуйста, введите:
            1 - для просмотра доступных сеансов
            2 - для просмотра репертуара фильмов
            3 - для приобретения билета
            4 - для просмотра приобретенных билетов
            5 - для возврата приобретенного билета
            6 - для отмены авторизации
            7 - для удаления аккаунта
            0 - для выхода из приложения
            """;
    public static final String USER_BLOCKED = "К сожалению, Ваш аккаунт был заблокирован или удален.";
    public static final String USER_DELETED = "Ваш аккаунт был удален.";
    public static final String MOVIES_MISSING = "К сожалению, на данный момент фильмы в репертуаре кинотеатра отсутствют.";
    public static final String SESSIONS_MISSING = "К сожалению, на данный момент будущие сеансы в репертуаре кинотеатра отсутствют.";
    public static final String CREATING_MOVIE_TITLE = "Пожалуйста, введите название фильма или 0 для возврата в меню пользователя.";
    public static final String INCORRECT_TITLE = """
            Введено недопустимое название фильма. Название должно состоять минимум из 1 символа, но не превышать длину 100 символов.
            Пожалуйста, задайте корректное название фильма или введите 0 для возврата в главное меню.""";
    public static final String CREATING_MOVIE_DURATION = "Пожалуйста, введите продолжительность фильма.";
    public static final String INPUT_HOURS = "Пожалуйста, введите количество часов.";
    public static final String INPUT_MINUTES = "Пожалуйста, введите количество минут.";
    public static final String INCORRECT_DURATION = """
            Введена недопустимая продолжительность фильма. Продолжительность должна быть больше 10 минут
            и не должна превышать 23 часа 59 минут, а также не содержать нечисловых символов.
            Пожалуйста, задайте корректную продолжительность фильма.""";
    public static final String CREATE_MOVIE_SUCCESSFUL = "Фильм усшешно добавлен в базу данных.";
    public static final String CREATE_MOVIE_FAILED = "Добавить фильм в базу данных не удалось.";
    public static final String MOVIE_IS_BUSY = "Фильм с введенными Вами данными уже существует.";
    public static final String CREATING_MOVIE_ID = "Пожалуйста, введите ID фильма, показ которого будет осуществляться." +
            " или введите 0 для возврата в предыдущее меню.";
    public static final String INVALID_MOVIE = "Фильм с введенным Вами ID не существует." +
            "Пожалуйста, задайте корректный ID фильма или введите 0 для возврата в предыдущее меню.";
    public static final String INCORRECT_MOVIE_ID = """
            Введен недопустимый ID.
            ID фильма должен быть целым числом большим 0.
            Пожалуйста, задайте корректный ID фильма или введите 0 для возврата в предыдущее меню.""";
    public static final String CREATING_MOVIE_START_TIME = "Пожалуйста, введите дату и время начала сеанса " +
            "в формате yyyy MM dd hh mm.";
    public static final String INCORRECT_MOVIE_START_TIME = "Введено некорректное начало сеанса.";
    public static final String INVALID_START_TIME = "Время начала сеанса задано некорректно. Текущее время не может " +
            "быть позже времени начала сеанса." +
            "\nПожалуйста, задайте сеанс с учетом вышеуказанных ограничений.";
    public static final String CREATE_SESSION_SUCCESSFUL = "Сеанс усшешно добавлен в базу данных.";
    public static final String CREATE_SESSION_FAILED = "Добавить сеанс в базу данных не удалось.";
    public static final String SESSIONS_IS_BUSY = "Сеанс с указанными Вами данными не может быть создан, поскольку " +
            "одновременно в кинотеатре не может проходить более одного сеанса. Пожалуйста, задайте сеанс корректно.";
    public static final String SESSION_MENU = "Пожалуйста, введите ID сеанса, на который Вы желаете приобрести билет " +
            "или введите 0 для возврата в предыдущее меню.";
    public static final String TICKETS_MISSING = "К сожалению, билеты на выбранный Вами сеанс отсутствуют.";
    public static final String INCORRECT_SESSION_ID = "Введен некорректный ID сеанса, сеанс с введенным ID не существует.";
    public static final String MOVIE_MENU = "Пожалуйста, введите ID фильма, на который Вы желаете приобрести билет " +
            "или введите 0 для возврата в предыдущее меню.";
    public static final String TICKET_MENU_BUY = "Пожалуйста, введите ID билета, который Вы желаете приобрести " +
            "или введите 0 для возврата в предыдущее меню.";
    public static final String INCORRECT_TICKET_ID = "Введен некорректный ID билета, билет с введенным ID не существует.";
    public static final String BUY_TICKET_SUCCESS = "Поздравляем, Вы приобрели билет. С Вашей карточки было списано ";
    public static final String TICKET_MISSING = "К сожалению, у Вас отсутствуют приобретенные билеты.";
    public static final String TICKET_MENU_RETURN = "Пожалуйста, введите ID билета, который Вы желаете сдать " +
            "или введите 0 для возврата в предыдущее меню.";
    public static final String RETURN_TICKET_SUCCESS = "Поздравляем, Вы успешно сдали билет. На Вашу карточку было возвращено ";
    public static final String RETURN_TICKET_FAILED = "К сожалению возврат билета не удался. Возможно Вы пытаетесь вернуть билет, который " +
            "у Вас отсутствует, либо сеанс по данному билету уже начался.";

    private Constants() {
    }
}