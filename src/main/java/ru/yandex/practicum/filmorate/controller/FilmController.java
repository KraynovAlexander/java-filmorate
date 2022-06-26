package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmIdException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }



    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Запрос на добавление фильма {}", film);
        return filmService.addFilm(film);
    }
    @DeleteMapping("/{id}/like/{userId}")
    public void deleteMapping(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Запрос от пользователя c  id = {} удалить лайк фильма с  id = {}", userId, id);
        filmService.removeLike(id, userId);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Запрос на обновление фильма {}", film);
        return filmService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Запрос от пользователя с id = {} поставить лайк на фильм с id = {}", userId, id);
        filmService.addLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> popularFilms(@RequestParam(required = false) Integer count) {
        log.info("Запросить лучьшие фильмы, count = {}", count);
        if (count == null) count = 10;
        System.out.println(count);
        return filmService.getFilmsByRating(count);
    }
    @GetMapping
    public Collection<Film> findAll() {
        log.info("Запросить все фильмы");
        return filmService.getFilms();
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Long id) {
        log.info("Запросить фильм по id = {}", id);
        return filmService.getFilmById(id);
    }
}


