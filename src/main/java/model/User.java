package model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
@AllArgsConstructor
public class User {
    private int id;
    @NonNull
    @NotBlank
    @Email
    private String email;
    @NonNull
    @NotBlank
    @Pattern(regexp = "^\\S*$")
    private String login;
    private String name;
    @NonNull
    private LocalDate birthday;
}
