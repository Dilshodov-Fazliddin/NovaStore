package uz.nova.novastore.domain;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserCreateDto {
    @NotNull(message = "Insert name")
    private String name;
    @NotNull(message = "Insert email")
    private String email;
    @NotNull(message = "Insert password")
    private String password;
    @DateTimeFormat(pattern = "YY-mm-dd")
    private LocalDate birthDay;
    @NotNull(message = "Insert surname")
    private String surname;
}
