package uz.nova.novastore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.nova.novastore.entity.tmp.BaseEntity;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookingEntity extends BaseEntity {
    @ManyToOne
    private UserEntity client;
    private Double quantity;
    @ManyToOne
    private ProductEntity product;
    private Boolean isSold;
}
