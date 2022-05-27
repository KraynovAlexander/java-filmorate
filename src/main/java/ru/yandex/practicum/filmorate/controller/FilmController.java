package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmIdException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")

public class FilmController {
    private final FilmService filmService;
    private final InMemoryFilmStorage filmStorage;

    @Autowired
    public FilmController(FilmService filmService, InMemoryFilmStorage filmStorage) {
        this.filmService = filmService;
        this.filmStorage = filmStorage;
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        filmStorage.create(film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
        if (film.getId() < 0) throw new FilmIdException("Отрицательным не может быть id.");
        filmStorage.update(film);
        return film;
    }


    @PutMapping("/{id}/like/{userId}")
    public void like(@PathVariable Long id, @PathVariable Long userId) {
        if (id < 0 || userId < 0) throw new ValidationException("Отрицательным не может быть id.");
        filmService.like(id, userId);
    }


    @GetMapping
    public ArrayList<Film> getAll() {
        return filmStorage.getAll();
    }


    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Long id) {
        if (id < 0) throw new FilmIdException("Отрицательным не может быть id.");
        return filmStorage.findById(id);
    }


    @DeleteMapping
    public Film delete(@RequestBody Film film) {
        filmStorage.delete(film);
        return film;
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(defaultValue = "10") Long count) {
        return filmService.getTopFilms(count);
    }


    @DeleteMapping("/{id}/like/{userId}")
    public void unlike(@PathVariable Long id, @PathVariable Long userId) {
        if (id < 0 || userId < 0) {
            throw new FilmIdException("Отрицательным не может быть id.");
        }
        filmService.unlike(id, userId);
    }
}