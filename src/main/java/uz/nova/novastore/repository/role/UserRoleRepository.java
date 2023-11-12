package uz.nova.novastore.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nova.novastore.entity.user.UserRoleEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UUID> {
    Optional<UserRoleEntity>findUserRoleEntitiesByName(String name);
}
