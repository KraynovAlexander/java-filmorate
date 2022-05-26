package ru.yandex.practicum.filmorate.service.film;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService  {
    private final FilmStorage filmStorage;

    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void like(Long id, Long userId) {
        filmStorage.findById(id).addLike(userId);
        log.debug("Лайков у фильма было {} стало {} лайков", filmStorage.findById(id).getName(),
                filmStorage.findById(id).getLikes().size());
    }

    public void unlike(Long id, Long userId) {
        filmStorage.findById(id).remoteLike(userId);
        log.debug("Лайков у фильма было {} стало {} лайков", filmStorage.findById(id).getName(),
                filmStorage.findById(id).getLikes().size());
    }

    public List<Film> getTopFilms(Long count) {
        return filmStorage.getAllFilms().stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}