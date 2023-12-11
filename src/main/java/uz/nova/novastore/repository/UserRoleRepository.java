package uz.nova.novastore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.nova.novastore.domain.RoleDto;
import uz.nova.novastore.entity.RoleEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<RoleEntity, UUID> {
    RoleEntity findByName(String name);
    void deleteById(UUID id);
}
