package ru.yandex.practicum.filmorate.storage.user;


import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Long, User> usersMap = new HashMap<>();

    private Long id = 1L;

    private ValidaterUser validaterUser = new ValidaterUser();

    @Override
    public User create(User user) {
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        validaterUser.validateUser(user);
        user.setId(id++);
        return usersMap.put(user.getId(), user);
    }


    @Override
    public User update(User user) {
        validaterUser.validateUser(user);
        return usersMap.put(user.getId(), user);
    }


    @Override
    public ArrayList<User> getAll() {
        return new ArrayList<>(usersMap.values());
    }


    @Override
    public User findById(Long id) {
        if (!usersMap.containsKey(id)) {
            throw new UserException("Пользователя с таким id не существует.");
        }
        return usersMap.get(id);
    }


    @Override
    public void delete(User user) {
        if (!usersMap.containsKey(user.getId()))
            throw new UserException("Пользователя с таким id не существует.");
        usersMap.remove(user.getId());
    }
}
