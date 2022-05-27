package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/users")

public class UserController {
    private final InMemoryUserStorage userStorage;
    private final UserService userService;

    @Autowired
    public UserController(InMemoryUserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }


    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        if (id < 0 || friendId < 0) throw new UserException("Отрицательным не может быть id.");
        userService.addFriend(id, friendId);
    }


    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {
        userStorage.create(user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException {
        if (user.getId() < 0) throw new UserException("Отрицательным не может быть id.");
        userStorage.update(user);
        return user;
    }
    @GetMapping
    public List<User> getAll() { return userStorage.getAll(); }


    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        if (id == null || id < 0) throw new UserException("Отрицательным не может быть id.");
        return userStorage.findById(id);
    }


    @DeleteMapping
    public void delete(@RequestBody User user) {
        userStorage.delete(user);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriendsList(@PathVariable Long id) {
        if (id < 0) throw new UserException("Отрицательным не может быть id.");
        return userService.getFriends(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        if (id < 0 || friendId < 0) throw new UserException("Отрицательным не может быть id.");
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.makeFriends(id, otherId);
    }
}