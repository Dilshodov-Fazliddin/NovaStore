package uz.nova.novastore.entity.booking;

import jakarta.persistence.Entity;
import lombok.*;
import uz.nova.novastore.entity.BaseEntity;
import uz.nova.novastore.entity.product.ProductEntity;

import java.util.UUID;

@Entity(name = "booking")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingEntity extends BaseEntity {
    private UUID user;
    private UUID customer;
    private Double price;
    private ProductEntity product;
}
