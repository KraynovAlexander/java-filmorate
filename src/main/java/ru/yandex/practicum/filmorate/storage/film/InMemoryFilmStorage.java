package ru.yandex.practicum.filmorate.storage.film;


import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmIdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Long, Film> filmsMap = new HashMap<>();
    private long id = 1;

    private long getNewId() {
        return id++;
    }

    private ValidaterFilm validater = new ValidaterFilm();

    @Override
    public Film create(Film film) {
        validater.validateFilm(film);
        film.setId(getNewId());
        return filmsMap.put(film.getId(), film);
    }

    @Override
    public Film update(Film film) {
        validater.validateFilm(film);
        return filmsMap.put(film.getId(), film);
    }

    @Override
    public void delete(Film film) {
        if (!filmsMap.containsKey(film.getId())) throw new FilmIdException("Фильма с таким id не существует.");
        filmsMap.remove(film.getId());
    }

    @Override
    public ArrayList<Film> getAllFilms() {
        return new ArrayList<>(filmsMap.values());
    }

    @Override
    public Film findById(long id) {
        if (!filmsMap.containsKey(id)) throw new FilmIdException("Фильма с таким id не существует.");
        return filmsMap.get(id);
    }


}
