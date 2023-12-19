package uz.nova.novastore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nova.novastore.entity.CategoryEntity;

import java.util.UUID;
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity , UUID> {
    CategoryEntity findByName(String name);
}
