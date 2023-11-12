package uz.nova.novastore.entity.user;

import jakarta.persistence.*;
import lombok.*;
import uz.nova.novastore.entity.BaseEntity;

import java.util.List;

@Entity(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRole extends BaseEntity {
    @Column(unique = true)
    private String role;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<PermissionEntity> permissions;
}
