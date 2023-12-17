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
    @NotNull(message = "Insert name")
    private String firstname;
    @NotNull(message = "Insert email")
    private String email;
    @NotNull(message = "Insert password")
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    @NotNull(message = "Insert surname")
    private String lastname;
}
