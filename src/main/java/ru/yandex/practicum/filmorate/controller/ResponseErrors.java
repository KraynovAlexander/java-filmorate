package ru.yandex.practicum.filmorate.controller;


public class ResponseErrors {
    private final String error;

    public ResponseErrors(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}

