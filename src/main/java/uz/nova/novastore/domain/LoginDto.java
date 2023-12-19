package uz.nova.novastore.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDto {
    @NotNull(message = "Insert your email")
    private String email;
    @NotNull(message = "Insert your password")
    private String password;
}
