package uz.nova.novastore.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import uz.nova.novastore.entity.BaseEntity;

@Entity(name = "permission")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PermissionEntity extends BaseEntity {
    @Column(unique = true)
    private String permission;
}
