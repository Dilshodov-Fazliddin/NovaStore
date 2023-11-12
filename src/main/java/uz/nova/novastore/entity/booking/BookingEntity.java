package uz.nova.novastore.entity.booking;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.nova.novastore.entity.BaseEntity;
import uz.nova.novastore.entity.product.ProductEntity;

import java.util.UUID;

@Entity(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingEntity extends BaseEntity {
    private UUID owner;
    private UUID customer;
    private Double price;
    @ManyToOne
    private ProductEntity product;
}
