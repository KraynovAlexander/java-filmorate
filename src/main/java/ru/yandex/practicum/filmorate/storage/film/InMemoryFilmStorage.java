package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmIdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@Component("inMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Film create(Film film) {
        films.put(film.getId(), film);
        log.info("Добавлен новый фильм: {}", film);
        return film;
    }

    @Override
    public Film update(Film film) {
        Long id = film.getId();
        if (!films.containsKey(id))
            throw new FilmIdException(String.format("Попытка обновить пленку с отсутствующим id = %d", id));
        films.put(id, film);
        log.info("Фильм {} был успешно обновлен", film);
        return film;
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }

    @Override
    public Optional<Film> getById(Long id) {
        return Optional.of(films.get(id));
    }

    @Override
    public Film delete(Film film) {
        if (films.containsKey(film.getId())) {
            log.info("Фильм {} был удален", film);
            return films.remove(film.getId());
        }
        else throw new FilmIdException(String.format("Попытка удалить пленку с отсутствующим id = %d", film.getId()));
    }
}
