package uz.nova.novastore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.apache.catalina.User;
import uz.nova.novastore.entity.tmp.BaseEntity;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavouritesEntity extends BaseEntity {
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private ProductEntity product;
}
