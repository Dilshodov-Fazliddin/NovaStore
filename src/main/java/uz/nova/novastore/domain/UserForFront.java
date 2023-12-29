package uz.nova.novastore.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserForFront {
    private String firstname;
    private String lastName;
    private Integer age;
    private Boolean isEnabled;

}
