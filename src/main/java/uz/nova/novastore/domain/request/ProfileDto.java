package uz.nova.novastore.domain.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProfileDto {
    private String firstname;
    private String lastName;
    private Integer age;
    private Boolean isEnabled;

}
