package uz.nova.novastore.domain.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserCreateDto {
    private String name;
    private String email;
    private String username;
    private String password;
    private Integer age;
}
