package ru.yandex.practicum.filmorate.filmorate.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryStorageTests {
    InMemoryFilmStorage films = new InMemoryFilmStorage();
    InMemoryUserStorage users = new InMemoryUserStorage();
    private Film film;
    private User user;

    @BeforeEach
    void makingPollzovatelMovieTest() {
        film = new Film(
                1L, new HashSet<>(),
                "В бой идут одни старики",
                " советский художественный фильм 1973 года" +
                        "Военный фильм",
                LocalDate.of(1973, 12, 18),
                87);
        user = new User(
                1L, new HashSet<>(),
                "malahov@yandex.ru",
                "zver",
                "Vova",
                LocalDate.of(1982, 5, 12));
    }

    @Test
    void createMovieTest() {
        films.create(film);
        assertFalse(films.getAllFilms().isEmpty());
    }

    @Test
    void updateFilmTest() {
        Film newOne = new Film(
                1L, new HashSet<>(),
                "В бой идут одни старики",
                "Док и Марти решили исправить будущее, но сперва сломали все окончательно. Таким виделся 2015 год из " +
                        "1973-го",
                LocalDate.of(1973, 12, 18),
                87);
        films.create(film);
        films.update(newOne);
        assertEquals(87, films.getAllFilms().get(0).getDuration());
    }

    @Test
    void FileNameTest() {
        film.setName("");
        assertThrows(ValidationException.class, () -> films.create(film));
    }

    @Test
    void сheckTheMovieDescriptionTest() {
        film.setDescription("Из боевого вылета возвращаются лётчики-истребители второй эскадрильи гвардейского истребительного авиационного полка. Нет только её командира — Героя Советского Союза гвардии капитана Титаренко по прозвищу «Маэстро». Когда уже все, кроме его механика Макарыча, перестали ждать, ведь топливо в баках кончилось более сорока минут назад, на лётное поле сел «Мессершмитт», пилотируемый Титаренко. Его действительно сбили за линией фронта, но атаковавшая в тот момент пехота выручила лётчика, а на аэродроме подскока ему подарили трофей.\n" +
                "\n" +
                "На следующий день в полку распределяют по эскадрильям только что прибывшее пополнение. Несколько новичков, среди которых лейтенант Александров и младшие лейтенанты Щедронов и Сагдуллаев, просятся в знаменитую вторую эскадрилью. Титаренко спрашивает каждого об их музыкальных талантах: вторая эскадрилья известна как «Поющая» и после боевой работы превращается в самодеятельный оркестр, где Титаренко выступает дирижёром. Щедронов напевает песню «Смуглянка» и получает соответствующее прозвище.\n" +
                "\n" +
                "Едва познакомившись с пополнением, «старики» со словами «На ваш век хватит!» вылетают на перехват большой группы немецких бомбардировщиков. Сразу в бой новичков не берут: в Оренбургском лётном училище их готовили по ускоренной программе, и им ещё надо доучиваться летать и учиться воевать.\n" +
                "\n" +
                "На аэродром базирования возвращаются все, но Маэстро в гневе: уже не в первый раз его ведомый, старший лейтенант Скворцов, вышел из боя без приказа. После серьёзного разговора выясняется, что после того, как Скворцов в ходе Курской битвы над Понырями столкнулся в лобовой атаке с немецким истребителем и лишь чудом уцелел сам, ведомый «Маэстро» подсознательно боится боя. Скворцов просит списать его из авиации, но Титаренко сжигает его рапорт, давая другу шанс исправить ситуацию.\n" +
                "\n" +
                "В перерывах между вылетами вторая эскадрилья репетирует музыкальные номера; даже питающий отвращение к музыке Александров берётся исполнять партию бубна, а вскоре начинает руководить репетициями вместо комэска.\n" +
                "\n" +
                "Новички начинают летать. После того как Александров разбивает самолёт при посадке, командир отчитывает его, но Александров как ни в чём не бывало отправляется ловить кузнечиков. Титаренко отстраняет Александрова от полётов на неопределённое время («Назначить дежурным, вечным дежурным по аэродрому!»). За Александровым прочно закрепляется прозвище «Кузнечик».\n" +
                "\n" +
                "Титаренко на трофейном «Мессершмитте» улетает на разведку. В его отсутствие на аэродроме вынужденную посадку совершает лёгкий ночной бомбардировщик — биплан У-2, пилотируемый лётчицами Зоей и Машей. Сагдуллаев с первого взгляда влюбляется в Машу и получает от товарищей прозвище «Ромео».\n" +
                "\n" +
                "Вернувшийся из разведвылета Маэстро подтверждает сведения партизан о большой группе немецких танков. Когда пополнение (кроме Кузнечика) было готово к первому бою, Титаренко отправляется на повторную разведку (немцы замаскировали свои танки под копны и сараи), но на обратном пути его сбивают. Солдаты-пехотинцы, принявшие Титаренко за немца, который любил стрелять по медсанбату, устраивают ему «горячую встречу», но вовремя понимают, что фашист не стал бы давать сдачи избивающим его советским солдатам. Вернувшись в полк на лошади, Титаренко узнаёт от Макарыча, что погиб Смуглянка. Со своим ведущим он отрабатывал слётанность в паре и над аэродромом был сбит немецкими «бубновыми» «Фоккерами».\n" +
                "\n" +
                "Через некоторое время Ромео признаётся Маше в любви.");
        assertThrows(ValidationException.class, () -> films.create(film));

    }

    @Test
    void ReleaseDateTest() {
        film.setReleaseDate(LocalDate.of(1777, 12, 27));
        assertThrows(ValidationException.class, () -> films.create(film));
    }

    @Test
    void MovieDurationTest() {
        film.setDuration(-45);
        assertThrows(ValidationException.class, () -> films.create(film));
    }

    @Test
    void UserCreationTest() {
        users.create(user);
        assertEquals(1, users.getAllUsers().size());
    }

    @Test
    void UpdateUserTest() {
        User newOne = new User(
                1L, new HashSet<>(),
                "Golov@yandex.ru",
                "pupok",
                "Roma",
                LocalDate.of(1987, 5, 25));
        users.create(user);
        users.update(newOne);
        assertEquals("Roma", users.getAllUsers().get(0).getName());
    }

    @Test
    void EmailTest() {
        user.setEmail("Saha ru");
        assertThrows(ValidationException.class, () -> users.create(user));
    }

    @Test
    void LoginTest() {
        user.setLogin("Lo gin!");
        assertThrows(ValidationException.class, () -> users.create(user));
    }

    @Test
    void DateBirthTest() {
        user.setBirthday(LocalDate.of(2059, 6, 6));
        assertThrows(ValidationException.class, () -> users.create(user));
    }
}
