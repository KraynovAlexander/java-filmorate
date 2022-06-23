package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.*;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleFilmNotFoundException(final FilmIdException e) {
        log.warn(e.getMessage());
        return new ResponseError(
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleUserNotFoundException(final UserException e) {
        log.warn(e.getMessage());
        return new ResponseError(
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleGenreNotFoundException(final GenreException e) {
        log.warn(e.getMessage());
        return new ResponseError(
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleMPANotFoundException(final MPAException e) {
        log.warn(e.getMessage());
        return new ResponseError(
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleValidationException(final ValidationException e) {
        log.warn(e.getMessage());
        return new ResponseError(
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleRunTimeException(final RuntimeException e) {
        log.warn(e.getMessage());
        return new ResponseError(
                e.getMessage()
        );
    }
}
