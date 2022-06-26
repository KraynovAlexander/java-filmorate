package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserStorage {
    User create(User user);

    User update(User user);
    Collection<User> getAll();
    User delete(User user);
    Optional<User> getById(Long id);
}
