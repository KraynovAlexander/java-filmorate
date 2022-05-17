package controller;

import lombok.extern.slf4j.Slf4j;
import model.Film;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

 @RestController
 @Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;
    private final Validator Validator = new Validator();


     @PostMapping(value = "/films")
     public Film addFilm(@RequestBody Film film) {
         Validator.filmValidator(film);
         film.setId(++id);
         films.put(id, film);
         log.debug("Фильм добавлен, всего фильмов = {}", films.size());

         return film;
     }

     @PutMapping(value = "/films")
     public Film updateFilm(@RequestBody Film film) {
         Validator.filmValidator(film);
         films.put(film.getId(), film);
         log.debug("Фильм успешно изменен, всего фильмов = {}", films.size());

         return film;
     }

    @GetMapping("/films")
    public Collection<Film> getFilms() {
        log.debug("Всего фильмов = {}", films.size());
        return films.values();
    }

}
