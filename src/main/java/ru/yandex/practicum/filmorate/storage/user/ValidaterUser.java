package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Objects;

public class ValidaterUser {

    public void validateUser(User user) throws ValidationException {
        if (user.getEmail().contains(" ") || !user.getEmail().contains("@")) {
            throw new ValidationException("Невалидный Email!");
        } else if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Пустой Логин или введен пробел!");
        } else if (Objects.isNull(user.getName()) || user.getName().isBlank()) {
            throw new ValidationException("Имя не может быть пустым!");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть сегодняшней или будущей!");
        }
    }
}
