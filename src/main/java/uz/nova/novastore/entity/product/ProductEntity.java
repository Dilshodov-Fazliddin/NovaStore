package uz.nova.novastore.entity.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import uz.nova.novastore.entity.BaseEntity;

@Entity(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductEntity extends BaseEntity {
    private String name;
    private String brand;
    private Category category;
    private Integer ranking;
    private Double price;
    private String color;
    @Column(length = 135)
    private String description;
}
