package uz.nova.novastore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nova.novastore.entity.CategoryEntity;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryEntity , UUID> {
}
