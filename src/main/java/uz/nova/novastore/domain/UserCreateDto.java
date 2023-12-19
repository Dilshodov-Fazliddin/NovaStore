package uz.nova.novastore.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserCreateDto {
    @NotNull(message = "Insert your name")
    private String firstname;
    @NotNull(message = "Insert your email")
    private String email;
    @NotNull(message = "Insert your password")
    private String password;
    @NotNull(message = "Insert your birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    @NotNull(message = "Insert your surname")
    private String lastname;
}
