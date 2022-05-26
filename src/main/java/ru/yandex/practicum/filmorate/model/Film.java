package ru.yandex.practicum.filmorate.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import java.time.LocalDate;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
public class Film {

    private Long id;

    private Set<Long> likes;

    @NotBlank
    private String name;

    @Size(max = 200)
    private String description;

    @PastOrPresent
    private LocalDate releaseDate;

    @Positive
    private int duration;


    public Film() {
        likes = new HashSet<>();
    }


    public void remoteLike(Long id) {
        likes.remove(id);
    }



    public void addLike(Long id) {
        likes.add(id);
    }
}
