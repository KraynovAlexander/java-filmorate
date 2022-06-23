package ru.yandex.practicum.filmorate.service.film;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmIdException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikesStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService  {
    private final FilmStorage filmStorage;
    private final LikesStorage likesStorage;
    private Long id = 0L;
    private static final LocalDate releaseDate = LocalDate.of(1895, 12, 28);

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       LikesStorage likesStorage) {
        this.filmStorage = filmStorage;
        this.likesStorage = likesStorage;
    }

    private Long generateId() {
        return ++id;
    }

    public Film addFilm(Film film) {
        if (film.getReleaseDate().isBefore(releaseDate))
            throw new ValidationException("Попытка добавить фильм " +
                    "с датой выпуска до 28-12-1895");
        filmStorage.create(film);
        return film;
    }

    public Film updateFilm(Film film) {
        filmStorage.update(film);
        Film filmReturn = filmStorage.getById(film.getId()).orElseThrow(
                () ->  new FilmIdException(String.format("Запрос фильма с отсутствующим id = %d", id)));
        if (film.getGenres() == null) filmReturn.setGenres(null);
        else if (film.getGenres().isEmpty()) filmReturn.setGenres(new HashSet<>());
        return filmReturn;
    }

    public Collection<Film> getFilms() {
        return filmStorage.getAll();
    }

    public Film getFilmById(Long id) {
        return filmStorage.getById(id)
                .orElseThrow(() ->  new FilmIdException(String.format("Запрос фильма с отсутствующим id = %d", id)));
    }

    public void addLike(Long id, Long userId) {
        likesStorage.addLike(id, userId);
        log.info("User id = {} лайки фильма с id = {}", userId, id);
    }

    public void removeLike(Long id, Long userId) {
        likesStorage.removeLike(id, userId);
        log.info("User id = {} удалить лайки фильма с id = {}", userId, id);
    }

    public List<Film> getFilmsByRating(int count) {
        return likesStorage.getPopular(count);
    }
}