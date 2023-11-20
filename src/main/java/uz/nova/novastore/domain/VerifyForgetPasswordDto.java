package uz.nova.novastore.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyForgetPasswordDto {
    private String emailCode;
    private String email;
    private String newPassword;
}
