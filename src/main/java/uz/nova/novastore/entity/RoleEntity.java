package uz.nova.novastore.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.nova.novastore.entity.tmp.BaseEntity;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleEntity extends BaseEntity {
    @Column(unique = true)
    private String name;
}
