package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.mpa.MPAService;

import java.util.Collection;

@RestController
@Validated
@Slf4j
@RequestMapping("/mpa")
public class MpaController {
    private final MPAService mpaService;

    @Autowired
    public MpaController(MPAService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping
    public Collection<Mpa> findAll() {
        log.info("Запрашивать все mpa");
        return mpaService.getMpa();
    }

    @GetMapping("/{id}")
    public Mpa getGenre(@PathVariable int id) {
        log.info("Запрос mpa с  id = {}", id);
        return mpaService.getMpa(id);
    }
}

