package ru.yandex.practicum.filmorate.exception;

public class ExceptionInvalidRequest extends RuntimeException {
    public ExceptionInvalidRequest(String message) {
        super(message);
    }
}
