package controller;

import lombok.extern.slf4j.Slf4j;
import model.User;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;
    private Validator Validator = new Validator();


    @PostMapping()
    public User add(@RequestBody User user) {
        Validator.userValidator(user);
        user.setId(++id);
        users.put(id, user);
        log.debug("Пользователь добавлен, всего пользователей = {}", users.size());

        return user;

    }


    @PutMapping()
    public User update(@RequestBody User user) {
        Validator.userValidator(user);
        users.put(user.getId(), user);
        log.debug("Пользователь успешно изменен, всего фильмов = {}", users.size());
        return user;
    }


    @GetMapping()
    public Collection<User> getAll() {
        return users.values();
    }

    @DeleteMapping()
    public void clear() {
        users.clear();
        id = 0;
        log.debug("Список пользователь очищен = {}", users.size());
    }
}
