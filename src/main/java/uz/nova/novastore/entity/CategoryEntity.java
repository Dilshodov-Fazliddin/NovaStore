package uz.nova.novastore.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.nova.novastore.entity.tmp.BaseEntity;

import java.util.List;
@Entity(name = "categories")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private List<CategoryEntity> category;
}
