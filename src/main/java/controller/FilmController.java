package controller;

import lombok.extern.slf4j.Slf4j;
import model.Film;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;
    private final Validator Validator = new Validator();


    @PostMapping()
    public Film add(@RequestBody Film film) {
        Validator.filmValidator(film);
        film.setId(++id);
        films.put(id, film);
        log.debug("Фильм добавлен, всего фильмов = {}", films.size());

        return film;
    }

    @PutMapping()
    public Film update(@RequestBody Film film) {
        Validator.filmValidator(film);
        films.put(film.getId(), film);
        log.debug("Фильм успешно изменен, всего фильмов = {}", films.size());

        return film;
    }

    @GetMapping()
    public Collection<Film> get() {
        return films.values();
    }

}
