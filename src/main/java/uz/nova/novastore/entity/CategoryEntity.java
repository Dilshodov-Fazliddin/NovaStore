package uz.nova.novastore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import uz.nova.novastore.entity.tmp.BaseEntity;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CategoryEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany
    private List<CategoryEntity> category;
}
