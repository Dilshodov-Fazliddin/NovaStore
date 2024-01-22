package uz.nova.novastore.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.nova.novastore.entity.BookingEntity;
import uz.nova.novastore.entity.RoleEntity;
import uz.nova.novastore.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    UserEntity searchByEmail(String email);

    Boolean existsByEmail(String email);

    List<UserEntity>findByRole(RoleEntity role);

    Optional<UserEntity> findByEmailAndEmailCode(String email, Integer emailCode);
    Integer countUserEntityBy();

    @Query(value = "SELECT COUNT(u) AS disabled_user_count FROM users u WHERE u.is_enabled = false;",nativeQuery = true)
    Integer countUserEntityBlock();

}
