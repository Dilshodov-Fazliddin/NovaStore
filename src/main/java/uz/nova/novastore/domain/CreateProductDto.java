package uz.nova.novastore.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateProductDto {
    @NotNull(message = "Insert  product name")
    private String name;
    @NotNull(message = "Insert brand surname")
    private String brand;
    @NotNull(message = "Insert price")
    private Double price;
    @NotNull(message = "Insert color")
    private String color;
    @NotNull(message = "Insert category")
    private String category;
    @NotNull(message = "Insert  description")
    @Size(max = 135,message = "max size 135 latter")
    private String description;
}
