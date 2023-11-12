package uz.nova.novastore.entity.product;

import jakarta.persistence.Entity;
import uz.nova.novastore.entity.BaseEntity;

import java.util.UUID;

@Entity(name = "favourites")
public class Favourites extends BaseEntity {
    private ProductEntity product;
    private UUID userId;
}