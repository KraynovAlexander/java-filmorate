package ru.yandex.practicum.filmorate;

import controller.FilmController;
import controller.UserController;
import exception.ValidationException;
import model.Film;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


//@SpringBootTest
class FilmorateApplicationTests {
	UserController userController;
	FilmController filmController;
	User user;
	Film film;

	@BeforeEach
	public void beforeEach() {
		userController = new UserController();
		filmController = new FilmController();
	}

	@Test
	void UserAdd() {
		user = new User(1,
				"Shyra@yandex.ru",
				"Шурка",
				"Саша",
				LocalDate.of(2018, 5, 30));
		userController.add(user);

		assertEquals(user.getEmail(),
				"Shyra@yandex.ru");

	}

	@Test
	void emptyAndreMail() {
		user = new User(1,
				"  ",
				"Шурка",
				"Саша",
				LocalDate.of(2018, 5, 30));
		ValidationException thrown = assertThrows(ValidationException.class, () -> userController.add(user));

		assertEquals("Адрес не может быть нулевым", thrown.getMessage());
	}

	@Test
	void addressMustContainLetters() {
		user = new User(1,
				"Shyra1yandex.ru",
				"Шурка",
				"Саша",
				LocalDate.of(2018, 5, 30));
		ValidationException thrown = assertThrows(ValidationException.class, () -> userController.add(user));
		assertEquals("Невалидный Email", thrown.getMessage());
	}

	@Test
	void loginMayBeEmpty() {
		user = new User(1,
				"Kapa@yandex.ru",
				"  ",
				"Саша",
				LocalDate.of(2018, 5, 30));
		ValidationException thrown = assertThrows(ValidationException.class, () -> userController.add(user));

		assertEquals("Пустой Логин или введен пробел", thrown.getMessage());

		user = new User(1,
				"Kapa@yandex.ru",
				"Кошка Саша",
				"Саша",
				LocalDate.of(2018, 5, 30));
		thrown = assertThrows(ValidationException.class, () -> userController.add(user));

		assertEquals("Пустой Логин или введен пробел", thrown.getMessage());

	}

	@Test
	void emptyName() {
		user = new User(1,
				"Kapa@yandex.ru",
				"Шурка",
				" ",
				LocalDate.of(2018, 5, 30));
		userController.add(user);
		assertEquals(user.getName(), user.getLogin());

	}

	@Test
	void dateBirthFuturePresent() {
		ValidationException thrown = assertThrows(ValidationException.class, () -> userController.add(new User(1,
				"Kapa@yandex.ru",
				"Шурка",
				" ",
				LocalDate.now())));
		assertEquals("Дата рождения не может быть сегодняшней или будущей", thrown.getMessage());

		thrown = assertThrows(ValidationException.class, () -> userController.add(new User(1,
				"Kapa@yandex.ru",
				"Шурка",
				" ",
				LocalDate.now().plusWeeks(20))));
		assertEquals("Дата рождения не может быть сегодняшней или будущей", thrown.getMessage());
	}


	@Test
	void MovieAdd() {
		film = new Film(1,
				"Веселые ребята",
				"Советская комедия",
				LocalDate.of(1985, 5, 30),
				Duration.ofMinutes(120));
		filmController.add(film);

		assertEquals(film.getName(), "Веселые ребята");
	}

	@Test
	void titleTheFilmCannotEmpty() {
		film = new Film(1,
				"",
				"Советская комедия",
				LocalDate.of(1985, 5, 30),
				Duration.ofMinutes(120));
		ValidationException thrown = assertThrows(ValidationException.class, () -> filmController.add(film));
		assertEquals("Название не может быть пустым", thrown.getMessage());
	}

	@Test
	void descriptionToExceed200() {
		film = new Film(1, "Веселые ребята",
				"Костя Потехин работает пастухом в деревне, при этом он талантливо поёт, \n"+
						"играет на дудочке и скрипке. Недалеко от деревни он случайно встречается с девушкой из \n " +
						"богатой семьи Еленой, которая по ошибке принимает его за известного дирижёра.\n" +
						" Елена говорит, что она певица и приглашает «дирижёра» на вечеринку в пансион,\n " +
						"в котором она отдыхает вместе с матерью. Костя влюбляется в Елену, но в него самого \n" +
						"тайком влюблена домработница Елены — Анюта. ", LocalDate.of(1985, 5, 30), Duration.ofMinutes(120));
		ValidationException thrown = assertThrows(ValidationException.class, () -> filmController.add(film));
		assertEquals("Описание не должно превышать  200 символов", thrown.getMessage());
	}

	@Test
	void releaseDateEarlierThan1985() {
		film = new Film(1,
				"Веселые ребята",
				"Советская комедия",
				LocalDate.of(1884, 5, 30),
				Duration.ofMinutes(120));
		ValidationException thrown = assertThrows(ValidationException.class, () -> filmController.add(film));
		assertEquals("Дата выхода не может быть раньше 28 декабря 1895 года", thrown.getMessage());
	}


	@Test
	void theDurationTheFilmCannotMinus() {
		film = new Film(1,
				"Веселые ребята",
				"Советская комедия",
				LocalDate.of(1984, 5, 30),
				Duration.ofMinutes(-120));
		ValidationException thrown = assertThrows(ValidationException.class, () -> filmController.add(film));
		assertEquals("Продолжительность фильма не может быть отрицательной", thrown.getMessage());
	}

}