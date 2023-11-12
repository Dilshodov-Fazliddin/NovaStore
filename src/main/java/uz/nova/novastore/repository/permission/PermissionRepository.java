package uz.nova.novastore.repository.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nova.novastore.entity.user.PermissionEntity;

import java.util.UUID;
@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, UUID> {
}
