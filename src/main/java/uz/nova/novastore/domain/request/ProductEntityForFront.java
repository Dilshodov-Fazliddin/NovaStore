package uz.nova.novastore.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.nova.novastore.entity.CategoryEntity;
import uz.nova.novastore.entity.enums.Category;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductEntityForFront {
    private String name;
    private String customer;
    private String brand;
    private String category;
    private Double price;
    private String description;

}
