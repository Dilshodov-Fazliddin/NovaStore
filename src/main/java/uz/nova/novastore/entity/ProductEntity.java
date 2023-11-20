package uz.nova.novastore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.nova.novastore.entity.tmp.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @ManyToOne
    private UserEntity customer;
    private String brand;
    @ManyToOne
    private CategoryEntity category;
    private Double price;
    private String color;
    @Column(length = 135)
    private String description;
}
