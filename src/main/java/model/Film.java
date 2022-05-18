package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;


@Data
@AllArgsConstructor
public class Film {
    private int id;
    @NonNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    @Size(max = 200)
    private String description;
    @NonNull
    private LocalDate releaseDate;
    @NonNull
    private Duration duration;
}
