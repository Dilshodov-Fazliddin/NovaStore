package uz.nova.novastore.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nova.novastore.domain.request.ProductEntityForFront;
import uz.nova.novastore.entity.CategoryEntity;
import uz.nova.novastore.entity.ProductEntity;


import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntityForFront>findProductEntitiesByCategory(CategoryEntity category, Pageable pageable);
    List<ProductEntity>searchAllByCategory(CategoryEntity category,Pageable pageable);
}
