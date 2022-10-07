package ru.itacademy.util;

public final class Constants {

    public final static int MINUTES_IN_HOUR = 60;
    public final static int MINUTES_IN_DAY = 1440;
    public final static int MIN_MINUTES_IN_MOVIE = 10;
    public final static int MAX_ROW = 10;
    public final static int MAX_PLACE_IN_ROW = 10;
    public final static int CHEAP_ROWS = 8;
    public final static int COST_CHEAP_TICKET = 10;
    public final static int COST_EXPENSIVE_TICKET = 15;
    public final static String CURRENCY = " рублей";
    public final static String GREETING = "Добро пожаловать в приложение Мой кинотеатр!!!";
    public final static String FAREWELL = "Спасибо, что воспользовались приложением, до свидания!!!";
    public final static String FAILED_LOAD_DRIVER = "Не удалось загрузить jdbc драйвер.";
    public final static String FAILED_CONNECTION_DATABASE = "Не удалось установить " +
            "соединение с базой данных.";
    public final static String FAILED_READ_FILE = "Не удалось прочитать файл application.properties.";
    public static final String FAILED_REGISTRATION_USER = "Во время регистрации возникла " +
            "непредвиденная ошибка, пожалуйста, попробуйте позже.";
    public static final String FAILED_AUTHORIZATION_USER = "Авторизация не удалась, введенные " +
            "Вами данные были некорректны.";
    public static final String FAILED_CREATE_MOVIE = "Добавить фильм в базу данных не удалось.";
    public static final String FAILED_CREATE_SESSION = "Добавить сеанс в базу данных не удалось.";
    public static final String FAILED_RETURN_TICKET = """
            К сожалению, операция не удалась.
            Возможно, сеанс по данному билету уже начался,
            либо билет с данным ID у пользователя отсутствует.
            """;
    public static final String FAILED_BUY_TICKET = "К сожалению, покупка билета не удалась. " +
            "Данный билет уже был кем-то приобретен.";
    public final static String SUCCESSFUL_REGISTRATION_USER = "Регистрация прошла успешно.";
    public static final String SUCCESSFUL_CREATE_MOVIE = "Фильм усшешно добавлен в базу данных.";
    public static final String SUCCESSFUL_CREATE_SESSION = "Сеанс усшешно добавлен в базу данных.";
    public static final String SUCCESSFUL_BUY_TICKET = "Поздравляем, Вы приобрели билет. " +
            "С Вашей карточки было списано ";
    public static final String SUCCESSFUL_RETURN_TICKET = "Поздравляем, операция завершена успешно. " +
            "На карточку пользователя было возвращено ";
    public final static String MENU_MAIN = """
            Пожалуйста, введите:
            0 - для выхода из приложения
            1 - для авторизации в приложении
            2 - для регистрации нового пользователя
            """;
    public static final String MENU_USER = """
            Пожалуйста, введите:
            0 - для выхода из приложения
            1 - для просмотра доступных сеансов
            2 - для просмотра репертуара фильмов
            3 - для приобретения билета
            4 - для просмотра приобретенных Вами билетов
            5 - для возврата приобретенного Вами билета
            6 - для отмены авторизации
            7 - для удаления аккаунта
            """;
    public static final String MENU_MANAGER = """
            8 - для редактирования фильма
            9 - для редактирования сеанса
            10 - для просмотра пользователей
            11 - для выкупа билета пользователя
            """;
    public static final String MENU_SESSION = "Пожалуйста, введите ID сеанса, " +
            "на который Вы желаете приобрести билет, " +
            "или введите 0 для возврата в предыдущее меню.";
    public static final String MENU_SESSION_UPDATE = """
            Пожалуйста, введите:
            0 - для возврата к меню пользователя
            1 - для изменения начала сеанса
            2 - для замены показываемого фильма
            """;
    public static final String MENU_MOVIE_UPDATE = """
            Пожалуйста, введите:
            0 - для возврата к меню пользователя
            1 - для изменения названия фильма
            2 - для изменения продолжительности фильма
            """;
    public static final String MENU_MOVIE = """
            Пожалуйста, введите ID фильма,
            на который Вы желаете приобрести билет,
            или введите 0 для возврата в предыдущее меню.
            """;
    public static final String MENU_TICKET_USER_BUY = """
            Пожалуйста, введите ID пользователя,
            билет которого Вы желаете выкупить,
            или введите 0 для возврата в предыдущее меню.
            """;
    public static final String MENU_TICKET_BUY = """
            Пожалуйста, введите ID билета,
            который Вы желаете приобрести,
            или введите 0 для возврата в предыдущее меню.
            """;
    public static final String MENU_TICKET_RETURN = "Пожалуйста, введите ID билета, " +
            "или введите 0 для возврата в предыдущее меню.";
    public final static String INVALID_INPUT = "Некорректный ввод.";
    public static final String INVALID_USER = "Пользователь с введенными Вами данными не существует.";
    public final static String INVALID_USER_LOGIN = """
            Введен недопустимый логин. Логин должен содержать только
            буквы латинского алфавита или цифры, не содержать пробелов, иных знаков,
            а также состоять минимум из 5 символов, но не превышать длину 32 символа.
            Пожалуйста, задайте корректный логин или введите 0 для возврата в главное меню.
            """;
    public static final String INVALID_USER_PASSWORD = """
            Введен недопустимый пароль. Пароль должен содержать только
            буквы латинского алфавита или цифры, не содержать пробелов, иных знаков,
            а также состоять минимум из 5 символов, но не превышать длину 32 символа.
            Пожалуйста, задайте корректный пароль или введите 0 для возврата в главное меню.
            """;
    public static final String INVALID_USER_ID = "Пользователь с введенным Вами ID не существует.";
    public static final String INVALID_MOVIE_TITLE = """
            Введено недопустимое название фильма.
            Название должно состоять минимум из 1 буквы или цифры, но не превышать длину 100 символов,
            содержать только буквы латинского алфавита, цифры, восклицательный, вопросительный знаки,
            пробел, запятую или дефис, и не содержать иных знаков препинания.
            Пожалуйста, задайте корректное название фильма или введите 0 для возврата в главное меню.
            """;
    public static final String INVALID_MOVIE_DURATION = """
            Введена недопустимая продолжительность фильма. Продолжительность должна быть больше 10 минут
            и не должна превышать 23 часа 59 минут, а также не должна содержать нечисловых символов.
            Пожалуйста, задайте корректную продолжительность фильма.
            """;
    public static final String INVALID_MOVIE_ID = """
            Введен недопустимый ID. Фильм с введенным ID не существует.
            Пожалуйста, задайте корректный ID фильма или введите 0 для возврата в предыдущее меню.
            """;
    public static final String INVALID_SESSION_ID = "Введен некорректный ID сеанса, " +
            "сеанс с введенным ID не существует.";
    public static final String INVALID_SESSION_START_TIME = "Введено некорректное начало сеанса.";
    public static final String INVALID_TICKET_ID = "Введен некорректный ID билета, " +
            "билет с введенным ID не существует.";
    public final static String CREATING_USER_LOGIN = "Пожалуйста, задайте логин " +
            "или введите 0 для возврата в главное меню.";
    public final static String CREATING_USER_PASSWORD = "Пожалуйста, задайте пароль " +
            "или введите 0 для возврата в главное меню.";
    public static final String CREATING_MOVIE_TITLE = "Пожалуйста, задайте название фильма " +
            "или 0 для возврата в меню пользователя.";
    public static final String CREATING_MOVIE_DURATION = "Пожалуйста, задайте продолжительность фильма.";
    public static final String CREATING_MOVIE_DURATION_HOURS = "Пожалуйста, задайте количество часов.";
    public static final String CREATING_MOVIE_DURATION_MINUTES = "Пожалуйста, задайте количество минут.";
    public static final String CREATING_SESSION_START_TIME = """
            Пожалуйста, задайте дату и время начала сеанса в формате yyyy MM dd hh mm.
            Сеанс не может начинаться раньше текущего времени.
            """;
    public static final String CREATING_MOVIE_ID = """
            Пожалуйста, задайте ID фильма, показ которого будет осуществляться.
            или введите 0 для возврата в предыдущее меню.
            """;
    public static final String INPUT_LOGIN = "Пожалуйста, введите свой логин, " +
            "или введите 0 для возврата в главное меню.";
    public static final String INPUT_PASSWORD = "Пожалуйста, введите свой пароль, " +
            "или введите 0 для возврата в главное меню.";
    public static final String USER_BLOCKED = "К сожалению, Ваш аккаунт был заблокирован или удален.";
    public static final String USER_DELETED = "Ваш аккаунт был удален.";
    public static final String MISSING_MOVIES = "К сожалению, на данный момент " +
            "фильмы в репертуаре кинотеатра отсутствют.";
    public static final String MISSING_SESSIONS = "К сожалению, на данный момент " +
            "будущие сеансы в репертуаре кинотеатра отсутствют.";
    public static final String MISSING_TICKETS = "К сожалению, билеты на выбранный Вами сеанс отсутствуют.";
    public static final String MISSING_USER_TICKETS = "К сожалению, у пользователя отсутствуют приобретенные билеты.";
    public final static String LOGIN_IS_BUSY = "Данный логин уже занят, попробуйте задать другой " +
            "или введите 0 для возврата в главное меню.";
    public static final String MOVIE_IS_BUSY = "Фильм с введенными Вами данными уже существует.";
    public static final String SESSIONS_IS_BUSY = """
            Сеанс с указанными Вами данными не может быть создан,
            поскольку одновременно в кинотеатре не может проходить более одного сеанса.
            Пожалуйста, задайте сеанс корректно.
            """;
    public static final String UPDATE_SESSION = """
            Пожалуйста, введите ID сеанса,
            который Вы желаете изменить,
            или введите 0 для возврата в предыдущее меню.
            """;
    public static final String UPDATE_MOVIE = """
            Пожалуйста, введите ID фильма,
            который Вы желаете изменить,
            или введите 0 для возврата в предыдущее меню.
            """;

    private Constants() {
    }
}