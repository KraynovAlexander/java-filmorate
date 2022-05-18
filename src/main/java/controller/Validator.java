package controller;

import exception.ValidationException;
import model.Film;
import model.User;

import java.time.LocalDate;

public class Validator {
    protected void userValidator(User user) {
        if (user.getEmail().isBlank() ) {
            throw new ValidationException("Адрес не может быть нулевым");
        }
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Невалидный Email");
        }

        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Пустой Логин или введен пробел");
        }

        if (user.getName().isBlank() || user.getName() == null) {
            user.setName(user.getLogin());
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть сегодняшней или будущей");
        } else if (user.getBirthday().equals(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть сегодняшней или будущей");
        }
    }

    protected void filmValidator(Film film) {
        if (film.getName().isBlank() ) {
            throw new ValidationException("Название не может быть пустым");
        }

        if (film.getDescription().isBlank() || film.getDescription() == null || film.getDescription().length() > 200) {
            throw new ValidationException("Описание не должно превышать  200 символов");
        }

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата выхода не может быть раньше 28 декабря 1895 года");
        }

        if (film.getDuration().getSeconds() < 0) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        }

    }


}
