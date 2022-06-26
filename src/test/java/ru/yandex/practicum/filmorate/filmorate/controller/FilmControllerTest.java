package ru.yandex.practicum.filmorate.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled
public class FilmControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Test
    void addFilm() throws Exception {
        mockMvc.perform(
                post("/films")
                        .content("{\n" +
                                "  \"name\": \"Брат2\",\n" +
                                "  \"description\": \"Про двух братьев\",\n" +
                                "  \"releaseDate\": \"2000-05-11\",\n" +
                                "  \"duration\": 127\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content()
                .string(containsString("{\"id\":1," +
                        "\"name\":\"Брат2\"," +
                        "\"description\":\"Про двух братьев\"," +
                        "\"releaseDate\":\"2000-05-11\"," +
                        "\"duration\":\"PT1M40S\"}")));
    }

    @Test
    void addingMovieWithEmptyName() throws Exception {
        mockMvc.perform(
                post("/films")
                        .content("{\n" +
                                "  \"name\": \"\",\n" +
                                "  \"description\": \"Про двух братьев\",\n" +
                                "  \"releaseDate\": \"2000-05-11\",\n" +
                                "  \"duration\": 127\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void addMovieWithLongDescription() throws Exception {
        mockMvc.perform(
                post("/films")
                        .content("{\n" +
                                "  \"name\": \"Брат2\",\n" +
                                "  \"description\": \"Данилу Багрова и его боевых друзей приглашают поучаствовать в " +
                                "съёмке телепередачи Ивана Демидова «В мире людей» на телеканале «ТВ-6», посвящённой " +
                                "героям Чеченской войны. В коридорах останкинского телецентра Данила встречает Ирину " +
                                "Салтыкову, но не узнаёт в ней знаменитость, поскольку совсем не слушает поп-музыку. " +
                                "После передачи Данила отдыхает со своими друзьями в бане, где один из " +
                                "них — Константин Громов, работающий охранником в Николаевском банке — сообщает о " +
                                "проблеме, возникшей у его брата-близнеца Дмитрия Громова, хоккеиста НХЛ: " +
                                "американский бизнесмен Ричард Мэннис, первоначально согласившийся защитить спортсмена " +
                                "от украинской мафии в США, сейчас при помощи кабального контракта забирает почти все " +
                                "деньги, которые тот зарабатывает. У Мэнниса есть финансовые интересы в России, из-за" +
                                " которых он как раз сейчас приехал в Москву для встречи с руководителем Николаевского " +
                                "банка Валентином Белкиным. Константин собирается сам попросить Белкина поговорить с " +
                                "американцем. Белкин действительно упоминает в беседе с Мэннисом хоккеиста, но тот " +
                                "меняет тему разговора. Тогда Белкин даёт указание помощникам «разобраться» с " +
                                "Константином, чтобы он не вмешивался и не портил его отношения с Мэннисом, но те, " +
                                "неправильно поняв своего шефа, убивают Константина. Данила, выполняя последнюю просьбу" +
                                " своего боевого товарища, спасшего ему жизнь, решает помочь его брату-близнецу. " +
                                "Для этого ему нужно ехать в США и заставить американского бизнесмена вернуть брату " +
                                "убитого друга заработанные им деньги. \",\n" +
                                "  \"releaseDate\": \"2000-05-11\",\n" +
                                "  \"duration\": 127\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void addingMovieWithVeryStrangeReleaseDate() throws Exception {
        mockMvc.perform(
                post("/films")
                        .content("{\n" +
                                "  \"name\": \"Брат2\",\n" +
                                "  \"description\": \"Про двух братьев\",\n" +
                                "  \"releaseDate\": \"1400-03-25\",\n" +
                                "  \"duration\": 127\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void addingMovieWithNegativeDuration() throws Exception {
        mockMvc.perform(
                post("/films")
                        .content("{\n" +
                                "  \"name\": \"Брат2\",\n" +
                                "  \"description\": \"Про двух братьев\",\n" +
                                "  \"releaseDate\": \"2000-05-11\",\n" +
                                "  \"duration\": -127\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
}