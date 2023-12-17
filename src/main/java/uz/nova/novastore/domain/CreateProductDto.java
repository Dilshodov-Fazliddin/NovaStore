package uz.nova.novastore.domain;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateProductDto {
    @Column(nullable = false)
    private String name;
    private String brand;
    private Double price;
    private String color;
    @Column(length = 135)
    private String description;
}
