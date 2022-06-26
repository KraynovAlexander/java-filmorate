package ru.yandex.practicum.filmorate.filmorate.controller;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateTests {

    private final UserDbStorage userDbStorage;
    private final FilmDbStorage filmDbStorage;

    @Test
    public void SearchForUserByIdTest() {
        User user = new User(null, "Krainov@mail.ru", "Goga", "Georgi",
                LocalDate.of(1946, 8, 20));
        Long id = userDbStorage.create(user).getId();
        Optional<User> userOptional = userDbStorage.getById(id);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(u ->
                        assertThat(u).hasFieldOrPropertyWithValue("id", id)
                );
    }

    @Test
    public void updateTheUserTest() {
        User user = new User(null, "Krainov@mail.ru", "Goga", "Georgi",
                LocalDate.of(1946, 8, 20));
        User userUpdate = new User(null, "Krainov@mail.ru", "NewGoga", "Georgi",
                LocalDate.of(1946, 8, 20));
        Long id = userDbStorage.create(user).getId();
        userUpdate.setId(id);
        userDbStorage.update(userUpdate);
        Optional<User> userOptional = userDbStorage.getById(id);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(u -> assertThat(u)
                        .hasFieldOrPropertyWithValue("login", "NewGoga"));
    }

    @Test
    public void DeleteUserTest() {
        User user = new User(null, "Krainov@mail.ru", "Goga", "Georgi",
                LocalDate.of(1946, 8, 20));
        userDbStorage.create(user);
        userDbStorage.delete(user);
        Optional<User> userOptional = userDbStorage.getById(1L);
        assertThat(userOptional).isEmpty();
    }

    @Test
    public void findMovieByIdTest() {
        Film film = new Film(null, "name", "description",
                LocalDate.of(1975, 5, 17),
                127);
        film.setMpa(new Mpa(1, null));
        Long id = filmDbStorage.create(film).getId();
        Optional<Film> filmOptional = filmDbStorage.getById(id);

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(f ->
                        assertThat(f).hasFieldOrPropertyWithValue("id", id)
                );
    }

    @Test
    public void updateTest() {
        Film film = new Film(null, "name", "description",
                LocalDate.of(1975, 5, 17),
                127);
        film.setMpa(new Mpa(1, null));
        Film filmUpdate = new Film(null, "NewName", "description",
                LocalDate.of(1975, 5, 17),
                127);
        filmUpdate.setMpa(new Mpa(1, null));
        Long id = filmDbStorage.create(film).getId();
        filmUpdate.setId(id);
        filmDbStorage.update(filmUpdate);
        Optional<Film> filmOptional = filmDbStorage.getById(id);

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(u -> assertThat(u)
                        .hasFieldOrPropertyWithValue("name", "NewName"));
    }

    @Test
    public void filmDeleteTest() {
        Film film = new Film(null, "name", "description",
                LocalDate.of(1975, 5, 17),
                127);
        film.setMpa(new Mpa(1, null));
        filmDbStorage.create(film);
        filmDbStorage.delete(film);
        Optional<Film> filmOptional = filmDbStorage.getById(1L);
        assertThat(filmOptional).isEmpty();
    }

}