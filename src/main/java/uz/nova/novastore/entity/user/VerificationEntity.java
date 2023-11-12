package uz.nova.novastore.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.nova.novastore.entity.BaseEntity;

@Entity(name = "verification")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VerificationEntity extends BaseEntity {

    @ManyToOne
    private UserEntity user;

    private Integer code;
}
