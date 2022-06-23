package ru.yandex.practicum.filmorate.filmorate.controller;

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
public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addUserTest() throws Exception {
        mockMvc.perform(
                        post("/users")
                                .content("{\n" +
                                        "  \"login\": \"Goga\",\n" +
                                        "  \"name\": \" Про двух братьев\",\n" +
                                        "  \"email\": \"Krainov@mail.ru\",\n" +
                                        "  \"birthday\": \"1987-07-10\"\n" +
                                        "}" +
                                        "}")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1," +
                        "\"email\":\"Krainov@mail.ru\"," +
                        "\"login\":\"Goga\"," +
                        "\"name\":\" Про двух братьев\"," +
                        "\"birthday\":\"1987-07-10\",\"friends\":[]}")));
        ;
    }

    @Test
    void addingUserWithWrongMail() throws Exception {
        mockMvc.perform(
                post("/users")
                        .content("{\n" +
                                "  \"login\": \"Goga\",\n" +
                                "  \"name\": \" Про двух братьев\",\n" +
                                "  \"email\": \"mail.ru\",\n" +
                                "  \"birthday\": \"1987-07-10\"\n" +
                                "}" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void addingUserWithIncorrectName() throws Exception {
        mockMvc.perform(
                post("/users")
                        .content("{\n" +
                                "  \"login\": \"Goga qwerty\",\n" +
                                "  \"name\": \" Про двух братьев\",\n" +
                                "  \"email\": \"Krainov@mail.ru\",\n" +
                                "  \"birthday\": \"1987-07-10\"\n" +
                                "}" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void addingUserWithEmptyUsername() throws Exception {
        mockMvc.perform(
                post("/users")
                        .content("{\n" +
                                "  \"login\": \"\",\n" +
                                "  \"name\": \" Про двух братьев\",\n" +
                                "  \"email\": \"Krainov@mail.ru\",\n" +
                                "  \"birthday\": \"1987-07-10\"\n" +
                                "}" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void addingUserWithEmptyName() throws Exception {
        mockMvc.perform(
                        post("/users")
                                .content("{\n" +
                                        "  \"login\": \"Goga\",\n" +
                                        "  \"name\": \"\",\n" +
                                        "  \"email\": \"Krainov@mail.ru\",\n" +
                                        "  \"birthday\": \"1987-07-10\"\n" +
                                        "}" +
                                        "}")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"name\":\"Goga\"")));
    }

    @Test
    void addingUserWithFutureBirthday() throws Exception {
        mockMvc.perform(
                post("/users")
                        .content("{\n" +
                                "  \"login\": \"Goga\",\n" +
                                "  \"name\": \"est Про двух братьев\",\n" +
                                "  \"email\": \"Krainov@mail.ru\",\n" +
                                "  \"birthday\": \"2946-08-20\"\n" +
                                "}" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
}
