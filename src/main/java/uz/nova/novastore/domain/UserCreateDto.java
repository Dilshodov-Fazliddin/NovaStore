package uz.nova.novastore.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserCreateDto {
    @Size(min = 4,max = 30)
    @NotNull(message = "Insert your name")
    private String firstname;

    @NotNull(message = "Insert your email")
    private String email;

    @Size(min = 4,max = 35)
    @NotNull(message = "Insert your password")
    private String password;

    @NotNull(message = "Insert your birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Size(min = 4,max = 30)
    @NotNull(message = "Insert your surname")
    private String lastname;
}
